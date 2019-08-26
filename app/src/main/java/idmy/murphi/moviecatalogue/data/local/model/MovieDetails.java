package idmy.murphi.moviecatalogue.data.local.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails {

    @Embedded
    public Movie movie = null;

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<MovieCast> castList = new ArrayList<>();
}
