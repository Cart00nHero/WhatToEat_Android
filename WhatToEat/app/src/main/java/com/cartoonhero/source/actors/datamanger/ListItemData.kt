package com.cartoonhero.source.actors.dataManger

enum class ItemStyle {
    LeftRight,Button,RadarMap
}
enum class ItemContentType {
    TextView,EditText, DropDown, Range, AddressInput
}

interface ItemTemplateInterface {
    val itemStyle: ItemStyle
}

data class LRItemTemplate(
    override val itemStyle: ItemStyle = ItemStyle.LeftRight,
    val leftInterface: ItemInterface,
    val rightInterface: ItemInterface
) : ItemTemplateInterface

interface ItemInterface {
    val contentType: ItemContentType
    val itemHeight: Int
}

data class TextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.TextView
    override var itemHeight: Int = 48
}

data class EditTextItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.EditText
    override var itemHeight: Int = 48
}