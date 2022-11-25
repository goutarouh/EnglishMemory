package com.github.goutarouh.englishmemory.data.notion.model

import com.squareup.moshi.Json


data class BlocksChildrenResponse(
    val results: List<Block>
)

data class Block(
    @Json(name = "table_row")
    val tableRow: TableRow
)


data class TableRow(
    val cells: List<List<Cell>>
)

data class Cell(
    @Json(name = "plain_text")
    val plainText: String
)