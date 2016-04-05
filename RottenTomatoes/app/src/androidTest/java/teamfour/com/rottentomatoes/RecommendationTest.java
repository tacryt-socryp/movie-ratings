package teamfour.com.rottentomatoes;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

import models.MovieListModel;
import models.MovieModel;
import models.MovieTitlesModel;


/**
 * Created by logan on 4/4/16.
 */
public class RecommendationTest extends ActivityInstrumentationTestCase2<RecommendationActivity> {

    private RecommendationActivity recActivity;

    public RecommendationTest() {
        super(RecommendationActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        recActivity = getActivity();

    }

    public void testPreconditions() {
        assertNotNull("recActivity is null", recActivity);
    }

    public void testEmptyGetMoviesEvent() {
        MovieListModel movieList = new MovieListModel();
        movieList.movies = new ArrayList<MovieModel>();

        recActivity.addToRecommendedMovies(movieList);

        assertEquals(recActivity.getRecommendedMovies().size(), 0);
        assertEquals(recActivity.getTitleToPosition().size(), 0);
    }

    public void testGetMoviesEvent() {
        MovieTitlesModel titlesModel = new MovieTitlesModel();
        String[] movieTitles = {"The Dark Knight"};
        titlesModel.movieTitles = movieTitles;
        recActivity.addToMovieTitle(titlesModel);

        assertEquals(recActivity.getTitleToPosition().size(), 1);

        MovieListModel movieList = new MovieListModel();
        List<MovieModel> movies = new ArrayList<MovieModel>();
        MovieModel movie = new MovieModel();
        movie.title = "The Dark Knight";
        movie.year = "2014";
        movies.add(movie);
        movieList.movies = movies;

        recActivity.addToRecommendedMovies(movieList);

        assertEquals(recActivity.getRecommendedMovies().size(), 1);


        // recActivity.getTitleToPosition().get("The Dark Knight");
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}