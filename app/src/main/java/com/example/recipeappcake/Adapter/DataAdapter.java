package com.example.recipeappcake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeappcake.Model.Recipe;
import com.example.recipeappcake.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    Context context;
    ArrayList<Recipe>arrayList;

    public DataAdapter(Context context, ArrayList<Recipe> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Recipe model = arrayList.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.tvName.setText(model.getName());
        holder.tvPrice.setText(model.getPrice());
        holder.tvDesc.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView tvName;
        TextView tvPrice;
        TextView tvDesc;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            tvName= itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

}
