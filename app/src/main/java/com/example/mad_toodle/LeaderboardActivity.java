package com.example.mad_toodle;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ImageView backArrow = findViewById(R.id.backArrow);
        ImageView settingsIcon = findViewById(R.id.settingsIcon);
        backArrow.setOnClickListener(v -> finish());
        settingsIcon.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            View popupView = LayoutInflater.from(this).inflate(R.layout.view_login_bonus_popup, null);
            dialog.setContentView(popupView);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Button claimButton = popupView.findViewById(R.id.claimRewardButton);
            TextView tapToContinue = popupView.findViewById(R.id.tapToContinue);
            View root = popupView.findViewById(android.R.id.content);

            claimButton.setOnClickListener(btn -> {
                Toast.makeText(this, "Reward claimed!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            tapToContinue.setOnClickListener(v2 -> dialog.dismiss());
            popupView.setOnClickListener(v2 -> dialog.dismiss());
            dialog.show();
        });

        // Setup RecyclerView for leaderboard list
        RecyclerView recyclerView = findViewById(R.id.leaderboardRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Dummy data matching the PNG
        List<LeaderboardAdapter.LeaderboardItem> items = new ArrayList<>();
        items.add(new LeaderboardAdapter.LeaderboardItem(1, "Mita S.", 3120, R.drawable.avatar_mita));
        items.add(new LeaderboardAdapter.LeaderboardItem(2, "Devi R.", 2840, R.drawable.avatar_devi));
        items.add(new LeaderboardAdapter.LeaderboardItem(3, "Rohan W.", 2560, R.drawable.avatar_rohan));
        items.add(new LeaderboardAdapter.LeaderboardItem(4, "Alex", 2340, R.drawable.avatar_alex));
        items.add(new LeaderboardAdapter.LeaderboardItem(5, "Lisa", 2210, R.drawable.avatar_lisa));
        items.add(new LeaderboardAdapter.LeaderboardItem(6, "Jane", 1980, R.drawable.avatar_jane));
        items.add(new LeaderboardAdapter.LeaderboardItem(7, "Sam", 1920, R.drawable.avatar_sam));
        items.add(new LeaderboardAdapter.LeaderboardItem(8, "You", 1880, R.drawable.avatar_you));
        items.add(new LeaderboardAdapter.LeaderboardItem(9, "Chris", 1800, R.drawable.avatar_chris));
        items.add(new LeaderboardAdapter.LeaderboardItem(10, "Priya", 1750, R.drawable.avatar_priya));
        items.add(new LeaderboardAdapter.LeaderboardItem(11, "Ava", 1700, R.drawable.avatar_ava));
        items.add(new LeaderboardAdapter.LeaderboardItem(12, "Noah", 1650, R.drawable.avatar_noah));
        items.add(new LeaderboardAdapter.LeaderboardItem(13, "Olivia", 1600, R.drawable.avatar_olivia));
        items.add(new LeaderboardAdapter.LeaderboardItem(14, "Liam", 1550, R.drawable.avatar_liam));
        items.add(new LeaderboardAdapter.LeaderboardItem(15, "Emma", 1500, R.drawable.avatar_emma));

        int youPosition = 7; // 'You' is at index 7 in the dummy data
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, items, youPosition);
        recyclerView.setAdapter(adapter);
    }
} 