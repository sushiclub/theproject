package com.kellydwalters.beertome;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BeerEntry extends AppCompatActivity {

    private EditText etBeerName;

    private Button btnFind, btnAddBeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_entry);
        // Search query example
//        search?q=kilkenny&type=beer&key= + apiKey

        //this will be blank if the new button on main was pressed
        // or filled with editable items if it is from the description page

        //http://api.brewerydb.com/v2/search?q=<query name>&type=beer&key=ba3c926f89626f68edeb102cb57f0a93


        btnFind = findViewById(R.id.btnFind);
        btnAddBeer = findViewById(R.id.btnAddBeer);
        etBeerName = findViewById(R.id.etBeerName);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeerEntry.this,FindResults.class);
                intent.putExtra("beerName", etBeerName.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){


        } else {
            //cancelled
            Toast.makeText(this, "It broke", Toast.LENGTH_SHORT).show();
            Log.d("kelly", "it broke " );
        }
    }

}
