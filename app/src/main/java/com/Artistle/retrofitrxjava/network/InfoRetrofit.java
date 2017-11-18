package com.Artistle.retrofitrxjava.network;

import com.Artistle.retrofitrxjava.model.InfoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InfoRetrofit {

    @GET("posts")
    Observable<List<InfoModel>> getInfo(@Query("userId") int id);
}
