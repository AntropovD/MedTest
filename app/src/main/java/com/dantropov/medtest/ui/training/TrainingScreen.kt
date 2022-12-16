package com.dantropov.medtest.ui.training

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dantropov.medtest.R
import com.dantropov.medtest.compose.LoadingAnimation

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
fun ScaffoldWithTopBarReady(state: TrainingUiState.Ready) {
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
            TrainingsList(state.trainingLevels)
        }
    })
}

@Composable
fun TrainingsList(trainingLevels: List<TrainingLevelData>) {
    LazyColumn() {
        items(trainingLevels) {
            TrainingListItem(data = it)
        }
    }
}

@Composable
fun TrainingListItem(data: TrainingLevelData) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.White),

        shape = RoundedCornerShape(corner = CornerSize(4.dp))
    ) {
        Text(
            text = "${data.start} - ${data.end}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewTrainingListItem() {
    TrainingListItem(data = TrainingLevelData(1, 20))
}