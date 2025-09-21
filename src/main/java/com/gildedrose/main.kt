package com.gildedrose

import org.http4k.routing.RoutingHttpHandler
import java.io.File
import java.time.LocalDate

fun main() {
    val file = File("stock.tsv").also { it.createNewFile() }
    val routes = routesFor(file)
    val server = Server(routes)
    server.start()
}

fun routesFor(
    file: File,
    clock: () -> LocalDate = LocalDate::now
): RoutingHttpHandler {
    val stock = file.loadItems()
    stock.saveTo(file)
    return routes(stock = stock)
}
