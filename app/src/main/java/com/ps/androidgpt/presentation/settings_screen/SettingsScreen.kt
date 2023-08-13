package com.ps.androidgpt.presentation.settings_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.navigation.Screen
import com.ps.androidgpt.utils.Constants
import com.ps.androidgpt.utils.dataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val snackBarState by remember { mutableStateOf(SnackbarHostState()) }

    var isMenuDropdownMenuExpended by remember {
        mutableStateOf(false)
    }

    var apiKey by remember { mutableStateOf(Constants.INVALID_API_KEY) }
    var model by remember { mutableStateOf(Constants.DEFAULT_MODEL_ID) }

    LaunchedEffect(Unit) {
        context.dataStore.data.collect { settings ->
            apiKey = settings.apiKey
            model = settings.model
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        MyNavigationDrawer(
            currentScreenId = Screen.SettingsScreen.id,
            onItemClick = { destination ->
                scope.launch { drawerState.close() }
                navController.navigate(destination)
            })
    }) {
        Scaffold(snackbarHost = { SnackbarHost(snackBarState) }, topBar = {
            MyTopAppBar(
                title = stringResource(id = R.string.settings), drawerState = drawerState
            )
        }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    label = { Text(stringResource(id = R.string.api_key)) },
                    value = apiKey,
                    onValueChange = {
                        apiKey = it
                    })

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(modifier = Modifier.fillMaxWidth(),
                    expanded = isMenuDropdownMenuExpended,
                    onExpandedChange = {
                        isMenuDropdownMenuExpended = !isMenuDropdownMenuExpended
                    }) {
                    TextField(
                        value = model,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(id = R.string.gpt_model)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuDropdownMenuExpended) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = isMenuDropdownMenuExpended, onDismissRequest = {
                        isMenuDropdownMenuExpended = false
                    }) {
                        Constants.models.forEach { modelItem ->
                            DropdownMenuItem(text = { Text(modelItem) }, onClick = {
                                isMenuDropdownMenuExpended = false
                                model = modelItem
                            })
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Button(modifier = Modifier.padding(16.dp), onClick = {
                        scope.launch {
                            context.dataStore.updateData {
                                it.copy(
                                    model = model, apiKey = apiKey
                                )
                            }
                            snackBarState.showSnackbar(message = context.getString(R.string.settings_updated))
                        }
                    }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}