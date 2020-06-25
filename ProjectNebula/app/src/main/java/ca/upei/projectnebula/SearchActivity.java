package ca.upei.projectnebula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity implements MovieInterface.informationRequirements,
MovieAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText searchTerm;
    MovieDriver driver;
    JSONArray results;
    JSONArray trailers;
    JSONArray actors;
    JSONArray streaming;
    JSONArray reviews;
    JSONObject movieChosen;
    String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView = findViewById(R.id.moviesView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(this, results);
        recyclerView.setAdapter(adapter);
        searchTerm = findViewById(R.id.searchBar);



        //begin communication with movieInterface and add yourself as a subscriber
        MovieInterface movieInterface = MovieInterface.getInstance();
        movieInterface.addSearchSubscriber(this);
        movieInterface.addMoviePageSubscriber(this);
        movieInterface.addMovieGenresSubscriber(this);

        //request a movie search
        driver = new MovieDriver(); //movie Driver Class that will communicate with the API
    }
    private String replaceSpaces(String sentence){
        return sentence.replace(" ", "+");
    }

    public void searchMovie(View view){
        movieName = searchTerm.getText().toString();
        if(!movieName.matches("") && !movieName.matches(" ")){
            driver.searchMovieName(replaceSpaces(movieName));
        }
    }

    public void printArray(JSONArray results) throws JSONException {
        for(int i = 0; i < results.length();i++){
            Log.d("debug", results.getJSONObject(i).getString("title"));
        }
    }

    /**
     * when Async is done, this method might get called from movieInterface to update Main with the movie attributes
     * please look here: https://developers.themoviedb.org/3/getting-started/search-and-query-for-details
     * to get the available keywords that can be used to receive data
     * @param resultsArray
     */

    @Override
    public void updateSearch(JSONArray resultsArray)
    {
        results = resultsArray;
        try {
            // FIND BETTER WAY TO DO THIS
            adapter = new MovieAdapter(this, results);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
            printArray(results);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * when Async is done, this method might get called from movieInterface to update Main with the movie Details such as: trailer and actors
     * please look here: https://developers.themoviedb.org/3/movies/get-movie-videos and https://developers.themoviedb.org/3/movies/get-movie-credits
     * to get available key words for trailer and cast
     * @param trailerArray,actorArray
     */
    @Override
    public void updatePage(JSONArray trailerArray,JSONArray actorArray,JSONArray streamingArray, JSONArray reviewsArray)
    {

        actors = actorArray;
        trailers = trailerArray;
        try {
            streaming = streamingArray.getJSONObject(0).getJSONArray("locations");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        reviews = reviewsArray;


    }

    @Override
    public void updateSlideShow(JSONArray resultsArray)
    {

    }

    @Override
    protected void onStart() {
        super.onStart();
        trailers = null;
        actors = null;
        reviews = null;
        streaming = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            movieChosen = results.getJSONObject(position);
            driver.searchMovieDetails(Integer.parseInt(movieChosen.getString("id")),movieChosen.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("debug", "in on click");
        new Thread(() -> {
            while(trailers == null || actors == null || reviews == null){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(SearchActivity.this, MoviePage.class);
            Bundle bundle = new Bundle();
            bundle.putString("array", results.toString());
            bundle.putString("movieObject", movieChosen.toString());
            bundle.putString("trailers", trailers.toString());
            if(streaming != null) {
                bundle.putString("streaming", streaming.toString());
            }
            bundle.putString("actors", actors.toString());
            bundle.putString("reviews", reviews.toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }).start();
    }
}
