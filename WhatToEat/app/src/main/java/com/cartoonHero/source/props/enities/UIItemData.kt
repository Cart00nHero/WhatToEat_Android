package com.cartoonHero.source.props.enities

import android.graphics.Color
import android.text.InputType
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

enum class TemplateStyle {
    LeftRight,UpDown,TabMenu,ViewPager,FragmentContainer
}

/***** Templates ******/
interface TemplateInterface {
    val templateStyle: TemplateStyle
    var itemHeight: Int
}
data class LRTemplate (
    val leftViewItem: ViewItemInterface?,
    val rightViewItem: ViewItemInterface?,
    var leftLayoutWidth: Int = 66,
    override var itemHeight: Int = 100

): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.LeftRight
}
data class UDTemplate (
    val upViewItem: ViewItemInterface?,
    val downViewItem: ViewItemInterface?,
    var bottomHeight: Int = 66,
    override var itemHeight: Int = 100

): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.UpDown
}

data class TabMenuTemplate(
    val viewItem: TabMenuViewItem,
    override var itemHeight: Int = 100
): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.TabMenu
}

data class ViewPagerTemplate(
    val vpItem: ViewPagerItem,
    override var itemHeight: Int = 100
): TemplateInterface {
    override val templateStyle: TemplateStyle
        get() = TemplateStyle.ViewPager
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
    TextView,TabText,EditText,ImageView,
    Spinner,TabMenu,ViewPager,Button
}

interface ViewItemInterface {
    val viewType: ViewType
}
data class TextViewItem (
    var text: String = "",
    var numberOfLines: Int = 1,
    var textColor: Int = Color.WHITE,
    var textSize: Float = 14.0F,
    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.TextView
}

data class EditTextItem (
    var hint: String = "",
    var text: String = "",
    var inputType: Int = InputType.TYPE_CLASS_TEXT
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.EditText
}
data class ImageViewItem(
    var imageDrawable: Int = 0,
    var scaleType: ImageView.ScaleType =
        ImageView.ScaleType.CENTER
): ViewItemInterface {
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

data class TabMenuViewItem(
    val tabItems: MutableList<TabTextViewItem> = mutableListOf(),
    var vpItem: ViewPagerItem = ViewPagerItem(),
    var selectedIndex: Int = 0
): ViewItemInterface {
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

data class ButtonItem(
    var title: String = "",
    var backgroundColor: Int = Color.parseColor("#FF4D40"),
    var textColor: Int = Color.WHITE
): ViewItemInterface {
    override val viewType: ViewType
        get() = ViewType.Button
}