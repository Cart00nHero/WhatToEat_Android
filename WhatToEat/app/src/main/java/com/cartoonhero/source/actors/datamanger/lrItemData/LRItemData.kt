package com.cartoonhero.source.actors.dataManger.lrItemData

enum class ItemStyle {
    LeftRight,Button,RadarMap
}
enum class ItemContentStyle {
    TextView,EditField, DropDown, Range, AddressInput
}

interface LRItemInterface {
    val itemType: ItemContentStyle
    val itemHeight: Int
}
data class EditTextItem(
    override val itemType: ItemContentStyle = ItemContentStyle.EditField,
    override val itemHeight: Int = 48

) : LRItemInterface

interface ItemDataInterface {
    val itemStyle: ItemStyle
}

data class LRItemData(
    override val itemStyle: ItemStyle = ItemStyle.LeftRight,
    val leftContentStyle: ItemContentStyle,
    val rightContentStyle: ItemContentStyle
) : ItemDataInterface