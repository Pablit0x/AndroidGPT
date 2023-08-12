package com.ps.androidgpt.presentation.saved_chats_screen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.use_case.get_saved_entries.GetSavedEntriesUseCase
import com.ps.androidgpt.presentation.chat_screen.ChatState
import com.ps.androidgpt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedChatsViewModel @Inject constructor(private val getSavedEntriesUseCase: GetSavedEntriesUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getSavedEntries()
            }
        }
    }

    private suspend fun getSavedEntries() {
        val chatEntries = viewModelScope.async {
            getSavedEntriesUseCase.invoke().first()
        }.await()

        _state.update {
            it.copy(
                chatEntries = chatEntries
            )
        }
    }
}