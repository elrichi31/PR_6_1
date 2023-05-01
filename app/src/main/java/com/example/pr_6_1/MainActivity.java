package com.example.pr_6_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NasaAdapter nasaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String url = "https://nasasearch.nasa.gov/search?affiliate=nasa&page=6&query=%2A.jpg&sort_by=&utf8=%E2%9C%93";
        new DownloadTask(MainActivity.this).execute(url);

        // Agrega este código para configurar el botón y el oyente de clic
        Button openImageGridButton = findViewById(R.id.button_open_image_grid);
        openImageGridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
                startActivity(intent);
            }
        });
    }

    // Agrega este método a la clase MainActivity para configurar el adaptador
    void setupAdapter(ArrayList<Nasa> nasaList) {
        nasaAdapter = new NasaAdapter(this, nasaList);
        recyclerView.setAdapter(nasaAdapter);
    }
}

