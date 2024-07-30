package com.pedromoura.chatdevspace.domain.usecase

import com.pedromoura.chatdevspace.domain.model.User

interface GetLoginUseCase {
    suspend operator fun invoke(user: User) : User
}