package com.dantropov.medtest.ui.quiz

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dantropov.medtest.R
import com.dantropov.medtest.database.model.Answer
import com.dantropov.medtest.database.model.MedQuiz

@Composable
fun QuizScreen(@StringRes titleId: Int, uiState: QuizUiState) {
    ScaffoldWithTopBar(titleId, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(@StringRes titleId: Int, uiState: QuizUiState) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(titleId),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint,
                titleContentColor = Color.White,
            ),
        )
    }, content = {
        QuizContent(it, uiState)
    })
}

@Composable
private fun QuizContent(padding: PaddingValues, uiState: QuizUiState) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizLayout(uiState)
    }
}

@Composable
fun QuizLayout(quizUiState: QuizUiState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            quizUiState.,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        ProgressBar()
    }
}

@Composable
fun ProgressBar() {
    Row() {
        LinearProgressIndicator(
            modifier = Modifier
                .height(15.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            color = MaterialTheme.colorScheme.primary,
            progress = 0.5f
        )
        Spacer(Modifier.width(4.dp))
        Text("2/10")
    }
}

@Preview
@Composable
fun PreviewQuizScreen() {
    QuizScreen(
        titleId = R.string.practice,
        QuizUiState.Ready(
            MedQuiz(
                0, "", "Текст вопроса",
                mutableListOf(
                    Answer("Вопрос 1", false),
                    Answer("Вопрос 2", false),
                    Answer("Вопрос 3", false),
                    Answer("Вопрос 4", true),
                    Answer("Вопрос 5", false)
                )
            )
        )
    )
}