package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.music.databinding.ActivityMusicDetailsBinding;

import java.util.concurrent.TimeUnit;


public class MusicDetailsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean wasPlaying = false;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);
        Music music = getIntent().getParcelableExtra("Music");
        ActivityMusicDetailsBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_music_details);
        dataBinding.setMusic(music);

        ImageView imageView = findViewById(R.id.play_pause);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong();
            }
        });
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangeValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressChangeValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assert music != null;
        long duration = music.getDuration();
        TextView textView = findViewById(R.id.duration);
        textView.setText(calculateTime(duration));

        }

    private void playSong() {

    }

    private static String calculateTime(long seconds){
        Log.d("MusicDetailsActivity", "duration of the second is " + seconds);
        int minute = (int) TimeUnit.SECONDS.toMinutes(seconds);
        int second = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60));
        String duration;
        if(second >= 0 && second <=9){
            return minute + ":0" + second;
        }
        else {
            return minute + ":" + second;
        }
    }
}