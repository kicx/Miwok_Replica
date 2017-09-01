package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    MediaPlayer mMediaPlayer;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener mAfChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };



    @Override
    public void onStop() {
        super.onStop();

        am.abandonAudioFocus(mAfChangeListener);
    }

    private MediaPlayer.OnCompletionListener mOncompleteListenr = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            am.abandonAudioFocus(mAfChangeListener);
        }
    };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.word_list,container,false);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(R.drawable.color_red, "red", "weṭeṭṭi", R.raw.color_red));
        words.add(new Word(R.drawable.color_green, "green", "chokokki",R.raw.color_green));
        words.add(new Word(R.drawable.color_brown,"brown", "ṭakaakki", R.raw.color_brown));
        words.add(new Word(R.drawable.color_gray, "gray", "ṭopoppi", R.raw.color_gray));
        words.add(new Word(R.drawable.color_black, "black", "kululli", R.raw.color_black));
        words.add(new Word(R.drawable.color_white, "white", "kelelli",R.raw.color_white));
        words.add(new Word(R.drawable.color_dusty_yellow, "dusty yellow", "ṭopiisә",R.raw.color_dusty_yellow));
        words.add(new Word(R.drawable.color_mustard_yellow, "mustard yellow", "chiwiiṭә",R.raw.color_mustard_yellow));



        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView)viewRoot.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                int result = am.requestAudioFocus(mAfChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getmSoundResourceID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOncompleteListenr);

                }
            }
        });
            return  viewRoot;
    }
    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
        }else{
            mMediaPlayer = null;
        }
    }



}
