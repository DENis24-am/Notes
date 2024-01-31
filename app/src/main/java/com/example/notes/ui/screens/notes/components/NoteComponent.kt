package com.example.notes.ui.screens.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.notes.common.LocalProperty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteComponent(
    canDelete: Boolean,
    _title: String, _description: String,
    titleChange: (String) -> Unit, descriptionChange: (String) -> Unit,
    saveNote: () -> Unit,
    onBackPress: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = LocalProperty.current.normalPadding, end = LocalProperty.current.normalPadding, top = LocalProperty.current.topPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackPress) {
                Icon(
                    modifier = Modifier.size(LocalProperty.current.iconSize),
                    imageVector = Icons.Default.ArrowBack,
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = "Back Screen",
                )
            }
            Row {

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        modifier = Modifier.size(LocalProperty.current.iconSize),
                        imageVector = Icons.Default.Delete,
                        tint = Color.Red,
                        contentDescription = "Delete Note",
                    )
                }
                Spacer(modifier = Modifier.width(LocalProperty.current.spacerSizeSmall))
                IconButton(onClick = saveNote) {
                    Icon(
                        modifier = Modifier.size(LocalProperty.current.iconSize),
                        imageVector = Icons.Default.CheckCircle,
                        tint = MaterialTheme.colorScheme.outline,
                        contentDescription = "Save Note",
                    )
                }

            }
        }
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.titleLarge,
            placeholder = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.LightGray)
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            value = _title,
            onValueChange = titleChange
        )
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Note",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            value = _description,
            onValueChange = descriptionChange
        )
    }
}