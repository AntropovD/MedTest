package com.dantropov.medtest.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dantropov.medtest.R
import com.dantropov.medtest.compose.LoadingAnimation

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarLoading() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.practice),
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
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarReady(
    state: TrainingUiState.Ready,
    onTrainingLevelClick: (TrainingLevelData) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.practice),
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
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TrainingScreen(state.trainingLevels, onTrainingLevelClick)
        }
    })
}


@Composable
fun TrainingScreen(
    trainingLevels: List<TrainingLevelData>,
    onTrainingLevelClick: (TrainingLevelData) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.practice_description),
            style = MaterialTheme.typography.bodyLarge
        )
        TrainingsList(trainingLevels = trainingLevels, onTrainingLevelClick)
    }
}


@Composable
fun TrainingsList(
    trainingLevels: List<TrainingLevelData>,
    onTrainingLevelClick: (TrainingLevelData) -> Unit
) {
    LazyColumn() {
        items(trainingLevels) {
            Surface(modifier = Modifier.clickable { onTrainingLevelClick(it) }) {
                TrainingListItem(data = it)
            }
        }
    }
}

@Composable
fun TrainingListItem(data: TrainingLevelData) {
    Spacer(Modifier.height(16.dp))
    Card(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "${data.start} - ${data.end}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun PreviewTrainingReadyScreen() {
    ScaffoldWithTopBarReady(
        state = TrainingUiState.Ready(
            listOf(
                TrainingLevelData(1, 10),
                TrainingLevelData(11, 20),
                TrainingLevelData(21, 30)
            )
        )
    ) {}
}