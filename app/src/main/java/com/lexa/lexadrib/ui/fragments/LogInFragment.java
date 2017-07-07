package com.lexa.lexadrib.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lexa.lexadrib.R;

import static com.lexa.lexadrib.constants.Constants.CLIENT_ID;


public class LogInFragment extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url = "https://dribbble.com/oauth/authorize?client_id="+CLIENT_ID +
                "&redirect_uri=lexadrib://code&scope=public";
        View view = inflater.inflate(R.layout.log_in_fragment, container, false);
        WebView web = (WebView) view.findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.loadUrl(url);
        return view;
    }

}

