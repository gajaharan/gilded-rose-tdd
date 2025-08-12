package com.gildedrose

import java.io.File
import java.time.LocalDate


fun File.loadItems(): List<Item> {
    return useLines { lines ->
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

fun List<Item>.saveTo(file: File) {
    file.writer().buffered().use { writer ->
        this.forEach { item ->
            writer.appendLine(item.toLine())
        }
    }
}

private fun Item.toLine(): String {
    return "$name\t$sellByDate\t$quantity"
}
