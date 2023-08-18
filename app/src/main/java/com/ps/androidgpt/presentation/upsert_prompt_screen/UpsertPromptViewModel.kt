package com.ps.androidgpt.presentation.upsert_prompt_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.model.PromptEntry
import com.ps.androidgpt.domain.model.toPromptEntity
import com.ps.androidgpt.domain.use_case.edit_prompt.EditPromptUseCase
import com.ps.androidgpt.domain.use_case.insert_prompt.InsertPromptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertPromptViewModel @Inject constructor(
    private val insertPromptUseCase: InsertPromptUseCase,
    private val editPromptUseCase: EditPromptUseCase
) : ViewModel() {

    fun insertPrompt(prompt: PromptEntry) {
        viewModelScope.launch {
            insertPromptUseCase(promptEntity = prompt.toPromptEntity())
        }
    }

    fun editPrompt(promptEntry: PromptEntry) {
        viewModelScope.launch {
            editPromptUseCase(promptEntity = promptEntry.toPromptEntity())
        }
    }

}