package com.lexa.lexadrib.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lexa.lexadrib.R;
import com.lexa.lexadrib.data.Shot;
import com.lexa.lexadrib.ui.activities.MainActivity;
import com.squareup.picasso.Picasso;

public class ShotFragment extends Fragment {

    private MainActivity activity;

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.one_shot_card, container, false);

        ImageView picture = (ImageView)  view.findViewById(R.id.image);
        TextView title = (TextView)  view.findViewById(R.id.title);
        TextView name = (TextView)  view.findViewById(R.id.name);
        TextView date = (TextView)  view.findViewById(R.id.date);

        TextView views = (TextView)  view.findViewById(R.id.views_count);
        TextView likes = (TextView)  view.findViewById(R.id.like_count);
        TextView comments = (TextView)  view.findViewById(R.id.comm_count);
        WebView description = (WebView) view.findViewById(R.id.description);

        final Shot s = activity.shot;
        assert s != null;

        String[] dateSymbols = s.getCreatedAt().split("");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            builder.append(dateSymbols[i]);
        }
        Picasso.with(activity.getBaseContext()).load(s.getImages().getHidpi()).into(picture);
        title.setText(s.getTitle());
        name.setText(s.getUser().getName());
        date.setText(builder.toString());

        views.setText("" + s.getViewsCount());
        likes.setText("" + s.getLikesCount());
        comments.setText("" + s.getCommentsCount());

        description.getSettings().setJavaScriptEnabled(true);
        description.getSettings().setDomStorageEnabled(true);
        description.loadDataWithBaseURL(null, s.getDescription(), "text/html", "en_US", null);

        return view;
    }
}
