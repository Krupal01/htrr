package com.htr.htrr.data.repository

import com.htr.htrr.domain.model.User
import com.htr.htrr.domain.repository.UserRepository
import com.htr.htrr.base.Result
import com.htr.htrr.base.getResult
import com.htr.htrr.data.remote.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun getUser(): Result<User> = withContext(dispatcher){
        try {
//            val response = apiService.getUser()
            delay(400)
            Result.Success(User(name = "hello hello", age = 10))
        } catch (e: Exception) {
            Result.Error(exception = e)
        }
    }
}