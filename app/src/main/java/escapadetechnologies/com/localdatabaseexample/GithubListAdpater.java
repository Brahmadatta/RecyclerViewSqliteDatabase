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

public class GithubListAdpater extends RecyclerView.Adapter<GithubListAdpater.GithubViewHolder>{

    ArrayList<HashMap<String,String>> githubArrayList;
    private Context context;

    public GithubListAdpater(ArrayList<HashMap<String, String>> githubArrayList, Context context) {
        this.githubArrayList = githubArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.github_card_layout,viewGroup,false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder githubViewHolder, int i) {

        HashMap<String,String> hashMap = githubArrayList.get(i);

        githubViewHolder.id.setText(hashMap.get("id"));
        githubViewHolder.name.setText(hashMap.get("name"));
        githubViewHolder.fullname.setText(hashMap.get("full_name"));
        githubViewHolder.type.setText(hashMap.get("type"));

        Picasso.get().load(hashMap.get("avatar_url")).into(githubViewHolder.avatarUrl);

    }

    @Override
    public int getItemCount() {
        return githubArrayList.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        ImageView avatarUrl;
        TextView id,name,fullname,type;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            fullname = itemView.findViewById(R.id.fullName);
            type = itemView.findViewById(R.id.type);
            avatarUrl = itemView.findViewById(R.id.avatar_url);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(),GitHubDatabaseActivity.class));
                }
            });

        }
    }
}
