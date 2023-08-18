package com.ps.androidgpt.presentation.prompts_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.data.local.entity.PromptEntity
import com.ps.androidgpt.domain.model.PromptEntry
import com.ps.androidgpt.domain.model.toPromptEntity
import com.ps.androidgpt.domain.use_case.delete_prompt.DeletePromptUseCase
import com.ps.androidgpt.domain.use_case.edit_prompt.EditPromptUseCase
import com.ps.androidgpt.domain.use_case.get_saved_prompts.GetSavedPromptsUseCase
import com.ps.androidgpt.domain.use_case.insert_prompt.InsertPromptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromptViewModel @Inject constructor(
    getSavedPromptsUseCase: GetSavedPromptsUseCase,
    private val insertPromptUseCase: InsertPromptUseCase,
    private val deletePromptUseCase: DeletePromptUseCase,
    private val editPromptUseCase: EditPromptUseCase
) : ViewModel() {

    val prompts = getSavedPromptsUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun insertPrompt(prompt: PromptEntry): PromptEntity {
        val promptEntity = prompt.toPromptEntity()
        viewModelScope.launch {
            insertPromptUseCase(promptEntity = promptEntity)
        }
        return promptEntity
    }

    fun deletePrompt(id: String) {
        viewModelScope.launch {
            deletePromptUseCase(id = id)
        }
    }

    fun editPrompt(promptEntry: PromptEntry) {
        viewModelScope.launch {
            editPromptUseCase(promptEntity = promptEntry.toPromptEntity())
        }
    }
}