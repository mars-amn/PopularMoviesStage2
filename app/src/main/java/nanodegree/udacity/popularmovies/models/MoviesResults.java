package nanodegree.udacity.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MoviesResults implements Parcelable {

    public final static Parcelable.Creator<MoviesResults> CREATOR = new Creator<MoviesResults>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MoviesResults createFromParcel(Parcel in) {
            return new MoviesResults(in);
        }

        public MoviesResults[] newArray(int size) {
            return (new MoviesResults[size]);
        }

    };
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MoviesResponse> movieDetails = new ArrayList<>();

    protected MoviesResults(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.movieDetails, MoviesResponse.class.getClassLoader());
    }

    public MoviesResults() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MoviesResponse> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MoviesResponse> movieDetails) {
        this.movieDetails = movieDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(movieDetails);
    }

    public int describeContents() {
        return 0;
    }

}
