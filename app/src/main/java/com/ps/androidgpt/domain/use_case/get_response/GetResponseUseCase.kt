package com.ps.androidgpt.domain.use_case.get_response

import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.data.remote.dto.toStringResponse
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetResponseUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(request: ChatRequestDto): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val chatResponse = chatRepository.getChatCompletion(request = request).toStringResponse()
            emit(Resource.Success<String>(data = chatResponse))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}