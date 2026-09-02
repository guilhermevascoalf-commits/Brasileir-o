package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Standing
import com.example.model.Team
import com.example.model.getZoneForPosition
import com.example.ui.BrasileiraoViewModel
import com.example.ui.components.FormBadge
import com.example.ui.components.PositionChangeBadge
import com.example.ui.components.TeamBadge
import com.example.ui.components.ZoneLegend
import com.example.ui.components.getZoneColor
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.RedZ4
import com.example.ui.theme.RedZ4Light
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowGold

enum class StandingsSortColumn {
    POINTS, WINS, GOAL_DIFF, GOALS_FOR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandingsScreen(
    viewModel: BrasileiraoViewModel,
    onTeamClick: (Team) -> Unit,
    modifier: Modifier = Modifier
) {
    val standings by viewModel.standings.collectAsState()
    val simulatedScores by viewModel.simulatedScores.collectAsState()
    val isSimulated = simulatedScores.isNotEmpty()

    var searchQuery by remember { mutableStateOf("") }
    var sortColumn by remember { mutableStateOf(StandingsSortColumn.POINTS) }

    val filteredStandings = remember(standings, searchQuery, sortColumn) {
        var list = if (searchQuery.isBlank()) {
            standings
        } else {
            standings.filter {
                it.team.name.contains(searchQuery, ignoreCase = true) ||
                it.team.shortName.contains(searchQuery, ignoreCase = true) ||
                it.team.code.contains(searchQuery, ignoreCase = true)
            }
        }

        when (sortColumn) {
            StandingsSortColumn.POINTS -> list
            StandingsSortColumn.WINS -> list.sortedByDescending { it.won }
            StandingsSortColumn.GOAL_DIFF -> list.sortedByDescending { it.goalDifference }
            StandingsSortColumn.GOALS_FOR -> list.sortedByDescending { it.goalsFor }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Simulation Active Alert Banner
        if (isSimulated) {
            Surface(
                color = GreenPrimary.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚡ Tabela recalculada com ${simulatedScores.size} palpites simulados",
                        color = GreenPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Limpar",
                        color = YellowGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .clickable { viewModel.clearSimulations() }
                            .padding(4.dp)
                    )
                }
            }
        }

        // Search & Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar time...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DarkSurfaceElevated,
                    unfocusedContainerColor = DarkSurface,
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = DarkBorder
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("search_team_input")
            )
        }

        // Quick Sort Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { sortColumn = StandingsSortColumn.POINTS },
                label = { Text("Classificação Geral", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (sortColumn == StandingsSortColumn.POINTS) GreenPrimary.copy(alpha = 0.2f) else DarkSurface,
                    labelColor = if (sortColumn == StandingsSortColumn.POINTS) GreenPrimary else MaterialTheme.colorScheme.onSurface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (sortColumn == StandingsSortColumn.POINTS) GreenPrimary else DarkBorder
                )
            )
            AssistChip(
                onClick = { sortColumn = StandingsSortColumn.WINS },
                label = { Text("Mais Vitórias (V)", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (sortColumn == StandingsSortColumn.WINS) GreenPrimary.copy(alpha = 0.2f) else DarkSurface,
                    labelColor = if (sortColumn == StandingsSortColumn.WINS) GreenPrimary else MaterialTheme.colorScheme.onSurface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (sortColumn == StandingsSortColumn.WINS) GreenPrimary else DarkBorder
                )
            )
            AssistChip(
                onClick = { sortColumn = StandingsSortColumn.GOAL_DIFF },
                label = { Text("Melhor Saldo (SG)", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (sortColumn == StandingsSortColumn.GOAL_DIFF) GreenPrimary.copy(alpha = 0.2f) else DarkSurface,
                    labelColor = if (sortColumn == StandingsSortColumn.GOAL_DIFF) GreenPrimary else MaterialTheme.colorScheme.onSurface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (sortColumn == StandingsSortColumn.GOAL_DIFF) GreenPrimary else DarkBorder
                )
            )
            AssistChip(
                onClick = { sortColumn = StandingsSortColumn.GOALS_FOR },
                label = { Text("Mais Gols (GP)", fontSize = 11.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (sortColumn == StandingsSortColumn.GOALS_FOR) GreenPrimary.copy(alpha = 0.2f) else DarkSurface,
                    labelColor = if (sortColumn == StandingsSortColumn.GOALS_FOR) GreenPrimary else MaterialTheme.colorScheme.onSurface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (sortColumn == StandingsSortColumn.GOALS_FOR) GreenPrimary else DarkBorder
                )
            )
        }

        // Table Header
        TableHeader()

        HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)

        // Standings List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("standings_list")
        ) {
            itemsIndexed(
                items = filteredStandings,
                key = { _, item -> item.team.id }
            ) { index, item ->
                StandingRow(
                    standing = item,
                    isLeader = item.position == 1 && sortColumn == StandingsSortColumn.POINTS,
                    onClick = { onTeamClick(item.team) }
                )
                if (index < filteredStandings.size - 1) {
                    HorizontalDivider(color = DarkBorderSubtle, thickness = 0.5.dp)
                }
            }

            item {
                ZoneLegend(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp))
            }
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSurface)
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "POS / CLUBE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderCol(text = "PTS", width = 34.dp, isBold = true)
            HeaderCol(text = "J", width = 26.dp)
            HeaderCol(text = "V", width = 26.dp)
            HeaderCol(text = "E", width = 26.dp)
            HeaderCol(text = "D", width = 26.dp)
            HeaderCol(text = "SG", width = 30.dp)
            HeaderCol(text = "%", width = 36.dp)
        }
    }
}

@Composable
private fun HeaderCol(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    isBold: Boolean = false
) {
    Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.SemiBold,
        color = if (isBold) GreenAccent else TextSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.width(width)
    )
}

@Composable
private fun StandingRow(
    standing: Standing,
    isLeader: Boolean = false,
    onClick: () -> Unit
) {
    val zone = getZoneForPosition(standing.position)
    val zoneColor = getZoneColor(zone)

    val rowBg = if (isLeader) {
        GreenPrimary.copy(alpha = 0.08f)
    } else {
        Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBg)
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Zone color indicator strip
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(zoneColor)
        )

        Spacer(modifier = Modifier.width(6.dp))

        // Position Number + Delta
        Column(
            modifier = Modifier.width(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${standing.position}º",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isLeader) GreenAccent else TextPrimary
            )
            if (standing.positionChange != 0) {
                PositionChangeBadge(change = standing.positionChange)
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Team Badge + Name
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamBadge(team = standing.team, size = 26.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = standing.team.shortName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                // Form dots in row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    standing.form.take(5).forEach { result ->
                        FormBadge(result = result, modifier = Modifier.size(12.dp))
                    }
                }
            }
        }

        // Stats columns
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Points (Highlighted)
            Text(
                text = "${standing.points}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = if (isLeader) GreenAccent else TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(34.dp)
            )
            // J
            Text(
                text = "${standing.played}",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(26.dp)
            )
            // V
            Text(
                text = "${standing.won}",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(26.dp)
            )
            // E
            Text(
                text = "${standing.drawn}",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(26.dp)
            )
            // D
            Text(
                text = "${standing.lost}",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(26.dp)
            )
            // SG
            Text(
                text = "${if (standing.goalDifference > 0) "+" else ""}${standing.goalDifference}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (standing.goalDifference > 0) GreenAccent else if (standing.goalDifference < 0) RedZ4Light else TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(30.dp)
            )
            // % Aproveitamento
            Text(
                text = "%.0f%%".format(standing.winPercentage),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(36.dp)
            )
        }
    }
}
