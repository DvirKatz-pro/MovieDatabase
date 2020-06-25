package ca.upei.projectnebula;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

public class FragmentTrailer extends Fragment implements View.OnClickListener {
    View view;
    Bundle bundle;
    String video_id;
    Button button;
    final String YOUTUBE_BASE = "https://www.youtube.com/watch?v=";
//    final String YOUTUBE_API_KEY = "AIzaSyDfmnZGd6TSIItK0u7QJDXXoTlYT9M_Oi8";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trailer_fragment, container, false);
        bundle = getArguments();
        button = view.findViewById(R.id.watch_button);
        button.setOnClickListener(this);
        try {
            video_id = new JSONArray(bundle.getString("trailers"))
                    .getJSONObject(0)
                    .getString("key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE + video_id));
        startActivity(browserIntent);
    }
}
