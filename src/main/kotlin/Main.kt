package bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.jetbrains.exposed.sql.transactions.transaction
import src.db.DatabaseFactory
import src.db.models.GameEntity
import src.db.models.UserEntity

fun main() {
    // Initialize database
    DatabaseFactory.init()

    val telegramBot = bot {
        token = System.getenv("TELEGRAM_BOT_TOKEN")  ?: error("Bot token not found in environment variables")

        dispatch {
            command("startgame") {
                val botUsername = bot.getMe().get().username!!
                val groupChatId = message.chat.id

                transaction {
                    val game = GameEntity.findOrCreate(groupChatId)
                    bot.sendMessage(
                        chatId = ChatId.fromId(groupChatId),
                        text = "🏁 Game started! Click below to join and submit your event.",
                        replyMarkup = game.getJoinButton(botUsername)
                    )
                }

            }

            command("start") {
                val user = message.from
                val textParts = message.text?.split(" ") ?: emptyList()
                val gameId: String? = textParts.getOrNull(1)
                val userId = user!!.id
                val name = listOfNotNull(user.firstName, user.lastName).joinToString(" ")
                if (gameId.isNullOrEmpty()){
                    bot.sendMessage(
                        chatId = ChatId.fromId(userId),
                        text = "❌ Sorry $name!\n\n Your game was not found."
                    )
                }else{
                    /// add user to game
                    bot.sendMessage(
                        chatId = ChatId.fromId(userId),
                        text = "✅ Lets go $name!\n\n Later you can add your events and odds. WAIT SUKA!"
                    )
                }
            }

        }
    }

    telegramBot.startPolling()
}
