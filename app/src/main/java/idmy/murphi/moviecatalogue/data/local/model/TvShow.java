package idmy.murphi.moviecatalogue.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = TvShow.TABLE_NAME)
public class TvShow {

    public static final String TABLE_NAME = "tv_show";
    public static final String COLUMN_TITLE = "name";

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @SerializedName(TvShow.COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String releaseDate;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    private List<Genre> genres;

    @Ignore
    @SerializedName("credits")
    private TvShowCastResponse creditsResponse;

    public TvShowCastResponse getCreditsResponse() {
        return creditsResponse;
    }

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
        TvShow tvShow = (TvShow) o;
        return id == tvShow.id &&
                Double.compare(tvShow.popularity, popularity) == 0 &&
                Double.compare(tvShow.voteAverage, voteAverage) == 0 &&
                Objects.equals(title, tvShow.title) &&
                Objects.equals(posterPath, tvShow.posterPath) &&
                Objects.equals(overview, tvShow.overview) &&
                Objects.equals(releaseDate, tvShow.releaseDate);
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
