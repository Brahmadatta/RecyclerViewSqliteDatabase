package escapadetechnologies.com.localdatabaseexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
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
    public void onBindViewHolder(@NonNull final GithubViewHolder githubViewHolder, int i) {

        final HashMap<String,String> hashMap = githubArrayList.get(i);

        githubViewHolder.id.setText(hashMap.get("id"));
        githubViewHolder.name.setText(hashMap.get("name"));
        githubViewHolder.fullname.setText(hashMap.get("full_name"));
        githubViewHolder.type.setText(hashMap.get("type"));
        githubViewHolder.repos_url.setText(hashMap.get("repos_url"));


        /*Picasso.get()
                .load(hashMap.get("avatar_url"))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(githubViewHolder.avatarUrl, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get()
                                .load(hashMap.get("avatar_url"))
                                .error(R.drawable.ic_launcher_background)
                        .into(githubViewHolder.avatarUrl);
                    }

                    @Override
                    public void onError(Exception e) {

                        //Try again online if cache failed
                        Picasso.get()
                                .load(hashMap.get("avatar_url"))
                                .error(R.drawable.ic_launcher_background)
                                .into(githubViewHolder.avatarUrl);

                    }
                });*/


       /* if (!checkInternetConnection(context)){
            String avatar = hashMap.get("avatar_url");

            try {
                URL url = new URL(avatar);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                githubViewHolder.avatarUrl.setImageBitmap(myBitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else {
            String avatar = hashMap.get("avatar_url");
            Picasso.get().load(hashMap.get("avatar_url")).into(githubViewHolder.avatarUrl);
        }*/

    }

    @Override
    public int getItemCount() {
        return githubArrayList.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        //ImageView avatarUrl;
        TextView id,name,fullname,type,repos_url;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            fullname = itemView.findViewById(R.id.fullName);
            type = itemView.findViewById(R.id.type);
            repos_url = itemView.findViewById(R.id.repos_url);
            //avatarUrl = itemView.findViewById(R.id.avatar_url);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*v.getContext().startActivity(new Intent(v.getContext(),GitHubDatabaseActivity.class));*/
                    /*Intent intent = new Intent(v.getContext(),GitHubDatabaseActivity.class);
                    intent.putExtra("id",id.getText().toString());
                    v.getContext().startActivity(intent);*/

                    Intent intent = new Intent(v.getContext(),GithubReposUrlActivity.class);
                    intent.putExtra("repos_url",repos_url.getText().toString());
                    intent.putExtra("id",id.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    private static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
