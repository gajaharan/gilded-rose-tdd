package com.gildedrose

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class Tests {
    private val sellBy = LocalDate.parse("2025-08-10")
    @Test
    fun `Add item to stock`() {
        val stock = listOf<Item>()

        assertEquals(listOf<Item>(),
            stock)

        val newStock = stock + Item("banana", sellBy, 42)

        assertEquals(listOf(Item("banana", sellBy, 42)),
            newStock)

    }
}



