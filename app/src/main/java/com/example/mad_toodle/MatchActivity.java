package com.example.mad_toodle;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MatchActivity extends AppCompatActivity {
    private int correctMatches = 0;
    private static final int TOTAL_MATCHES = 3; // Total number of shapes to match

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        // Set touch listeners for all draggable shapes
        int[] draggableIds = {
                R.id.draggableCircle1, R.id.draggableCircle2,
                R.id.draggableTri1, R.id.draggableTri2, R.id.draggableTri3,
                R.id.draggableRect1, R.id.draggableRect2, R.id.draggableRect3
        };

        for (int id : draggableIds) {
            findViewById(id).setOnTouchListener(new MyTouchListener());
        }

        // Set drag listeners for each target shape
        findViewById(R.id.targetCircle).setOnDragListener(new MyDragListener("circle"));
        findViewById(R.id.targetSquare).setOnDragListener(new MyDragListener("rect"));
        findViewById(R.id.targetTriangle).setOnDragListener(new MyDragListener("tri"));
    }

    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            }
            return false;
        }
    }

    private class MyDragListener implements View.OnDragListener {
        String targetShape;

        public MyDragListener(String targetShape) {
            this.targetShape = targetShape;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    View draggedView = (View) event.getLocalState();
                    String draggedName = getResources().getResourceEntryName(draggedView.getId());

                    boolean isMatch = false;
                    switch (targetShape) {
                        case "circle":
                            isMatch = draggedName.contains("Circle");
                            break;
                        case "rect":
                            isMatch = draggedName.contains("Rect");
                            break;
                        case "tri":
                            isMatch = draggedName.contains("Tri");
                            break;
                    }

                    if (isMatch) {
                        // Move dragged view to the center of target
                        draggedView.setX(v.getX());
                        draggedView.setY(v.getY());
                        Toast.makeText(MatchActivity.this, "Correct Match!", Toast.LENGTH_SHORT).show();
                        correctMatches++;
                        
                        // Check if all matches are complete
                        if (correctMatches == TOTAL_MATCHES) {
                            Toast.makeText(MatchActivity.this, "Great job! All shapes matched!", Toast.LENGTH_SHORT).show();
                            // Navigate to DinoEndingActivity after 4 seconds
                            new Handler().postDelayed(() -> {
                                startActivity(new Intent(MatchActivity.this, DinoEndingActivity.class));
                                finish();
                            }, 4000);
                        }
                    } else {
                        Toast.makeText(MatchActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        }
    }
}
