package com.gildedrose

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class Tests {
    @Test
    fun `Add item to stock`() {
        val stock = listOf<Item>()

        assertEquals(listOf<Item>(),
            stock)

        val newStock = stock + Item("banana", aug10, 42)

        assertEquals(listOf(Item("banana", aug10, 42)),
            newStock)

    }
}

val aug10: LocalDate = LocalDate.parse("2025-08-10")



