package com.pedromoura.chatdevspace.presentation.chat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pedromoura.chatdevspace.domain.model.Message
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.FirebaseDatabase
import com.pedromoura.chatdevspace.ui.theme.Purple40
import com.pedromoura.chatdevspace.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false,
            contentPadding = PaddingValues(8.dp),
        ) {
            items(messages) { message ->
                AlignedMessageBubble(message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = viewModel.messageText,
                onValueChange = { viewModel.onMessageTextChanged(it) },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { viewModel.sendMessage() }) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun AlignedMessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Se a mensagem for do outro usuário, adiciona um spacer para empurrar a bolha para a direita
        if (message.senderId != "1") {
            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp)
                .background(
                    if (message.senderId == "1")
                        Purple40 // Bolha do usuário 1
                    else
                        Color.Green // Bolha de outro usuário
                )
                .padding(8.dp)
        ) {
            message.text?.let {
                Text(
                    text = it,
                    color = Color.White
                )
            }
        }

        // Se a mensagem for do usuário, adiciona um spacer para empurrar a bolha para a esquerda
        if (message.senderId == "1") {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    val database = FirebaseDatabase.getInstance()
    val fakeViewModel = ChatViewModel(database, LocalContext.current)
    ChatScreen(fakeViewModel)
}