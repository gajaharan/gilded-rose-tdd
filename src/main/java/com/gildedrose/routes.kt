package com.gildedrose

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates
import org.intellij.lang.annotations.Language
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

private val handlebars = HandlebarsTemplates().HotReload("src/main/java")

fun routes(
    stock: List<Item>,
    clock: () -> LocalDate = LocalDate::now
): RoutingHttpHandler = routes(
    "/" bind Method.GET to { request ->
        val now = clock()
        Response.Companion(Status.Companion.OK).body(
            handlebars(
                StockListViewModel(
                    now = dateFormat.format(now),
                    items = stock.map { it.toMap(now) }
                )
            )
        )
    }
)

private fun Item.toMap(now: LocalDate): Map<String,String> = mapOf(
    "name" to name,
    "sellByDate" to dateFormat.format(sellByDate),
    "sellByDays" to this.daysUntilSellBy(now).toString(),
    "quantity" to quantity.toString()
)

private fun Item.daysUntilSellBy(now: LocalDate): Long = ChronoUnit.DAYS.between(now, this.sellByDate)

