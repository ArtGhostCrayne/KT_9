import java.time.LocalDateTime

data class Message(
    var sender: Int,
    var date: LocalDateTime = LocalDateTime.now(),
    val text: String,
    var read: Boolean = false
)

data class Chat(var messages: MutableList<Message> = mutableListOf())

object Chats {
    var chats: MutableMap<Pair<Int, Int>, Chat> = mutableMapOf()

    fun clear() {
        chats.clear()
    }

    fun addMessage(receiverId: Int, senderId: Int, text: String): Boolean {
        return chats.getOrPut(receiverId toSorted senderId) { Chat() }.messages.add(
            Message(
                sender = senderId,
                text = text
            )
        )
    }

    fun readChat(receiverId: Int, senderId: Int, count: Int): List<Message> {
        return chats[receiverId toSorted senderId]?.messages?.asSequence()?.take(count).orEmpty().onEach { it.read }
            .toList()
    }

    fun delChat(receiverId: Int, senderId: Int) {
        chats[receiverId toSorted senderId]?.messages?.clear()
        chats.remove(receiverId toSorted senderId)
    }

    fun delMessage(receiverId: Int, senderId: Int, index: Int) {
        chats[receiverId toSorted senderId]?.messages?.removeAt(index)
        if (chats[receiverId toSorted senderId]?.messages?.size == 0) chats.remove(receiverId toSorted senderId)
    }

    fun getUnreadChatsCount(): Int {
        return chats.values.count { it.messages.filter { !it.read }.isNotEmpty() }
    }

    fun getChats() {
        chats.forEach {
            print(it.value.messages.last())
        }
    }

}

private infix fun Int.toSorted(senderId: Int): Pair<Int, Int> =
    if (this < senderId) Pair(senderId, this) else Pair(this, senderId)

fun main(args: Array<String>) {

    val service = Chats
    service.addMessage(999, 1, "Hi")
    service.addMessage(999, 2, "Hi")
    service.addMessage(1, 999, "Hi")
    service.addMessage(999, 1, "How are you")
    service.addMessage(1, 999, "I am fine")
    service.addMessage(999, 1, "Me too")

    println(service.readChat(999, 1, 30))


}
