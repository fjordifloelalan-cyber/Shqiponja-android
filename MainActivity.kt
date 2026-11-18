package com.shqiponja.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ShqipRed = Color(0xFFE31B23)
private val Ink = Color(0xFF111111)
private val Soft = Color(0xFFF5F5F5)

data class Chat(val name: String, val message: String, val time: String, val unread: Int = 0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShqiponjaApp() }
    }
}

@Composable
fun ShqiponjaApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var activeChat by remember { mutableStateOf<Chat?>(null) }
    val chats = remember {
        mutableStateListOf(
            Chat("Ardit", "A jemi për kafe sot?", "18:42", 2),
            Chat("Mami ❤️", "Mos harro të më telefonosh", "17:20", 1),
            Chat("Familja", "Foto shumë të bukura!", "16:05"),
            Chat("Erisa", "Faleminderit 😊", "Dje"),
            Chat("Grupi i shokëve", "Shihemi nesër!", "Dje", 4)
        )
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = ShqipRed,
            onPrimary = Color.White,
            background = Color.White,
            surface = Color.White
        )
    ) {
        if (activeChat != null) {
            ConversationScreen(chat = activeChat!!, onBack = { activeChat = null })
        } else {
            Scaffold(
                containerColor = Color.White,
                topBar = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Ink).padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            EagleMark()
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text("SHQIPONJA", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.5.sp)
                                Text("Lidhu. Fol. Ndaj.", color = Color.LightGray, fontSize = 11.sp)
                            }
                            Icon(Icons.Default.Search, contentDescription = "Kërko", tint = Color.White, modifier = Modifier.size(26.dp))
                            Spacer(Modifier.width(16.dp))
                            Icon(Icons.Default.MoreVert, contentDescription = "Më shumë", tint = Color.White)
                        }
                        TabRow(selectedTabIndex = selectedTab, containerColor = Ink, contentColor = ShqipRed) {
                            listOf("Bisedat", "Telefonatat", "Cilësimet").forEachIndexed { index, label ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { Text(label, color = if (selectedTab == index) Color.White else Color.LightGray, fontSize = 12.sp) }
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { selectedTab = 0 }, containerColor = ShqipRed, contentColor = Color.White) {
                        Icon(Icons.Default.Chat, contentDescription = "Bisedë e re")
                    }
                }
            ) { padding ->
                when (selectedTab) {
                    0 -> ChatList(chats, Modifier.padding(padding)) { activeChat = it }
                    1 -> SimplePage("Telefonatat", "Telefonatat audio dhe video do të shfaqen këtu.", Icons.Default.Call, Modifier.padding(padding))
                    else -> SimplePage("Cilësimet", "Profili, privatësia dhe njoftimet do të vendosen këtu.", Icons.Default.Settings, Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun EagleMark() {
    Box(Modifier.size(42.dp).background(ShqipRed, RoundedCornerShape(13.dp)), contentAlignment = Alignment.Center) {
        Text("SH", color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
    }
}

@Composable
fun ChatList(chats: List<Chat>, modifier: Modifier = Modifier, onClick: (Chat) -> Unit) {
    Column(modifier.fillMaxSize()) {
        Text("Mesazhet", modifier = Modifier.padding(start = 18.dp, top = 20.dp, bottom = 10.dp), fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Ink)
        LazyColumn {
            items(chats) { chat ->
                Row(
                    Modifier.fillMaxWidth().clickable { onClick(chat) }.padding(horizontal = 18.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(52.dp).background(Color(0xFFFFE5E5), CircleShape), contentAlignment = Alignment.Center) {
                        Text(chat.name.take(1).uppercase(), color = ShqipRed, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Spacer(Modifier.width(13.dp))
                    Column(Modifier.weight(1f)) {
                        Text(chat.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Ink)
                        Spacer(Modifier.height(4.dp))
                        Text(chat.message, color = Color.Gray, fontSize = 13.sp, maxLines = 1)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(chat.time, color = Color.Gray, fontSize = 11.sp)
                        if (chat.unread > 0) {
                            Spacer(Modifier.height(6.dp))
                            Box(Modifier.size(21.dp).background(ShqipRed, CircleShape), contentAlignment = Alignment.Center) {
                                Text(chat.unread.toString(), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                HorizontalDivider(color = Soft, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ConversationScreen(chat: Chat, onBack: () -> Unit) {
    var draft by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Përshëndetje! Si je?" to false,
            "Mirë, faleminderit. Po ti?" to true,
            "Shumë mirë 😊" to false
        )
    }
    Column(Modifier.fillMaxSize().background(Color(0xFFF8F6F6))) {
        Row(Modifier.fillMaxWidth().background(Ink).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Kthehu", tint = Color.White, modifier = Modifier.clickable { onBack() })
            Spacer(Modifier.width(14.dp))
            Text(chat.name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(Icons.Default.Call, contentDescription = "Telefonatë", tint = Color.White)
            Spacer(Modifier.width(18.dp))
            Icon(Icons.Default.Videocam, contentDescription = "Video", tint = Color.White)
        }
        LazyColumn(Modifier.weight(1f).fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(messages) { msg ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = if (msg.second) Arrangement.End else Arrangement.Start) {
                    Surface(color = if (msg.second) Color(0xFFFFE2E2) else Color.White, shape = RoundedCornerShape(16.dp)) {
                        Text(msg.first, Modifier.padding(horizontal = 14.dp, vertical = 10.dp), color = Ink)
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth().background(Color.White).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = draft, onValueChange = { draft = it }, modifier = Modifier.weight(1f),
                placeholder = { Text("Shkruaj mesazh...") }, shape = RoundedCornerShape(24.dp), singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = { if (draft.isNotBlank()) { messages.add(draft to true); draft = "" } }) {
                Icon(Icons.Default.Send, contentDescription = "Dërgo", tint = ShqipRed)
            }
        }
    }
}

@Composable
fun SimplePage(title: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(icon, contentDescription = null, tint = ShqipRed, modifier = Modifier.size(54.dp))
        Spacer(Modifier.height(16.dp))
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Ink)
        Spacer(Modifier.height(8.dp))
        Text(description, color = Color.Gray)
    }
}
