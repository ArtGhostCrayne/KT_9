import java.time.LocalDateTime

data class Message(
    val messageId: Int,
    val receiverId: Int,
    val senderId: Int,
    val chatId: Int,
    var date: LocalDateTime = LocalDateTime.now(),
    val text: String,
    var status: Int = 0
)

data class Chat(val chatId: Int, val receiverId: Int, val senderId: Int, var unRead: Boolean = true)

object Chats {
    private var lastChatId = 0
    private var lastMessageId = 0
    var messages: MutableList<Message> = arrayListOf()
    private var chats: MutableList<Chat> = arrayListOf()

    fun addChat(receiverId: Int, senderId: Int): Int {
        chats.add(Chat(++lastChatId, receiverId, senderId))
        return lastChatId
    }

    fun checkChat(receiverId: Int, senderId: Int): Int {
        return when (chats.none { it.receiverId == receiverId && it.senderId == senderId }) {
            true -> 0
            false -> chats.last { it.receiverId == receiverId && it.senderId == senderId }.chatId
        }
    }

    fun addMessage(receiverId: Int, senderId: Int, chatId: Int, text: String): Boolean {
        chats.last { it.chatId == chatId }.chatId
        return messages.add(Message(++lastMessageId, receiverId, senderId, chatId, text = text))
    }

    fun newMessage(receiverId: Int, senderId: Int, text: String) {
        var id = checkChat(receiverId, senderId)

        if (id == 0) {
            id = addChat(receiverId, senderId)
        }
        addMessage(receiverId, senderId, id, text)
    }

    fun getChatsByUser(userId: Int): List<Chat> {
        return chats.filter { it.receiverId == userId }
    }

    fun getUnreadChatsCount(receiverId: Int): Int {
        return chats.filter { it.receiverId == receiverId && it.unRead }.size
    }

    fun delChat(id: Int) {
//        chats = chats.filter { it.chatId != id }.toMutableList()
        chats.dropWhile { it.chatId == id }
        messages = messages.dropWhile { it.chatId == id }.toMutableList()
    }

    fun delMessage(messageId: Int) {
        val chatId = messages.last { it.messageId == messageId }.chatId
        messages = messages.dropWhile { it.messageId == messageId }.toMutableList()
        var mes = messages.filter { it.messageId == messageId }
        if (mes.isEmpty()) {
            delChat(chatId)
        }
    }

    fun getChats() {
        chats.forEach {
            val mes = messages.last { message: Message -> message.chatId == it.chatId }.text
            if (mes.isEmpty()) println("No messages")
            else println(mes)
        }
    }

    fun readChats(chatId: Int, messageId: Int, count: Int) {
        val mes = messages.filter { it.messageId >= messageId && it.chatId == chatId }
        val cnt = 0
        mes.forEach {
            messages[messages.indexOfLast { message: Message -> message.messageId == it.messageId }].status = 1
            chats[chats.indexOfLast { chat: Chat -> chat.chatId == it.chatId }].unRead = false
            println("${it.date}: ${it.text}")
        }
    }
}


fun main(args: Array<String>) {

    val service = Chats
    service.newMessage(999, 1, "chat 1 mes 1")
    service.newMessage(999, 3, "chat 3 mes 1")
    service.newMessage(999, 2, "chat 2 mes 1")
    service.newMessage(999, 1, "chat 1 mes 2")
    service.newMessage(999, 2, "chat 2 mes 2")
    service.newMessage(999, 2, "chat 2 mes 3")
    service.newMessage(999, 1, "chat 1 mes 4")
    service.newMessage(999, 1, "chat 1 mes 5")
    service.newMessage(2, 1, "chat 4 mes 1")

    println(service.getChatsByUser(2))

//    println(service.getChats())

    service.readChats(1, 5, 5)


}