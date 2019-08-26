package idmy.murphi.moviecatalogue.data.local.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<TvShow> tvShows;

    public List<TvShow> getTvShows() {
        return tvShows;
    }

}
