package com.moudjames23.filmou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.moudjames23.filmou.R;
import com.moudjames23.filmou.activity.DetailsActivity;
import com.moudjames23.filmou.adapter.AdapterFilm;
import com.moudjames23.filmou.model.Film;
import com.moudjames23.filmou.networkapi.NetworkCallBack;
import com.moudjames23.filmou.networkapi.Utils;
import com.moudjames23.filmou.networkapi.WebService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterFilm.OnFilmClickLister, NetworkCallBack<Film> {


    @Bind(R.id.rv_film)
    RecyclerView rvFilm;
    
    @Bind(R.id.loader)
    AVLoadingIndicatorView loader;

    private final String TAG = "TAG_FILM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        


        initData();


    }


    /**
     * Initialise la recupération des donnnées
     */
    private void initData()
    {
        /*List<Film> data = new LinkedList<>();
        data.add(new Film("Avatar"));
        data.add(new Film("Titanic"));
        data.add(new Film("Avenger"));
        data.add(new Film("Batman"));
        data.add(new Film("Spider"));

        AdapterFilm adapterFilm = new AdapterFilm(data);
        adapterFilm.setOnFilmClickLister(this);

        rvFilm.setHasFixedSize(true);
        //rvFilm.setLayoutManager(new LinearLayoutManager(this));

        rvFilm.setLayoutManager(new GridLayoutManager(this, 3));

        rvFilm.setAdapter(adapterFilm);*/

        Utils utils = new Utils(this);
        WebService webService = new WebService(utils, this);
        webService.getMovie();
    }


    @Override
    public void onFilmClick(Film film) {

        Intent next  = new Intent(this, DetailsActivity.class);
        next.putExtra("DATA", film);

        startActivity(next);
    }


    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading: ");
        loader.show();
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading: ");
        loader.hide();
    }

    @Override
    public void noInternetConnexion() {
        Log.d(TAG, "noInternetConnexion: ");
        Toast.makeText(MainActivity.this, "Oops ! Aucune connexion internet", Toast.LENGTH_SHORT).show();
    }
    

    @Override
    public void noItem() {
        Log.d(TAG, "noItem: ");
        Toast.makeText(MainActivity.this, "Aucune données à afficher", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.d(TAG, "onFailure: ");
        Toast.makeText(MainActivity.this, "Oops ! Une erreur s'est produite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<Film> data) {
        Log.d(TAG, "onResponse: " +data.size());

        AdapterFilm adapterFilm = new AdapterFilm(data);
        adapterFilm.setOnFilmClickLister(this);

        rvFilm.setHasFixedSize(true);
        //rvFilm.setLayoutManager(new LinearLayoutManager(this));

        rvFilm.setLayoutManager(new GridLayoutManager(this, 3)); // Affiche les éléments en 3

        rvFilm.setAdapter(adapterFilm);
    }
}
