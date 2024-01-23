package com.example.notes.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings

object NavigationItem {
    val items = listOf(
        NavigationItemModel(icon = Icons.Outlined.Email, label = "Notes"),
        NavigationItemModel(icon = Icons.Outlined.Notifications, label = "Reminders"),
        NavigationItemModel(icon = Icons.Outlined.Add, label = "Create new label"),
        NavigationItemModel(icon = Icons.Outlined.List, label = "Archive"),
        NavigationItemModel(icon = Icons.Outlined.Delete, label = "Trash"),
        NavigationItemModel(icon = Icons.Outlined.Settings, label = "Settings"),
        NavigationItemModel(icon = Icons.Outlined.Send, label = "Hep & feedback"),
    )
}