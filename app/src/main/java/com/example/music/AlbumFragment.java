package com.example.music;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar mLoadingProgress;
    private RecyclerView mRvBooks;
    private TextView mTvError;

    public AlbumFragment() {
        // Required empty public constructor
    }

    static final String BLOW = "a good time";
    private static final String USER_REQUEST = "https://api.deezer.com/search?q=album:" + '"' + BLOW + '"';

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_track, container, false);

        mLoadingProgress = rootView.findViewById(R.id.pb_loading);
        mRvBooks = rootView.findViewById(R.id.music_recycler);
        mTvError = rootView.findViewById(R.id.tv_arrow);
        setHasOptionsMenu(true);
        MusicAsyncTask task = new MusicAsyncTask();
        task.execute();

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search Album");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_search){
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private class MusicAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = createUrl(USER_REQUEST);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // String response = extractFeatureFromJson(jsonResponse);

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result.isEmpty()){
                mRvBooks.setVisibility(View.INVISIBLE);
                mTvError.setVisibility(View.VISIBLE);
            }
            else{
                mTvError.setVisibility(View.INVISIBLE);
                mRvBooks.setVisibility(View.VISIBLE);
                ArrayList<Music> music = getBooksFromJson(result);

                MusicAdapter adapter = new MusicAdapter(music);
                mRvBooks.setAdapter(adapter);
                mRvBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }

        private String extractFeatureFromJson(String jsonResponse) {
            return null;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            catch (IOException e){

            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null){
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        private URL createUrl(String userRequest) {
            URL url = null;
            try {
                url = new URL(userRequest);
            }
            catch (MalformedURLException e){
                Log.e(TrackFragment.class.getSimpleName(), "Error with creating url", e);
            }
            return url;
        }
    }
    public static ArrayList<Music> getBooksFromJson(String json){
        final String ID = "id";
        final String SONG = "title";
        final String DATA = "data";
        final String ARTISTINFO = "artist";
        final String ALBUMINFO = "album";
        final String PICTUREXL = "picture_xl";

        ArrayList<Music> music = new ArrayList<Music>();
        try{
            JSONObject jsonMusic = new JSONObject(json);
            JSONArray arrayMusic = jsonMusic.getJSONArray(DATA);
            int numberOfMusic = arrayMusic.length();
            for(int i = 0; i < numberOfMusic; i++){
                JSONObject musicJSON = arrayMusic.getJSONObject(i);
//                JSONObject title = musicJSON.getJSONObject(SONG);
                JSONObject artistInfoJson = musicJSON.getJSONObject(ARTISTINFO);
                JSONObject imageLinksJson = null;
                if(artistInfoJson.has(PICTUREXL)){
//                    imageLinksJson = artistInfoJson.getJSONObject(PICTUREXL);
                }
//                JSONObject artistName = artistInfoJson.getJSONObject("name");
                JSONObject album = musicJSON.getJSONObject(ALBUMINFO);
//                JSONObject albumTitle = album.getJSONObject(SONG);
                Music music1 = new Music(
                        musicJSON.getString(SONG),
                        artistInfoJson.getString("name"),
                        album.getString(SONG));
//                        (imageLinksJson==null?"":imageLinksJson.getString(THUMBNAIL)));
                music.add(music1);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return music;
    }
}