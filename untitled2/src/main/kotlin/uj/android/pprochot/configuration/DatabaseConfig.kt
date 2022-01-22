package uj.android.pprochot.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import uj.android.pprochot.models.tables.*

class DatabaseConfig {

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/postgres"
        driverClassName = "org.postgresql.Driver"
        username = "shop_app"
        password = "shop"
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
}