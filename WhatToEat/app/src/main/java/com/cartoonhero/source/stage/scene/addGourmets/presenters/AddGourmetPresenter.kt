package com.cartoonhero.source.stage.scene.addGourmets.presenters

import com.cartoonhero.source.actors.dataManger.*
import com.cartoonhero.source.stage.scene.UITestFragment
import com.cartoonhero.source.stage.scene.entrance.OptionalFragment
import com.cartoonhero.source.stage.scene.entrance.SignFragment

class AddGourmetPresenter {

    inner class GourmetsTableData {
        var dataSource: MutableList<ItemTemplateInterface> = mutableListOf(
//                    LRTemplate(TextViewItem(text = "aaa"), EditTextItem(hint = "")),
//                    LRTemplate(TextViewItem(text = "aaa"), EditTextItem(hint = "")),
                    TabMenuTemplate(
                        tabItems = mutableListOf("First","Second","Third"),
                        vpFragments = mutableListOf(
                            SignFragment(),UITestFragment(),OptionalFragment())
                    )
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

