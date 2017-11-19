package com.Artistle.retrofitrxjava;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.Artistle.retrofitrxjava.Data.DBHelper;
import com.Artistle.retrofitrxjava.adapter.UserAdapter;
import com.Artistle.retrofitrxjava.model.UserModel;
import com.Artistle.retrofitrxjava.network.UserRetrofit;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.Artistle.retrofitrxjava.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable;
    private UserAdapter adapter;
    private ArrayList<UserModel> InfoListModels;
    private DBHelper db;
    private UserModel userModel;
    private SQLiteDatabase database;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoListModels = new ArrayList<UserModel>();

        compositeDisposable = new CompositeDisposable();

        db = new DBHelper(this);
        database = db.getWritableDatabase();

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {


        UserRetrofit userRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UserRetrofit.class);

        compositeDisposable.add(userRetrofit.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<UserModel> userList) {

        InfoListModels = new ArrayList<UserModel>();
        adapter = new UserAdapter(InfoListModels);
        contentValues = new ContentValues();

        recyclerView.setAdapter(adapter);
        InfoListModels.addAll(userList);

        Collections.sort(InfoListModels, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel o1, UserModel o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        contentValues.put(DBHelper.KEY_NAME, userModel.getName());
        contentValues.put(DBHelper.KEY_COMPANY, userModel.getCompany().getName_company());
        contentValues.put(DBHelper.KEY_MAIL, userModel.getEmail());
    }

    private void handleError(Throwable error) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
