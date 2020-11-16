package com.cartoonhero.source.actors.dataManger

enum class ItemStyle {
    LeftRight,Button,RadarMap
}
enum class ItemContentType {
    TextView,EditText, Spinner, Range, AddressInput
}

interface ItemTemplateInterface {
    val itemStyle: ItemStyle
    val itemHeight: Int
}
data class LRItemTemplate (
    val leftInterface: ItemInterface,
    val rightInterface: ItemInterface
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.LeftRight
    override var itemHeight: Int = 48
}

interface ItemInterface {
    val contentType: ItemContentType
}
data class TextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.TextView
}
data class EditTextItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.EditText
}

data class SpinnerItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.Spinner
}