package connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 08/11/2016.
 */
public class LoginConnection {
    private SharedPreferences cookiePreferences;
    private SharedPreferences.Editor cookiePrefsEditor;

    public String login(Context context, String username, String password) {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/UserLogin").build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("username", username);

        jsonParams.put("password", password);
      //  jsonParams.put("Content-Type", "application/x-www-form-urlencoded");
       RequestBody body = RequestBody.create(JSON, (new JSONObject(jsonParams)).toString());

      /*  RequestBody body = new RequestBody() {
            @Override
            public MediaType contentType() {
                return JSON;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };*/




        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        try {
            Response response = client.newCall(request).execute();
            Log.e("response", response.toString());

            if (response.isSuccessful()) {
                Headers heade = response.headers();
                URI uri = URI.create("http://94.232.173.172:80/Services/SlsService.svc/UserLogin");
                HttpUrl u = HttpUrl.get(uri);
                List<Cookie> cookieHeader = Cookie.parseAll(u, heade);
                HashSet<String> setCookies = new HashSet<String>();
                setCookies.add(cookieHeader.toString());
                cookiePreferences = context.getSharedPreferences("cookiePrefs", Context.MODE_PRIVATE);
                cookiePrefsEditor = cookiePreferences.edit();
                cookiePrefsEditor.putStringSet("cookie", setCookies);
                cookiePrefsEditor.commit();
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