package com.pedromoura.chatdevspace.presentation.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pedromoura.chatdevspace.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(private val database: FirebaseDatabase, private val context: Context) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    var username: String = ""
    var password: String = ""
    var userID: String = ""

    init {
        val messagesRef = database.reference.child("messages")
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messagesList = mutableListOf<Message>()
                dataSnapshot.children.forEach { child ->
                    val message = child.getValue(Message::class.java)
                    message?.let { messagesList.add(it) }
                }
                _messages.value = messagesList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    var messageText by mutableStateOf("")
        private set

    fun onMessageTextChanged(text: String) {
        messageText = text
    }

    fun sendMessage() {
        viewModelScope.launch {
            userID = sharedPreferences.getString("USERID", "") ?: ""
            username = sharedPreferences.getString("USERNAME", "") ?: ""
            password = sharedPreferences.getString("PASSWORD", "") ?: ""
        }

        val newMessage = Message(
            // Gere um ID único para a mensagem
            UUID.randomUUID().toString(),
            messageText,
            userID ?: "",
            System.currentTimeMillis() / 1000L // Timestamp em segundos
        )

        database.reference.child("messages").push().setValue(newMessage)
            .addOnSuccessListener {
                // Mensagem enviada com sucesso
            }
            .addOnFailureListener {
                // Tratar erros
            }

        messageText = ""
    }
}