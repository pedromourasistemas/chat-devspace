package com.pedromoura.chatdevspace.domain.model

data class Message(
    val id: String? = null, // Chave única da mensagem no Firebase
    val text: String? = null,
    val senderId: String? = null, // ID do usuário que enviou a mensagem
    val timestamp: Long? = null
) {
    constructor() : this(null, null, null, null)
}