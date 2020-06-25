package ca.upei.projectnebula;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


/**
 * Encapsulates All APIs in use, guideBox(discontinued) , moviedb and utelly
 * for additional information: https://developers.themoviedb.org/3/getting-started/introduction
 *
 */
public class MovieAPI extends AsyncTask<Integer,Void,String>
{

    URL url = null;
    String apiKey =  "8ce2398d6af57591784dbd730025152c";
    String guideApiKey = "334315cfc4d86e536d5ebd342e8998f08e4d8788";


    /**
     * 43-52 is needed for Utelly API
     */

    private final String UTELLY_API_DEFAULT_URL = "https://utelly-tv-shows-and-movies-availability-v1.p.rapidapi.com/lookup";
    private final String UTELLY_API_HOST_HEADER = "x-rapidapi-host";
    private final String UTELLY_API_HOST = "utelly-tv-shows-and-movies-availability-v1.p.rapidapi.com";
    private final String UTELLY_API_KEY_HEADER = "x-rapidapi-key";
    private final String UTELLY_API_KEY = "9fe449fe2bmsh8998b6f89a523ddp1dc59bjsna67aca15f258";
    private final String UTELLY_DEFAULT_REQUEST_METHOD = "GET";
    private final String[] REGIONS = {"us", "uk"};
    private final String DEFAULT_REGION = REGIONS[0];
    private final String CHARSET = "UTF-8";
    URL utellyRequestURL = null;


    //pre set instructions
    int instructions;
    final int searchMovieNum = 200;
    final int searchMovieDetailsNum = 100;
    final int searchMovieGenresNum = 300;


    //JSON arrays holding movie information
    JSONObject jsonObject;
    JSONArray streamingArray;
    //JSONArray topStreamingArray;
    JSONArray resultsArray;
    JSONArray actorArray;
    JSONArray trailerArray;
    JSONArray genresArray;
    JSONArray reviewArray;
    //JSONObject guideBoxObject;


    MovieInterface movieInterface = MovieInterface.getInstance();

    WeakReference<AsyncRequirements> delegate;

    //to communicate with Driver
    public MovieAPI(MovieAPI.AsyncRequirements theDelegate)
    {
        delegate = new WeakReference<>(theDelegate);
    }

    //list of methods needed to communicate with Driver
    public interface AsyncRequirements
    {
        String getURL();
        String getName();
        int getMovieID();
        int getGenresID();
    }

    @Override
    protected String doInBackground(Integer... integers)
    {
        instructions = integers[0];
        //bsdrf on instructions given call appropriate methods
        switch (instructions)
        {
            case (searchMovieNum):
                search(); //search for a movie
                break;

            case (searchMovieDetailsNum):
                //get the trailer and the actors
                getVideo();
                getActors();
                getReview();
                getStreaming();


                break;

            case (searchMovieGenresNum):
                getGenres();
                //getTopStreaming();
                break;

        }

        return "";
    }

    @Override
    protected void onPostExecute(String s)
    {
        //update the movie interface with the results
        switch (instructions)
        {
            //based on the instructions, send the result to movie interface
            case (searchMovieNum):
                movieInterface.updateSearch(resultsArray);
                break;

            case (searchMovieDetailsNum):
                //get the trailer and the actors
                movieInterface.updatePage(trailerArray, actorArray,streamingArray,reviewArray);

                break;

            case (searchMovieGenresNum):
                movieInterface.updateSlideShow(genresArray);
                break;
        }


        super.onPostExecute(s);
    }


