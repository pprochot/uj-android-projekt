package uj.android.pprochot.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import uj.android.pprochot.models.tables.*

class DatabaseConfig(config: ApplicationConfig) {

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = config.property("database.jdbcUrl").getString()
        driverClassName = config.property("database.driverClassName").getString()
        username = config.property("database.username").getString()
        password = config.property("database.password").getString()
    }

    val database = Database.connect(HikariDataSource(hikariConfig))

    fun createTables() {
        transaction {
            SchemaUtils.create(
                UsersTable, CategoriesTable, ProductsTable,
                CartsTable, CartsProductTable,
                OrdersTable, OrdersProductTable
            )
        }
    }
}a