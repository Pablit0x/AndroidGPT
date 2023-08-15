package com.ps.androidgpt.presentation.saved_chats_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.use_case.delete_entry.DeleteEntryUseCase
import com.ps.androidgpt.domain.use_case.get_saved_entries.GetSavedEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedResponsesViewModel @Inject constructor(
    getSavedEntriesUseCase: GetSavedEntriesUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase
) : ViewModel() {


    val chatEntries = getSavedEntriesUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())


    fun deleteEntry(id: String) {
        viewModelScope.launch { deleteEntryUseCase.invoke(id = id) }
    }
}