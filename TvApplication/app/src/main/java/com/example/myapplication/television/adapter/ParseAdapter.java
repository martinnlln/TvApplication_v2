package com.example.myapplication.television.adapter;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CustomDialogActivity;
import com.example.myapplication.R;
import com.example.myapplication.room_database.TelevisionModel.Television;
import com.example.myapplication.television.StartTelevisionActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {

    private List<Television> televisions;
    Activity activity;

    public ParseAdapter(List<Television> parseItems, Activity activity) {
        this.televisions = parseItems;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.television_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {
        Television television = televisions.get(position);
        holder.textView.setText(television.getTv_name());
        Picasso.get().load(television.getTv_img()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return televisions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.tvImg);
            textView = view.findViewById(R.id.tvName);
            view.setOnClickListener(this);

            view.setOnFocusChangeListener((view12, b) -> {
                if (b) {
                    view12.setBackgroundResource(R.drawable.gradient_src_hover);
                } else {
                    view12.setBackgroundResource(R.drawable.rec_view_backgroun_item);
                }
            });

            view.setOnLongClickListener(view1 -> {
                int position = getAdapterPosition();
                Television television = televisions.get(position);
                Intent intent = new Intent(activity, CustomDialogActivity.class);
                intent.putExtra("tv_name", television.getTv_name());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return false;
            });
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Television television = televisions.get(position);
            Intent intent = new Intent(activity, StartTelevisionActivity.class);
            intent.putExtra("tv_name", television.getTv_name());
            intent.putExtra("tv_img", television.getTv_img());
            intent.putExtra("tv_url", television.getTv_url());
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);

        }
    }



    @SuppressLint("NotifyDataSetChanged")
    public void setFilter(List<Television> newList) {
        televisions = new ArrayList<>();
        televisions.addAll(newList);
        notifyDataSetChanged();
    }
}