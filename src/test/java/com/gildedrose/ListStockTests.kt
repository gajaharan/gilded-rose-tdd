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
private val expected = """
    <html>
    <body>
    <tr>
        <td>banana</td>
        <td>9 August 2025</td>
        <td>-1</td>
        <td>42</td>
    </tr>
    <tr>
        <td>kumquat</td>
        <td>11 August 2025</td>
        <td>1</td>
        <td>101</td>
    </tr>

    </body>
    </html>
    """.trimIndent()
