package com.htr.htrr.domain.repository

import com.htr.htrr.base.Result
import com.htr.htrr.domain.model.User

interface UserRepository {
    suspend fun getUser() : Result<User>
}