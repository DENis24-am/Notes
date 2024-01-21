package com.example.notes.ui.screens.notesview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun BottomBar(
    viewGrid: Boolean = false,
    createNewNote: () -> Unit,
    onViewGrid: () -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onViewGrid) {
                Icon(if(viewGrid) Icons.Outlined.DateRange else Icons.Filled.Menu, contentDescription = null)
            }
            Text(
                text = "Filled?"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    createNewNote()
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, null)
            }
        }
    )
}