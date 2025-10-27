package com.example.myapplication.ui.tasks // Mantenha seu pacote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.state.UiState

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = viewModel()
) {
    // 1. ISSO É IDÊNTICO: Observa o ViewModel
    val state by viewModel.uiState.collectAsState()

    Surface(modifier = modifier.fillMaxSize()) {
        // 2. ISSO É IDÊNTICO: O `when` decide o que mostrar
        when (val s = state) {
            is UiState.Loading -> LoadingComponent()
            is UiState.Success -> TasksListComponent( // 3. AQUI MUDA: Chamamos nosso novo layout
                tasks = s.data,
                onReload = { viewModel.loadTasks() }
            )
            is UiState.Error -> ErrorComponent( // 4. AQUI MUDA: Chamamos nosso novo layout de erro
                message = s.message,
                onRetry = { viewModel.loadTasks() }
            )
        }
    }
}

// --- COMPONENTES DE ESTADO (Layout Novo) ---

@Composable
fun LoadingComponent() {
    // (Layout levemente alterado para ficar mais claro)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(16.dp))
        Text(
            "Carregando tarefas...",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TasksListComponent(
    tasks: List<Task>,
    onReload: () -> Unit
) {
    // *** NOSSO NOVO LAYOUT DE SUCESSO ***
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título e Botão
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Minhas Tarefas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onReload) {
                Text("Recarregar")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Nova Lista com Cards
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Espaço entre os cards
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskCardItem(task = task) // Nosso novo Composable de item
            }
        }
    }
}

@Composable
private fun TaskCardItem(task: Task) {
    // *** ESTE É O NOVO ITEM DE LAYOUT USANDO CARD ***
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            // Deixa o card levemente esverdeado se concluído
            containerColor = if (task.done) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone que muda
            Icon(
                imageVector = if (task.done) Icons.Default.CheckCircle else Icons.Default.List,
                contentDescription = "Status",
                tint = if (task.done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.width(16.dp))

            // Título
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ErrorComponent(
    message: String,
    onRetry: () -> Unit
) {
    // (Layout levemente alterado para ficar mais claro)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Erro",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(60.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Ops! Algo deu errado.",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onRetry) {
            Text("Tentar Novamente")
        }
    }
}