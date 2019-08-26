package idmy.murphi.moviecatalogue.utils;


public class Constants {
    // network
    public static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W185 = "w185";
    private static final String BACKDROP_SIZE = "w780";
    public static final String IMAGE_URL = IMAGE_URL_PREFIX + IMAGE_SIZE_W185;
    public static final String BACKDROP_URL = IMAGE_URL_PREFIX + BACKDROP_SIZE;
    public static final String LANGUAGE_REQUEST_PARAM = "language";
    public static final String PAGE_REQUEST_PARAM = "page";
    public static final String QUERY_REQUEST_PARAM = "query";
    public static final String ADULT_REQUEST_PARAM = "include_adult";
    public static final String START_DATE_REQUEST_PARAM = "primary_release_date.gte";
    public static final String END_DATE_REQUEST_PARAM = "primary_release_date.lte";
    public static final String API_KEY = "7e1d4d757f7c2ed8cc08ad89f1a9d961";
    public static final int PAGE_SIZE = 20;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    // DB
    public static final String DATABASE_NAME = "Catalogue.db";
    static final int NUMBERS_OF_THREADS = 5;
}
