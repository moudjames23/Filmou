package com.moudjames23.filmou.networkapi;

import com.moudjames23.filmou.model.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Moudjames on 18/12/2017.
 */
public class WebService {

    private Utils app;
    private NetworkCallBack callBack;

    private final String TAG = "webservice";


    public WebService(Utils app, NetworkCallBack callBack) {
        this.app = app;
        this.callBack = callBack;
    }

    public WebService(Utils app) {
        this.app = app;
    }

    /**
     * recupère la liste des films existants depuis le serveur
     */
    public void getMovie()
    {
        if(app.isOnline()) // Check si le téléphone a une source de connectivité activée
        {
            callBack.showLoading(); // Affiche le loader

            Call<List<Film>> call = app.setupNetworkAPI().movies("");
            call.enqueue(new Callback<List<Film>>() {
                @Override
                public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                    callBack.hideLoading(); // cache le loader

                    if(response != null && response.body().size() != 0)
                        callBack.onResponse(response.body());
                    else
                        callBack.noItem(); // Aucune données existantes

                }

                @Override
                public void onFailure(Call<List<Film>> call, Throwable t) {
                    callBack.hideLoading();

                    callBack.onFailure(t.getMessage()); // Une erreur s'est produite

                }
            });

        }
        else
        {
            callBack.noInternetConnexion();
        }
    }



}
