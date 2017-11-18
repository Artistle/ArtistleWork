package com.Artistle.retrofitrxjava.network;

import com.Artistle.retrofitrxjava.model.UserModel;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserRetrofit {

    @GET("users")
    Observable<List<UserModel>> register();
}
