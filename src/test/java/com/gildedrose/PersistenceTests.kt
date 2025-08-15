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
        assertEquals(stockList, file.loadItems(defaultLastModified = now.plusSeconds(3600)))
    }


    @Test
    fun `load and save empty`() {
        val stockList = StockList(now, emptyList())
        assertEquals(
            stockList,
            stockList.toLines().toStockList(defaultLastModified = now.plusSeconds(3600))
        )
    }

    @Test
    fun `load with no lastModified header`() {
        val lines = sequenceOf("# not modified")
        assertEquals(
            StockList(now, emptyList()),
            lines.toStockList(defaultLastModified = now)
        )
    }

    @Test
    fun `load with blank lastModified header`() {
        val lines = sequenceOf("# LastModified: ")
        try {
            lines.toStockList(defaultLastModified = now)
        } catch (x: IOException) {
            assertEquals(
                "Cound not parse LastModified header: Text '' could not be parsed at index 0",
                x.message
                )
        }
    }

    @Test
    fun `load legacy file`(@TempDir dir: File) {

        println(dir)
        val file = File(dir, "stock.tsv")
        items.legacySaveTo(file)
        assertEquals(StockList(
            lastModified = now,
            items = items
        ), file.loadItems(defaultLastModified = now))
    }

    private fun List<Item>.legacySaveTo(file: File) {
        fun Item.toLine() = "$name\t$sellByDate\t$quantity"
        file.writer().buffered().use { writer ->
            this.forEach { item ->
                writer.appendLine(item.toLine())
            }
        }
    }
}
