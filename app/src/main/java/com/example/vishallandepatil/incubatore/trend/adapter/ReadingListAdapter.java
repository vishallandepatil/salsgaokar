package com.example.vishallandepatil.incubatore.trend.adapter;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;

import java.util.List;

public class ReadingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReadingTable> moviesList;
    private  final  int HEADER=0,DATAVIEW=1;

    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return HEADER;
        }
        else
        {
            return DATAVIEW;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==HEADER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rowheader, parent, false);

            return new HeaderViewHolder(itemView);
        }
        else
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.datarow, parent, false);

            return new RowViewHolder(itemView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {

        if(holder instanceof RowViewHolder)
        {
            RowViewHolder holder1= (RowViewHolder) holder;
            ReadingTable readingTable=moviesList.get(position-1);
            holder1.date.setText(readingTable.getDateTime());
            holder1.co2.setText(readingTable.getCoreading());
            holder1.o2.setText(readingTable.getO2reaading());
        }
    }
    public ReadingListAdapter(List<ReadingTable> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public int getItemCount() {

        if(moviesList!=null)
        {
            return  moviesList.size()+1;
        }
        else
        {
            return 0;
        }

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public HeaderViewHolder(View view) {
            super(view);

        }
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        public TextView date, co2, o2;

        public RowViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            co2 = (TextView) view.findViewById(R.id.co2);
            o2 = (TextView) view.findViewById(R.id.o2);
        }
    }
}