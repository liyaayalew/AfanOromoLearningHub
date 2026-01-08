package com.example.afan_oromo_learning.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.models.Lesson;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private final Context context;
    private List<Lesson> lessonList;
    private final OnLessonClickListener listener;

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
        
    }

    public LessonAdapter(Context context, List<Lesson> lessonList, OnLessonClickListener listener) {
        this.context = context;
        this.lessonList = lessonList;
        this.listener = listener;
    }

    public void updateLessons(List<Lesson> newLessonList) {
        this.lessonList = newLessonList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lesson_card, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.bind(lesson);
    }

    @Override
    public int getItemCount() {
        return lessonList != null ? lessonList.size() : 0;
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardLesson;
        private final TextView tvLessonNumber;
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvDuration;
        private final TextView tvLevel;
        private final ProgressBar progressBar;
        private final TextView tvProgress;
        private final ImageView ivLock;
        private final TextView btnStart;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLesson = itemView.findViewById(R.id.cardLesson);
            tvLessonNumber = itemView.findViewById(R.id.tvLessonNumber);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            ivLock = itemView.findViewById(R.id.ivLock);
            btnStart = itemView.findViewById(R.id.btnStart);

            // Set click listeners
            btnStart.setOnClickListener(v -> handleLessonClick());
            cardLesson.setOnClickListener(v -> handleLessonClick());
        }

        private void handleLessonClick() {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Lesson lesson = lessonList.get(position);
                listener.onLessonClick(lesson);
            }
        }

        // In the bind method of LessonViewHolder:
        public void bind(Lesson lesson) {
            // Set lesson number
            int lessonNumber = getBindingAdapterPosition() + 1;
            tvLessonNumber.setText(String.format(Locale.getDefault(),
                    context.getString(R.string.lesson_number_format), lessonNumber));

            // Set lesson details
            tvTitle.setText(lesson.getTitle());
            tvDescription.setText(lesson.getDescription());
            tvDuration.setText(lesson.getDuration() + " min");
            
            // FIXED: Convert level from int to string properly
            int levelInt = lesson.getLevel();
            String levelText = getLevelText(levelInt);
            tvLevel.setText(levelText);  // Now passing String instead of int

            // FIXED: Pass the converted string to setLevelBackground
            setLevelBackground(levelText);

            // Set progress
            int progress = lesson.getProgress();
            progressBar.setProgress(progress);
            tvProgress.setText(String.format(Locale.getDefault(),
                    context.getString(R.string.percent_format), progress));
            
            // Handle "coming soon" lessons specially
            if (lesson.isComingSoon()) {
                showComingSoonState(lesson);
                return;
            }

            // Handle locked/unlocked state
            if (!lesson.isUnlocked()) {
                showLockedState();
                btnStart.setText(context.getString(R.string.locked));
                btnStart.setBackgroundResource(R.drawable.btn_disabled);
                btnStart.setTextColor(ContextCompat.getColor(context, R.color.textHint));
            } else {
                showUnlockedState();

                // Update button text based on progress
                if (lesson.isCompleted()) {
                    btnStart.setText(context.getString(R.string.review));
                    btnStart.setBackgroundResource(R.drawable.btn_success);
                    progressBar.setProgressTintList(
                            ContextCompat.getColorStateList(context, R.color.success));
                    tvProgress.setTextColor(ContextCompat.getColor(context, R.color.success));
                } else if (progress > 0) {
                    btnStart.setText(context.getString(R.string.continue_text));
                    btnStart.setBackgroundResource(R.drawable.btn_primary);
                } else {
                    btnStart.setText(context.getString(R.string.start));
                    btnStart.setBackgroundResource(R.drawable.btn_primary);
                }
                btnStart.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        }

        // helper method to the LessonViewHolder class:
        private String getLevelText(int level) {
            switch (level) {
                case 1: return "Beginner";
                case 2: return "Intermediate";
                case 3: return "Advanced";
                default: return "Beginner";
            }
        }

        // Your setLevelBackground method can be simplified since we're now passing the text version:
        private void setLevelBackground(String level) {
            int colorResId;

            switch (level.toLowerCase()) {
                case "beginner":
                    colorResId = R.color.level_beginner;
                    break;
                case "intermediate":
                    colorResId = R.color.level_intermediate;
                    break;
                case "advanced":
                    colorResId = R.color.level_advanced;
                    break;
                default:
                    colorResId = R.color.level_beginner;
                    break;
            }

            tvLevel.setBackgroundColor(ContextCompat.getColor(context, colorResId));
        }

        private void showLockedState() {
            ivLock.setVisibility(View.VISIBLE);
            cardLesson.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grayLight));

            // Dim text colors
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.textSecondary));
            tvDescription.setTextColor(ContextCompat.getColor(context, R.color.textHint));

            // Disable progress bar
            progressBar.setProgressTintList(
                    ContextCompat.getColorStateList(context, R.color.grayDark));
            tvProgress.setTextColor(ContextCompat.getColor(context, R.color.grayDark));

            // Disable button
            btnStart.setEnabled(false);
        }

        private void showUnlockedState() {
            ivLock.setVisibility(View.GONE);
            cardLesson.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

            // Normal text colors
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.textPrimary));
            tvDescription.setTextColor(ContextCompat.getColor(context, R.color.textSecondary));

            // Enable button
            btnStart.setEnabled(true);
        }
        
        private void showComingSoonState(Lesson lesson) {
            // Gray out the card
            cardLesson.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grayLight));
            
            // Show "Coming Soon" text instead of progress
            tvProgress.setText("Coming Soon");
            progressBar.setVisibility(View.GONE);
            
            // Change button text
            btnStart.setText("Coming Soon");
            btnStart.setBackgroundResource(R.drawable.btn_disabled);
            btnStart.setEnabled(false);
            
            // Make entire card not clickable
            cardLesson.setClickable(false);
            cardLesson.setEnabled(false);
            
            // Dim the text
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.textSecondary));
            tvDescription.setTextColor(ContextCompat.getColor(context, R.color.textHint));
        }
    }
}