package com.dantropov.medtest.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dantropov.medtest.R

sealed class StartEvent {
    object NavigateToTraining : StartEvent()
    object NavigateToExam : StartEvent()
}


@Composable
fun StartScreen(onEvent: (StartEvent) -> Unit) {
    ScaffoldWithTopBar(onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(onEvent: (StartEvent) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(stringResource(id = R.string.title))
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
            StartContent(onEvent)
        }
    })
}

@Composable
fun StartContent(onEvent: (StartEvent) -> Unit) {
    Column {
        ProfileCardComposable(stringResource(id = R.string.practice)) { onEvent(StartEvent.NavigateToTraining) }
        ProfileCardComposable(stringResource(id = R.string.exam)) { onEvent(StartEvent.NavigateToExam) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCardComposable(title: String, onEventClick: () -> Unit) {
    Card(
        onClick = onEventClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.White)
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)) {
            ProfilePictureComposable()
            ProfileContentComposable(title)
        }
    }
}

@Composable
fun ProfilePictureComposable() {
    Column(
        modifier = Modifier.size(120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.quiz),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp),
            contentDescription = "Profile picture holder"
        )
    }
}

@Composable
fun ProfileContentComposable(stringResource: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        Text(stringResource, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}


@Preview
@Composable
fun StartScreenPreview() {
    StartScreen { }
}