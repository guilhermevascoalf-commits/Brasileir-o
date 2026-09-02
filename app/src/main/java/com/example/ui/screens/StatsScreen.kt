package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
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
import com.example.model.Scorer
import com.example.model.Team
import com.example.ui.BrasileiraoViewModel
import com.example.ui.components.TeamBadge
import com.example.ui.theme.BlueLiberta
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowGold

enum class StatsTab {
    SCORERS, ASSISTS, OVERVIEW
}

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun StatsScreen(
    viewModel: BrasileiraoViewModel,
    onTeamClick: (Team) -> Unit,
    modifier: Modifier = Modifier
) {
    val scorers by viewModel.scorers.collectAsState()
    val overview = remember { viewModel.getLeagueOverview() }
    val standings by viewModel.standings.collectAsState()

    var activeTab by remember { mutableStateOf(StatsTab.SCORERS) }

    val sortedPlayers = remember(scorers, activeTab) {
        when (activeTab) {
            StatsTab.SCORERS -> scorers.sortedByDescending { it.goals }
            StatsTab.ASSISTS -> scorers.sortedByDescending { it.assists }
            StatsTab.OVERVIEW -> scorers
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Tab Selector
        PrimaryTabRow(
            selectedTabIndex = activeTab.ordinal,
            containerColor = DarkSurface,
            contentColor = GreenAccent,
            divider = { Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(DarkBorderSubtle)) }
        ) {
            Tab(
                selected = activeTab == StatsTab.SCORERS,
                onClick = { activeTab = StatsTab.SCORERS },
                text = {
                    Text(
                        "⚽ Artilharia",
                        fontSize = 12.sp,
                        fontWeight = if (activeTab == StatsTab.SCORERS) FontWeight.Bold else FontWeight.Medium,
                        color = if (activeTab == StatsTab.SCORERS) GreenAccent else TextSecondary
                    )
                },
                modifier = Modifier.testTag("tab_scorers")
            )
            Tab(
                selected = activeTab == StatsTab.ASSISTS,
                onClick = { activeTab = StatsTab.ASSISTS },
                text = {
                    Text(
                        "🎯 Assistências",
                        fontSize = 12.sp,
                        fontWeight = if (activeTab == StatsTab.ASSISTS) FontWeight.Bold else FontWeight.Medium,
                        color = if (activeTab == StatsTab.ASSISTS) GreenAccent else TextSecondary
                    )
                },
                modifier = Modifier.testTag("tab_assists")
            )
            Tab(
                selected = activeTab == StatsTab.OVERVIEW,
                onClick = { activeTab = StatsTab.OVERVIEW },
                text = {
                    Text(
                        "📊 Estatísticas Gerais",
                        fontSize = 12.sp,
                        fontWeight = if (activeTab == StatsTab.OVERVIEW) FontWeight.Bold else FontWeight.Medium,
                        color = if (activeTab == StatsTab.OVERVIEW) GreenAccent else TextSecondary
                    )
                },
                modifier = Modifier.testTag("tab_overview")
            )
        }

        if (activeTab == StatsTab.OVERVIEW) {
            // General League Overview & Team Records
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Summary Cards Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OverviewMetricCard(
                            title = "Total de Gols",
                            value = "${overview.totalGoals}",
                            subtitle = "Média: %.2f por jogo".format(overview.totalGoals.toDouble() / overview.playedMatches.coerceAtLeast(1)),
                            color = YellowGold,
                            modifier = Modifier.weight(1f)
                        )
                        OverviewMetricCard(
                            title = "Jogos Realizados",
                            value = "${overview.playedMatches} / ${overview.totalMatches}",
                            subtitle = "${overview.totalMatches - overview.playedMatches} restantes",
                            color = GreenAccent,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OverviewMetricCard(
                            title = "Vitórias Mandante",
                            value = "${overview.homeWins}",
                            subtitle = "%.1f%% dos jogos".format((overview.homeWins.toDouble() / overview.playedMatches) * 100),
                            color = BlueLiberta,
                            modifier = Modifier.weight(1f)
                        )
                        OverviewMetricCard(
                            title = "Vitórias Visitante",
                            value = "${overview.awayWins}",
                            subtitle = "%.1f%% dos jogos".format((overview.awayWins.toDouble() / overview.playedMatches) * 100),
                            color = Color(0xFFFF9800),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    val bestAttack = standings.maxByOrNull { it.goalsFor }
                    val bestDefense = standings.minByOrNull { it.goalsAgainst }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Destaques Coletivos",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            if (bestAttack != null) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TeamBadge(team = bestAttack.team, size = 28.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Melhor Ataque", fontSize = 11.sp, color = TextSecondary)
                                        Text(text = bestAttack.team.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    }
                                    Text(text = "${bestAttack.goalsFor} gols", fontSize = 14.sp, fontWeight = FontWeight.Black, color = GreenAccent)
                                }
                            }

                            HorizontalDivider(color = DarkBorderSubtle, modifier = Modifier.padding(vertical = 10.dp))

                            if (bestDefense != null) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TeamBadge(team = bestDefense.team, size = 28.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Melhor Defesa", fontSize = 11.sp, color = TextSecondary)
                                        Text(text = bestDefense.team.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    }
                                    Text(text = "${bestDefense.goalsAgainst} sofridos", fontSize = 14.sp, fontWeight = FontWeight.Black, color = BlueLiberta)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Player Ranking List (Goals or Assists)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .testTag("players_ranking_list"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = sortedPlayers,
                    key = { _, player -> player.id }
                ) { index, player ->
                    val team = viewModel.getTeam(player.teamId)
                    val rank = index + 1
                    val mainStatValue = if (activeTab == StatsTab.SCORERS) player.goals else player.assists
                    val secondaryStatLabel = if (activeTab == StatsTab.SCORERS) "${player.assists} assistências" else "${player.goals} gols"

                    PlayerStatCard(
                        rank = rank,
                        player = player,
                        team = team,
                        mainStat = "$mainStatValue",
                        mainStatLabel = if (activeTab == StatsTab.SCORERS) "gols" else "assists",
                        secondaryStat = secondaryStatLabel,
                        onClick = { onTeamClick(team) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OverviewMetricCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun PlayerStatCard(
    rank: Int,
    player: Scorer,
    team: Team,
    mainStat: String,
    mainStatLabel: String,
    secondaryStat: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (rank == 1) YellowGold.copy(alpha = 0.5f) else DarkBorderSubtle
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(if (rank == 1) YellowGold else if (rank in 2..3) DarkSurfaceElevated else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$rank",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = if (rank == 1) Color.Black else TextPrimary
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Team Badge
            TeamBadge(team = team, size = 32.dp)

            Spacer(modifier = Modifier.width(10.dp))

            // Player name & Team
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = player.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "${team.name} • ${player.matches} jogos • $secondaryStat",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            // Main Stat Big Number
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = mainStat,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = if (rank == 1) YellowGold else GreenAccent
                )
                Text(
                    text = mainStatLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
            }
        }
    }
}

