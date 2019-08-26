package idmy.murphi.moviecatalogue.data.local.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class TvShowDetails {

    @Embedded
    public TvShow tvShow = null;

    @Relation(parentColumn = "id", entityColumn = "tv_show_id")
    public List<TvShowCast> castList = new ArrayList<>();
}
