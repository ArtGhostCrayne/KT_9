import org.junit.Assert.*
import org.junit.Before


class ChatsTest {

    @Before
    fun clearBeforeTest() {
        Chats.clear()
    }

    @org.junit.Test
    fun addMessage() {
        val service = Chats
        assertTrue(service.addMessage(999, 1, "Hi"))
    }

    @org.junit.Test
    fun readChat() {
        val service = Chats
        service.addMessage(999, 1, "Hi")
        service.addMessage(1, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        val list = service.readChat(999,1,30)
        assertEquals(2, list.size)

    }

    @org.junit.Test
    fun delChat() {
        val service = Chats
        service.addMessage(999, 1, "Hi")
        service.addMessage(1, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(3, 999, "Hi")
        service.delChat(1, 999)
        assertEquals(2, service.chats.size)
    }

    @org.junit.Test
    fun delChatMessages() {
        val service = Chats
        service.addMessage(999, 1, "Hi")
        service.addMessage(1, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(3, 999, "Hi")
        service.delChat(1, 999)
        val list = service.readChat(999,1,30)
        assertEquals(0, list.size )
    }

    @org.junit.Test
    fun delMessage() {
        val service = Chats
        service.addMessage(999, 1, "Hi")
        service.addMessage(1, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(3, 999, "Hi")
        service.delMessage(999,2,0)
        val list = service.readChat(999,2,30)
        assertEquals(1, list.size )
    }

    @org.junit.Test
    fun getUnreadChatsCount() {
        val service = Chats
        service.addMessage(999, 1, "Hi")
        service.addMessage(1, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(2, 999, "Hi")
        service.addMessage(3, 999, "Hi")
        assertEquals(3, service.getUnreadChatsCount() )

    }
}