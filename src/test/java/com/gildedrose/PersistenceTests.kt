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
        assertEquals(stock, load(file))
    }

    @Test
    fun `load and save empty`(@TempDir dir: File) {
        val file = File(dir, "stock.tsv")
        val stock = emptyList<Item>()

        stock.saveTo(file)
        assertEquals(stock, load(file))
    }

}


private fun load(file: File): List<Item> {
    return file.useLines { lines ->
        lines.map { line ->
            line.toItem()
        }.toList()
    }
}

private fun String.toItem(): Item {
    val parts: List<String> = this.split('\t')
    return Item(
        name = parts[0],
        sellByDate = LocalDate.parse(parts[1]),
        quantity = parts[2].toInt()
    )
}

private fun List<Item>.saveTo(file: File) {
    file.writer().buffered().use { writer ->
        this.forEach { item ->
            writer.appendLine(item.toLine())
        }
    }
}

private fun Item.toLine(): String {
    return "$name\t$sellByDate\t$quantity"
}
