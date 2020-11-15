package com.cartoonhero.source.stage.scene.addGourmets.presenters

import com.cartoonhero.source.actors.dataManger.*

class AddGourmetPresenter {

    inner class GourmetsTableData {
        var dataSource: MutableList<ItemTemplateInterface> = mutableListOf(
                    LRItemTemplate(TextViewItem(text = "aaa"),EditTextItem(text = "")),
                    LRItemTemplate(TextViewItem(text = "aaa"),EditTextItem(text = ""))
                )

//        var dataSource: MutableList<MutableList<ItemTemplateInterface>> =
//            mutableListOf(
//                mutableListOf(
//                    LRItemTemplate(TextViewItem(text = "aaa"),EditTextItem(text = "")),
//                    LRItemTemplate(TextViewItem(text = "aaa"),EditTextItem(text = ""))
//                ),
//                mutableListOf(
//                    LRItemTemplate(TextViewItem(text = "bbb"),EditTextItem(text = "")),
//                    LRItemTemplate(TextViewItem(text = "bbb"),EditTextItem(text = ""))
//                ),
//                mutableListOf(
//                    LRItemTemplate(TextViewItem(text = "bbb"),EditTextItem(text = "")),
//                    LRItemTemplate(TextViewItem(text = "bbb"),EditTextItem(text = ""))
//                )
//            )
    }
}

