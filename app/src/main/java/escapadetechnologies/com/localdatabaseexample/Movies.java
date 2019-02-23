package escapadetechnologies.com.localdatabaseexample;

public class Movies {


    private String id;

    private String title;

    private String overView;

    private String popularity;

    private int isAdult;

    private String posterPath;


    public Movies(String id, String title, String overView, String popularity, int isAdult, String posterPath) {
        this.id = id;
        this.title = title;
        this.overView = overView;
        this.popularity = popularity;
        this.isAdult = isAdult;
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(int isAdult) {
        this.isAdult = isAdult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
