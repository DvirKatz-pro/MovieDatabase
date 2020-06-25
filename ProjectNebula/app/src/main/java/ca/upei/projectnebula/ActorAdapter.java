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

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
    private String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";
    private JSONArray mData;
    private LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    // data is passed into the constructor
    ActorAdapter(Context context, JSONArray data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_actor, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = null;
        String character = null;
        String picture_path = null;
        try {
            name = getItem(position).getString("name");
            character = getItem(position).getString("character");
            picture_path = getItem(position).getString("profile_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(picture_path != null) {
            Picasso.get().load(MOVIE_BASE_URL + picture_path).into(holder.actor_image);
        }
        holder.nameView.setText(name);
        holder.characterView.setText(character);
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
        TextView nameView;
        TextView characterView;
        ImageView actor_image;

        ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.actor_name);
            characterView = itemView.findViewById(R.id.character);
            actor_image = itemView.findViewById(R.id.actor_image);
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
