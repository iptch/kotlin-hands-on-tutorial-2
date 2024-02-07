package com.example.kotlin.chat.service

import com.github.javafaker.Faker
import org.springframework.stereotype.Service
import java.net.URL
import java.time.Instant
import kotlin.random.Random

@Service
class FakeMessageService : MessageService {

    val users: Map<String, UserVM> = mapOf(
        "Shakespeare"  to UserVM("Shakespeare", URL("https://upload.wikimedia.org/wikipedia/commons/c/c5/First_Folio_%28cropped%29.jpg")),
        "RickAndMorty" to UserVM("RickAndMorty", URL("https://images.english.elpais.com/resizer/FW8hybtfFiHQXxdaeGVHURNBU-4=/1960x1470/cloudfront-eu-central-1.images.arcpublishing.com/prisa/LKBJXYFNZFA4ZOPCAUZZBXZMQA.PNG")),
        "Yoda"         to UserVM("Yoda", URL("https://news.toyark.com/wp-content/uploads/sites/4/2019/03/SH-Figuarts-Yoda-001.jpg"))
    )

    val usersQuotes: Map<String, () -> String> = mapOf(
        "Shakespeare"  to { Faker.instance().shakespeare().asYouLikeItQuote() },
        "RickAndMorty" to { Faker.instance().rickAndMorty().quote() },
        "Yoda"         to { Faker.instance().yoda().quote() }
    )

    override fun latest(): List<MessageVM> {
        val count = Random.nextInt(1, 15)
        return (0..count).map {
            val user = users.values.random()
            val userQuote = usersQuotes.getValue(user.name).invoke()

            MessageVM(userQuote, user, Instant.now(), Random.nextBytes(10).toString())
        }.toList()
    }

    override fun after(messageId: String): List<MessageVM> {
        return latest()
    }

    override fun post(message: MessageVM) {
        TODO("Not yet implemented")
    }
}
