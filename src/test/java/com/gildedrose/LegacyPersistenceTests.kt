package com.gildedrose

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.Instant
import kotlin.test.assertEquals

class LegacyPersistenceTests {
    private val items = listOf<Item>(
        Item("banana", aug10, 42),
        Item("kumquat", aug10.plusDays(1), 101)
    )

    @Test
    fun `load legacy file`(@TempDir dir: File) {

        println(dir)
        val file = File(dir, "stock.tsv")
        items.legacySaveTo(file)
        assertEquals(StockList(
            lastModified = Instant.EPOCH,
            items = items
        ), file.loadItems())
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
