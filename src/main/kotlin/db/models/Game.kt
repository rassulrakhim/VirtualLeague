package src.db.models

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object Game : IntIdTable() {
    val groupId = long("group_id")
}

class GameEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GameEntity>(Game) {
        fun findOrCreate(groupId: Long): GameEntity {
            return find { Game.groupId eq groupId }.firstOrNull() ?: new {
                    this.groupId = groupId
            }
        }
    }

    var groupId by Game.groupId

    fun getJoinButton(botUsername: String): InlineKeyboardMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(
                    InlineKeyboardButton.Url(
                        text = "Join Game",
                        url = "https://t.me/$botUsername?start=$groupId"
                    )
                )
            )
        )
    }
}