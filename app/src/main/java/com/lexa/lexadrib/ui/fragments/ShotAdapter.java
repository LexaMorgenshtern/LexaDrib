package com.lexa.lexadrib.ui.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lexa.lexadrib.R;
import com.lexa.lexadrib.data.Shot;
import com.squareup.picasso.Picasso;

import java.util.List;

class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {
    List<Shot> shots;
    private Context context;
    private ShotsFragment shotsFragment;
    ShotAdapter(List<Shot> shots, Context context, ShotsFragment shotsFragment) {
        this.shots = shots;
        this.context = context;
        this.shotsFragment = shotsFragment;
    }

    @Override
    public ShotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_shot_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShotAdapter.ViewHolder holder, int position) {
        final Shot s = shots.get(position);

        String[] dateSymbols = s.getCreatedAt().split("");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            builder.append(dateSymbols[i]);
        }
        holder.title.setText(s.getTitle());
        holder.name.setText(s.getUser().getName());
        holder.date.setText(builder.toString());
        Picasso.with(context).load(s.getImages().getNormal()).into(holder.picture);
        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotsFragment.retrofitManager.getShot(s.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }

    class ViewHolder  extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView title;
        TextView name;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);

            picture = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
