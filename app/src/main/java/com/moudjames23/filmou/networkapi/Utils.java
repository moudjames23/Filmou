package com.moudjames23.filmou.networkapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.widget.TextView;


import com.moudjames23.filmou.app.Constant;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Moudjames on 18/12/2017.
 */
public class Utils {

    private Context context;
    private NetworkAPI networkAPI;

    public Context getContext() {
        return context;
    }

    private int READ_TIMEOUT = 10;
    private int CONNECT_TIMEOUT = 20;

    public Utils(Context context)
    {
        this.context = context;
        //setupNetworkAPI();
    }


    /**
     * Verifie s'il y a une source de connexion
     * @return un boolean si ou
     */
    public boolean isOnline()
    {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return  info != null && info.isConnectedOrConnecting();
    }

    /**
     * initialise retrofit
     * @return une instance de NetworkAPI
     */
    public NetworkAPI setupNetworkAPI()
    {
        if(networkAPI == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();


            networkAPI = new Retrofit.Builder()
                    .baseUrl(Constant.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(NetworkAPI.class);

        }

        return networkAPI;
    }

    public static String getYoutubeVideoId(String youtubeUrl)
    {
        String video_id="";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http"))
        {

            String expression = "^.*((youtu.be"+ "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(youtubeUrl);
            if (matcher.matches())
            {
                String groupIndex1 = matcher.group(7);
                if(groupIndex1!=null && groupIndex1.length()==11)
                    video_id = groupIndex1;
            }
        }
        return video_id;
    }

}
