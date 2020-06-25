package ca.upei.projectnebula;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StreamingAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> services;
    public ItemClickListener mClickListener;
    private int pos;

    public StreamingAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0 , list);
        mContext = context;
        services = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        pos = position;
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listitem_streaming,parent,false);

        String service = services.get(position);

        ImageView image = listItem.findViewById(        R.id.streamingIcon);

        switch (service) {
            case "Netflix":
                image.setImageResource(R.mipmap.netflix_icon);
                break;
            case "Amazon Prime":
                image.setImageResource(R.mipmap.amazon_icon);
                break;
            case "GooglePlay":
                image.setImageResource(R.mipmap.googleplay_icon);
                break;
        }
        return listItem;
    }

    // allows clicks events to be caught
    void setClickListener(StreamingAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View v, int pos);
    }


}
