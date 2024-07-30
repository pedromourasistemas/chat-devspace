package com.pedromoura.chatdevspace.domain.usecase

import com.pedromoura.chatdevspace.domain.model.User
import com.pedromoura.chatdevspace.domain.repository.LoginRepository
import javax.inject.Inject

class GetLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : GetLoginUseCase {
    override suspend fun invoke(user: User): User {
        return loginRepository.login(user)
    }
}