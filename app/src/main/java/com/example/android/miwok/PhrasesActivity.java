package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
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
      final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus",R.raw.audio_phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә",R.raw.audio_phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...",R.raw.audio_phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?",R.raw.audio_phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit",R.raw.audio_phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?",R.raw.audio_phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm",R.raw.audio_phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm",R.raw.audio_phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis",R.raw.audio_phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem",R.raw.audio_phrase_come_here));
        WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());

                // Start the audio file
                mMediaPlayer.start();
                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
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
        }
    }
    }
