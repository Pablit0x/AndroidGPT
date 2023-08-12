package com.ps.androidgpt.presentation.saved_chats_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.use_case.delete_entry.DeleteEntryUseCase
import com.ps.androidgpt.domain.use_case.get_saved_entries.GetSavedEntriesUseCase
import com.ps.androidgpt.presentation.chat_screen.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedResponsesViewModel @Inject constructor(
    private val getSavedEntriesUseCase: GetSavedEntriesUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    init {
        getSavedEntries()
    }

    private fun getSavedEntries() {
        viewModelScope.launch {
            getSavedEntriesUseCase.invoke().collectLatest { chatEntries ->
                _state.update {
                    it.copy(
                        chatEntries = chatEntries
                    )
                }
            }
        }
    }

    fun deleteEntry(id: String) {
        viewModelScope.launch { deleteEntryUseCase.invoke(id = id) }
    }
}