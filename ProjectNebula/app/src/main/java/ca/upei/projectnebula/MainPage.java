package ca.upei.projectnebula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;


public class MainPage extends Activity implements MovieAdapter.ItemClickListener{
    private RecyclerView netflixView;
    private RecyclerView amazonView;
    private RecyclerView googleView;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager googleLayout;
    private RecyclerView.LayoutManager netflixLayout;
    private RecyclerView.LayoutManager amazonLayout;
    JSONArray amazonMovies, netflixMovies, googleMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            amazonMovies = new JSONArray(bundle.getString("amazon"));
            netflixMovies = new JSONArray(bundle.getString("netflix"));
            googleMovies = new JSONArray(bundle.getString("google"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        googleLayout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        googleView = findViewById(R.id.googleplayView);
        googleView.setHasFixedSize(true);
        googleView.setLayoutManager(googleLayout);
        adapter = new MovieAdapter(this, googleMovies);
        googleView.setAdapter(adapter);

        netflixLayout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        netflixView = findViewById(R.id.netflixView);
        netflixView.setHasFixedSize(true);
        netflixView.setLayoutManager(netflixLayout);
        adapter = new MovieAdapter(this, netflixMovies);
        netflixView.setAdapter(adapter);

        amazonLayout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        amazonView = findViewById(R.id.amazonpView);
        amazonView.setHasFixedSize(true);
        amazonView.setLayoutManager(amazonLayout);
        adapter = new MovieAdapter(this, amazonMovies);
        amazonView.setAdapter(adapter);

    }

    public void openSearch(View view){
        Intent page = new Intent(this, SearchActivity.class);
        startActivity(page);
    }

    @Override
    public void onItemClick(View view, int position) {
        //if(//netflix clicked){}
        //else if (//google clicked){}.....
    }
}
