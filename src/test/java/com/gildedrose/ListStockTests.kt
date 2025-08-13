package com.gildedrose

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(ApprovalTest::class)
class ListStockTests {
    private val now = LocalDate.parse("2025-08-10")

    @Test
    fun `print stock list`(approver: Approver) {
        val stock = listOf(
            Item("banana", now.minusDays(1), 42),
            Item("kumquat", now.plusDays(1), 101)
        )

        val routes = routes(stock) { now }
        val response: Response = routes(Request(Method.GET, "/"))

        approver.assertApproved(response, Status.OK)
    }
}
