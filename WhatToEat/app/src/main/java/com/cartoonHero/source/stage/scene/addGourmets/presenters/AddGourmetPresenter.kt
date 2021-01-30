package com.cartoonhero.source.stage.scene.addGourmets.presenters

import com.cartoonhero.source.actors.dataManger.*

class AddGourmetPresenter {
    val listData = GourmetsData()
    inner class GourmetsData {
        var dataSource: MutableList<TemplateInterface> = mutableListOf(
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb")),
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb"))
                )
    }
}

