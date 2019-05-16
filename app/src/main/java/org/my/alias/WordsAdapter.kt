package org.my.alias

import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

class WordsAdapter(private val context: AppCompatActivity, internal var words: ArrayList<Pair>) : ArrayAdapter<Pair>(context, R.layout.result_element, words) {

    internal class ViewHolder {
        var imageView: ImageView? = null
        var textView: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val word = words[position]
        val holder: ViewHolder
        var rowView = convertView
        if (rowView == null) {
            val inflater = context.layoutInflater
            rowView = inflater.inflate(R.layout.result_element, null, true)
            holder = ViewHolder()
            holder.textView = rowView!!.findViewById(R.id.title)

            holder.textView!!.typeface = getTypeface(context)
            holder.imageView = rowView.findViewById(R.id.image)
            rowView.tag = holder
        } else {
            holder = rowView.tag as ViewHolder
        }

        holder.textView!!.text = word.word

        if (word.isGuess) {
            holder.imageView!!.setImageResource(R.mipmap.ok)
        } else {
            holder.imageView!!.setImageResource(R.mipmap.cancel)
        }
        holder.imageView!!.setOnClickListener {
            if (word.isGuess) {
                word.isGuess = false
                holder.imageView!!.setImageResource(R.mipmap.cancel)
            } else {
                word.isGuess = true
                holder.imageView!!.setImageResource(R.mipmap.ok)
            }
        }

        return rowView
    }

    private fun getTypeface(context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, "a_stamper.ttf")
    }
}
