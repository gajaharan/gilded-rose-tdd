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

        val server: Server = Server(stock) { now }
        val client: RoutingHttpHandler = server.routes
        val response: Response = client(Request(Method.GET, "/"))
        assertEquals(expected, response.bodyString())
    }
}

@Language("HTML")
val expected = """
    <html>
    <body>
    <tr>
        <td>banana</td></tr>
        <td>9 August 2025</td>
        <td>-1</td>
        <td>42</td>
    </tr>
    <tr>
        <td>kumquat</td></tr>
        <td>11 August 2025</td>
        <td>1</td>
        <td>101</td>
    </tr>

    </body>
    </html>
    """.trimIndent()

class Client(server: Server)

class Server(
    stock: List<Item>,
    clock: () -> LocalDate = { LocalDate.now() }
) {
    val handlebars = Handlebars()
    val rootTemplate = handlebars.compile(
        StringTemplateSource("no such file", templateSource)
    )
    val routes = routes(
        "/" bind Method.GET to { request ->
            val now = clock()
            Response(Status.OK).body(rootTemplate
                .apply(stock.map {
                    it.toMap(now)
                }))
        }
    )
}

private fun Item.toMap(now: LocalDate): Map<String,String> = mapOf(
    "name" to name,
    "sellByDate" to format.format(sellByDate),
    "sellByDays" to this.daysUntilSellBy(now).toString(),
    "quantity" to quantity.toString()
)
@Language("HTML")
val templateSource = """
    <html>
    <body>
    {{#each}}<tr>
        <td>{{this.name}}</td></tr>
        <td>{{this.sellByDate}}</td>
        <td>{{this.sellByDays}}</td>
        <td>{{this.quantity}}</td>
    </tr>
    {{/each}}
    </body>
    </html>
    """.trimIndent()
