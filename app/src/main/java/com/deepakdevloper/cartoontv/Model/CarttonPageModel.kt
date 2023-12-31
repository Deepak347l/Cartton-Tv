package com.deepakdevloper.cartoontv.Model

data class CarttonPageModel(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)