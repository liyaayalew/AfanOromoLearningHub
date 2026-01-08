package com.example.afan_oromo_learning.adapters;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.models.Vocabulary;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {
    private final Context context;
    private List<Vocabulary> vocabularyList;
    private OnVocabularyClickListener listener;
    private TextToSpeech textToSpeech;
    private boolean ttsInitialized = false;

    public interface OnVocabularyClickListener {
        void onVocabularyClick(Vocabulary vocabulary, int position);
        void onFavoriteClick(Vocabulary vocabulary, int position);
    }

    public VocabularyAdapter(Context context) {
        this.context = context.getApplicationContext();
        this.vocabularyList = new ArrayList<>();
        initializeTextToSpeech();
    }

    public VocabularyAdapter(Context context, List<Vocabulary> vocabularyList) {
        this.context = context.getApplicationContext();
        this.vocabularyList = vocabularyList != null ? vocabularyList : new ArrayList<>();
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                ttsInitialized = true;
                // Try to set language to English (for pronunciation guides)
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Language not supported, use default
                    ttsInitialized = false;
                }
            }
        });
    }

    public void setOnVocabularyClickListener(OnVocabularyClickListener listener) {
        this.listener = listener;
    }

    public void updateVocabularyList(List<Vocabulary> newList) {
        if (newList != null) {
            int oldSize = vocabularyList.size();
            vocabularyList.clear();
            vocabularyList.addAll(newList);
            if (oldSize == newList.size()) {
                notifyItemRangeChanged(0, oldSize);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public void addVocabularyItem(Vocabulary item) {
        if (item != null) {
            vocabularyList.add(item);
            notifyItemInserted(vocabularyList.size() - 1);
        }
    }

    public void updateVocabularyFavorite(int position, boolean isFavorite) {
        if (position >= 0 && position < vocabularyList.size()) {
            vocabularyList.get(position).setFavorite(isFavorite);
            notifyItemChanged(position, "favorite");
        }
    }

    public void removeVocabularyItem(int position) {
        if (position >= 0 && position < vocabularyList.size()) {
            vocabularyList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vocabulary item = vocabularyList.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return vocabularyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardVocabulary;
        private final TextView tvOromoWord;
        private final TextView tvEnglishTranslation;
        private final TextView tvPronunciation;
        private final ImageView ivFavorite;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardVocabulary = itemView.findViewById(R.id.cardVocabulary);
            tvOromoWord = itemView.findViewById(R.id.tvOromoWord);
            tvEnglishTranslation = itemView.findViewById(R.id.tvEnglishTranslation);
            tvPronunciation = itemView.findViewById(R.id.tvPronunciation);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);

            // Set content descriptions
            ivFavorite.setContentDescription(context.getString(R.string.add_to_favorites));
        }

        void bind(Vocabulary vocabulary, int position) {
            // Set vocabulary text
            tvOromoWord.setText(vocabulary.getOromoWord());
            tvEnglishTranslation.setText(vocabulary.getEnglishTranslation());
            tvPronunciation.setText(vocabulary.getPronunciation());

            // Set favorite icon
            updateFavoriteIcon(vocabulary.isFavorite());

            // Set click listeners
            cardVocabulary.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVocabularyClick(vocabulary, position);
                }
            });

            ivFavorite.setOnClickListener(v -> {
                boolean newFavoriteState = !vocabulary.isFavorite();
                vocabulary.setFavorite(newFavoriteState);
                updateFavoriteIcon(newFavoriteState);

                if (listener != null) {
                    listener.onFavoriteClick(vocabulary, position);
                }
            });
        }

        private void updateFavoriteIcon(boolean isFavorite) {
            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
                ivFavorite.setContentDescription(context.getString(R.string.remove_from_favorites));
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border);
                ivFavorite.setContentDescription(context.getString(R.string.add_to_favorites));
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakWithTTS(String text) {
            if (ttsInitialized && textToSpeech != null) {
                // Clear any previous speech
                textToSpeech.stop();

                // Speak the text
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "vocabulary_" + text);
            } else {
                // TTS not available, show message
                Toast.makeText(context,
                        "Playing pronunciation: " + text,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}