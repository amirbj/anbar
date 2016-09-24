package connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ArrayRes;
import android.text.TextUtils;
import android.util.Log;

import com.paya.administrator.anbar.ProductList;

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

import Database.Product;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 21/08/2016.
 */
public class StoreConnection {
    private SharedPreferences cookiePreferences;
    public String StoreAll(Context context,  String formid,String  Dt_Effect_M, String IdInv,String IdPrs
            , String slsHeaderNo, String IdSubAccountPerson,String IdSubAccountGood, String IdSubAccountCostCenter,
                           String IdSubAccountProject, String IdSubAccountInvent) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("94.232.173.172")
                .port(80)
                .addPathSegment("Services/SlsService.svc/InsertInvIncome").build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        Map<Object, Object> jsonParams = new HashMap<>();
        jsonParams.put("IdForm", formid);
        jsonParams.put("Dsc", "تست ذخیره وارده برگشت از فروش");
        jsonParams.put("Dt_Effect_M", Dt_Effect_M);
       // jsonParams.put("Dsc","null");

        jsonParams.put("IdInv",IdInv);
        jsonParams.put("IdPrs",IdPrs);
        jsonParams.put("idSndInv ", "null");
        jsonParams.put("slsHeaderNo","00100009");
        jsonParams.put("IdSubAccountPerson", IdSubAccountPerson);
        jsonParams.put("IdSubAccountGood",IdSubAccountGood);
        jsonParams.put("IdSubAccountCostCenter", IdSubAccountCostCenter);
        jsonParams.put("IdSubAccountProject", IdSubAccountProject);
        jsonParams.put("IdSubAccountInvent", IdSubAccountInvent);
        Product product = new Product(context);
        List<ProductList> list1 = new ArrayList<>();
        list1=product.getProduct();
        List<String> detail = new ArrayList<>();
        detail.add(0,"Amount");
        detail.add(1,"ConfirmedAmount");
        detail.add(2,"DscDetail");
        detail.add(3,"IdGds");
        detail.add(4,"IdUnitEntered");
        for(ProductList pro:list1) {
List<String> detailvalue = new ArrayList<>();
            detailvalue.add(0,String.valueOf(pro.getNumber()));
            detailvalue.add(1,String.valueOf(pro.getNumber()));
            detailvalue.add(2,"آرتیکل وارده برگشت از فروش");
            detailvalue.add(3,pro.getProductid());
            detailvalue.add(4,pro.getUnitid());

          //  jsonParams.put(detail,detailvalue);
            jsonParams.put("Amount", pro.getNumber());
            Log.e("in store", pro.getProductid() + "" + pro.getUnitid() + "" + pro.getNumber());
            jsonParams.put("ConfirmedAmount", pro.getNumber());
            jsonParams.put("DscDetail", "آرتیکل وارده برگشت از فروش");
            jsonParams.put("IdGds",pro.getName());
            jsonParams.put("IdUnitEntered",pro.getUnitid());
        }
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

        Request request = new Request.Builder().addHeader("Cookie", lo.substring(1)).post(body).url(url).build();
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