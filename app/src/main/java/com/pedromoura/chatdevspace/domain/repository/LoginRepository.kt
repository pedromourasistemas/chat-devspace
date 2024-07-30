package com.pedromoura.chatdevspace.domain.repository

import com.pedromoura.chatdevspace.domain.model.User

interface LoginRepository {
    suspend fun login(user: User): User
}