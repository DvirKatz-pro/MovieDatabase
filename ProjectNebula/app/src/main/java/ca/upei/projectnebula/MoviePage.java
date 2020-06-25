package ca.upei.projectnebula;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MoviePage extends AppCompatActivity implements StreamingAdapter.ItemClickListener {
    private JSONObject movie;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StreamingAdapter adapter;
    private ListView listView;
    private JSONArray streaming;
    private ArrayList<String> streamingNames;

    private
    ImageView poster;
    MovieDriver driver;
    TextView title;
    TextView description;
    public String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page);
        Bundle bundle = getIntent().getExtras();
        streamingNames = new ArrayList<>();
        try {
            String test = bundle.getString("streaming");
            if(test != null) {
                streaming = new JSONArray(bundle.getString("streaming"));
                Log.d("debug", streaming.toString());
                for (int i = 0; i < streaming.length(); i++) {
                    streamingNames.add(streaming.getJSONObject(i).getString("display_name"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.streamingView);
        adapter = new StreamingAdapter(this, streamingNames);
        adapter.setClickListener(this);
        listView.setAdapter(adapter);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding fragments for View Pager
        FragmentTrailer ft = new FragmentTrailer();
        FragmentActors fa = new FragmentActors();
        FragmentReviews fd = new FragmentReviews();
        adapter.addFragment(ft, "Trailer(s)");
        adapter.addFragment(fa, "Actors");
        adapter.addFragment(fd, "Reviews");
        ft.setArguments(bundle);
        fa.setArguments(bundle);
        fd.setArguments(bundle);

        // Setting up adapter with pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        driver = new MovieDriver();
        poster = findViewById(R.id.movie_poster);
        title = findViewById(R.id.movie_title);
        description = findViewById(R.id.plot_synopsis);
        try {
            movie = new JSONObject(bundle.getString("movieObject"));
            Picasso.get().load(MOVIE_BASE_URL + movie.getString("poster_path")).into(poster);
            title.setText(movie.getString("title"));
            description.setText(movie.getString("overview"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v, int pos) {
        try {
            String url = streaming.getJSONObject(pos).getString("link");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
