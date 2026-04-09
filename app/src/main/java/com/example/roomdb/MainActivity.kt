package com.example.roomdb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomdb.ui.data.AppDatabase
import com.example.roomdb.ui.data.User
import com.example.roomdb.ui.data.UserDao
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


// --- 1. Simple ViewModel ---
class UserViewModel(private val dao: UserDao) : ViewModel() {
    // Expose the Flow from Room as a State for Compose
    val usersFlow = dao.getAllUsers()

    fun addUser(name: String) {
        viewModelScope.launch {
            dao.insertUser(User(name = name))
        }
    }
}

// --- 2. Main Activity ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room Database
        val database = AppDatabase.getDatabase(this)
        val dao = database.userDao()

        // Create ViewModel manually for this quick test
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(dao) as T
            }
        }
        val viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen(viewModel)
                }
            }
        }
    }
}

// --- 3. Compose UI ---
@Composable
fun UserScreen(viewModel: UserViewModel) {
    // Collect the Flow from Room as Compose State
    val users by viewModel.usersFlow.collectAsState(initial = emptyList())
    var nameInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Enter a name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        viewModel.addUser(nameInput)
                        nameInput = "" // Clear the input
                    }
                }
            ) {
                Text("Add User")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of users
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "ID: ${user.id} | Name: ${user.name}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}