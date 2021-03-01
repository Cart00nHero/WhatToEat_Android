package com.cartoonHero.source.stage.scene.addGourmets.presenters

import com.cartoonHero.source.enities.LRTemplate
import com.cartoonHero.source.enities.TemplateInterface
import com.cartoonHero.source.enities.TextViewItem

class AddGourmetPresenter {
    val listData = GourmetsData()
    inner class GourmetsData {
        var dataSource: MutableList<TemplateInterface> = mutableListOf(
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb")),
                    LRTemplate(TextViewItem(text = "aaa"), TextViewItem(text = "bbb"))
                )
    }
}

