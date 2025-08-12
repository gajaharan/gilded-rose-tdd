package com.gildedrose

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.RoutingHttpHandler
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

        val server = Server(stock) { now }
        val client: RoutingHttpHandler = server.routes
        val response: Response = client(Request(Method.GET, "/"))
        assertEquals(expected, response.bodyString())
    }
}

@Language("HTML")
private val expected = """
 <html lang="en">
 <body>
 <h1>10 August 2025</h1>
 <table>
<th>
     <td>Name</td>
     <td>Sell by date</td>
     <td>Sell by days</td>
     <td>Quantity</td>
 </th>
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
 
 </table>
 </body>
 </html>
    """.trimIndent()
