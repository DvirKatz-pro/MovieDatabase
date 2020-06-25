package ca.upei.projectnebula;

public class MovieDriver implements MovieAPI.AsyncRequirements {

    private String name;
    private String streamingName;
    private int ID;


    int genresID;

    //avilable instruction codes
    final int searchMovieNum = 200;
    final int searchMovieDetailsNum = 100;
    final int searchMovieGenresNum = 300;

    //avilable genres to search for, not implemented yet
    public final int thrillerNum = 53;
    public final int actionNum = 28;
    public final int adventureNum = 12;
    public final int comedyNum = 35;
    public final int documentryNum = 35;
    public final int dramaNum = 18;
    public final int fantasyNum = 14;
    public final int horrorNum = 27;
    public final int romanceNum = 10749;
    public final int scienceFictionNum = 878;


    /**
     * getter methods for Movie API requierd information
     * @return
     */
    public String getURL()
    {
        return "https://api.themoviedb.org";
    }
    public String getGuideURL()
    {
        return "https://api-public.guidebox.com/v2";
    }
    public int getMovieID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public int getGenresID()
    {
        return genresID;
    }
    public String getStreamingName()
    {
        return streamingName;
    }


    /**
     * given a movie name search for that movie
     * @param m_name
     */
    public void searchMovieName(String m_name)
    {
        name = m_name;
        new MovieAPI(this).execute(searchMovieNum);
    }

    /**
     * given a movie ID search for the cast and a trailer
     * @param m_ID
     */
    public void searchMovieDetails(int m_ID,String m_name)
    {
        ID = m_ID;
        name = m_name;
        new MovieAPI(this).execute(searchMovieDetailsNum);

    }

    /**
     * given a genre name return top movies for that genre, this method is currently only used to initilize the first page
     *
     * @param m_genresID
     * @param m_streamingName if guideBox API in use (removed because trial expierd) then takes a streaming service to search for its top movies
     */
    public void searchGenres(int m_genresID,String m_streamingName)
    {
        genresID = m_genresID;
        streamingName = m_streamingName;
        new MovieAPI(this).execute(searchMovieGenresNum);
    }

}