package com.gildedrose

import java.io.File
import java.io.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeParseException


fun File.loadItems(
    defaultLastModified: Instant = Instant.now()
): StockList =
    useLines { lines -> lines.toStockList(defaultLastModified) }


fun Sequence<String>.toStockList(
    defaultLastModified: Instant
): StockList {
    val (header, body) = partition { it.startsWith("#") }
    return StockList(
        lastModified = lastModifiedFrom(header) ?: defaultLastModified,
        items = body.map { line -> line.toItem() }.toList()
    )
}

fun StockList.saveTo(file: File) {
    file.writer().buffered().use { writer ->
        toLines().forEach { line ->
            writer.appendLine(line)
        }
    }
}

fun StockList.toLines(): Sequence<String> =
    sequenceOf("# LastModified: ${this.lastModified}") +
        items.map { it.toLine() }

private fun lastModifiedFrom(
    header: List<String>,
): Instant? =
    header.lastOrNull { it.startsWith("# LastModified: ")}
    ?.substring("# LastModified: ".length)
    ?.trim()
    ?.toInstant()

private fun String.toInstant(): Instant = try {
    Instant.parse(this)
} catch (x: DateTimeParseException) {
    throw IOException("Cound not parse LastModified header: ${x.message}")
}

private fun String.toItem(): Item {
    val parts: List<String> = this.split('\t')
    return Item(
        name = parts[0],
        sellByDate = LocalDate.parse(parts[1]),
        quantity = parts[2].toInt()
    )
}


private fun Item.toLine(): String = "$name\t$sellByDate\t$quantity"

