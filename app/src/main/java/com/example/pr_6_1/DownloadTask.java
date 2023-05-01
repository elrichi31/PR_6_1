package com.example.pr_6_1;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadTask extends AsyncTask<String, Void, ArrayList<Nasa>> {
    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }
    @Override
    protected ArrayList<Nasa> doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection;
        ArrayList<Nasa> nasaList = new ArrayList<>();

        try {
            // Download 1: Descarga el código fuente de la página
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result += line;
            }

            // Procesamiento del resultado (extracción de href y títulos)
            int startIndex = result.indexOf("<div id='results'>");
            if (startIndex != -1) {
                String extractedResult = result.substring(startIndex);
                Pattern hrefPattern = Pattern.compile("href=\"(.*?)\".*?>(.*?)<\\/a>");
                Matcher hrefMatcher = hrefPattern.matcher(extractedResult);

                ArrayList<String> extractedHttpList = new ArrayList<>();
                ArrayList<String> extractedTitleList = new ArrayList<>();

                while (hrefMatcher.find()) {
                    extractedHttpList.add(hrefMatcher.group(1));
                    extractedTitleList.add(hrefMatcher.group(2));
                }

                // Download 2: Descarga las páginas individuales y crea objetos Nasa
                for (int i = 0; i < extractedHttpList.size(); i++) {
                    String http = extractedHttpList.get(i);
                    String pageTitle = extractedTitleList.get(i);

                    url = new URL(http);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuilder pageContentBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        pageContentBuilder.append(line);
                    }

                    String pageContent = pageContentBuilder.toString();

                    Document document = Jsoup.parse(pageContent);
                    Element downloadLink = document.select("a:contains(Download JPG)").first();

                    if (downloadLink != null) {
                        String imageUrl = downloadLink.attr("href");
                        Nasa nasaObject = new Nasa(pageTitle, http, imageUrl);
                        nasaList.add(nasaObject);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nasaList;
    }

    @Override
    protected void onPostExecute(ArrayList<Nasa> nasaList) {
        MainActivity activity = (MainActivity) context;
        activity.setupAdapter(nasaList);
        NasaDataSingleton.getInstance().updateData(nasaList);
    }


}

