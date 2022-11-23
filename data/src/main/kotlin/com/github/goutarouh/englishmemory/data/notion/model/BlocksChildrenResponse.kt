package com.github.goutarouh.englishmemory.data.notion.model

import com.squareup.moshi.Json


data class BlocksChildrenResponse(
    val results: List<Block>
)

data class Block(
    val paragraph: Paragraph
)

data class Paragraph(
    @Json(name = "rich_text")
    val richText: List<RichText>
)

data class RichText(
    @Json(name = "plain_text")
    val plainText: String
)