package com.kanneki.positionrecyclerview

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
