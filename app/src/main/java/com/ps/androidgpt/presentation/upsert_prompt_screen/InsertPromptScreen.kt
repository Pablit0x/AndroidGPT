package com.ps.androidgpt.presentation.upsert_prompt_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.domain.model.PromptEntry
import com.ps.androidgpt.presentation.composables.gradientSurface

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InsertPromptScreen(
    navController: NavController,
    onInsert: (promptEntry: PromptEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    var updatedPrompt by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    val softKeyboard = LocalSoftwareKeyboardController.current

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ), title = {
                Text(
                    text = stringResource(id = R.string.add_prompt_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
            }, modifier = Modifier.padding(start = 16.dp)) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = null
                )
            }
        }, actions = {
            IconButton(onClick = {
                updatedPrompt.let { onInsert(PromptEntry(prompt = it)) }
                navController.popBackStack()
            }, modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    imageVector = Icons.Default.Check, contentDescription = null
                )
            }
        })
    }) { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = updatedPrompt ?: "",
                onValueChange = {
                    updatedPrompt = it
                },
                placeholder = { Text(text = stringResource(id = R.string.enter_prompt)) },
                minLines = 6,
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .gradientSurface()
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        focusRequester.requestFocus()
                        softKeyboard?.show()
                    }
            )
        }
    }
}