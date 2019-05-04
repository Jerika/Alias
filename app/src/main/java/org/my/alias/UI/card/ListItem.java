package org.my.alias.UI.card;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import org.my.alias.R;

import androidx.recyclerview.widget.RecyclerView;

class ListItem extends RecyclerView.ViewHolder {
    private TextView word;

    ListItem(Context context, View itemView) {
        super(itemView);
        word = itemView.findViewById(R.id.word);
        setTypeface(context);
    }

    void bind(String text) {
        word.setText(text);
    }

    private void setTypeface(Context context) {
        Typeface type = Typeface.createFromAsset(context.getAssets(), "a_stamper.ttf");
        word.setTypeface(type);

    }
}
