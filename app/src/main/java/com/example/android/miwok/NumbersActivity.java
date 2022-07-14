package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
   private MediaPlayer mMediaPlayer;
   private AudioManager mAudioManager;
   AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
       @Override
       public void onAudioFocusChange(int focusChange) {
           if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) {
               //pause playback
               mMediaPlayer.pause();
               mMediaPlayer.seekTo(0);
           } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
               //resume playback
               mMediaPlayer.start();
           } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
               releaseMediaPlayer();
           }
       }
   };
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        // create and setup the(@Link AudioManager) to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
       final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One","lutti",R.drawable.number_one, R.raw.audio_number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two, R.raw.audio_number_two));
        words.add(new Word("three", "tolookosu",R.drawable.number_three, R.raw.audio_number_three));
        words.add(new Word("four", "oyyisa",R.drawable.number_four, R.raw.audio_number_four));
        words.add(new Word("five", "massokka",R.drawable.number_five, R.raw.audio_number_five));
        words.add(new Word("six", "temmokka",R.drawable.number_six, R.raw.audio_number_six));
        words.add(new Word("seven", "kenekaku",R.drawable.number_seven, R.raw.audio_number_seven));
        words.add(new Word("eight", "kawinta",R.drawable.number_eight, R.raw.audio_number_eight));
        words.add(new Word("nine", "wo’e",R.drawable.number_nine, R.raw.audio_number_nine));
        words.add(new Word("ten", "na’aacha",R.drawable.number_ten, R.raw.audio_number_ten));

        WordAdapter adapter = new WordAdapter(this,words, R.color.category_numbers );

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 Word word = words.get(position);
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener , AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                        if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                        {

                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}