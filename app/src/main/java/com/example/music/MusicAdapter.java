package com.example.music;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private ArrayList<Music> mMusic;
    Context mContext;
    RecyclerView mRecyclerView;

    public MusicAdapter(Context context, ArrayList<Music> music, RecyclerView recycler){
        this.mContext = context;
        this.mMusic = music;
        this.mRecyclerView = recycler;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e((String) view.getTag(), "onClick method is getting called");
            int position = mRecyclerView.getChildLayoutPosition(view);
            Toast.makeText(mContext, "onClick activated", Toast.LENGTH_SHORT).show();
            Music selectedMusic = mMusic.get(position);
            Intent intent = new Intent(view.getContext(), MusicDetailsActivity.class);
            intent.putExtra("Music", selectedMusic);
            view.getContext().startActivity(intent);
        }
    };

    @NonNull
    @Override
    public MusicAdapter.MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View musicView = inflater.inflate(R.layout.music_list, parent, false);
        musicView.setOnClickListener(mOnClickListener);

        return new MusicViewHolder(musicView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, int position) {

        Music music = mMusic.get(position);

        TextView textView = holder.artist;
        textView.setText(music.getArtist());
        TextView textView1 = holder.album;
        textView1.setText(music.getAlbum());
        TextView textView2 = holder.track;
        textView2.setText(music.getTrack());
        ImageView imageView = holder.image;
        Picasso.with(imageView.getContext()).load(music.getImage()).error(R.drawable.ic_baseline_insert_photo_24).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }



    public class MusicViewHolder extends RecyclerView.ViewHolder {

        public TextView artist;
        public TextView album;
        public TextView track;
        public ImageView image;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            artist = (TextView) itemView.findViewById(R.id.title);
            album = (TextView) itemView.findViewById(R.id.subTitle);
            track = (TextView) itemView.findViewById(R.id.track);
            image = (ImageView) itemView.findViewById(R.id.imageUrl);
        }
    }
}
