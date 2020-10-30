package com.example.music;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.squareup.picasso.Picasso;


public class Music implements Parcelable {
    private String mImage;
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mLink;
    private String mImageLarge;

    public Music(String artist, String album, String track, String image, String imageLarge){
        this.mAlbum = album;
        this.mArtist = artist;
        this.mTrack = track;
        this.mImage = image;
//        this.mLink = link;
        this.mImageLarge = imageLarge;
    }

    protected Music(Parcel in) {
        mImage = in.readString();
        mArtist = in.readString();
        mAlbum = in.readString();
        mTrack = in.readString();
//        mLink = in.readString();
        mImageLarge = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mImage);
        parcel.writeString(mArtist);
        parcel.writeString(mAlbum);
        parcel.writeString(mTrack);
        parcel.writeString(mImageLarge);
//        parcel.writeString(mLink);
    }

    public String getArtist(){
        return mArtist;
    }

    public String getAlbum(){
        return mAlbum;
    }

    public String getTrack(){
        return mTrack;
    }

    public String getImage(){
        return mImage;
    }

    public String getLink(){
        return mLink;
    }

    public String getImageLarge(){
        return mImageLarge;
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
        if(!imageUrl.isEmpty()) {
            Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.ic_baseline_music_note_24).into(view);
        }
        else{
            view.setBackgroundResource(R.drawable.ic_baseline_music_note_24);
        }
    }
}
