package com.cartoonHero.source.stage.scene.addGourmets.presenters

import com.cartoonHero.source.actors.dataManger.*

class AddGourmetPresenter {
    val listData = GourmetsData()
    inner class GourmetsData {
        var dataSource: MutableList<TemplateInterface> = mutableListOf(
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb")),
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb"))
                )
    }
}

