package com.example.haushaltsapp.ChartPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haushaltsapp.R;
import com.example.haushaltsapp.Database.Outgo;
import java.util.ArrayList;

public class RecyclerAdapterOut extends RecyclerView.Adapter<RecyclerAdapterOut.MyViewHolder> {

    private ArrayList<Outgo> outgoList;
    private RecyclerViewClickListener listener;

    public RecyclerAdapterOut(ArrayList<Outgo> outgoList, RecyclerViewClickListener listener) {
        this.outgoList = outgoList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView outName;
        private TextView outValue;
        private TextView outDate;
        private TextView outCategory;

        public MyViewHolder(final View view) {
            super(view);
            outName = view.findViewById(R.id.Chartname);
            outValue = view.findViewById(R.id.ChartValue);
            outDate = view.findViewById(R.id.ChartDate);
            outCategory = view.findViewById(R.id.ChartCategorie);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerAdapterOut.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item,parent,false);
        return new MyViewHolder(itemView);
    }

    //runden auf zwei Nachkommazahlen
    public double round(double number, int positions) {
        return (double) ((int)number + (Math.round(Math.pow(10,positions)*(number-(int)number)))/(Math.pow(10,positions)));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOut.MyViewHolder holder, int position) {

        //Setzen der Textview
        String name = outgoList.get(position).getName();
        holder.outName.setText(name);

        double valueDouble = outgoList.get(position).getValue();
        String value = Double.toString(round(valueDouble,2));
        holder.outValue.setText(value+" €");

        String day = Integer.toString(outgoList.get(position).getDay());
        String month = Integer.toString(outgoList.get(position).getMonth());
        String year = Integer.toString(outgoList.get(position).getYear());
        String date = day+"."+month+"."+year;
        holder.outDate.setText(date);

        String category = outgoList.get(position).getCategory();
        holder.outCategory.setText(category);
    }

    @Override
    public int getItemCount() {
        return outgoList.size();
    }

    public  interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
