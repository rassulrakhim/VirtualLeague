package src.db.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object User : IntIdTable() {
    val telegramId = long("telegram_id").uniqueIndex()
    val username = varchar("username", 64).nullable()
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(User) {
        fun findOrCreate(telegramId: Long, name: String): UserEntity {
            return find { User.telegramId eq telegramId }.firstOrNull() ?: new {
                this.telegramId = telegramId
                this.username = name
            }
        }
    }

    var telegramId by User.telegramId
    var username by User.username
}
