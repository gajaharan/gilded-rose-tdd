package com.gildedrose

import org.http4k.template.ViewModel

data class StockListViewModel(
    val now: String,
    val items: List<Map<String,String>>
): ViewModel
