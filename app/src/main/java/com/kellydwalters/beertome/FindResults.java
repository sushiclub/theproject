package com.kellydwalters.beertome;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FindResults extends AppCompatActivity {
    private String apiURL = "http://api.brewerydb.com/v2/search?q=";
    private String apiKey = "key=ba3c926f89626f68edeb102cb57f0a93";
    private String apiSearchType = "&type=beer&";

    private ListView mainListView ;
    private ArrayList<ListItem> item = new ArrayList<>();
    CustomListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_results);

        // get the beer name from the intent passed in and set it to the query string
        Intent intent = getIntent();
        String query = intent.getStringExtra("beerName");

        // build the api call using the passed in beername query value
        String url = apiURL + query + apiSearchType +  apiKey;
        FindResults.OkHttpHandler okHttpHandler= new FindResults.OkHttpHandler();

        okHttpHandler.execute(url);
    }

    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(Object[] params) {
            mainListView = findViewById( R.id.mainListView );

            //builds the http request
            Request.Builder builder = new Request.Builder();
            //builds the url
            builder.url(params[0].toString());
            // sets the request to the built url
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d("KELLLY", o.toString());
            parseResponse(o.toString());

        }
    }

    private void parseResponse(String response) {
        try{

            // Convert String to json object
            JSONObject reader = new JSONObject(response);
//
            // for beer name: ['data'][i]['name']
            // for abv: ['data'][i]['abv']
            // description ['data'][0]['style']['description']
            // image ['data'][i]['labels']['icon']


            // get Data json object
            JSONArray jsonData = reader.getJSONArray("data");

            for (int i = 0; i < jsonData.length(); i++) {
                // get the main level of items
                JSONObject c = jsonData.getJSONObject(i);

                String name = c.getString("name");
                String abv = c.getString("abv");

                //description is nested
                JSONObject style = c.getJSONObject("style");
                String description = style.getString("description");
//
//                // Label is nested
//                JSONObject label = c.getJSONObject("labels");
//                String image = label.getString("icon");



                // print out what we got
                Log.d("KELLLY", name);
                Log.d("KELLLY", abv);
//                Log.d("KELLLY", image);
                Log.d("KELLLY", description);


                // tmp hash map for single contact
                HashMap<String, String> beer = new HashMap<>();

                // adding each child node to HashMap key => value

                beer.put("name", name);
                beer.put("abv", abv);
                beer.put("description", description);
//                beer.put("image", image);


            // Creates a new list item for each call
                ListItem x = new ListItem();

                x.setName(name);
                x.setAbv(abv);
//                x.setImage(image);

                item.add(x);

                ArrayList<String> titleList = new ArrayList<String>();
                ArrayList<String> imageList = new ArrayList<String>();
                ArrayList<String> abvList = new ArrayList<String>();

                for(ListItem li: item){
                    titleList.add(li.getName());
                    abvList.add(li.getAbv());
                    imageList.add(li.getImage());
                }

                // Resets the adapter after each request, so that everything is added
                customListView = new CustomListView(FindResults.this, titleList, imageList, abvList);
                mainListView.setAdapter( customListView );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class CustomListView extends ArrayAdapter<String> {
        private ArrayList<String> title, abvValue, image;
        private Activity context;

        public CustomListView(Activity context, ArrayList<String> title, ArrayList<String> abvValue,  ArrayList<String> image) {
            super(context, R.layout.found_row, title);

            this.title = title;
            this.context = context;
            this.abvValue = abvValue;
            this.image = image;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null){
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.found_row, null, true);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder)convertView.getTag();
            }

            vh.txtTitle.setText(title.get(position));
            vh.tvABV.setText(abvValue.get(position));


            // Loads the image via url into the image view
            Picasso.get().load(image.get(position)).into(vh.image);

            return convertView;
        }

        class ViewHolder {
            TextView txtTitle, tvABV;
            ImageView image;

            ViewHolder (View v){
                this.txtTitle = v.findViewById(R.id.tvName);
                this.tvABV = v.findViewById(R.id.tvABV);
                this.image = v.findViewById(R.id.imageView);

            }
        }
    }

}
