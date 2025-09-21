package com.gildedrose

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.IOException
import kotlin.test.assertEquals
import java.time.Instant

class PersistenceTests {
    private val now = Instant.now()
    private val items = listOf<Item>(
        Item("banana", aug10, 42),
        Item("kumquat", aug10.plusDays(1), 101)
    )

    @Test
    fun `save and load file`(@TempDir dir: File) {

        println(dir)
        val file = File(dir, "stock.tsv")
        val stockList = StockList(now, items)
            stockList.saveTo(file)
        assertEquals(stockList, file.loadItems())
    }


    @Test
    fun `load and save empty stocklist`() {
        val stockList = StockList(now, emptyList())
        assertEquals(
            stockList,
            stockList.toLines().toStockList()
        )
    }

    @Test
    fun `load from empty file`() {
        assertEquals(
            StockList(Instant.EPOCH, emptyList()),
            emptySequence<String>().toStockList()
        )
    }


    @Test
    fun `load with no lastModified header`() {
        val lines = sequenceOf("# not modified")
        assertEquals(
            StockList(Instant.EPOCH, emptyList()),
            lines.toStockList()
        )
    }

    @Test
    fun `load with blank lastModified header`() {
        val lines = sequenceOf("# LastModified: ")
        try {
            lines.toStockList()
        } catch (x: IOException) {
            assertEquals(
                "Cound not parse LastModified header: Text '' could not be parsed at index 0",
                x.message
                )
        }
    }
}
