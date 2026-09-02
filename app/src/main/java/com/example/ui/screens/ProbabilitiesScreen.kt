package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Team
import com.example.model.TeamProbabilities
import com.example.ui.BrasileiraoViewModel
import com.example.ui.ProbabilitySort
import com.example.ui.components.TeamBadge
import com.example.ui.theme.BlueLiberta
import com.example.ui.theme.BlueSula
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.RedZ4
import com.example.ui.theme.RedZ4Light
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowGold

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun ProbabilitiesScreen(
    viewModel: BrasileiraoViewModel,
    onTeamClick: (Team) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedProbs by viewModel.sortedProbabilities.collectAsState()
    val probSort by viewModel.probSort.collectAsState()

    var showInfoDialog by remember { mutableStateOf(false) }

    val categories = listOf(
        ProbabilitySort.CHAMPION to ("🏆 Título" to YellowGold),
        ProbabilitySort.LIBERTADORES to (" Libertadores" to BlueLiberta),
        ProbabilitySort.SULAMERICANA to ("🌎 Sul-Americana" to BlueSula),
        ProbabilitySort.RELEGATION to ("🔻 Rebaixamento" to RedZ4Light)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // UFMG Mathematics Reference Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = "UFMG",
                            tint = GreenAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PROBABILIDADES MATEMÁTICAS",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = GreenAccent
                        )
                    }

                    Text(
                        text = "Fonte: UFMG",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Cálculos probabilísticos baseados em simulações de Monte Carlo (10.000 iterações) considerando força de ataque, defesa e mando de campo de cada clube.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp
                )
            }
        }

        // Category Selector Tabs
        PrimaryTabRow(
            selectedTabIndex = categories.indexOfFirst { it.first == probSort },
            containerColor = DarkSurface,
            contentColor = GreenAccent,
            divider = { Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(DarkBorderSubtle)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEachIndexed { index, (sort, pair) ->
                val (label, accentColor) = pair
                val isSelected = probSort == sort
                Tab(
                    selected = isSelected,
                    onClick = { viewModel.setProbabilitySort(sort) },
                    text = {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) accentColor else TextSecondary
                        )
                    },
                    modifier = Modifier.testTag("prob_tab_${sort.name.lowercase()}")
                )
            }
        }

        // Probabilities List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("probabilities_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = sortedProbs,
                key = { _, it -> it.teamId }
            ) { rank, prob ->
                val team = viewModel.getTeam(prob.teamId)
                val targetPct = when (probSort) {
                    ProbabilitySort.CHAMPION -> prob.championPct
                    ProbabilitySort.LIBERTADORES -> prob.libertadoresPct
                    ProbabilitySort.SULAMERICANA -> prob.sulamericanaPct
                    ProbabilitySort.RELEGATION -> prob.relegationPct
                }
                val activeColor = categories.find { it.first == probSort }?.second?.second ?: GreenAccent

                ProbabilityRow(
                    rank = rank + 1,
                    team = team,
                    percentage = targetPct,
                    barColor = activeColor,
                    onClick = { onTeamClick(team) }
                )
            }
        }
    }
}

@Composable
private fun ProbabilityRow(
    rank: Int,
    team: Team,
    percentage: Double,
    barColor: Color,
    onClick: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (percentage / 100.0).toFloat().coerceIn(0f, 1f),
        label = "progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rank Number
                Text(
                    text = "${rank}º",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    modifier = Modifier.width(24.dp)
                )

                // Team badge
                TeamBadge(team = team, size = 26.dp)

                Spacer(modifier = Modifier.width(8.dp))

                // Team Name
                Text(
                    text = team.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )

                // Percentage Value
                Text(
                    text = "%.1f%%".format(percentage),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = if (percentage > 0.0) barColor else TextMuted,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Visual Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(DarkSurfaceElevated)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(barColor)
                )
            }
        }
    }
}

