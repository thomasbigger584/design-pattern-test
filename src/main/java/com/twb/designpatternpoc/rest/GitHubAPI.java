package com.twb.designpatternpoc.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubAPI {
    @GET("iluwatar/java-design-patterns/archive/refs/heads/master.zip")
    Call<ResponseBody> downloadLatestZip();
}
