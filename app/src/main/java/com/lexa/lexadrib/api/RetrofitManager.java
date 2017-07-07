package com.lexa.lexadrib.api;

import android.util.Log;

import com.lexa.lexadrib.data.Shot;
import com.lexa.lexadrib.data.Token;
import com.lexa.lexadrib.ui.activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lexa.lexadrib.constants.Constants.CLIENT_ID;
import static com.lexa.lexadrib.constants.Constants.CLIENT_SECRET;
import static com.lexa.lexadrib.constants.Constants.OAUTH_URL;
import static com.lexa.lexadrib.constants.Constants.REQUESTS_URL;

public class RetrofitManager {

    private Token token;
    private static String ACCESS_TOKEN;
    private MainActivity activity;
    public String sort;

    public RetrofitManager(MainActivity activity) {
        this.activity = activity;
    }

    public void getAccessToken(String code) {

            final APIService apiService = getApiService(OAUTH_URL);

            final Call<Token> tokenCall = apiService.getToken(CLIENT_ID, CLIENT_SECRET, code);

            tokenCall.enqueue(new Callback<Token>() {
                @Override public void onResponse(Call<Token> call, Response<Token> response) {
                    Log.e("REQUEST_CODE", String.valueOf(response.code()));
                    try {
                        Log.e("ANSWER_ERROR", response.errorBody().string());
                    } catch (Exception e) {
                        token = response.body();
                        ACCESS_TOKEN = token.getAccessToken();
                        Log.e("TOKEN", ACCESS_TOKEN);
                        getShots(null, 1);
                    }
                }

                @Override public void onFailure(Call<Token> call, Throwable t) {
                    Log.e("ERROR", t.getMessage());
                }
            });
    }

    public void getShots(String sort, final int page) {
        Log.e("PAGE", String.valueOf(page));

        this.sort = sort;
        final APIService apiService = getApiService(REQUESTS_URL);

        Call<List<Shot>> shotsCall =
                sort == null ? apiService.getShots(ACCESS_TOKEN, String.valueOf(page), "20") :
                        apiService.getSortedShots(ACCESS_TOKEN, String.valueOf(page), "20", sort);

        shotsCall.enqueue(new Callback<List<Shot>>() {
            @Override public void onResponse(Call<List<Shot>>call, Response<List<Shot>>response) {
                int code = response.code();
                Log.e("REQUEST_CODE", String.valueOf(code));
                if (code == 200) {
                    activity.viewShots(page, response.body());
                } else
                try {
                    Log.e("ANSWER_ERROR", response.errorBody().string());
                } catch (Exception ignored) {
                }
            }

            @Override public void onFailure(Call<List<Shot>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                activity.viewShots(0, null);
            }
        });
    }

    public void getShot(Integer id) {
        Log.e("SHOT", String.valueOf(id));

        final APIService apiService = getApiService(REQUESTS_URL);

        Call<Shot> oneShot = apiService.getShot(id, ACCESS_TOKEN);

        oneShot.enqueue(new Callback<Shot>() {
            @Override public void onResponse(Call<Shot>call, Response<Shot>response) {
                int code = response.code();
                Log.e("REQUEST_CODE", String.valueOf(code));
                if (code == 200) {
                    activity.viewShot(response.body());
                } else
                try {
                    Log.e("ANSWER_ERROR", response.errorBody().string());
                } catch (Exception ignored) {
                }
            }

            @Override public void onFailure(Call<Shot> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private APIService getApiService(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
    }
}
