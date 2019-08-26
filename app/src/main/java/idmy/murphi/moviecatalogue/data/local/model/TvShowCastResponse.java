package idmy.murphi.moviecatalogue.data.local.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowCastResponse {

    @SerializedName("cast")
    private List<TvShowCast> tvShowCasts;

    public List<TvShowCast> getTvShowCast() {
        return tvShowCasts;
    }

}
