package id.my.murphi.favoriteapp.entity;

import java.util.List;
import java.util.Objects;

public class Movie {

    public static final String TABLE_NAME = "movie";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER = "poster_path";

    private long id;

    private String title;

    private String posterPath;

    private String backdropPath;

    private String overview;

    private String originalLanguage;

    private double popularity;

    private double voteAverage;

    private int voteCount;

    private String releaseDate;

    private boolean isFavorite;

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Double.compare(movie.popularity, popularity) == 0 &&
                Double.compare(movie.voteAverage, voteAverage) == 0 &&
                Objects.equals(title, movie.title) &&
                Objects.equals(posterPath, movie.posterPath) &&
                Objects.equals(overview, movie.overview) &&
                Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterPath, overview, popularity, voteAverage, releaseDate);
    }

    public String getOriginalLanguage() {
        return originalLanguage.substring(0, 1).toUpperCase() + originalLanguage.substring(1);
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
}
