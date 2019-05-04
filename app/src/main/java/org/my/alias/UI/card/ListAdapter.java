package org.my.alias.UI.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.my.alias.Pair;
import org.my.alias.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListItem> {
    private ArrayList<String> words = new ArrayList<>();
    private Context context;

    public ListAdapter(ArrayList<String> playWords, Context context) {
        this.words = playWords;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListItem(context,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListItem holder, int position) {
        holder.bind(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public List<String> getItems() {
        return words;
    }

    public void removeTopItem() {
        words.remove(0);
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        words.add(item);
        notifyDataSetChanged();
    }

    public String getCurrent() {
        return words.get(0);
    }
}
