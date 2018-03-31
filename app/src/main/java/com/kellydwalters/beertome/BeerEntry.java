package com.kellydwalters.beertome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BeerEntry extends AppCompatActivity {

    private EditText etBeerName, etAbv, etDescription, etReview;
    private SharedPreferences sharedPreferences;

    // Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_entry);

        Button btnFind = findViewById(R.id.btnFind);
        Button btnAddBeer = findViewById(R.id.btnSubmit);
        etBeerName = findViewById(R.id.etBeerName);
        etAbv = findViewById(R.id.etABV);
        etDescription =findViewById(R.id.etDescription);
        etReview = findViewById(R.id.etReview);

        db = new DatabaseHelper(getApplicationContext());


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeerEntry.this,FindResults.class);
                intent.putExtra("beerName", etBeerName.getText().toString());
                startActivityForResult(intent, 0);
            }
        });
        
        btnAddBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BeerEntry.this, "This will add the beer to the db", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            // set text fields to selected beer data
            etBeerName.setText(data.getStringExtra("name"));
            etAbv.setText(data.getStringExtra("abv"));
            etDescription.setText(data.getStringExtra("description"));

        } else {

            clearTextFields();

            //cancelled
//            Toast.makeText(this, "It broke", Toast.LENGTH_SHORT).show();
            Log.d("kelly", "Nothing selected " );
        }
    }

    private void clearTextFields(){
        etAbv.setText("");
        etDescription.setText("");
        etReview.setText("");
        // do the other things here as well so that a menu option can clear it too
    }

}
