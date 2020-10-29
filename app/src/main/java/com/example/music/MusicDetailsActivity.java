package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.music.databinding.ActivityMusicDetailsBinding;


public class MusicDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);
        Music music = getIntent().getParcelableExtra("Music");
        ActivityMusicDetailsBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_music_details);
        dataBinding.setMusic(music);

//        ImageView imageView = (ImageView) findViewById(R.id.imgCover);
//        assert music != null;
//        imageView.setImageDrawable(Drawable.createFromPath(music.getImageLarge()));

    }
}