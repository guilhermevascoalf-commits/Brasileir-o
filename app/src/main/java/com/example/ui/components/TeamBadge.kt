package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.Team

@Composable
fun TeamBadge(
    team: Team,
    size: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val primary = Color(team.primaryColor)
    val secondary = Color(team.secondaryColor)

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(primary, secondary)
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (team.logoUrl.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(team.logoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = team.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(size * 0.75f),
                onError = {
                    // Fallback to text initials
                }
            )
        }

        // Inner monogram badge for instant visibility and offline fallback
        Text(
            text = team.code.take(3),
            color = if (team.primaryColor == 0xFF000000L) Color.White else Color.White,
            fontSize = (size.value * 0.32f).sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-0.5).sp
        )
    }
}
