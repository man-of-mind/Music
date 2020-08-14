package com.example.music;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public SimpleFragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
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
        else if (position == 3){
            return new PlaylistFragment();
        }
        else {
            return new SettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.track);
        }
        else if (position == 1){
            return mContext.getString(R.string.album);
        }
        else if (position == 2){
            return mContext.getString(R.string.artist);
        }
        else if (position == 3){
            return mContext.getString(R.string.playlist);
        }
        else {
            return mContext.getString(R.string.settings);
        }
    }


}
