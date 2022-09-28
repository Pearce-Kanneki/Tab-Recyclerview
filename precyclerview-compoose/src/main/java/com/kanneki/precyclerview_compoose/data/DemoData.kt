package com.kanneki.precyclerview_compoose.data

data class DemoData(
    val id: Int,
    val title: String,
    val des: String = "",
    val image: String = "",
    val type: DemoDataType = DemoDataType.NONE
)

enum class DemoDataType {
    NONE, TITLE, DETAIL
}
