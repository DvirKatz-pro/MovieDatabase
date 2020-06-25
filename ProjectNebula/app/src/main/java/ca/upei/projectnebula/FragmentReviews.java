package ca.upei.projectnebula;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class FragmentReviews extends Fragment implements ReviewAdapter.ItemClickListener{
    View view;
    JSONArray reviews;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reviews_fragment,container, false);
        Bundle bundle = getArguments();
        try {
            reviews = new JSONArray(bundle.getString("reviews"));
            layoutManager = new LinearLayoutManager( getActivity(), RecyclerView.VERTICAL, false);
            recyclerView = view.findViewById(R.id.reviewsView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ReviewAdapter(getActivity(), reviews);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onItemClick(View view, int position) {
        try {
            String url = reviews.getJSONObject(position).getString("url");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
