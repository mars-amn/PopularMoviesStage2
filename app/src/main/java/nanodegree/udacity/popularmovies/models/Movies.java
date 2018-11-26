package nanodegree.udacity.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


// POJO class.
public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    private String title;
    private String posterPath;
    private double averageVote;
    private String overView;
    private String releaseDate;
    private int id;
    private List<String> trailerKey = new ArrayList<>();
    private List<String> reviewAuthor = new ArrayList<>();
    private List<String> contentReview = new ArrayList<>();

    public Movies() {
    }


    protected Movies(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        averageVote = in.readDouble();
        overView = in.readString();
        releaseDate = in.readString();
        id = in.readInt();

    }

    public List<String> getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(List<String> reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public List<String> getContentReview() {
        return contentReview;
    }

    public void setContentReview(List<String> contentReview) {
        this.contentReview = contentReview;
    }

    public List<String> getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(List<String> trailerKey) {
        this.trailerKey = trailerKey;
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

    public double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(double averageVote) {
        this.averageVote = averageVote;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeDouble(averageVote);
        parcel.writeString(overView);
        parcel.writeString(releaseDate);
        parcel.writeInt(id);


    }
}
