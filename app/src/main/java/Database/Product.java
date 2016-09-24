package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.paya.administrator.anbar.ProductList;
import com.paya.administrator.anbar.ProductObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16/08/2016.
 */
public class Product extends SQLiteOpenHelper {
    private static final int VERSION = 3;
    private static final String name = "product";

    public Product(Context context) {
        super(context, name, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTable = "CREATE TABLE product(id INTEGER PRIMARY KEY AUTOINCREMENT, unitid Text,productid Text,name TEXT, number INTEGER, unit TEXT);";
        sqLiteDatabase.execSQL(CreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS product");
        onCreate(sqLiteDatabase);
    }

    public Long insertProduct(ProductObject product) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("unitid", product.getUnitId());
        values.put("productid",product.getProductId());
        values.put("name", product.getName());
        values.put("number", product.getNumber());
        values.put("unit", product.getUnit());

        Long id = db.insert("product", null, values);
        return id;
    }

    public  List<ProductList> getProduct() {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        List<ProductList> list = new ArrayList<>();
        list.clear();
        try {

            String[] Columns = {"id","unitid","productid","name", "number", "unit"};
            db = this.getReadableDatabase();
            cursor = db.query("product", Columns, null, null, null, null, null);
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    ProductList productList = new ProductList();

                    productList.setName(cursor.getString(cursor.getColumnIndex("name")));
                    productList.setUnitid(cursor.getString(cursor.getColumnIndex("unitid")));
                    productList.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
                    productList.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
                    productList.setProductid(cursor.getString(cursor.getColumnIndex("productid")));
                    productList.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    list.add(productList);
                }


                for (ProductList st : list) {
                    Log.e("here in getproduct", st.getName());
                }
                return list;
            }
            return  null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cursor.close();
            db.close();

        }

    }

    public int delete(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("product",  "id = ?", new String[] {Integer.toString(id)} );


    }
    public void deleteAll(){
    SQLiteDatabase db = this.getWritableDatabase();
db.execSQL("delete from product");


        }
        }
