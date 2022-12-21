package com.dantropov.medtest.ui.quiz

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dantropov.medtest.R
import com.dantropov.medtest.compose.LoadingAnimation
import com.dantropov.medtest.database.model.Answer
import com.dantropov.medtest.database.model.MedQuiz

@Composable
fun QuizScreen(
    @StringRes titleId: Int,
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int) -> Unit,
    onClick: () -> Unit
) {
    ScaffoldWithTopBar(titleId, medQuiz, state, onAnswerClick, onClick)
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenEmpty(@StringRes titleId: Int = R.string.practice) {
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(id = R.string.quiz_error),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun QuizScreenLoading(@StringRes titleId: Int = R.string.practice) {
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(
    @StringRes titleId: Int, medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int) -> Unit,
    onClick: () -> Unit
) {
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
        QuizContent(it, medQuiz, state, onAnswerClick, onClick)
    })
}

@Composable
private fun QuizContent(
    padding: PaddingValues,
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable { onClick },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizLayout(medQuiz, state, onAnswerClick, onClick)
    }
}

@Composable
fun QuizLayout(
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            medQuiz.question,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        ProgressBar()
        Spacer(Modifier.height(16.dp))
        QuizQuestion(medQuiz, state, onAnswerClick, onClick)
    }
}

@Composable
fun QuizQuestion(
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int) -> Unit,
    onClick: () -> Unit
) {
    LazyColumn {
        items(medQuiz.answers.size) { index ->
            val answer = medQuiz.answers[index]
            Spacer(Modifier.height(8.dp))
            Answer(answer) { if (state == QuestionState.NotAnswered) onAnswerClick(answer, index) }
        }
    }

}

@Composable
fun Answer(
    answer: Answer,
    selected: Boolean = false,
    onOptionSelected: (answer: Answer) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .then(Modifier.clickable(onClick = { onOptionSelected(answer) }))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(8.dp))
            Text(answer.text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Box(Modifier.padding(8.dp)) {
                RadioButton(selected, onClick = null)
            }
        }
    }
}

@Composable
fun ProgressBar() {
    Row() {
        LinearProgressIndicator(
            modifier = Modifier
                .height(16.dp)
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
        MedQuiz(
            0, "", "Текст вопроса",
            mutableListOf(
                Answer("Вопрос 1", false),
                Answer("Вопрос 2", false),
                Answer("Вопрос 3", false),
                Answer("Вопрос 4", true),
                Answer("Вопрос 5", false)
            )
        ),
        QuestionState.WrongAnswer(1, 2),
        { _, _ -> },
        {})
}