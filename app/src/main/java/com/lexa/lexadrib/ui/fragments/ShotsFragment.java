package com.lexa.lexadrib.ui.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lexa.lexadrib.R;
import com.lexa.lexadrib.api.RetrofitManager;
import com.lexa.lexadrib.data.Shot;
import com.lexa.lexadrib.ui.activities.MainActivity;

import java.util.List;

public class ShotsFragment extends Fragment {

    public boolean load;
    private MainActivity activity;
    public ShotAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    public RetrofitManager retrofitManager;
    private BottomSheetBehavior mBottomSheetBehavior;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        retrofitManager = activity.retrofitManager;
        load = true;

        View view = inflater.inflate(R.layout.shots_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShotAdapter(activity.shots, activity.getBaseContext(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions = layoutManager.findLastVisibleItemPositions(null);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (load){
                    for (int i : positions) {
                        if (totalItemCount == i + visibleItemCount+1) {
                            retrofitManager.getShots(retrofitManager.sort, (totalItemCount/20)+1);
                            load = false;
                            break;
                        }
                    }
                }
            }
        });

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        ImageView sort = (ImageView) view.findViewById(R.id.sort);
        sort.setImageResource(R.drawable.ic_sort_black_36dp);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = mBottomSheetBehavior.getState();

                if (i == BottomSheetBehavior.STATE_EXPANDED){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        return view;
    }

    public void addData(List<Shot> shots) {
        adapter.shots.addAll(shots);
        adapter.notifyDataSetChanged();
        Log.e("SHOTS", String.valueOf(adapter.shots.size()));
        load = true;
    }
}