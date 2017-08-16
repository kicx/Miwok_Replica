package com.example.android.miwok;

/**
 * Created by lee on 6/26/2017.
 */



public class Word {
    private int mSoundResourceID;
    private int mImage = NO_IMAGE_PROVIDED;
    private String mMiwokTranslation;
    private String mEnglishTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;



    public Word(String mMiwokTranslation, String mEnglishTranslation, int mMiwokSoundResourceId){
        this.mSoundResourceID = mMiwokSoundResourceId;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mEnglishTranslation = mEnglishTranslation;
    }



    public Word(int mImage, String mMiwokTranslation , String mEnglishTranslation,int mMiwokSoundresourceID){
        this.mImage = mImage;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mEnglishTranslation = mEnglishTranslation;
        this.mSoundResourceID = mMiwokSoundresourceID;

    }

    public int getmSoundResourceID(){
        return mSoundResourceID;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }



    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public void setmMiwokTranslation(String mMiwokTranslation) {
        this.mMiwokTranslation = mMiwokTranslation;
    }

    public String getmEnglishTranslation() {
        return mEnglishTranslation;
    }

    public void setmEnglishTranslation(String mEnglishTranslation) {
        this.mEnglishTranslation = mEnglishTranslation;
    }


    public boolean hasImage(){
        return mImage != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mSoundResourceID=" + mSoundResourceID +
                ", mImage=" + mImage +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mEnglishTranslation='" + mEnglishTranslation + '\'' +
                '}';
    }
}
