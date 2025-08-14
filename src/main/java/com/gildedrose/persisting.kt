package com.gildedrose

import java.io.File
import java.time.Instant
import java.time.LocalDate


fun File.loadItems(defaultLastModified: Instant = Instant.now()): StockList {
    return useLines { lines ->
        val (header, body) = lines.partition { it.startsWith("#") }
        StockList(
            lastModified = lastModifiedFrom(header) ?: defaultLastModified,
            items = body.map { line -> line.toItem() }.toList()
        )

    }
}

private fun lastModifiedFrom(
    header: List<String>,
): Instant? = (header.firstOrNull()
    ?.substring("# LastModified: ".length)
    ?.toInstant())

fun String.toInstant(): Instant = Instant.parse(this)

private fun String.toItem(): Item {
    val parts: List<String> = this.split('\t')
    return Item(
        name = parts[0],
        sellByDate = LocalDate.parse(parts[1]),
        quantity = parts[2].toInt()
    )
}

fun StockList.saveTo(file: File) {
    file.writer().buffered().use { writer ->
        writer.appendLine("# LastModified: ${this.lastModified}")
        this.forEach { item ->
            writer.appendLine(item.toLine())
        }
    }
}


private fun Item.toLine(): String = "$name\t$sellByDate\t$quantity"

data class StockList(
    val lastModified: Instant,
    val items: List<Item>
) : List<Item> by items