    /**
     * given a movie name from MovieDriver set the result array to basic movie details (ID,name,plot etc)
     * Uses moviedb
     *
     */
    public void search()
    {
        try
        {
            AsyncRequirements original = delegate.get();
            if (original != null)
            {

                String name = original.getName();

                String appendURL = original.getURL() + "/3/search/movie?api_key=" + apiKey + "&query=" + name;
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null )
            {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try
            {
                jsonObject = new JSONObject(jsonString);
                resultsArray = new JSONArray(jsonObject.get("results").toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * given a movie ID from MovieDriver, set the trailer array to a youtube key, (url extension)
     * Uses moviedb
     */
    public void getVideo()
    {
        try
        {
            AsyncRequirements original = delegate.get();
            if (original != null)
            {

                int ID = original.getMovieID();
                String appendURL = original.getURL() + "/3/movie/" + ID + "/videos?api_key=" + apiKey + "&language=en-US";
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null )
            {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try
            {
                jsonObject = new JSONObject(jsonString);
                trailerArray = new JSONArray(jsonObject.get("results").toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * given a movie ID set the actor array to the list of actors for that movie
     * uses moviedb
     */
    public void getActors()
    {
        try
        {
            AsyncRequirements original = delegate.get();
            if (original != null)
            {

                int ID = original.getMovieID();
                String appendURL = original.getURL() + "/3/movie/" + ID + "/credits?api_key=" + apiKey;
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null )
            {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                actorArray = new JSONArray(jsonObject.get("cast").toString());


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * given a genre ID set the genres array to the top 20 movies of that genre, (not in use)
     * uses moviedb
     */
    public void getGenres()
    {
        try
        {
            AsyncRequirements original = delegate.get();
            if (original != null)
            {

                int ID = original.getGenresID();
                String appendURL = original.getURL() + "/3/discover/movie?api_key="+ apiKey + "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + ID;
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null )
            {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                genresArray = new JSONArray(jsonObject.get("results").toString());


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * check if given region is valid to get streaming info from that region(us or uk) not in use, set to US by default
     * @param searchRegion
     * @return
     */
    private boolean isValidRegion(String searchRegion)
    {
        for (String validRegion : REGIONS)
            if (searchRegion.equalsIgnoreCase(validRegion))
                return true;

      return false;
    }

    /**
     * build the url for utelly API
     * @param searchPhrase
     * @param searchRegion
     * @return
     */
    private String buildRequestURLString(String searchPhrase, String searchRegion)
    {
       String result = UTELLY_API_DEFAULT_URL + "?";
       result += "term=" + searchPhrase + "&";
        result += "country=" + searchRegion;

       return result;
   }

    /**
     * given a movie name return the streaming information for that movie, 50% accuracy due to Utelly API
     */
    public void getStreaming()
    {
        AsyncRequirements original = delegate.get();
        if (original != null)
        {
            String searchPhrase = original.getName();
            String searchRegion = DEFAULT_REGION;

            try
            {
                // Encode the Search Parameters to the appropriate charset
                searchPhrase = URLEncoder.encode(searchPhrase, CHARSET);
                searchRegion = URLEncoder.encode(searchRegion, CHARSET);

                // Build the Utelly Request URL String based on the encoded search phrase and search region
                String utellyRequestURLString = buildRequestURLString(searchPhrase, searchRegion);

                // Create the Utelly Request URL and open a HTTP Connection to the Utelly API Server
                try
                {
                    utellyRequestURL = new URL(utellyRequestURLString);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }


        HttpURLConnection utellyHCon;
        try
        {
            utellyHCon = (HttpURLConnection) utellyRequestURL.openConnection();
            utellyHCon.setRequestMethod(UTELLY_DEFAULT_REQUEST_METHOD);

            // Adding Header Parameters for the connection
            utellyHCon.setRequestProperty(UTELLY_API_HOST_HEADER, UTELLY_API_HOST);
            utellyHCon.setRequestProperty(UTELLY_API_KEY_HEADER, UTELLY_API_KEY);

            // Debugging
            // int utellyResponseCode = utellyHCon.getResponseCode();
            // Log.d("Utelly Response Code", utellyResponseCode + "");

            // Reading the API Server's response and return the output as a String
            InputStream responseStream = utellyHCon.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = responseReader.readLine()) != null)
                stringBuilder.append(line + "\n");

            String jsonString = stringBuilder.toString();

            try
            {
                jsonObject = new JSONObject(jsonString);
                streamingArray = jsonObject.getJSONArray("results");

            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            // Log.d("Utelly Response", jsonString);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * given a movie ID, set the reviewArray to a list of reviews for that movie
     * uses moviedb
     */
    public void getReview()
    {
        try
        {
            AsyncRequirements original = delegate.get();
            if (original != null)
            {

                int ID = original.getMovieID();
                String appendURL = original.getURL() + "/3/movie/" + ID + "/reviews?api_key="+ apiKey + "&language=en-US&page=1";
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null )
            {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                reviewArray = new JSONArray(jsonObject.get("results").toString());


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  the methods below use guide box to retrieve streaming  information, get guideBox() returns a movie array, getStreaming() returns streaming results for one movie
     *  getTopstreaming() returns results for top rated movies on each streaming service. (discontinued due API trial expiration)
     */
/*


    public void getGuideBox() {
        try {
            AsyncRequirements original = delegate.get();
            if (original != null) {

                String name = original.getName();
                // String encoded = URLEncoder.encode(name, "UTF-8");
                String appendURL = original.getGuideURL() + "/search?api_key=" + guideApiKey + "&type=movie&field=title&query=" + name; // guideApiKey + //"/movies?api_key=" + guideApiKey + "&platform=ios";// "/CA/" + guideApiKey + " /search/movie/title/" + encoded;
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = new JSONArray(jsonObject.get("results").toString());
                guideBoxObject = (JSONObject) jsonArray.get(0);
                //guideBoxObject = new JSONObject(jsonArray.get(0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getStreaming()
    {
        try {
            AsyncRequirements original = delegate.get();
            if (original != null) {

                try
                {
                    int ID = guideBoxObject.getInt("id");
                    String appendURL = original.getGuideURL() +"/movies/" + ID + "?api_key=" + guideApiKey + "&sources=" + original.getStreamingName();
                    url = new URL(appendURL);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try {
                streamingObject = new JSONObject(jsonString);

                //String stream = jsonObject.get("subscription_web_sources").toString();
                //Log.d("stream",stream);
                //streamingObject = jsonObject.get("source").toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void getTopStreaming() {
        try {
            AsyncRequirements original = delegate.get();
            if (original != null) {

                String name = original.getStreamingName();
                // String encoded = URLEncoder.encode(name, "UTF-8");
                String appendURL = original.getGuideURL() + "/movies?api_key=" + guideApiKey + "&sources=" + name;
                url = new URL(appendURL);
            }

            HttpsURLConnection hcon = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = hcon.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            String jsonString = stringBuilder.toString();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                topStreamingArray = new JSONArray(jsonObject.get("results").toString());
                //guideBoxObject = new JSONObject(jsonArray.get(0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


*/

}
