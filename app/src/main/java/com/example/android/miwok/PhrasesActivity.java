package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
MediaPlayer mMediaPlayer;
    AudioManager am;

  private AudioManager.OnAudioFocusChangeListener amListener = new AudioManager.OnAudioFocusChangeListener() {
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
  }  ;

    private MediaPlayer.OnCompletionListener mOnCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
        releaseMediaPlayer();
            am.abandonAudioFocus(amListener);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

        am.abandonAudioFocus(amListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();



        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let's go", "yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));



        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);


                int result = am.requestAudioFocus(amListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmSoundResourceID());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mOnCompleteListener);

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
        }else{
            mMediaPlayer = null;
        }
    }
}
