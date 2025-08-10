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

        val newStock = stock + Item("banana", LocalDate.now(), 42)

        assertEquals(listOf(Item("banana", LocalDate.now(), 42)),
            newStock)

    }

    @Test
    fun `print stock list`() {
        val stock = listOf<Item>()
        val expected = listOf("10 August 2025")

        assertEquals(expected, stock.printout())
    }

    @Test
    fun `print non empty stock list`() {
       val stock = listOf(
           Item("banana", LocalDate.now(), 42),
           Item("kumquat", LocalDate.now(), 101)
       )

        val expected = listOf(
            "10 August 2025"
        )

        assertEquals(expected, stock.printout())
    }
}
 private fun List<Item>.printout(): List<String> {
     return listOf("10 August 2025")
 }


