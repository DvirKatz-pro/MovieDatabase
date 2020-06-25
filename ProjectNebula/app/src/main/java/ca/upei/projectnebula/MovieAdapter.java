package ca.upei.projectnebula;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";
    private JSONArray mData;
    private LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    // data is passed into the constructor
    MovieAdapter(Context context, JSONArray data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_movie, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = null;
        String posterPath = null;
        try {
            title = getItem(position).getString("title");
            posterPath = getItem(position).getString("poster_path");
            Picasso.get().load(MOVIE_BASE_URL + posterPath).into(holder.posterView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.movieTitleView.setText(title);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.length();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitleView;
        ImageView posterView;

        ViewHolder(View itemView) {
            super(itemView);
            movieTitleView = itemView.findViewById(R.id.movie_title);
            posterView = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    JSONObject getItem(int id) throws JSONException {
        return mData.getJSONObject(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
