package com.github.goutarouh.englishmemory.data.sentence

data class Sentence(
    val en: String,
    val ja: String
)

val sentences = listOf(
    Sentence(
        en = "It's one hour drive away.",
        ja = "車で1時間の距離です。"
    ),
    Sentence(
        en = "I live about 30 minutes away from my office by train and on foot.",
        ja = "会社から電車と歩きで30分くらいの距離に住んでいます。"
    ),
    Sentence(
        en = "Price of food in Japan is getting more and more expensive due to the war and covid-19",
        ja = "戦争やコロナの影響で日本の食べ物の値段はどんどん高くなっています。"
    ),
)
