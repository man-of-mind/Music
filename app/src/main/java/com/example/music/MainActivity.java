package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setHideOnContentScrollEnabled(true);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        SimpleFragmentAdapter adapter = new SimpleFragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        int[] imageResId = {
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_album_24,
                R.drawable.ic_baseline_person_24,
                R.drawable.ic_baseline_playlist_play_24,
                R.drawable.ic_baseline_settings_24
        };


        for (int i = 0; i < imageResId.length; i++){
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
            if( i != 0) {
                tabLayout.getTabAt(i).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED);
                int tabPosition = tab.getPosition();
                if (tabPosition == 4) {
                    toolbar.setVisibility(View.GONE);
                }
                else toolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
                int tabPosition = tab.getPosition();
                if(tabPosition != 4)
                    toolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


}