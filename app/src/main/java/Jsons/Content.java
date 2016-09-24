package Jsons;

import android.util.Log;

import com.paya.administrator.anbar.FormObject;
import com.paya.administrator.anbar.InvObject;
import com.paya.administrator.anbar.PersonObject;
import com.paya.administrator.anbar.ProductObject;
import com.paya.administrator.anbar.Tafzili;
import com.paya.administrator.anbar.UnitObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15/08/2016.
 */
public class Content {


    public static List<PersonObject> people(String response) {

        JSONArray data;
        List<PersonObject> lableList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(response);
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {
                PersonObject personObject = new PersonObject();
                JSONObject object = data.getJSONObject(i);
                personObject.setName(object.optString("label"));
                personObject.setId(object.optString("id"));
                lableList.add(personObject);
            }
            return lableList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Tafzili> tafzili(String response) {

        JSONArray data;
        List<Tafzili> lableList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(response);
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {
                Tafzili tafzili = new Tafzili();

                JSONObject object = data.getJSONObject(i);
                tafzili.setLable( object.optString("label"));
                tafzili.setId(object.optString("id"));

                lableList.add(tafzili);
            }
            return lableList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<UnitObject> getUnit(String response) {

        JSONArray data;
        List<UnitObject> lableList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(response);
            UnitObject unitObject = new UnitObject();
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {

                JSONObject object = data.getJSONObject(i);
                unitObject.setUnit(object.optString("value"));
                unitObject.setId(object.optString("id"));

                lableList.add(unitObject);
            }
            return lableList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<FormObject> getFormType(String Response) {
        JSONArray data;
        List<FormObject> formlist = new ArrayList<>();

        JSONObject obj = null;
        try {

            obj = new JSONObject(Response);
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {

                FormObject form = new FormObject();
                JSONObject object = data.getJSONObject(i);
                form.setFormid(object.optInt("IdForms"));
                Log.e("json form ", object.optString("DscForm"));
                form.setFormname(object.optString("DscForm"));

                formlist.add(form);

            }
            return formlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static List<Integer> getformIdcls(String res) {

        JSONArray data;
        List<Integer> listid = new ArrayList<>();

       JSONObject obj = null;


        try {
            obj = new JSONObject(res);
           data = obj.getJSONArray("d");
            if (obj.length() != 0) {
                for (int i = 0; i < data.length(); i++) {

                int id = data.optInt(i);
                    listid.add(id);


                }
                return listid;
            }
            else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<Tafzili> detailtafzili(String res) {


        JSONArray data;
        List<Tafzili> tafzililist = new ArrayList<>();

        JSONObject obj = null;


        try {
            obj = new JSONObject(res);
            data = obj.getJSONArray("d");

                for (int i = 0; i < data.length(); i++) {
                    Tafzili tafzili = new Tafzili();
                    JSONObject object = data.getJSONObject(i);
                    tafzili.setLable(object.optString("label"));
                    tafzili.setId(object.optString("id"));
                    tafzililist.add(tafzili);


                }
            return tafzililist;
            } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        }

    public static List<InvObject> getInv(String Response) {
        JSONArray data;
        List<InvObject> formlist = new ArrayList<>();

        JSONObject obj = null;
        try {

            obj = new JSONObject(Response);
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {

               InvObject form = new InvObject();
                JSONObject object = data.getJSONObject(i);
                form.setId(object.optString("id"));
                Log.e("json form ", object.optString("id"));
                form.setName(object.optString("label"));

                formlist.add(form);

            }
            return formlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ProductObject> getProduct(String Response) {
        JSONArray data;
        List<ProductObject> formlist = new ArrayList<>();

        JSONObject obj = null;
        try {

            obj = new JSONObject(Response);
            data = obj.getJSONArray("d");
            for (int i = 0; i < data.length(); i++) {

                ProductObject productObject = new ProductObject();
                JSONObject object = data.getJSONObject(i);
                productObject.setProductId(object.optString("id"));
                Log.e("json form ", object.optString("id"));
                productObject.setName(object.optString("label"));

                formlist.add(productObject);

            }
            return formlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTafziliName(String response) {

        JSONObject data;
        List<Integer> listid = new ArrayList<>();

        JSONObject obj = null;


        try {
            obj = new JSONObject(response);
            data = obj.getJSONObject("d");

            String name = data.optString("DscClass");

            return name;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    }