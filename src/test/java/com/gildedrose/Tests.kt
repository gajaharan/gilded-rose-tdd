package com.gildedrose

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class Tests {
    @Test
    fun test() {
        val stock = listOf<Item>()

        assertEquals(listOf<Item>(),
            stock)

        val newStock = stock + Item("banana", LocalDate.now(), 42)

        assertEquals(listOf(Item("banana", LocalDate.now(), 42)),
            newStock)

    }
}

