package com.example.music;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    public SimpleFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new TrackFragment();
        }
        else if (position == 1){
            return new AlbumFragment();
        }
        else if (position == 2){
            return new ArtistFragment();
        }
        else {
            return new SettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
