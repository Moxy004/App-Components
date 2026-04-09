package com.example.practice1.ui.components
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedSpinner() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[1]) } // Defaulting to "Item 2"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        // The Outlined text field gives you the border and floating label
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true, // Prevents keyboard from showing
            label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor() // Ties the menu to this text field
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun view(){
    OutlinedSpinner()
}