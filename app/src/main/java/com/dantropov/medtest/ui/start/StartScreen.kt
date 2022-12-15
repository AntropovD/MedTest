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
import com.dantropov.medtest.R

sealed class StartEvent {
    object NavigateToTraining : StartEvent()
    object NavigateToExam : StartEvent()
}


@Composable
fun StartScreen(onEvent: (StartEvent) -> Unit) {
    ScaffoldWithTopBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(stringResource(id = R.string.title))
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
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
            StartContent()
        }
    })
}

@Composable
fun StartContent() {
    Column {
        ProfileCardComposable(stringResource(id = R.string.practice))
        ProfileCardComposable(stringResource(id = R.string.exam))
    }
}

@Composable
fun ProfileCardComposable(stringResource: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.White)
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)) {
            ProfilePictureComposable()
            ProfileContentComposable(stringResource)
        }
    }
}

@Composable
fun ProfilePictureComposable() {
    Column(
        modifier = Modifier.size(80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.quiz),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(60.dp),
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
        Text(stringResource, fontWeight = FontWeight.Bold)
    }
}


@Preview
@Composable
fun StartScreenPreview() {
    StartScreen { }
}