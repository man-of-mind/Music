package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.music.databinding.ActivityMusicDetailsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class MusicDetailsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar mSeekBar;
    private Music mMusic;
    private TextView startTime;
    private Handler mHandler;
    private FloatingActionButton mFab;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);
        mMusic = getIntent().getParcelableExtra("Music");
        ActivityMusicDetailsBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_music_details);
        dataBinding.setMusic(mMusic);

        startTime = findViewById(R.id.start);
        mFab = findViewById(R.id.play_pause);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mHandler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    mFab.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
                else{
                    mediaPlayer.start();
                    mFab.setImageResource(R.drawable.ic_pause);
                    updateSeekBar();
                }
            }
        });

        prepareMediaPlayer();
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
             //   startTime.setText(calculateCurrentTime(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
        assert mMusic != null;
        long duration = mMusic.getDuration();
        TextView textView = findViewById(R.id.duration);
        textView.setText(calculateTime(duration));

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                mSeekBar.setSecondaryProgress(i);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mSeekBar.setProgress(0);
                mFab.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                startTime.setText(R.string.start_time);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });

    }


    private void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(mMusic.getLink());
            mediaPlayer.prepare();
        }
        catch (Exception e){
            Log.e("MusicDetailsActivity", Objects.requireNonNull(e.getMessage()));
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
//            startTime.setText(calculateCurrentTime(currentDuration));
        }
    };

    private String calculateCurrentTime(long currentDuration) {
        return null;
    }

    private void updateSeekBar() {
        if(mediaPlayer.isPlaying()){
            mSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            mHandler = new Handler();
            mHandler.postDelayed(updater, 1000);
        }
   }


    private static String calculateTime(long seconds){
        Log.d("MusicDetailsActivity", "duration of the second is " + seconds);
        int minute = (int) TimeUnit.SECONDS.toMinutes(seconds);
        int second = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60));
        if(second >= 0 && second <=9){
            return minute + ":0" + second;
        }
        else {
            return minute + ":" + second;
        }
    }
}