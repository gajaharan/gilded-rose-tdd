package com.gildedrose

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.StringTemplateSource
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.intellij.lang.annotations.Language
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class ListStockTests {
    private val now = LocalDate.parse("2025-08-10")

    @Test
    fun `print non empty stock list`() {
        val stock = listOf(
            Item("banana", now.minusDays(1), 42),
            Item("kumquat", now.plusDays(1), 101)
        )

        val server: Server = Server(stock)
        val client: RoutingHttpHandler = server.routes
        val response: Response = client(Request(Method.GET, "/"))
        assertEquals(templateSource, response.bodyString())
    }
}

@Language("HTML")
val templateSource = """
    <html>
    <body>
    <tr></tr>
    </body>
    </html>
    """.trimIndent()

class Client(server: Server)

class Server(stock: List<Item>) {
    val handlebars = Handlebars()
    val rootTemplate = handlebars.compile(
        StringTemplateSource("no such file", templateSource)
    )
    val routes = routes(
        "/" bind Method.GET to { request ->
            Response(Status.OK).body(rootTemplate.apply(stock))
        }
    )
}
