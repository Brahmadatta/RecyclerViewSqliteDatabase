package escapadetechnologies.com.localdatabaseexample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    ArrayList<HashMap<String,String>> moviesArrayList;

    private Context context;

    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public MoviesAdapter(ArrayList<HashMap<String, String>> moviesArrayList, Context context) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card_view,viewGroup,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder movieListViewHolder, int i) {

        HashMap<String,String> hashMap = moviesArrayList.get(i);

        movieListViewHolder.overview.setText(hashMap.get("overview"));
        movieListViewHolder.popularity.setText(hashMap.get("popularity"));
        movieListViewHolder.title.setText(hashMap.get("title"));
        movieListViewHolder.id.setText(hashMap.get("id"));
        Picasso.get().load(IMAGE_URL_BASE_PATH + hashMap.get("poster_path")).into(movieListViewHolder.posterPath);

    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }



    public class MoviesViewHolder extends RecyclerView.ViewHolder{

        ImageView posterPath;
        TextView title,popularity,overview,id;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            posterPath = itemView.findViewById(R.id.poster_path);
            title = itemView.findViewById(R.id.title);
            popularity = itemView.findViewById(R.id.popularity);
            overview = itemView.findViewById(R.id.over_view);
            id = itemView.findViewById(R.id.id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(),SqliteDataActivity.class));
                }
            });
        }
    }
}
