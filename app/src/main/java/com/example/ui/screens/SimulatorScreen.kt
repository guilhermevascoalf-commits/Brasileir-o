package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.ui.AppTab
import com.example.ui.BrasileiraoViewModel
import com.example.ui.SimulationPreset
import com.example.ui.components.MatchCard
import com.example.ui.components.PositionChangeBadge
import com.example.ui.components.TeamBadge
import com.example.ui.theme.BlueLiberta
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
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

@Composable
fun SimulatorScreen(
    viewModel: BrasileiraoViewModel,
    onTeamClick: (Team) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedRound by viewModel.selectedRound.collectAsState()
    val allMatches by viewModel.matches.collectAsState()
    val simulatedScores by viewModel.simulatedScores.collectAsState()
    val standings by viewModel.standings.collectAsState()

    val roundMatches = allMatches.filter { it.round == selectedRound }
    val leader = standings.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Real-Time Simulator Dashboard Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary.copy(alpha = 0.35f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // Title and Leader status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Simulador",
                            tint = YellowGold,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "SIMULADOR DE PALPITES",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = YellowGold
                            )
                            Text(
                                text = "Ajuste os placares e veja a tabela em tempo real",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    if (leader != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceElevated)
                                .border(1.dp, DarkBorderSubtle, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            TeamBadge(team = leader.team, size = 20.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "1º ${leader.team.shortName}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "${leader.points} pts",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = YellowGold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quick Simulation Preset Buttons (Horizontal Scroll)
                Text(
                    text = "Ações Rápidas de Simulação:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Home Wins Preset
                    PresetButton(
                        icon = Icons.Default.Home,
                        label = "Mandantes",
                        onClick = { viewModel.applyPreset(SimulationPreset.HOME_WINS) }
                    )
                    // Random Preset
                    PresetButton(
                        icon = Icons.Default.Casino,
                        label = "Aleatório",
                        onClick = { viewModel.applyPreset(SimulationPreset.RANDOM) }
                    )
                    // Favorites/Remaining Preset
                    PresetButton(
                        icon = Icons.Default.AutoAwesome,
                        label = "Simular Restante",
                        onClick = { viewModel.applyPreset(SimulationPreset.FAVORITES) },
                        highlight = true
                    )
                    // Clear Simulations
                    if (simulatedScores.isNotEmpty()) {
                        PresetButton(
                            icon = Icons.Default.Delete,
                            label = "Limpar (${simulatedScores.size})",
                            onClick = { viewModel.clearSimulations() },
                            isDestructive = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // View Recalculated Standings Shortcut Button
                Button(
                    onClick = { viewModel.selectTab(AppTab.STANDINGS) },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("view_standings_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.TableChart,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Ver Classificação Completa Recalculada",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Round Stepper Selector (Simulation usually on upcoming rounds 34..38)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.previousRound() },
                enabled = selectedRound > 1
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Rodada Anterior",
                    tint = if (selectedRound > 1) GreenAccent else TextMuted
                )
            }

            Text(
                text = "Palpites da Rodada $selectedRound",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )

            IconButton(
                onClick = { viewModel.nextRound() },
                enabled = selectedRound < 38
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Próxima Rodada",
                    tint = if (selectedRound < 38) GreenAccent else TextMuted
                )
            }
        }

        // List of Matches in this round with Interactive Stepper
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("simulator_matches_list"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
                    isSimulationMode = true,
                    simulatedScore = simScore,
                    onScoreChange = { h, a ->
                        viewModel.updateScore(match.id, h, a)
                    },
                    onTeamClick = onTeamClick
                )
            }
        }
    }
}

@Composable
private fun PresetButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    highlight: Boolean = false,
    isDestructive: Boolean = false
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = when {
            isDestructive -> RedZ4.copy(alpha = 0.15f)
            highlight -> GreenPrimary.copy(alpha = 0.2f)
            else -> DarkSurfaceElevated
        },
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            when {
                isDestructive -> RedZ4Light.copy(alpha = 0.5f)
                highlight -> GreenAccent.copy(alpha = 0.5f)
                else -> DarkBorderSubtle
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = when {
                    isDestructive -> RedZ4Light
                    highlight -> GreenAccent
                    else -> TextSecondary
                },
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = when {
                    isDestructive -> RedZ4Light
                    highlight -> GreenAccent
                    else -> TextPrimary
                }
            )
        }
    }
}
