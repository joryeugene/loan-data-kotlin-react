import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

fun main() {
    val dbPath = "./LoanStats_securev1_2017Q4.sqlite"
    if (!File(dbPath).exists()) {
        logger.error { "Database file not found: $dbPath" }
        return
    }

    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:sqlite:$dbPath"
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson { }
        }
        routing {
            get("/loans") {
                try {
                    val date = call.request.queryParameters["date"]
                    val state = call.request.queryParameters["state"]
                    val grade = call.request.queryParameters["grade"]
                    val ficoLow = call.request.queryParameters["ficoLow"]?.toIntOrNull()
                    val ficoHigh = call.request.queryParameters["ficoHigh"]?.toIntOrNull()
                    val sortBy = call.request.queryParameters["sortBy"]
                    val order = call.request.queryParameters["order"] ?: "asc"
                    val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 100

                    val sortOrder = if (order.equals("desc", ignoreCase = true)) SortOrder.DESC else SortOrder.ASC

                    val loans = transaction {
                        LoanStats.selectAll()
                            .apply {
                                date?.let { andWhere { LoanStats.issueDate eq it } }
                                state?.let { andWhere { LoanStats.addrState eq it } }
                                grade?.let { andWhere { LoanStats.grade eq it } }
                                ficoLow?.let { andWhere { LoanStats.ficoRangeLow greaterEq it } }
                                ficoHigh?.let { andWhere { LoanStats.ficoRangeLow lessEq it } }
                            }
                            .orderBy(
                                when (sortBy) {
                                    "date" -> LoanStats.issueDate to sortOrder
                                    "state" -> LoanStats.addrState to sortOrder
                                    "grade" -> LoanStats.grade to sortOrder
                                    "fico" -> LoanStats.ficoRangeLow to sortOrder
                                    else -> LoanStats.id to sortOrder
                                }
                            )
                            .limit(limit)
                            .map {
                                Loan(
                                    it[LoanStats.id],
                                    it[LoanStats.grade],
                                    it[LoanStats.addrState],
                                    it[LoanStats.issueDate],
                                    it[LoanStats.ficoRangeLow]
                                )
                            }
                    }
                    logger.info { "Retrieved ${loans.size} loans" }
                    call.respond(loans)
                } catch (e: Exception) {
                    logger.error(e) { "Error retrieving loans" }
                    call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.localizedMessage}")
                }
            }
        }
    }.start(wait = true)

    logger.info { "Server started at http://localhost:8080" }
}

object LoanStats : Table("loan_stats") {
    val id = integer("id").autoIncrement().primaryKey()
    val grade = varchar("grade", 2)
    val addrState = varchar("addr_state", 2)
    val issueDate = varchar("issue_d", 10)
    val ficoRangeLow = integer("fico_range_low")
}

data class Loan(val id: Int, val grade: String, val addrState: String, val issueDate: String, val ficoRangeLow: Int)
