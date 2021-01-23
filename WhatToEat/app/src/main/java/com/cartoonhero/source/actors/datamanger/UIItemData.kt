package com.cartoonhero.source.actors.dataManger

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

enum class TemplateStyle {
    LeftRight,TabMenu,FragmentVP,FragmentContainer
}

/***** Templates ******/
interface TemplateInterface {
    val templateStyle: TemplateStyle
    var itemHeight: Int
}
data class LRTemplate (
    val leftViewItem: ViewItemInterface?,
    val rightViewItem: ViewItemInterface?,
    var leftLayoutWidth: Int = 64,
    override var itemHeight: Int = 100

): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.LeftRight
}

data class TabMenuTemplate(
    val viewItem: TabMenuViewItem,
    override var itemHeight: Int = 100
): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.TabMenu
}

data class FragmentVPTemplate(
    val viewPagerItem: ViewPagerItem,
    override var itemHeight: Int = 100
): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.FragmentVP
}
data class FragmentContainerTemplate(
    val containerId: Int,
    val fragment: Fragment,
    override var itemHeight: Int = 100
): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.FragmentContainer
}

/***** Items ******/
enum class ViewType {
    TextView,TabText,EditText,ImageView,Spinner,StockStatus,
    StockTransaction,HorizontalTextViews,TabMenu,ViewPager
}

interface ViewItemInterface {
    val viewType: ViewType
}
data class TextViewViewItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.WHITE,
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.TextView
}

data class EditTextViewItem (
    var hint: String = "",
    var text: String = ""
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.EditText
}
data class ImageViewItem(
    var imageDrawable: Int = 0
):ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.ImageView
}

data class SpinnerViewItem (
    var hint: String = "",
    var text: String = ""
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.Spinner
}

data class HorizontalTextViewsViewItem (
    val textItems: MutableList<TextViewViewItem> = mutableListOf()
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.HorizontalTextViews
}
data class TabMenuViewItem(
    val tabItems: MutableList<TabTextViewItem> = mutableListOf(),
    var vpItem: ViewPagerItem = ViewPagerItem(),
    var selectedIndex: Int = 0
):ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.TabMenu
}

data class TabTextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.parseColor("#60f3f3f3"),
    var selectColor: Int = Color.parseColor("#24adf6"),
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.TabText
}

data class ViewPagerItem(
    val vpFragments: MutableList<Fragment> = mutableListOf(),
    var isUserInputEnabled: Boolean = true,
    var vpOrientation: Int = ViewPager2.ORIENTATION_HORIZONTAL
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.ViewPager
}