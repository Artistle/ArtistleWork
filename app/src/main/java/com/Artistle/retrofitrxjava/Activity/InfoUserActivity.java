package com.Artistle.retrofitrxjava.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.Artistle.retrofitrxjava.R;
import com.Artistle.retrofitrxjava.adapter.InfoAdapter;
import com.Artistle.retrofitrxjava.model.InfoModel;
import com.Artistle.retrofitrxjava.network.InfoRetrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoUserActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private ArrayList<InfoModel> infoModelsList;
    private RecyclerView rv_info;
    private CompositeDisposable compositeDisposable_info;
    private InfoAdapter infoAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_user_activity);

        compositeDisposable_info = new CompositeDisposable();
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {

        rv_info = (RecyclerView) findViewById(R.id.info_recycler_view);
        rv_info.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_info.setLayoutManager(layoutManager);
    }

    private void loadJSON() {

        InfoRetrofit infoRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(InfoRetrofit.class);

        compositeDisposable_info.add(infoRetrofit.getInfo(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<InfoModel> androidList) {
        infoModelsList = new ArrayList<InfoModel>();
        infoAdapter = new InfoAdapter(infoModelsList);
        rv_info.setAdapter(infoAdapter);
        infoModelsList.addAll(androidList);
    }

    private void handleError(Throwable error) {

        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable_info.clear();
    }
}

