package com.example.kolin.fintechhomework9.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kolin.fintechhomework9.R;
import com.example.kolin.fintechhomework9.data.model.NewsPojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kolin on 28.11.2017.
 */

public class NewsAdapterRV extends RecyclerView.Adapter<NewsAdapterRV.ViewHolder>  {

    private List<NewsPojo> data;
    private DateFormat sdf = SimpleDateFormat.getInstance();

    public NewsAdapterRV() {
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsPojo newsPojo = data.get(position);

        holder.text.setText(newsPojo.getText());
        holder.date.setText(sdf.format(new Date(newsPojo.getPublicationDate())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<NewsPojo> data){
        int size = this.data.size();
        this.data.addAll(data);
        notifyItemRangeInserted(size, data.size());
    }

    public void clear(){
        int size = this.data.size();
        this.data.clear();
        notifyItemRangeRemoved(0, size);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.item_text);
            date = itemView.findViewById(R.id.item_date);
        }
    }
}
