package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
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



    private MediaPlayer.OnCompletionListener mOncompleteListenr = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            am.abandonAudioFocus(mAfChangeListener);
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);





        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(R.drawable.number_one,"one", "lutti", R.raw.number_one));
        words.add(new Word(R.drawable.number_two, "two", "otiko",R.raw.number_two));
        words.add(new Word(R.drawable.number_three, "three", "tolookosu",R.raw.number_three));
        words.add(new Word(R.drawable.number_four, "four", "oyyisa", R.raw.number_four));
        words.add(new Word(R.drawable.number_five, "five", "massokka", R.raw.number_five));
        words.add(new Word(R.drawable.number_six, "six", "temmokka", R.raw.number_six));
        words.add(new Word(R.drawable.number_seven, "seven", "kenekaku",R.raw.number_seven));
        words.add(new Word(R.drawable.number_eight, "eight", "kawinta", R.raw.number_eight));
        words.add(new Word(R.drawable.number_nine, "nine", "wo'e",R.raw.number_nine));
        words.add(new Word(R.drawable.number_ten, "ten", "na'aacha",R.raw.number_ten));



        WordAdapter adapter = new WordAdapter(getActivity(), words , R.color.category_numbers);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(mAfChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmSoundResourceID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOncompleteListenr);
                }
                else{releaseMediaPlayer();}

            }
        });
        return rootView;
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            am.abandonAudioFocus(mAfChangeListener);
        }else{
            mMediaPlayer = null;
            am.abandonAudioFocus(mAfChangeListener);
        }
    }@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();

    }



}
