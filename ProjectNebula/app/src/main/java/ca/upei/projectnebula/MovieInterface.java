package ca.upei.projectnebula;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A singleton class designed to send search results to other classes
 */
public class MovieInterface {

    public static MovieInterface movieInterface = null;

    //list of Activates subscribed to each array
    private static ArrayList movieSearchArray = new ArrayList();
    private static ArrayList moviePage = new ArrayList();
    private static ArrayList movieSearchGenres = new ArrayList();

    WeakReference<informationRequirements> delegate;

    public interface informationRequirements
    {
        void updateSearch(JSONArray resultArray);
        void updatePage(JSONArray trailerArray,JSONArray actorArray,JSONArray streamingArrry,JSONArray reviewArray);
        void updateSlideShow(JSONArray resultsArray);
    }

    private MovieInterface()
    {

    }

    /**
     *  to ensure only one instate of Movie Interface exists
     * @return
     */
    public static MovieInterface getInstance() {
        if (movieInterface == null)
        {
            movieInterface = new MovieInterface();
        }

        return movieInterface;
    }

    /**
     * add an activity that wants to receive the movie attributes
     * @param theDelegate
     */
    public void addSearchSubscriber(informationRequirements theDelegate)
    {
        delegate = new WeakReference<>(theDelegate);
        movieSearchArray.add(delegate);
    }

    /**
     * add an activity that wants to receive additional movie details, such as cast or trailer
     * @param theDelegate
     */
    public void addMoviePageSubscriber(informationRequirements theDelegate) {
        delegate = new WeakReference<>(theDelegate);
        moviePage.add(delegate);
    }

    /**
     * add an activity that wants to receive the top genres for a genre search
     * @param theDelegate
     */
    public void addMovieGenresSubscriber(informationRequirements theDelegate)
    {
        delegate = new WeakReference<>(theDelegate);
        movieSearchGenres.add(delegate);
    }

    /**
     * update all Activates that are subscribed to receive movie attributes
     * @param jsonArray
     */
    public void updateSearch(JSONArray jsonArray)
    {

        for (int i = 0; i < movieSearchArray.size(); i++)
        {
            WeakReference<informationRequirements> delegate = (WeakReference<informationRequirements>) movieSearchArray.get(i);
            informationRequirements original = delegate.get();
            if (original != null)
            {
                original.updateSearch(jsonArray);
            }
        }


    }

    /**
     * update all Activates that are subscribed to receive movie attributes
     * @param trailerArray
     * @param actorArray
     */
    public void updatePage(JSONArray trailerArray, JSONArray actorArray, JSONArray streamingArray,JSONArray reviewArray)
    {
        for (int i = 0; i < moviePage.size(); i++)
        {
            WeakReference<informationRequirements> delegate = (WeakReference<informationRequirements>) movieSearchArray.get(i);
            informationRequirements original = delegate.get();
            if (original != null)
            {
                original.updatePage(trailerArray,actorArray,streamingArray,reviewArray);
            }
        }
    }
    public void updateSlideShow(JSONArray resultsArray)
    {
        for (int i = 0; i < movieSearchGenres.size();i++)
        {
            WeakReference<informationRequirements> delegate = (WeakReference<informationRequirements>) movieSearchArray.get(i);
            informationRequirements original = delegate.get();
        if (original != null)
            {
                original.updateSlideShow(resultsArray);
            }
        }
    }

}