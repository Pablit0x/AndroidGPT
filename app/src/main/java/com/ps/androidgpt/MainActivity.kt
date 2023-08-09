package com.ps.androidgpt

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ps.androidgpt.presentation.chat_screen.ChatViewModel
import com.ps.androidgpt.presentation.ui.theme.AndroidGPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidGPTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<ChatViewModel>()
                    val state by viewModel.state.collectAsState()

                    Button(
                        modifier = Modifier.fillMaxSize(0.2f),
                        onClick = { viewModel.getChatGPTResponse("Tell me joke!") }){
                        Text("Tell me joke!")
                    }

                    state.response?.let {
                        Log.d("LOLIPOP", it)
                    }

                }
            }
        }
    }
}