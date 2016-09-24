package adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paya.administrator.anbar.AnbarActivity;
import com.paya.administrator.anbar.ProductList;
import com.paya.administrator.anbar.R;

import java.util.ArrayList;
import java.util.List;

import Database.Product;

/**
 * Created by Administrator on 16/08/2016.
 */
public class ProductAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<ProductList> productlist;
    int delId;
    int position;


    public ProductAdapter(Context context, int resource, List<ProductList> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.resource = resource;
        this.productlist =objects;

    }
    public ProductAdapter(Context context, int resource) {
        super(context, resource);
        this.context =  context;
        this.resource = resource;


    }
    public void swapItems(List<ProductList> items) {
        productlist = items;
        notifyDataSetChanged();
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
final Holderview holder = new Holderview();

this.position = position;
    View Rootview = LayoutInflater.from(context).inflate(R.layout.goodsitem, parent, false);

        holder.productName = (TextView) Rootview.findViewById(R.id.product);
        holder.unit = (TextView) Rootview.findViewById(R.id.unit);
        holder.numberofItem = (TextView) Rootview.findViewById(R.id.number);
        holder.btndelete = (Button) Rootview.findViewById(R.id.remove);
       // holder.progressBar = (ProgressBar) Rootview.findViewById(R.id.progressd);
        productlist.remove(holder.products);

       holder.products = productlist.get(position);


            holder.productName.setText(""+holder.products.getName());
            holder.numberofItem.setText("" + holder.products.getNumber());
            holder.unit.setText(holder.products.getUnit());
            holder.btndelete.setVisibility(View.VISIBLE);
            delId = holder.products.getId();
        holder.btndelete.setId(delId);
            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = new Product(context);
                      int del = v.getId();
                        int id = product.delete(del);
                        Log.e("id delte ", String.valueOf(delId));

                        List<ProductList> list = new ArrayList<>();
                        list.clear();
                        list.addAll(product.getProduct());
                        swapItems(list);

                }
            });



        return Rootview;
    }

    @Override
    public int getCount() {
        return productlist.size();
    }



    class Holderview{
        TextView productName, unit, numberofItem;
        Button btndelete;
        ProductList products;
        ProgressBar progressBar;


    }





    public class DeleteTask extends AsyncTask<Integer, Void, Integer>{


        @Override
        protected Integer doInBackground(Integer... position) {
            Product product = new Product(context.getApplicationContext());
           int id= product.delete(position[0]);
            return id;
        }


        @Override
        protected void onPostExecute(Integer integer) {

        }
    }
}
