package com.kellydwalters.beertome;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAddBeer;
    // Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        // you should have an add beer button at the top
        btnAddBeer = findViewById(R.id.btnAddBeer);

        // Creating categories
        createCategories();

        btnAddBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeerEntry.class);
                startActivity(intent);
            }
        });
        // followed by a pretty list view of all the beers in your db
        // maybe put in a search

    }

    private void createCategories() {
        // create cats
        BeerCategory cat1 = new BeerCategory("Summer");
        BeerCategory cat2 = new BeerCategory("Winter");
        BeerCategory cat3 = new BeerCategory("Favourite");
        BeerCategory cat4 = new BeerCategory("Hated It");

        // Inserting tags in db
        long cat1_id = db.createCategory(cat1);
        long cat2_id = db.createCategory(cat2);
        long cat3_id = db.createCategory(cat3);
        long cat4_id = db.createCategory(cat4);

        Log.d("Category Count", "Category Count: " + db.getAllCategories().size());
        db.closeDB();

    }
}
