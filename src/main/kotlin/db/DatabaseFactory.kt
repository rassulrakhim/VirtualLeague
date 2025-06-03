package src.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import src.db.models.User
import src.db.models.Game
import java.io.File

object DatabaseFactory {
    fun init() {
        val dbFile = File("data/league.db")
        dbFile.parentFile.mkdirs() // Create "data/" if it doesn't exist
        Database.connect("jdbc:sqlite:${dbFile.path}", driver = "org.sqlite.JDBC")
        transaction {
            // create missing tables
            SchemaUtils.create(User, Game /*, other tables... */)
        }
    }
}
