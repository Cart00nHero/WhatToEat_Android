package com.cartoonhero.source.actors.dataManger

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment

enum class ItemStyle {
    LeftRight,TabViewPager,FragmentContainer
}

/***** Templates ******/
interface ItemTemplateInterface {
    val itemStyle: ItemStyle
}
data class LRTemplate (
    val leftInterface: ItemInterface?,
    val rightInterface: ItemInterface?,
    var leftLayoutWidth: Int = 64

): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.LeftRight
}

data class TabMenuTemplate(
    val tabItems: MutableList<String> = mutableListOf(),
    val vpFragments: MutableList<Fragment> = mutableListOf()
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.TabViewPager
}
data class FragmentContainerTemplate(
    val fragment: Fragment
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.FragmentContainer
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
