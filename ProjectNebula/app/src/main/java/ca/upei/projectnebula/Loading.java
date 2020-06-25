package ca.upei.projectnebula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

public class Loading extends Activity implements MovieInterface.informationRequirements {
    JSONArray netflixMovies;
    JSONArray amazonPrime;
    JSONArray googlePlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        netflixMovies = null;
        amazonPrime = null;
        googlePlay = null;

        MovieInterface movieInterface = MovieInterface.getInstance();
        movieInterface.addSearchSubscriber(this);
        movieInterface.addMovieGenresSubscriber(this);
        movieInterface.addMoviePageSubscriber(this);

        MovieDriver driver = new MovieDriver();
        driver.searchGenres(driver.horrorNum, "netflix");
        Thread thread = new Thread(() -> {
            while(netflixMovies == null){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("debug", "Netflix movies: " + netflixMovies.toString());
            driver.searchGenres(driver.adventureNum, "amazon_prime");
            Thread thread2 = new Thread(() -> {
                while (amazonPrime == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("debug", "Amazon movies: " + amazonPrime.toString());
                driver.searchGenres(driver.actionNum, "google_play");
            });
            thread2.start();
        });
        thread.start();

        Thread threadMain = new Thread(() -> {
            while(googlePlay == null){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("debug", "google Play movies: " + googlePlay.toString());
            Intent intent = new Intent(getBaseContext(), MainPage.class);
            Bundle bundle = new Bundle();
            bundle.putString("netflix", netflixMovies.toString());
            bundle.putString("google", googlePlay.toString());
            bundle.putString("amazon", amazonPrime.toString());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        threadMain.start();
    }

    @Override
    public void updateSearch(JSONArray resultArray) {

    }

    @Override
    public void updatePage(JSONArray trailerArray, JSONArray actorArray, JSONArray streamingArray, JSONArray reviewArray) {

    }

    @Override
    public void updateSlideShow(JSONArray resultsArray)
    {

        if(netflixMovies == null && googlePlay == null  && amazonPrime == null){
            netflixMovies = resultsArray;
        } else if (googlePlay == null && amazonPrime == null){
            amazonPrime = resultsArray;
        } else if (googlePlay == null){
            googlePlay = resultsArray;
        }



    }
}
