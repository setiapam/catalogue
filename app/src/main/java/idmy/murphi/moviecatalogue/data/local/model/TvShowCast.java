package idmy.murphi.moviecatalogue.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "tv_show_cast",
        foreignKeys = @ForeignKey(
                entity = TvShow.class,
                parentColumns = "id",
                childColumns = "tv_show_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ),
        indices = {
                @Index(value = {"tv_show_id"})
        }
)
public class TvShowCast {

    @NonNull
    @PrimaryKey
    @SerializedName("credit_id")
    private String id;

    @ColumnInfo(name = "tv_show_id")
    private long tvShowId;

    @SerializedName("character")
    private String characterName;

    @SerializedName("gender")
    private int gender;

    @SerializedName("name")
    private String actorName;

    @SerializedName("order")
    private int order;

    @SerializedName("profile_path")
    private String profileImagePath;

    public long getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(long tvShowId) {
        this.tvShowId = tvShowId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
