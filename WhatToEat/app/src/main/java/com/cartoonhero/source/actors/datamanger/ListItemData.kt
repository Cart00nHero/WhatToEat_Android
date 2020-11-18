package com.cartoonhero.source.actors.datamanger

import android.graphics.Color
import android.view.View

enum class ItemStyle {
    LeftRight,TabViewPager
}

/***** Templates ******/
interface ItemTemplateInterface {
    val itemStyle: ItemStyle
}
data class LRItemTemplate (
    val leftInterface: ItemInterface?,
    val rightInterface: ItemInterface?,
    var leftLayoutWidth: Int = 64

): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.LeftRight
}

data class TabViewPagerItemTemplate(
    val tabOptItems: MutableList<String> = mutableListOf()
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.TabViewPager
}

/***** Items ******/
enum class ItemContentType {
    TextView,EditText, Spinner,
    StockStatus, StockTransaction, HorizontalTextViews
}

interface ItemInterface {
    val contentType: ItemContentType
}
data class TextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.WHITE,
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
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

data class HorizontalTextViewsItem (
    val textItems: MutableList<TextViewItem>
): ItemInterface {
    override val contentType: ItemContentType
        get() = ItemContentType.HorizontalTextViews
}