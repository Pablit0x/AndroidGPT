package com.ps.androidgpt.presentation.prompts_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.use_case.delete_prompt.DeletePromptUseCase
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
    private val deletePromptUseCase: DeletePromptUseCase,
) : ViewModel() {

    val prompts = getSavedPromptsUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun deletePrompt(id: String) {
        viewModelScope.launch {
            deletePromptUseCase(id = id)
        }
    }
}