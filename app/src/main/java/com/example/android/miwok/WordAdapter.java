package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lee on 6/28/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceID){
        super(context,0, words);
        mColorResourceId = colorResourceID;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView  = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        ImageView mImageView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
            mImageView.setImageResource(currentWord.getmImage());
            mImageView.setVisibility(View.VISIBLE);
        }else{
            mImageView.setVisibility(View.GONE);
        }
        TextView mEnglishWord = (TextView) listItemView.findViewById(R.id.textV1);
        TextView mMiwokWord = (TextView) listItemView.findViewById(R.id.textV2);

        ImageView imageViewplay = (ImageView) listItemView.findViewById(R.id.imageView1);
        imageViewplay.setVisibility(View.VISIBLE);




        mEnglishWord.setText(currentWord.getmEnglishTranslation());
        mMiwokWord.setText(currentWord.getmMiwokTranslation());

        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}
