package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        SimpleFragmentAdapter adapter = new SimpleFragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(viewPager);

        int[] imageResId = {
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_album_24,
                R.drawable.ic_baseline_person_24,
                R.drawable.ic_baseline_playlist_play_24,
                R.drawable.ic_baseline_settings_24
        };


        for (int i = 0; i < imageResId.length; i++){
            mTabLayout.getTabAt(i).setIcon(imageResId[i]);
            if( i != 0) {
                mTabLayout.getTabAt(i).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
            }
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}