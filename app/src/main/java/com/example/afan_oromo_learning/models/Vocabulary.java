package com.example.afan_oromo_learning.models;

public class Vocabulary {
    private String id;
    private String oromoWord;
    private String englishTranslation;
    private String pronunciation;
    private boolean favorite;
    private int audioResourceId;

    // Constructor
    public Vocabulary(String id, String oromoWord, String englishTranslation,
                      String pronunciation, boolean favorite, int audioResourceId) {
        this.id = id;
        this.oromoWord = oromoWord;
        this.englishTranslation = englishTranslation;
        this.pronunciation = pronunciation;
        this.favorite = favorite;
        this.audioResourceId = audioResourceId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOromoWord() { return oromoWord; }
    public void setOromoWord(String oromoWord) { this.oromoWord = oromoWord; }

    public String getEnglishTranslation() { return englishTranslation; }
    public void setEnglishTranslation(String englishTranslation) { this.englishTranslation = englishTranslation; }

    public String getPronunciation() { return pronunciation; }
    public void setPronunciation(String pronunciation) { this.pronunciation = pronunciation; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public int getAudioResourceId() { return audioResourceId; }
    public void setAudioResourceId(int audioResourceId) { this.audioResourceId = audioResourceId; }
}