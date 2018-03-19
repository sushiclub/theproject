package com.kellydwalters.beertome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAddBeer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // you should have an add beer button at the top
        btnAddBeer = findViewById(R.id.btnAddBeer);

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
}
