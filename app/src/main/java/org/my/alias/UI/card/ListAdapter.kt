package org.my.alias.UI.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import org.my.alias.Pair
import org.my.alias.R

import java.util.ArrayList
import java.util.Arrays
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val words: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<ListItem>() {

    val items: List<String>
        get() = words

    val current: String
        get() = words[0]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItem {
        return ListItem(context,
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListItem, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }

    fun removeTopItem() {
        words.removeAt(0)
        notifyDataSetChanged()
    }

    fun addItem(item: String) {
        words.add(item)
        notifyDataSetChanged()
    }
}
