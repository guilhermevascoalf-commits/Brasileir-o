package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Team
import com.example.ui.BrasileiraoViewModel
import com.example.ui.components.FormBadge
import com.example.ui.components.MatchCard
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailSheet(
    team: Team,
    viewModel: BrasileiraoViewModel,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val standings by viewModel.standings.collectAsState()
    val probabilities by viewModel.sortedProbabilities.collectAsState()
    val simulatedScores by viewModel.simulatedScores.collectAsState()

    val standing = standings.find { it.team.id == team.id }
    val prob = probabilities.find { it.teamId == team.id }
    val teamMatches = viewModel.getTeamMatches(team.id)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSurface,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier.testTag("team_detail_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Close Button and Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TeamBadge(team = team, size = 44.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = team.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Stadium,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${team.stadium} (${team.city} - ${team.state})",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fechar",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Summary Stats Strip
            if (standing != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TeamStatPill(title = "Posição", value = "${standing.position}º", color = YellowGold)
                        TeamStatPill(title = "Pontos", value = "${standing.points}", color = GreenAccent)
                        TeamStatPill(title = "Jogos", value = "${standing.played}", color = TextPrimary)
                        TeamStatPill(title = "Vitórias", value = "${standing.won}", color = TextPrimary)
                        TeamStatPill(title = "Saldo", value = "${if (standing.goalDifference > 0) "+" else ""}${standing.goalDifference}", color = if (standing.goalDifference >= 0) GreenAccent else RedZ4Light)
                        TeamStatPill(title = "Aprov.", value = "%.0f%%".format(standing.winPercentage), color = TextPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // UFMG Probabilities Card for this team
            if (prob != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Probabilidades Matemáticas (UFMG)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = YellowGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ProbPill(label = "Título", pct = prob.championPct, color = YellowGold)
                            ProbPill(label = "Liberta", pct = prob.libertadoresPct, color = BlueLiberta)
                            ProbPill(label = "Sula", pct = prob.sulamericanaPct, color = BlueSula)
                            ProbPill(label = "Rebaixamento", pct = prob.relegationPct, color = RedZ4Light)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Partidas do Clube",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(teamMatches) { match ->
                    val homeTeam = viewModel.getTeam(match.homeTeamId)
                    val awayTeam = viewModel.getTeam(match.awayTeamId)
                    val simScore = simulatedScores[match.id]

                    MatchCard(
                        match = match,
                        homeTeam = homeTeam,
                        awayTeam = awayTeam,
                        isSimulationMode = false,
                        simulatedScore = simScore
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamStatPill(title: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 10.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Black, color = color)
    }
}

@Composable
private fun ProbPill(label: String, pct: Double, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "%.1f%%".format(pct), fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}

