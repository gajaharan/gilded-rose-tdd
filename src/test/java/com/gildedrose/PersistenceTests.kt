package com.gildedrose

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals

class PersistenceTests {

    @Test
    fun `load and save`(@TempDir dir: File) {
        println(dir)
        val file = File(dir, "stock.tsv")
        val stock = listOf<Item>(
            Item("banana", aug10, 42),
            Item("kumquat", aug10.plusDays(1), 101)
        )
        stock.saveTo(file)
        assertEquals(stock, file.loadItems())
    }

    @Test
    fun `load and save empty`(@TempDir dir: File) {
        val file = File(dir, "stock.tsv")
        val stock = emptyList<Item>()

        stock.saveTo(file)
        assertEquals(stock, file.loadItems())
    }

}
