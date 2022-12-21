package com.dantropov.medtest.ui.quiz

import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
    onAnswerClick: (Answer, Int, QuestionState) -> Unit,
    onLayoutClick: () -> Unit
) {
    ScaffoldWithTopBar(titleId, medQuiz, state, onAnswerClick, onLayoutClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenEmpty(@StringRes titleId: Int = R.string.practice) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(titleId), style = MaterialTheme.typography.titleLarge
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
@Composable
fun QuizScreenLoading(@StringRes titleId: Int = R.string.practice) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(titleId), style = MaterialTheme.typography.titleLarge
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
    @StringRes titleId: Int,
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int, QuestionState) -> Unit,
    onLayoutClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(titleId), style = MaterialTheme.typography.titleLarge
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint,
                titleContentColor = Color.White,
            ),
        )
    }, content = {
        QuizContent(it, medQuiz, state, onAnswerClick, onLayoutClick)
    })
}

@Composable
private fun QuizContent(
    padding: PaddingValues,
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int, QuestionState) -> Unit,
    onLayoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable(onClick = onLayoutClick),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizLayout(medQuiz, state, onAnswerClick, onLayoutClick)
    }
}

@Composable
fun QuizLayout(
    medQuiz: MedQuiz,
    state: QuestionState,
    onAnswerClick: (Answer, Int, QuestionState) -> Unit,
    onLayoutClick: () -> Unit
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
            medQuiz.question, style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        ProgressBar()
        Spacer(Modifier.height(16.dp))
        QuizQuestion(medQuiz, state, onAnswerClick, onLayoutClick)
    }
}

@Composable
fun QuizQuestion(
    medQuiz: MedQuiz, state: QuestionState, onAnswerClick: (Answer, Int, QuestionState) -> Unit, onClick: () -> Unit
) {
    var correctAnswerId = -1
    var wrongAnswerId = -1
    if (state is QuestionState.CorrectAnswer) correctAnswerId = state.correctAnswerId
    if (state is QuestionState.WrongAnswer) {
        correctAnswerId = state.correctAnswerId
        wrongAnswerId = state.wrongAnswerId
    }
    LazyColumn {
        items(medQuiz.answers.size) { index ->
            val answer = medQuiz.answers[index]
            Spacer(Modifier.height(8.dp))
            when (index) {
                wrongAnswerId -> Answer(answer, AnswerState.WRONG) { onAnswerClick(answer, index, state) }
                correctAnswerId -> Answer(answer, AnswerState.CORRECT) { onAnswerClick(answer, index, state) }
                else -> Answer(answer, AnswerState.NONE) { onAnswerClick(answer, index, state) }
            }
        }
    }

}

@Composable
fun Answer(
    answer: Answer, state: AnswerState, onOptionSelected: (answer: Answer) -> Unit
) {
    val backgroundColor = Color(0xFFEEDCFF)
    var animatedColor = remember { Animatable(Color(0xFFEEDCFF)) }

    when (state) {
        AnswerState.CORRECT -> {
            val accentColor = Color(0xFF4CAF50)
            LaunchedEffect(Unit) {
                animatedColor.animateTo(accentColor, animationSpec = tween(500))
                animatedColor.animateTo(backgroundColor, animationSpec = tween(500))
                animatedColor.animateTo(accentColor, animationSpec = tween(500))
                animatedColor.animateTo(backgroundColor, animationSpec = tween(500))
                animatedColor.animateTo(accentColor, animationSpec = tween(500))
            }
        }
        AnswerState.WRONG -> animatedColor = remember { Animatable(Color(0xFFF44336)) }
        AnswerState.NONE -> {}
    }


    Surface(
        shape = MaterialTheme.shapes.small,
        color = animatedColor.value,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
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
                RadioButton(state == AnswerState.CORRECT, onClick = null)
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
    QuizScreen(titleId = R.string.practice, MedQuiz(
        0, "", "Текст вопроса", mutableListOf(
            Answer("Вопрос 1", false),
            Answer("Вопрос 2", false),
            Answer("Вопрос 3", false),
            Answer("Вопрос 4", true),
            Answer("Вопрос 5", false)
        )
    ), QuestionState.WrongAnswer(1, 2), { _, _, _ -> }, {})
}

enum class AnswerState {
    CORRECT, WRONG, NONE
}