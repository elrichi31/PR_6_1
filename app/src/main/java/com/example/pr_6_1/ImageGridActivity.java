package com.example.pr_6_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ImageGridActivity extends AppCompatActivity {

    private HashMap<String, Nasa> listaImagenes;
    private ArrayList<ImageView> imageViews;
    private ArrayList<String> cachedImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        listaImagenes = NasaDataSingleton.getInstance().getListaImagenes();

        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.imageView1));
        imageViews.add(findViewById(R.id.imageView2));
        imageViews.add(findViewById(R.id.imageView3));
        imageViews.add(findViewById(R.id.imageView4));
        imageViews.add(findViewById(R.id.imageView5));
        imageViews.add(findViewById(R.id.imageView6));


        for (ImageView imageView : imageViews) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imageUrl = getRandomImageUrl();
                    if (imageUrl != null) {
                        new DownloadImageTask(v.getContext(), (ImageView) v).execute(imageUrl);
                    }
                }
            });
        }
    }

    private String getRandomImageUrl() {
        ArrayList<Nasa> nasaList = new ArrayList<>(listaImagenes.values());
        Log.i("we", nasaList.get(1).getUrl());
        if (nasaList.isEmpty()) {
            Log.i("ff", "hola");
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(nasaList.size());
        Nasa randomNasa = nasaList.get(randomIndex);
        String randomUrl = randomNasa.getUrl();

        // Verificar si la URL de la imagen ya ha sido descargada y almacenada en caché
        if (cachedImageUrls.contains(randomUrl)) {
            // Si la URL ya ha sido descargada, intenta obtener una URL diferente
            return getRandomImageUrl();
        } else {
            // Agrega la URL a la lista de URL almacenadas en caché
            cachedImageUrls.add(randomUrl);
            return randomUrl;
        }
    }


    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;
        private final Context context;

        public DownloadImageTask(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                // Descarga la imagen utilizando Glide
                return Glide.with(context).asBitmap().load(urls[0]).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // Establece la imagen en el ImageView utilizando Glide
                Glide.with(context).load(bitmap).into(imageView);
            } else {
                // Opcional: mostrar un mensaje de error si la imagen no se pudo descargar
            }
        }
    }

}
