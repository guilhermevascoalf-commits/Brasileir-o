package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Team
import com.example.ui.BrasileiraoViewModel
import com.example.ui.components.MatchCard
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowGold

enum class MatchFilter {
    ALL, FINISHED, UPCOMING
}

@Composable
fun RoundsScreen(
    viewModel: BrasileiraoViewModel,
    onTeamClick: (Team) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedRound by viewModel.selectedRound.collectAsState()
    val allMatches by viewModel.matches.collectAsState()
    val simulatedScores by viewModel.simulatedScores.collectAsState()

    var matchFilter by remember { mutableStateOf(MatchFilter.ALL) }

    val roundMatches = remember(allMatches, selectedRound, matchFilter) {
        val list = allMatches.filter { it.round == selectedRound }
        when (matchFilter) {
            MatchFilter.ALL -> list
            MatchFilter.FINISHED -> list.filter { it.isFinished }
            MatchFilter.UPCOMING -> list.filter { !it.isFinished }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Round Navigation Bar Header
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
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.previousRound() },
                        enabled = selectedRound > 1,
                        modifier = Modifier.testTag("prev_round_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Rodada Anterior",
                            tint = if (selectedRound > 1) GreenAccent else TextMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "RODADA $selectedRound DE 38",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = YellowGold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (selectedRound <= 33) "Jogos Realizados" else if (selectedRound == 34) "Rodada Atual" else "Jogos Futuros",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    IconButton(
                        onClick = { viewModel.nextRound() },
                        enabled = selectedRound < 38,
                        modifier = Modifier.testTag("next_round_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Próxima Rodada",
                            tint = if (selectedRound < 38) GreenAccent else TextMuted
                        )
                    }
                }

                // Quick Round Scroll Jumper (Key rounds 30..38)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    (31..38).forEach { roundNum ->
                        val isSelected = roundNum == selectedRound
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) GreenPrimary else DarkSurfaceElevated)
                                .clickable { viewModel.selectRound(roundNum) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "R$roundNum",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                color = if (isSelected) Color.Black else TextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Filter chips (Todos, Encerrados, A Realizar)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { matchFilter = MatchFilter.ALL },
                label = { Text("Todos (${allMatches.count { it.round == selectedRound }})", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (matchFilter == MatchFilter.ALL) GreenPrimary.copy(alpha = 0.15f) else DarkSurface,
                    labelColor = if (matchFilter == MatchFilter.ALL) GreenAccent else TextSecondary
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (matchFilter == MatchFilter.ALL) GreenAccent.copy(alpha = 0.6f) else DarkBorderSubtle
                )
            )
            AssistChip(
                onClick = { matchFilter = MatchFilter.FINISHED },
                label = { Text("Realizados", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (matchFilter == MatchFilter.FINISHED) GreenPrimary.copy(alpha = 0.15f) else DarkSurface,
                    labelColor = if (matchFilter == MatchFilter.FINISHED) GreenAccent else TextSecondary
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (matchFilter == MatchFilter.FINISHED) GreenAccent.copy(alpha = 0.6f) else DarkBorderSubtle
                )
            )
            AssistChip(
                onClick = { matchFilter = MatchFilter.UPCOMING },
                label = { Text("A Realizar", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (matchFilter == MatchFilter.UPCOMING) GreenPrimary.copy(alpha = 0.15f) else DarkSurface,
                    labelColor = if (matchFilter == MatchFilter.UPCOMING) GreenAccent else TextSecondary
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (matchFilter == MatchFilter.UPCOMING) GreenAccent.copy(alpha = 0.6f) else DarkBorderSubtle
                )
            )
        }

        // Matches List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("rounds_matches_list"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (roundMatches.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nenhum jogo encontrado para este filtro.",
                                color = TextSecondary,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            } else {
                items(
                    items = roundMatches,
                    key = { it.id }
                ) { match ->
                    val homeTeam = viewModel.getTeam(match.homeTeamId)
                    val awayTeam = viewModel.getTeam(match.awayTeamId)
                    val simScore = simulatedScores[match.id]

                    MatchCard(
                        match = match,
                        homeTeam = homeTeam,
                        awayTeam = awayTeam,
                        isSimulationMode = false,
                        simulatedScore = simScore,
                        onTeamClick = onTeamClick
                    )
                }
            }
        }
    }
}

