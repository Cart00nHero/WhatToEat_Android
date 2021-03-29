package com.cartoonHero.source.props.enities

import android.graphics.Color
import android.text.InputType
import android.view.View

data class Parcel(
    val contentType: String,
    var sender: String = "",
    var content: Any
)
data class ConcatPosition (
    var section: Int = 0,
    var position: Int = 0
)

class GourmetsListData constructor(inputObject: GQInputObject) {
    val dataSource:
            MutableList<MutableList<TemplateInterface>> = mutableListOf()
    private var inObject = inputObject
    private val itemHeight = 70
    private val leftLayoutWidth = 72
    init {
        dataSource.clear()
        dataSource.addAll(createDataSource())
    }
    fun reloadData(inputObject: GQInputObject) {
        inObject = inputObject
        dataSource.clear()
        dataSource.addAll(createDataSource())
    }
    private fun titleTextViewItem(title: String): TextViewItem {
        return TextViewItem(
            text = title,
            textSize = 16.0F,
            alignment = View.TEXT_ALIGNMENT_VIEW_START,
            textColor = Color.parseColor("#470024"))
    }
    private fun editItemTemplate(title: String, content:String): LRTemplate {
        return LRTemplate(
            itemHeight = itemHeight,
            leftLayoutWidth = leftLayoutWidth,
            leftViewItem = titleTextViewItem(title),
            rightViewItem = EditTextItem(
                text = content,
            )
        )
    }
    private fun createDataSource():List<MutableList<TemplateInterface>> {
        return listOf(
            mutableListOf(
                LRTemplate(
                    itemHeight = 34,
                    leftLayoutWidth = 100,
                    leftViewItem = TextViewItem(
                        text = "Shop",
                        textSize = 20.0F,
                        alignment = View.TEXT_ALIGNMENT_VIEW_START,
                        textColor = Color.parseColor("#FF4D40")
                    ),
                    rightViewItem = null
                ),
                editItemTemplate("Title",inObject.shopBranch.title),
                editItemTemplate("Subtitle",
                    inObject.shopBranch.subtitle.value ?: ""),
                editItemTemplate(
                    "Under\nPrice",
                    inObject.shopBranch.underPrice.value.toString()),
                editItemTemplate(
                    "Tel",
                    inObject.shopBranch.tel.value ?: "")
            ),
            mutableListOf(
                LRTemplate(
                    itemHeight = 34,
                    leftLayoutWidth = 100,
                    leftViewItem = TextViewItem(
                        text = "Location",
                        textSize = 20.0F,
                        alignment = View.TEXT_ALIGNMENT_VIEW_START,
                        textColor = Color.parseColor("#FF4D40")
                    ),
                    rightViewItem = null
                ),
                LRTemplate(
                    itemHeight = itemHeight,
                    leftLayoutWidth = leftLayoutWidth,
                    leftViewItem = titleTextViewItem("Address"),
                    rightViewItem = TextViewItem(
                        text = inObject.address.completeInfo,
                        numberOfLines = 2
                    )
                ),
                LRTemplate(
                    itemHeight = itemHeight,
                    leftLayoutWidth = leftLayoutWidth,
                    leftViewItem = titleTextViewItem("Floor"),
                    rightViewItem = EditTextItem(
                        inputType = InputType.TYPE_CLASS_NUMBER,
                        text = inObject.address.floor.value ?: "")
                ),
                LRTemplate(
                    itemHeight = itemHeight,
                    leftLayoutWidth = leftLayoutWidth,
                    leftViewItem = titleTextViewItem("Room"),
                    rightViewItem = EditTextItem(
                        inputType = InputType.TYPE_CLASS_NUMBER,
                        text = inObject.address.room.value ?: "")
                )
            ),
            mutableListOf(
                LRTemplate(
                    itemHeight = 54,
                    leftViewItem = null,
                    rightViewItem = ButtonItem(
                        title = "Share on Map"
                    )
                )
            )
        )
    }
}