package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import connections.PersonConnection;

/**
 * Created by Administrator on 14/08/2016.
 */
public class Person extends AsyncTask<Void, Void, String> {
    Context context;

    @Override
    protected String doInBackground(Void... voids) {
        PersonConnection con = new PersonConnection();
        //String res =con.person(context);
        return null;
    }


}
