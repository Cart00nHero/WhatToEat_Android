package com.cartoonhero.source.actors.datamanger

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

enum class ItemStyle {
    LeftRight,TabViewPager,FragmentContainer
}

/***** Templates ******/
interface ItemTemplateInterface {
    val itemStyle: ItemStyle
    var itemHeight: Int
}
data class LRTemplate (
    val leftInterface: ItemInterface?,
    val rightInterface: ItemInterface?,
    var leftLayoutWidth: Int = 64,
    override var itemHeight: Int = 100

): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.LeftRight
}

data class TabMenuTemplate(
    val contentItem: TabMenuItem,
    override var itemHeight: Int = 100
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.TabMenu
}
data class FragmentContainerTemplate(
    val attachActivity: AppCompatActivity,
    val fragment: Fragment
):ItemTemplateInterface{
    override val itemStyle: ItemStyle
        get() = ItemStyle.FragmentContainer
}

data class FragmentVPTemplate(
    val vpItem: FragmentVPItem,
    override var itemHeight: Int = 100
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.FragmentVP
}

/***** Items ******/
enum class ContentItemType {
    TextView,TabText,EditText,Spinner,HorizontalTextViews,TabMenu,FragmentVP
}

interface ItemInterface {
    val itemType: ContentItemType
}
data class TextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.WHITE,
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.TextView
}

data class EditTextItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.EditText
}

data class SpinnerItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.Spinner
}

data class HorizontalTextViewsItem (
    val textItems: MutableList<TextViewItem> = mutableListOf()
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.HorizontalTextViews
}
