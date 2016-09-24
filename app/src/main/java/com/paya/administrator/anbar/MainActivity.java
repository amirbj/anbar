package com.paya.administrator.anbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import connections.LoginConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatEditText txtUsername;
    AppCompatEditText txtPassword;
    AppCompatButton btnLogin;
    ImageView imgLogo;
    String Username;
    String Password;
    String Response;
    ProgressDialog progress;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        txtUsername = (AppCompatEditText) findViewById(R.id.input_email);
        txtPassword = (AppCompatEditText) findViewById(R.id.input_pass);
        imgLogo = (ImageView) findViewById(R.id.ivLogo);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.checkBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            txtUsername.setText(loginPreferences.getString("username", ""));
            txtPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);


    /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:


                if (isOnline()) {
                    if (validate()) {
                        rememberUser();
                      LoginTask task = new LoginTask();
                        task.execute();
                    }
                }


        }
    }

    private void rememberUser() {
        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", Username);
            loginPrefsEditor.putString("password", Password);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Toast.makeText(this, "اینترنت شما وصل نیست", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void onLoginSuccess() {

        finish();
       Intent i = new Intent(this, AnbarActivity.class);
      //  i.putExtra("username", Username);

      startActivity(i);

    }

    public boolean validate() {

        boolean valid = true;
        Username = txtUsername.getText().toString();
        Password = txtPassword.getText().toString();
        if (Username.isEmpty()) {
            txtUsername.setError("نام کاربری وارد نشده است");
            valid = false;
        }

        if (Password.isEmpty()) {
            txtPassword.setError("رمز عبور وارد نشده است");
            valid = false;

        }
        if (Password.length() > 100) {
            txtPassword.setError("رمز عبور نا معتبر است");
            valid = false;
        }
        return valid;

    }

public class LoginTask extends AsyncTask<Void,Void, String>{
    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(MainActivity.this, "در خواست ورود", "لطفا منتظر بمانید", true);
    }

    @Override
    protected String doInBackground(Void... voids) {
        LoginConnection  con = new LoginConnection();
        String response = con.login(MainActivity.this, Username, Password);
        return  response;
    }

    @Override
    protected void onPostExecute(String resp) {

        progress.dismiss();
        if (resp != null) {
            String s = resp.substring(5);
            Log.e("sub ", s);
            if (s.startsWith("true}"))


            {

                onLoginSuccess();

            } else {
                Toast.makeText(getApplicationContext(), "ورود نا موفق...", Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "جوابی از سرور دریافت نشد", Toast.LENGTH_LONG).show();
    }
}

}
}
