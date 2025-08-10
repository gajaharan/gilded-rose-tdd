package com.gildedrose

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit.DAYS

val format: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

fun List<Item>.printout(now: LocalDate): List<String> =
    listOf(format.format(now)) + this.map { it.toPrintout(now) }

fun Item.toPrintout(now: LocalDate): String {
    return "$name, ${daysUntilSellBy(now)}, $quantity"
}

fun Item.daysUntilSellBy(now: LocalDate): Long = DAYS.between(now, this.sellByDate)

