package com.example.mad_toodle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private final List<LeaderboardItem> items;
    private final Context context;
    private final int youPosition;

    public LeaderboardAdapter(Context context, List<LeaderboardItem> items, int youPosition) {
        this.context = context;
        this.items = items;
        this.youPosition = youPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_leaderboard_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem item = items.get(position);
        holder.tvRank.setText(String.valueOf(item.rank));
        holder.tvName.setText(item.name);
        holder.tvPoints.setText(item.points + " points");
        holder.imgAvatar.setImageResource(item.avatarResId);

        // Highlight the 'You' row
        if (position == youPosition) {
            holder.itemView.setBackgroundResource(R.drawable.bg_leaderboard_you);
            holder.tvName.setTextColor(Color.parseColor("#4B3AFF")); // blue
            holder.tvPoints.setTextColor(Color.parseColor("#4B3AFF"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.tvName.setTextColor(Color.parseColor("#222222"));
            holder.tvPoints.setTextColor(Color.parseColor("#888888"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvName, tvPoints;
        ImageView imgAvatar;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvName = itemView.findViewById(R.id.tvName);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }

    // Dummy data model
    public static class LeaderboardItem {
        public int rank;
        public String name;
        public int points;
        public int avatarResId;
        public LeaderboardItem(int rank, String name, int points, int avatarResId) {
            this.rank = rank;
            this.name = name;
            this.points = points;
            this.avatarResId = avatarResId;
        }
    }
} 