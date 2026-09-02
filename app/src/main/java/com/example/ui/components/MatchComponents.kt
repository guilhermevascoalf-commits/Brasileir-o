package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.model.Match
import com.example.model.MatchResult
import com.example.model.Team
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.FormDraw
import com.example.ui.theme.FormLoss
import com.example.ui.theme.FormWin
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.RedZ4
import com.example.ui.theme.RedZ4Light
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowGold

@Composable
fun FormBadge(result: MatchResult, modifier: Modifier = Modifier) {
    val (letter, bg) = when (result) {
        MatchResult.WIN -> "V" to FormWin
        MatchResult.DRAW -> "E" to FormDraw
        MatchResult.LOSS -> "D" to FormLoss
    }

    Box(
        modifier = modifier
            .size(18.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PositionChangeBadge(change: Int) {
    if (change == 0) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (change > 0) GreenPrimary.copy(alpha = 0.15f) else RedZ4.copy(alpha = 0.15f))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Icon(
            imageVector = if (change > 0) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
            contentDescription = null,
            tint = if (change > 0) GreenAccent else RedZ4Light,
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = "${if (change > 0) "+" else ""}$change",
            color = if (change > 0) GreenAccent else RedZ4Light,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MatchCard(
    match: Match,
    homeTeam: Team,
    awayTeam: Team,
    isSimulationMode: Boolean = false,
    simulatedScore: Pair<Int, Int>? = null,
    onScoreChange: ((home: Int?, away: Int?) -> Unit)? = null,
    onTeamClick: ((Team) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val effectiveHomeScore = simulatedScore?.first ?: match.homeScore
    val effectiveAwayScore = simulatedScore?.second ?: match.awayScore
    val isSimulated = simulatedScore != null

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("match_card_${match.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isSimulated) DarkSurfaceElevated else DarkSurface
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isSimulated) {
            androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary.copy(alpha = 0.5f))
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header info: Stadium, Date, Broadcast
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${match.stadium} • ${match.date} às ${match.time}",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Tv,
                        contentDescription = "Transmissão",
                        tint = TextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = match.broadcast,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = YellowGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Match Body
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Team
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = onTeamClick != null) { onTeamClick?.invoke(homeTeam) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = homeTeam.shortName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextPrimary,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TeamBadge(team = homeTeam, size = 32.dp)
                }

                // Center Score Area / Simulation Stepper
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(if (isSimulationMode) 130.dp else 80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSimulationMode && !match.isFinished) {
                        // Interactive Score Stepper
                        SimulationScoreInput(
                            homeScore = effectiveHomeScore ?: 0,
                            awayScore = effectiveAwayScore ?: 0,
                            hasScore = isSimulated,
                            onHomeChange = { newHome ->
                                onScoreChange?.invoke(newHome, effectiveAwayScore ?: 0)
                            },
                            onAwayChange = { newAway ->
                                onScoreChange?.invoke(effectiveHomeScore ?: 0, newAway)
                            },
                            onClear = {
                                onScoreChange?.invoke(null, null)
                            }
                        )
                    } else {
                        // Regular Score Display
                        if (match.isFinished || isSimulated) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    color = DarkSurfaceElevated,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle),
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                ) {
                                    Text(
                                        text = "${effectiveHomeScore ?: 0}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isSimulated) GreenAccent else TextPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Text(
                                    text = "x",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                Surface(
                                    color = DarkSurfaceElevated,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle),
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                ) {
                                    Text(
                                        text = "${effectiveAwayScore ?: 0}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isSimulated) GreenAccent else TextPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        } else {
                            Surface(
                                color = DarkSurfaceElevated,
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle)
                            ) {
                                Text(
                                    text = "VS",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Away Team
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = onTeamClick != null) { onTeamClick?.invoke(awayTeam) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TeamBadge(team = awayTeam, size = 32.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = awayTeam.shortName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }
            }

            if (isSimulated) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "● Palpite Simulado",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenAccent
                    )
                }
            }
        }
    }
}

@Composable
fun SimulationScoreInput(
    homeScore: Int,
    awayScore: Int,
    hasScore: Boolean,
    onHomeChange: (Int) -> Unit,
    onAwayChange: (Int) -> Unit,
    onClear: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Home stepper
        ScoreStepper(
            score = homeScore,
            onIncrement = { onHomeChange(homeScore + 1) },
            onDecrement = { onHomeChange((homeScore - 1).coerceAtLeast(0)) }
        )

        Text(
            text = "x",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Away stepper
        ScoreStepper(
            score = awayScore,
            onIncrement = { onAwayChange(awayScore + 1) },
            onDecrement = { onAwayChange((awayScore - 1).coerceAtLeast(0)) }
        )

        if (hasScore) {
            IconButton(
                onClick = onClear,
                modifier = Modifier.size(22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Limpar palpite",
                    tint = RedZ4Light,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun ScoreStepper(
    score: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(DarkSurfaceHighlight)
                .clickable { onIncrement() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "+1",
                tint = GreenAccent,
                modifier = Modifier.size(14.dp)
            )
        }

        Text(
            text = "$score",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 2.dp)
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(DarkSurfaceHighlight)
                .clickable { onDecrement() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "-1",
                tint = RedZ4Light,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

