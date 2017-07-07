package com.lexa.lexadrib.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    @Expose private
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

}

