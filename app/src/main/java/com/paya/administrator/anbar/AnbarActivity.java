package com.paya.administrator.anbar;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Cal.Calendar;
import Database.Product;
import Jsons.Content;
import adapters.FilterWithSpaceAdapter;
import adapters.ProductAdapter;
import connections.FormConnection;
import connections.GoodsConnection;
import connections.PersonConnection;
import connections.StoreConnection;

/**
 * Created by Administrator on 08/13/2016.
 */
public class AnbarActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView textView;
    private ProgressBar pstock, pperson, pformtype, pgood, punit;
    private DatePicker datePicker;
    private Spinner monthspin;
    private NumberPicker day, year, months;
    private TextView datetxt, date;
    private EditText number;
    private AutoCompleteTextView person, stock, goods;
    private Button btnPerson, btnStock, btngood, btnadd, btncancle, btnset;
    static String goodData, SpinnerData;
    private Spinner unit, formtype;
    private ListView listView;
    private LinearLayout linear, parent;
    private ImageButton edtDate;
    static int selectedId;
    private static Map<String, Integer> map = new HashMap<>();
    private static Map<String, String> mapInv = new HashMap<>();
    private static Map<String, String> mapProduct = new HashMap<>();
    private static Map<String, String> mapPerson = new HashMap<>();
    private static Map<String, String> mapTafzili = new HashMap<>();
    private static Map<String, String> mapUnit = new HashMap<>();
    static ProductAdapter adapter;
    java.util.Date dt;
    static String InvId, PersonID;
    static List<String> TafzName = new ArrayList<>();
    static List<String> Tafzid = new ArrayList<>();
    private static int c = 0;

    private static List<Integer> listid = new ArrayList<>();
    static AutoCompleteTextView autoComplete;
    List<AutoCompleteTextView> aulist = new ArrayList<>();
    List<TextView> listtext = new ArrayList<>();
    private static String tafziliName;
    //List<Button> listbtn = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sarbarg);
        initialize();
        GetFormType getFormType = new GetFormType();
        getFormType.execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        date = (TextView) findViewById(R.id.date);
        datetxt = (TextView) findViewById(R.id.datetxt);


        Product product = new Product(AnbarActivity.this);
        product.deleteAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.store:

                if(mainValidate()&& proValidate()) {
                    Log.e("dt ", String.valueOf(dt.getTime()));
                    Log.e("invid", InvId);
                    Log.e("personid", PersonID);
                    Log.e("formid", String.valueOf(selectedId));
                    StoreData storeData = new StoreData();
                    storeData.execute(String.valueOf(selectedId), String.valueOf(dt.getTime()), InvId, PersonID, null, null, null,
                            null, null, null);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialize() {
        edtDate = (ImageButton) findViewById(R.id.datelogo);
        edtDate.setOnClickListener(this);
        pperson = (ProgressBar) findViewById(R.id.progressperson);
        pperson.setVisibility(View.INVISIBLE);
        pgood = (ProgressBar) findViewById(R.id.progressgood);
        pgood.setVisibility(View.INVISIBLE);
        punit = (ProgressBar) findViewById(R.id.progresspin);
        btnStock = (Button) findViewById(R.id.closestock);
        btnStock.setOnClickListener(this);

        pstock = (ProgressBar) findViewById(R.id.progressstock);
        pstock.setVisibility(View.INVISIBLE);
        punit.setVisibility(View.INVISIBLE);
        btnPerson = (Button) findViewById(R.id.closeperson);
        btnPerson.setOnClickListener(this);
        btngood = (Button) findViewById(R.id.closegood);
        btngood.setOnClickListener(this);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(this);
        person = (AutoCompleteTextView) findViewById(R.id.person);
        formtype = (Spinner) findViewById(R.id.formtype);
        formtype.setOnItemSelectedListener(this);
        person.addTextChangedListener(this);
        unit = (Spinner) findViewById(R.id.unitspin);
        pformtype = (ProgressBar) findViewById(R.id.progressformType);
        pformtype.setVisibility(View.INVISIBLE);

        number = (EditText) findViewById(R.id.number);
        stock = (AutoCompleteTextView) findViewById(R.id.stock);
        stock.addTextChangedListener(this);


        goods = (AutoCompleteTextView) findViewById(R.id.good);
        goods.addTextChangedListener(this);
        goods.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listofItems);


        // adapter.notifyDataSetChanged();
        //linear = (LinearLayout) findViewById(R.id.linear);
        parent = (LinearLayout) findViewById(R.id.parent);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable == person.getEditableText()) {
            String text = editable.toString();
            Person persontask = new Person();
            persontask.execute(text);
            for (Map.Entry<String, String> ent : mapPerson.entrySet()) {
                PersonID = ent.getKey();
            }
        }
        if (editable == stock.getEditableText()) {
            String text = editable.toString();
            GetInventory getInventory = new GetInventory();
            getInventory.execute(text);


        }

        if (editable == goods.getEditableText()) {
            String text = editable.toString();
            String Invname = stock.getText().toString();
            for (Map.Entry<String, String> entry : mapInv.entrySet()) {
                Log.e("INVentory name ", Invname);
                Log.e("Inventory id ", entry.getValue());

                if (entry.getKey().equals(Invname)) {
                    InvId = entry.getValue();
                    Goods goodsTask = new Goods();
                    Log.e("in editable ", InvId);
                    goodsTask.execute(text, InvId);
                    for (Map.Entry<String, String> pro : mapProduct.entrySet()) {
                        String proName = goods.getText().toString();
                        if (pro.getValue().equals(proName)) {
                            Log.e("unit name ", pro.getValue());
                            Log.e("unit id ", pro.getKey());
                            GetUnit getUnit = new GetUnit();
                            getUnit.execute(pro.getKey());
                        }

                    }
                }
            }
        }
        for (int i = 0; i < aulist.size(); i++) {
            if (editable == aulist.get(i)) {

                String text = editable.toString();

                Gettafizililist gettafizililist = new Gettafizililist();
                gettafizililist.execute(listid.get(c).toString(), text);



            }

        }
    }
    //  }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.closeperson:
                person.setText("");
                break;

            case R.id.closestock:
                stock.setText("");

                break;


            case R.id.closegood:
                goods.setText("");

                break;


            case R.id.btnadd:
                if (proValidate()) {
                    String goodsID = null;
                    goodData = goods.getText().toString();
                    for (Map.Entry<String, String> pro : mapProduct.entrySet()) {
                        if (pro.getValue().equals(goodData)) {
                            goodsID = pro.getKey();
                        }
                    }
                    String unitID = null;
                    SpinnerData = unit.getSelectedItem().toString();
                    for(Map.Entry<String , String> e:mapUnit.entrySet())
                    {
                        if(e.getValue().equals(SpinnerData)){
                            unitID = e.getKey();
                        }
                    }
                    ProductObject object = new ProductObject();
                    object.setProductId(goodsID);
                    object.setName(goodData);
                    object.setUnitId(unitID);
                    object.setUnit(SpinnerData);
                    object.setNumber(Integer.parseInt(number.getText().toString()));
                    Product pro = new Product(AnbarActivity.this);
                    List<ProductList> listp = new ArrayList<>();

                    GetProductTask getProductTask = new GetProductTask();
                    getProductTask.execute(object);
                }

                break;

            case R.id.datelogo:
                show();
                setDateTime();
                Date date = new Date();
                dt = Cal.JalaliCalendar.getGregorianDate(year.getValue() + "/" + months.getValue() + "/" + day.getValue());
                //Date jDate = Cal.JalaliCalendar.jalaliToGregorian(year.getValue()+months.getValue()+day.getValue())
                Log.e("date is ", dt.toString());
                Log.e("second date is ", String.valueOf(dt.getTime()));


        }
    }

    public void show() {
        final Dialog dialog = new Dialog(AnbarActivity.this);
        dialog.setTitle("تاریخ");
        dialog.setContentView(R.layout.dialog);
        day = (NumberPicker) dialog.findViewById(R.id.day);
        year = (NumberPicker) dialog.findViewById(R.id.year);
        months = (NumberPicker) dialog.findViewById(R.id.month);
        btnset = (Button) dialog.findViewById(R.id.set);
        btncancle = (Button) dialog.findViewById(R.id.cancle);
        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datetxt.setText("" + year.getValue() + "/" + months.getValue() + "/" + day.getValue());
                dialog.dismiss();
            }
        });

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void setDateTime() {
        /* List<String> month = Arrays.asList("فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور",
        //   "مهر"  ,"آبان","آذر","دی","بهمن","اسفند");

        // ArrayAdapter spinadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, month);
        //  monthspin.setAdapter(spinadapter);

*///JalaliCalendar jalali = new JalaliCalendar(year.getValue(), months.getValue(),day.getValue());
        //  GregorianCalendar gc = jalali.toGregorian();
        //  Log.e("here date", gc.toString());

        NumberPicker.OnValueChangeListener onChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int i, int newVal) {
                if (picker == months) {
                    if (newVal <= 6) {
                        day.setMaxValue(31);
                    } else {
                        day.setMaxValue(30);
                    }
                }

            }
        };


        months.setOnValueChangedListener(onChangeListener);
        Calendar cal = new Calendar();

        List<Integer> sc = Calendar.getCurrentShamsidate();


        months.setMinValue(1);
        months.setMaxValue(12);
        months.setValue(sc.get(1));
        months.setDisplayedValues(new String[]{"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
                "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"});
        months.setWrapSelectorWheel(false);

        day.setMinValue(1);
        day.setMaxValue(31);
        day.setWrapSelectorWheel(false);
        day.setValue(sc.get(2));
        year.setMinValue(1300);
        year.setMaxValue(sc.get(0));
        year.setWrapSelectorWheel(false);
        year.setValue(sc.get(0));


    }
    public boolean mainValidate(){
        boolean val = true;
        String per = person.getText().toString();
        String anbar = stock.getText().toString();
        String dt = datetxt.getText().toString();
        if(per.isEmpty()){
            person.setError("نام شخص انتخاب نشده است");
            val=false;
        }

        if(anbar.isEmpty()){
            stock.setError("انبار انتخاب نشده است");
            val=false;
        }

        if(dt.isEmpty()){
            datetxt.setError("تاریخ انتخاب نشده است");
            val=false;
        }

        return val;
    }

    public boolean proValidate() {
        boolean valid = true;
        String numOFitems = number.getText().toString();
        goodData = goods.getText().toString();

        if (numOFitems.isEmpty()) {
            number.setError("تعداد کالا وارد نشده است");
            valid = false;
        }
        if (goodData.isEmpty()) {
            goods.setError("کالا انتخاب نشده است");
            valid = false;
        }

        return valid;


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String formname = formtype.getSelectedItem().toString();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals(formname)) {
                Log.e("formname", formname);
                selectedId = entry.getValue();

            }
        }
        GettafziliId gettafziliId = new GettafziliId();
        gettafziliId.execute(selectedId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class Person extends AsyncTask<String, Void, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            btnPerson.setVisibility(View.GONE);
            pperson.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            PersonConnection con = new PersonConnection();
            String res = con.person(AnbarActivity.this, strings[0]);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {

            pperson.setVisibility(View.INVISIBLE);
            btnPerson.setVisibility(View.VISIBLE);
            List<PersonObject> personList = new ArrayList<>();
            personList = Content.people(s);
            List<String> listperson = new ArrayList<>();
            for (PersonObject per : personList) {
                listperson.add(per.getName());
                mapPerson.put(per.getId(), per.getName());
            }
            FilterWithSpaceAdapter adapter = new FilterWithSpaceAdapter(AnbarActivity.this, R.layout.listitems, R.id.Itemlist, listperson);

            person.setThreshold(1);
            person.setAdapter(adapter);

        }
    }

    public class Goods extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            btngood.setVisibility(View.GONE);
            pgood.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            GoodsConnection connection = new GoodsConnection();

            String res = connection.goods(AnbarActivity.this, strings[0], strings[1]);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            btngood.setVisibility(View.VISIBLE);
            pgood.setVisibility(View.INVISIBLE);
            List<ProductObject> product = new ArrayList<>();
            product = Content.getProduct(s);
            List<String> goodList = new ArrayList<>();
            for (ProductObject ob : product) {
                goodList.add(ob.getName());
                mapProduct.put(ob.getProductId(), ob.getName());
            }
            FilterWithSpaceAdapter adapter = new FilterWithSpaceAdapter(AnbarActivity.this, R.layout.listitems, R.id.Itemlist, goodList);
            goods.setThreshold(1);
            goods.setAdapter(adapter);
        }
    }

    public class GetProductTask extends AsyncTask<ProductObject, Void, List<ProductList>> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<ProductList> doInBackground(ProductObject... products) {
            Product db = new Product(AnbarActivity.this);
            Long id = db.insertProduct(products[0]);
            List<ProductList> list = new ArrayList<>();
            list.clear();
            if (id != -1) {
                list.addAll(db.getProduct());
                // list = db.getProduct();
                db.close();
                return list;
            } else {
                db.close();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ProductList> productLists) {
            if (productLists != null) {

                ProductAdapter adapter = new ProductAdapter(AnbarActivity.this, R.layout.goodsitem, productLists);
                setListViewHeightBasedOnChildren(listView);
                // adapter.notifyDataSetChanged();
                adapter.swapItems(productLists);

                listView.setAdapter(adapter);


            } else {
                Toast.makeText(AnbarActivity.this, "خطا در ذخیره اطلاعات رخ داده است", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + 10 + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.setMinimumHeight(params.height);
        listView.requestLayout();
    }

    public class GetFormType extends AsyncTask<Void, Void, List<FormObject>> {

        @Override
        protected void onPreExecute() {
            pformtype.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<FormObject> doInBackground(Void... voids) {
            FormConnection formConnection = new FormConnection();
            String Response = formConnection.getform(AnbarActivity.this);
            List<FormObject> list = Content.getFormType(Response);

            return list;
        }

        @Override
        protected void onPostExecute(List<FormObject> list) {


            List<String> listname = new ArrayList<>();
            for (FormObject s : list) {
                listname.add(s.getFormname());
                map.put(s.getFormname(), s.getFormid());
            }

            ArrayAdapter adapter = new ArrayAdapter(AnbarActivity.this, android.R.layout.simple_spinner_dropdown_item, listname);
            formtype.setAdapter(adapter);
            pformtype.setVisibility(View.INVISIBLE);

        }
    }

    public List<String> getSelectedID() {

        List<String> listIDS = new ArrayList<>();
        for (int i = 0; i < listid.size(); i++) {


            if (listid.get(i).intValue() == 0) {
                listIDS.add(i, null);
            } else {
                for(int v=0; v<aulist.size();v++){
                String text = aulist.get(v).getText().toString();
                Log.e("in selected id ", text);

                // mapTafzili.clear();
                for (Map.Entry<String, String> entry : mapTafzili.entrySet()) {
                    if (entry.getValue().equals(text)) {
                        String id1 = entry.getKey();

                        Log.e("in tafid list", id1);
                        listIDS.add(i, id1);
                        // Tafzid.add(id1);
                    }
                    }


                }
            }
        }
        return listIDS;
    }

    public class GettafziliId extends AsyncTask<Integer, Void, List<Integer>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<Integer> doInBackground(Integer... id) {
            FormConnection formConnection = new FormConnection();
            Log.e("in back", "" + selectedId);
            String res = formConnection.gettafzili(AnbarActivity.this, id[0]);

            listid = Content.getformIdcls(res);
            return listid;

        }


        @Override
        protected void onPostExecute(final List<Integer> idlist) {
            if (idlist != null) {
                parent.removeAllViews();

                for (int i = 0; i < idlist.size(); i++) {


                    if (idlist.get(i).intValue() != 0) {
                        final int d = i;
                        List<Tafzili> tafizli = new ArrayList<>();
                        //c = autoComplete.length;
                        autoComplete = new AutoCompleteTextView(AnbarActivity.this);
                        aulist.add(autoComplete);


                        textView = new TextView(AnbarActivity.this);
                        //listtext.add(textView);

                        Button del = new Button(AnbarActivity.this);
                        //listbtn.add(del);
                        parent.setOrientation(LinearLayout.VERTICAL);

                        LinearLayout lay = new LinearLayout(AnbarActivity.this);

                        lay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        parent.addView(lay);


                        autoComplete.setHeight(50);
                        autoComplete.setWidth(300);
                        textView.setId(i);
                        textView.setHeight(50);
                        textView.setWidth(50);


                        del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int x = 0; x < aulist.size(); x++) {
                                    aulist.get(x).setText("");
                                }
                            }
                        });
                        del.setWidth(5);
                        del.setHeight(5);
                        del.setBackgroundResource(R.drawable.ic_close_black_16dp_1x);
                        lay.setOrientation(LinearLayout.HORIZONTAL);
                        GettafziliName gettafziliName = new GettafziliName();
                        gettafziliName.execute(idlist.get(i));
                        textView.setText(tafziliName);

                        lay.addView(del);
                        lay.addView(autoComplete);
                        lay.addView(textView);

                        autoComplete.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                String text = editable.toString();
                                Log.e("idlist", idlist.get(d).toString());

                                Gettafizililist gettafizililist = new Gettafizililist();
                                gettafizililist.execute(idlist.get(d).toString(), text);
                            }
                        });


                    }

                }
            }
        }
    }


    public class Gettafizililist extends AsyncTask<String, Void, List<Tafzili>> {


        @Override
        protected List<Tafzili> doInBackground(String... items) {
            FormConnection con = new FormConnection();
            String response = con.gettafzililist(AnbarActivity.this, items[0], items[1]);
            List<Tafzili> listtafz = new ArrayList<>();
            listtafz = Content.tafzili(response);
            return listtafz;
        }


        @Override
        protected void onPostExecute(List<Tafzili> list) {
            List<String> li = new ArrayList<>();

            for (Tafzili taf : list) {
                li.add(taf.getLable());
                Log.e("test id tafzili", taf.getId());
                Log.e("test id tafzili", taf.getLable());
                mapTafzili.put(taf.getId(), taf.getLable());
            }
            final FilterWithSpaceAdapter adapter = new FilterWithSpaceAdapter(AnbarActivity.this, R.layout.listitems, R.id.Itemlist, li);
            for (int x = 0; x < aulist.size(); x++) {
                aulist.get(x).setThreshold(1);
                aulist.get(x).setAdapter(adapter);



            }
        }
    }
    public class GetInventory extends AsyncTask<String, Void, List<InvObject>> {
        @Override
        protected void onPreExecute() {
            pstock.setVisibility(View.VISIBLE);
            btnStock.setVisibility(View.GONE);

        }

        @Override
        protected List<InvObject> doInBackground(String... strings) {

            GoodsConnection con = new GoodsConnection();
            String res = con.getInventory(AnbarActivity.this, strings[0]);
            List<InvObject> invList = new ArrayList<>();
            invList = Content.getInv(res);
            return invList;
        }

        @Override
        protected void onPostExecute(List<InvObject> strings) {

            List<String> lst = new ArrayList<>();
            for (InvObject ob : strings) {
                lst.add(ob.getName());
                mapInv.put(ob.getName(), ob.getId());
                Log.e("object ", ob.getName());
            }
            FilterWithSpaceAdapter adapter = new FilterWithSpaceAdapter(AnbarActivity.this, R.layout.listitems, R.id.Itemlist, lst);
            stock.setThreshold(1);
            stock.setAdapter(adapter);
            btnStock.setVisibility(View.VISIBLE);
            pstock.setVisibility(View.INVISIBLE);
        }
    }

    public class GettafziliName extends AsyncTask<Integer, Void, String> {


        @Override
        protected String doInBackground(Integer... id) {
            FormConnection formConnection = new FormConnection();
            String res = formConnection.getTafziliName(AnbarActivity.this, id[0]);


            return res;
        }


        @Override
        protected void onPostExecute(String s) {

            tafziliName = Content.getTafziliName(s);


        }

    }

    public class GetUnit extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            punit.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            GoodsConnection con = new GoodsConnection();
            String res = con.getgoodsUnit(AnbarActivity.this, params[0]);

            return res;
        }

        @Override
        protected void onPostExecute(String  s) {
            List<UnitObject> listUnit = new ArrayList<>();
            List<String> data = new ArrayList<>();
            listUnit = Content.getUnit(s);
            for(UnitObject unit:listUnit){
                data.add(unit.getUnit());
                mapUnit.put(unit.getId(), unit.getUnit());
            }

            ArrayAdapter spinadapter = new ArrayAdapter(AnbarActivity.this, android.R.layout.simple_spinner_dropdown_item, data);
            unit.setAdapter(spinadapter);
            punit.setVisibility(View.INVISIBLE);
        }
    }

    public class StoreData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StoreConnection connection = new StoreConnection();
            Log.e("store ", String.valueOf(dt.getTime()));

            String res = connection.StoreAll(AnbarActivity.this,params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7],
                    params[8], params[9]);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
           Toast.makeText(AnbarActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }
}











