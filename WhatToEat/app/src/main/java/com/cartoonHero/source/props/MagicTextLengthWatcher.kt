package com.cartoonHero.source.props

import android.text.Editable
import android.text.TextWatcher

class MagicTextLengthWatcher constructor(
    private var maxLength: Int
) : TextWatcher {
    private var currentEnd = 0 // 儲存目前字串改變的結束位置，例如：abcdefg變成abcd1234efg，變化的結束位置就在索引8
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        currentEnd = start + count
    }

    override fun afterTextChanged(s: Editable?) {
        // 刪除最後變化的字元
        while (s?.let { calculateLength(it) } ?: 0 > maxLength) { // 若變化後的長度超過最大長度
            // 刪除最後變化的字元
            currentEnd--;
            s?.delete(currentEnd, currentEnd + 1);
        }
    }

    private fun calculateLength(c: CharSequence): Int {
        var len = 0
        val l = c.length
        for (i in 0 until l) {
            val tmp = c[i]
            if (tmp.toInt() in 0x20..0x7E) {
                // 字元值 32~126 是 ASCII 半形字元的範圍
                len++
            } else {
                // 非半形字元
                len += 2
            }
        }
        return len
    }
}