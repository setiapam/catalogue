package idmy.murphi.moviecatalogue.data.local.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCastResponse {

    @SerializedName("cast")
    private List<MovieCast> movieCast;

    public List<MovieCast> getMovieCast() {
        return movieCast;
    }

}
