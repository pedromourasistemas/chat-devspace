package com.pedromoura.chatdevspace.data.repository

import com.pedromoura.chatdevspace.data.datasource.ApiService
import com.pedromoura.chatdevspace.domain.model.User
import com.pedromoura.chatdevspace.domain.repository.LoginRepository

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {
    override suspend fun login(user: User): User {
        return User("pedromoura", "123456")//apiService.login(user)
    }
}