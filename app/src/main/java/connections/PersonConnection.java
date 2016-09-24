package connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 14/08/2016.
 */
public class PersonConnection {
    private SharedPreferences cookiePreferences;

    public String person(Context context, String term){

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Service.svc/GetPeopleWithCustomerAndUser")
                .addQueryParameter("tag", "null")
                .addQueryParameter("term",term)
                .build();


        cookiePreferences = context.getSharedPreferences("cookiePrefs", Context.MODE_PRIVATE);
        cookiePreferences.edit();
        Set<String> setCookies = cookiePreferences.getStringSet("cookie", new HashSet<String>());
        List<String> list = new ArrayList<String>(setCookies);
        List<HttpCookie> lstCookies = new ArrayList<HttpCookie>();

        for (String st : list) {


        lstCookies.addAll(HttpCookie.parse(st));
        Log.e("list string ", st.toString());
    }



        String PSCM = lstCookies.get(0).toString().substring(6);

        String lo = TextUtils.join(" ; ", lstCookies);
        Log.e("cookie", lo.toString());
        //RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());
        Request request = new Request.Builder().addHeader("Cookie",lo.substring(1) ).url(url).build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        try {
            Response response = client.newCall(request).execute();
           // Log.e("response", response.body().string());

            if (response.isSuccessful()) {

                String res = response.body().string();
                //    storeCookie();
                Log.e("response in body ", res);
                return res;
            } else {

                return null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
}
