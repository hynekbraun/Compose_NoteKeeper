package com.hynekbraun.composenotekeeper.presentation.notelist.composable

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderAscendance
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder,
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder == NoteOrder(
                    OrderType.HEADER,
                    OrderAscendance.ASCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.HEADER,
                    OrderAscendance.DESCENDING
                ),
                onSelect = {
                    onOrderChange(
                        noteOrder
                            .copy(orderType = OrderType.HEADER)
                    )
                    Log.d("ORDER", "OrderSection: Color clicked - $noteOrder")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder == NoteOrder(
                    OrderType.DATE,
                    OrderAscendance.ASCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.DATE,
                    OrderAscendance.DESCENDING
                ),
                onSelect = {
                    onOrderChange(
                        noteOrder
                            .copy(orderType = OrderType.DATE)
                    )
                    Log.d("ORDER", "OrderSection: Date clicked - $noteOrder")

                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder == NoteOrder(
                    OrderType.COLOR,
                    OrderAscendance.ASCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.COLOR,
                    OrderAscendance.DESCENDING
                ),
                onSelect = {
                    onOrderChange(
                        noteOrder
                            .copy(orderType = OrderType.COLOR)
                    )
                    Log.d("ORDER", "OrderSection: Color clicked - $noteOrder")
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder == NoteOrder(
                    OrderType.HEADER,
                    OrderAscendance.ASCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.DATE,
                    OrderAscendance.ASCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.COLOR,
                    OrderAscendance.ASCENDING
                ),
                onSelect = {
                    onOrderChange(
                        noteOrder
                            .copy(orderAscendance = OrderAscendance.ASCENDING)
                    )
                    Log.d("ORDER", "OrderSection: Ascending clicked - $noteOrder")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder == NoteOrder(
                    OrderType.HEADER,
                    OrderAscendance.DESCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.DATE,
                    OrderAscendance.DESCENDING
                ) || noteOrder == NoteOrder(
                    OrderType.COLOR,
                    OrderAscendance.DESCENDING
                ),
                onSelect = {
                    onOrderChange(
                        noteOrder
                            .copy(orderAscendance = OrderAscendance.DESCENDING)
                    )
                    Log.d("ORDER", "OrderSection: Descending clicked - $noteOrder")

                }
            )
        }
    }
}

@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}


