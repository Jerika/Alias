package org.my.alias.UI.card

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView

import org.my.alias.R

import androidx.recyclerview.widget.RecyclerView

class ListItem(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val word: TextView

    init {
        word = itemView.findViewById(R.id.word)
        setTypeface(context)
    }

    fun bind(text: String) {
        word.text = text
    }

    private fun setTypeface(context: Context) {
        val type = Typeface.createFromAsset(context.assets, "a_stamper.ttf")
        word.typeface = type

    }
}
