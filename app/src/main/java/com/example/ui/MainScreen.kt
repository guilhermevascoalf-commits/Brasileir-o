package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppTopBar
import com.example.ui.screens.ProbabilitiesScreen
import com.example.ui.screens.RoundsScreen
import com.example.ui.screens.SimulatorScreen
import com.example.ui.screens.StandingsScreen
import com.example.ui.screens.StatsScreen
import com.example.ui.screens.TeamDetailSheet
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GreenPrimary

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: BrasileiraoViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val simulatedScores by viewModel.simulatedScores.collectAsState()
    val selectedTeam by viewModel.selectedTeam.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val topBarSubtitle = when (currentTab) {
        AppTab.STANDINGS -> "Classificação Série A"
        AppTab.ROUNDS -> "Jogos e Resultados"
        AppTab.SIMULATOR -> "Simule Resultados e Veja Mudanças"
        AppTab.PROBABILITIES -> "Chances Matemáticas (UFMG)"
        AppTab.STATS -> "Artilharia e Destaques"
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Brasileirão Série A",
                subtitle = topBarSubtitle,
                simulationsCount = simulatedScores.size,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshData() },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        bottomBar = {
            Surface(
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.DarkBorderSubtle),
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBar(
                    containerColor = DarkSurface,
                    tonalElevation = 0.dp,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    // Tab 1: Tabela
                    NavigationBarItem(
                        selected = currentTab == AppTab.STANDINGS,
                        onClick = { viewModel.selectTab(AppTab.STANDINGS) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.TableChart,
                                contentDescription = "Tabela"
                            )
                        },
                        label = {
                            Text(
                                text = "Tabela",
                                fontSize = 11.sp,
                                fontWeight = if (currentTab == AppTab.STANDINGS) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = com.example.ui.theme.GreenAccent,
                            indicatorColor = com.example.ui.theme.GreenAccent,
                            unselectedIconColor = com.example.ui.theme.TextMuted,
                            unselectedTextColor = com.example.ui.theme.TextMuted
                        ),
                        modifier = Modifier.testTag("nav_tab_standings")
                    )

                    // Tab 2: Rodadas
                    NavigationBarItem(
                        selected = currentTab == AppTab.ROUNDS,
                        onClick = { viewModel.selectTab(AppTab.ROUNDS) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Rodadas"
                            )
                        },
                        label = {
                            Text(
                                text = "Jogos",
                                fontSize = 11.sp,
                                fontWeight = if (currentTab == AppTab.ROUNDS) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = com.example.ui.theme.GreenAccent,
                            indicatorColor = com.example.ui.theme.GreenAccent,
                            unselectedIconColor = com.example.ui.theme.TextMuted,
                            unselectedTextColor = com.example.ui.theme.TextMuted
                        ),
                        modifier = Modifier.testTag("nav_tab_rounds")
                    )

                    // Tab 3: Simulador
                    NavigationBarItem(
                        selected = currentTab == AppTab.SIMULATOR,
                        onClick = { viewModel.selectTab(AppTab.SIMULATOR) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.SportsScore,
                                contentDescription = "Simulador"
                            )
                        },
                        label = {
                            Text(
                                text = "Simulador",
                                fontSize = 11.sp,
                                fontWeight = if (currentTab == AppTab.SIMULATOR) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = com.example.ui.theme.GreenAccent,
                            indicatorColor = com.example.ui.theme.GreenAccent,
                            unselectedIconColor = com.example.ui.theme.TextMuted,
                            unselectedTextColor = com.example.ui.theme.TextMuted
                        ),
                        modifier = Modifier.testTag("nav_tab_simulator")
                    )

                    // Tab 4: Probabilidades
                    NavigationBarItem(
                        selected = currentTab == AppTab.PROBABILITIES,
                        onClick = { viewModel.selectTab(AppTab.PROBABILITIES) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.QueryStats,
                                contentDescription = "Probabilidades"
                            )
                        },
                        label = {
                            Text(
                                text = "Chances",
                                fontSize = 11.sp,
                                fontWeight = if (currentTab == AppTab.PROBABILITIES) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = com.example.ui.theme.GreenAccent,
                            indicatorColor = com.example.ui.theme.GreenAccent,
                            unselectedIconColor = com.example.ui.theme.TextMuted,
                            unselectedTextColor = com.example.ui.theme.TextMuted
                        ),
                        modifier = Modifier.testTag("nav_tab_probabilities")
                    )

                    // Tab 5: Artilharia / Estatísticas
                    NavigationBarItem(
                        selected = currentTab == AppTab.STATS,
                        onClick = { viewModel.selectTab(AppTab.STATS) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.SportsSoccer,
                                contentDescription = "Estatísticas"
                            )
                        },
                        label = {
                            Text(
                                text = "Stats",
                                fontSize = 11.sp,
                                fontWeight = if (currentTab == AppTab.STATS) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = com.example.ui.theme.GreenAccent,
                            indicatorColor = com.example.ui.theme.GreenAccent,
                            unselectedIconColor = com.example.ui.theme.TextMuted,
                            unselectedTextColor = com.example.ui.theme.TextMuted
                        ),
                        modifier = Modifier.testTag("nav_tab_stats")
                    )
                }
            }
        },
        containerColor = com.example.ui.theme.DarkBg
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.STANDINGS -> StandingsScreen(
                    viewModel = viewModel,
                    onTeamClick = { viewModel.selectTeam(it) }
                )
                AppTab.ROUNDS -> RoundsScreen(
                    viewModel = viewModel,
                    onTeamClick = { viewModel.selectTeam(it) }
                )
                AppTab.SIMULATOR -> SimulatorScreen(
                    viewModel = viewModel,
                    onTeamClick = { viewModel.selectTeam(it) }
                )
                AppTab.PROBABILITIES -> ProbabilitiesScreen(
                    viewModel = viewModel,
                    onTeamClick = { viewModel.selectTeam(it) }
                )
                AppTab.STATS -> StatsScreen(
                    viewModel = viewModel,
                    onTeamClick = { viewModel.selectTeam(it) }
                )
            }

            // Team Detail Modal Bottom Sheet
            selectedTeam?.let { team ->
                TeamDetailSheet(
                    team = team,
                    viewModel = viewModel,
                    onDismiss = { viewModel.selectTeam(null) }
                )
            }
        }
    }
}
