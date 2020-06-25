package ca.upei.projectnebula;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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


public class FragmentActors extends Fragment implements ActorAdapter.ItemClickListener{
    View view;
    private final String WIKIPEDIA_BASE = "https://en.wikipedia.org/wiki/";
    private JSONArray actors;
    private RecyclerView recyclerView;
    private ActorAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public String replaceSpaces(String sentence){
        return sentence.replace(" ", "_");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.actors_fragment,container, false);
        Bundle bundle = getArguments();
        try {
            actors = new JSONArray(bundle.getString("actors"));
            layoutManager = new LinearLayoutManager( getActivity(), RecyclerView.VERTICAL, false);
            recyclerView = view.findViewById(R.id.actorsView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ActorAdapter(getActivity(), actors);
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
            String name = replaceSpaces(actors.getJSONObject(position).getString("name"));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WIKIPEDIA_BASE + name));
            startActivity(browserIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
