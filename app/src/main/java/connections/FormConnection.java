package connections;

/**
 * Created by Administrator on 17/08/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 17/08/2016.
 */
public class FormConnection {

    private SharedPreferences cookiePreferences;

    public String getform(Context context){

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/GetSpecForms").build();
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("formsType", "inv");

        jsonParams.put("Content-Type", "application/json");
        RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());
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

        Request request = new Request.Builder().addHeader("Cookie",lo.substring(1) ).post(body).url(url).build();
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
    public String gettafzili(Context context,int formid){

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/GetFormAccIdCls").build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<String,Object> jsonParams = new HashMap<>();
        jsonParams.put("idFrm", formid);

        jsonParams.put("Content-Type", "application/json");

        RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());
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

        Request request = new Request.Builder().addHeader("Cookie",lo.substring(1) ).post(body).url(url).build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        try {
            Response response = client.newCall(request).execute();
            // Log.e("response", response.body().string());

            if (response.isSuccessful()) {

                String res = response.body().string();
                //    storeCookie();
                Log.e("response in accessid ", res);
                return res;
            } else {

                return null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    public String gettafzililist(Context context, String classid, String term){

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/GetSubAccs").build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("idCls", classid);
        jsonParams.put("term",term);


        jsonParams.put("Content-Type", "application/json");

        RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());
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

        Request request = new Request.Builder().addHeader("Cookie",lo.substring(1) ).post(body).url(url).build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        try {
            Response response = client.newCall(request).execute();
            // Log.e("response", response.body().string());

            if (response.isSuccessful()) {

                String res = response.body().string();
                //    storeCookie();
                Log.e(" ", res);
                return res;
            } else {

                return null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }


    }
    public String getTafziliName(Context context, Integer id){


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/GetCls").build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("idCls", id);



        jsonParams.put("Content-Type", "application/json");

        RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());
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

        Request request = new Request.Builder().addHeader("Cookie",lo.substring(1) ).post(body).url(url).build();
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

