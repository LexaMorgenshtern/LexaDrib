package com.lexa.lexadrib.ui.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lexa.lexadrib.ui.fragments.LogInFragment;
import com.lexa.lexadrib.R;
import com.lexa.lexadrib.api.RetrofitManager;
import com.lexa.lexadrib.data.Shot;
import com.lexa.lexadrib.ui.fragments.ShotFragment;
import com.lexa.lexadrib.ui.fragments.ShotsFragment;

import java.util.List;

public class MainActivity extends Activity {

   public static String CODE;

    private LogInFragment logInFragment;
    private ShotsFragment shotsFragment;
    private ShotFragment shotFragment;
    public FragmentTransaction transaction;
    public RetrofitManager retrofitManager;
    public Shot shot;
    public List<Shot> shots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri data = getIntent().getData();
        if (data == null) {
            logInFragment = new LogInFragment();
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.frame, logInFragment);
            transaction.commit();
        } else {
            CODE = data.toString().replace("lexadrib://code?code=", "");
            Log.e("CODE", CODE);
            retrofitManager = new RetrofitManager(this);
            retrofitManager.getAccessToken(CODE);
        }
    }

    public void onClick(View view) {
        TextView i = (TextView) view;
        switch (i.getText().toString()){
            case "Popular" : {
                retrofitManager.getShots(null, 1);
                break;
            } case "Most viewed" : {
                retrofitManager.getShots("views", 1);
                break;
            } case "Most commented" : {
                retrofitManager.getShots("comments", 1);
                break;
            } case "Recently added" : {
                retrofitManager.getShots("recent", 1);
                break;
            }
        }
    }

    public void viewShots(int page, List<Shot> shots) {
        if (page == 0 || shots == null){
            shotsFragment.load = true;
            return;
        }
        if (page == 1) {
            Log.e("SHOTS", String.valueOf(shots.size()));
            if (retrofitManager.sort != null) {
                Log.e("SORT", retrofitManager.sort);
            } else {
                Log.e("SORT", "popular");
            }
            this.shots = shots;
            shotsFragment = new ShotsFragment();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, shotsFragment);
            transaction.commit();
        } else {
            shotsFragment.addData(shots);
        }
    }

    public void viewShot(Shot shot) {
        this.shot = shot;
        shotFragment = new ShotFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, shotFragment);
        transaction.commit();
    }
}
