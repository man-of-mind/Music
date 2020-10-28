package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.music.databinding.ActivityMusicDetailsBinding;


public class MusicDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);
        Music music = getIntent().getParcelableExtra("Music");
        ActivityMusicDetailsBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_music_details);
        dataBinding.setMusic(music);
    }
}