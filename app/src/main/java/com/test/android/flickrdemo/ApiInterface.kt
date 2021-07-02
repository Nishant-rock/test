package com.test.android.flickrdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun searchText(
        @Query("api_key") api_key: String,
        @Query("text") text_Search: String
    ): Call<SearchDTO>
}