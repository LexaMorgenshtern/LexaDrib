package com.lexa.lexadrib.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("hidpi")
    @Expose
    private String hidpi;
    @SerializedName("normal")
    @Expose
    private String normal;

    public String getHidpi() {
        return hidpi;
    }

    public String getNormal() {
        return normal;
    }

}