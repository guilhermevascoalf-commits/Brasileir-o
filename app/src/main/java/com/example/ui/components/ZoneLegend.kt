package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TableZone
import com.example.ui.theme.BlueLiberta
import com.example.ui.theme.BlueSula
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.RedZ4

fun getZoneColor(zone: TableZone): Color {
    return when (zone) {
        TableZone.LIBERTADORES_GROUP -> GreenPrimary
        TableZone.LIBERTADORES_QUALIFIERS -> BlueLiberta
        TableZone.SULAMERICANA -> BlueSula
        TableZone.NEUTRAL -> Color.Transparent
        TableZone.RELEGATION -> RedZ4
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ZoneLegend(
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LegendItem(color = GreenPrimary, label = "Libertadores (G4)")
        LegendItem(color = BlueLiberta, label = "Pré-Libertadores (G6)")
        LegendItem(color = BlueSula, label = "Sul-Americana (7º-12º)")
        LegendItem(color = RedZ4, label = "Rebaixamento (Z4)")
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}
