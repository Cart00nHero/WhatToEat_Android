package com.cartoonhero.source.actors.dataManger

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

enum class ItemStyle {
    LeftRight,TabMenu,FragmentVP,FragmentContainer
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

data class FragmentVPTemplate(
    val vpItem: FragmentVPItem,
    override var itemHeight: Int = 100
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.FragmentVP
}
data class FragmentContainerTemplate(
    val containerId: Int,
    val fragment: Fragment,
    override var itemHeight: Int = 100
): ItemTemplateInterface {
    override val itemStyle: ItemStyle
        get() = ItemStyle.FragmentContainer
}

/***** Items ******/
enum class ContentItemType {
    TextView,TabText,EditText,ImageView,Spinner,StockStatus,
    StockTransaction,HorizontalTextViews,TabMenu,FragmentVP
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
data class ImageViewItem(
    var imageSrc:String = ""
):ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.ImageView
}

data class SpinnerItem (
    var hint: String = "",
    var text: String = ""
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.Spinner
}

data class StockStatusItem (
    var isStockRise: Boolean = true,
    var price: TextViewItem = TextViewItem(),
    var rate: TextViewItem = TextViewItem()
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.StockStatus
}

data class StockTransactionItem (
    var buyingPrice: TextViewItem = TextViewItem(),
    var sellingPrice: TextViewItem = TextViewItem(),
    // b = buying
    var bTradingVolume: TextViewItem = TextViewItem(),
    // s = selling
    var sTradingVolume: TextViewItem = TextViewItem(),
    var averageVolume: TextViewItem = TextViewItem()
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.StockTransaction
}

data class HorizontalTextViewsItem (
    val textItems: MutableList<TextViewItem> = mutableListOf()
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.HorizontalTextViews
}
data class TabMenuItem(
    val tabItems: MutableList<TabTextItem> = mutableListOf(),
    var vpItem: FragmentVPItem = FragmentVPItem(),
    var selectedIndex: Int = 0
):ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.TabMenu
}

data class TabTextItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.WHITE,
    var selectColor: Int = Color.WHITE,
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.TabText
}

data class FragmentVPItem(
    val vpFragments: MutableList<Fragment> = mutableListOf(),
    var isUserInputEnabled: Boolean = true,
    var vpOrientation: Int = ViewPager2.ORIENTATION_HORIZONTAL
): ItemInterface {
    override val itemType: ContentItemType
        get() = ContentItemType.FragmentVP
}