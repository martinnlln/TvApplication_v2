package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.myapplication.room_database.TelevisionDatabase;
import com.example.myapplication.room_database.TelevisionModel.Television;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    int dbCountSeir;
    int dbCountBgGledai;
    // What the fack
    // adasdasdasas
    // adasdasdasas
    // adasdasdasas
    // adasdasdasas
    // adasdasdasas
    // adasdasdasas
    // adasdasdasas

    SeirSandk seirSandk;
    BgGledaiTV bgGledaiTV;
    Thread waitThread;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        host = sharedPreferences.getString("host", "noHost");

        System.out.println(host);
        if (host.equals("noHost")) {
            host = "https://www.bg-gledai.live/";
        } else {
            host = "https://www.seirsanduk.com/";
        }

        waitThread = new Thread(() -> {
            if (host.equals("https://www.bg-gledai.live/")) {
                if (TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllBgGledaiTV().size() <= 0) {
                    bgGledaiTV = new BgGledaiTV();
                    bgGledaiTV.execute();
                } else {
                    startActivity(new Intent(getApplicationContext(), StartUpActivity.class));

                }
            } else {
                if (TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllSeirSanduk().size() <= 0) {
                    seirSandk = new SeirSandk();
                    seirSandk.execute();
                } else {
                    startActivity(new Intent(getApplicationContext(), StartUpActivity.class));
                }
            }

        });
        waitThread.start();
        try {
            waitThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitThread = new Thread(() -> {
            dbCountSeir = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllSeirSanduk().size();
            dbCountBgGledai = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllBgGledaiTV().size();
        });
        waitThread.start();
        try {
            waitThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class SeirSandk extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.seirsanduk.com/";
                Document doc = Jsoup.connect(url).get();
                Elements ul = doc.select("ul"); // select ul
                Elements links = ul.select("a[href]"); // a with href
                for (Element element : links) {
                    Element element1 = element.select("img").first();
                    String name = element.text();
                    String uurl = element.attr("href");
                    String imgSrc = element1.absUrl("src");

                    Television television = new Television(name, uurl, imgSrc, false, false);
                    TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().addNewTelevision(television);
                }
                startActivity(new Intent(getApplicationContext(), StartUpActivity.class));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    String url = "";

    private class BgGledaiTV extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 6; i++) {
                    if (i == 0) {
                        url = "https://www.bg-gledai.live/";

                    } else {
                        url = "https://www.bg-gledai.live/page/" + i;
                    }
                    Document doc = Jsoup.connect(url).get();
                    Elements ele1 = doc.select("div[id=content]");
                    Elements e = (ele1.select("h2"));
                    Elements element = e.select("a");
                    Elements imgLink = ele1.select("img");

                    int ij = 0;
                    String tvLinks[] = new String[imgLink.size()];
                    for (Element el : imgLink) {
                        tvLinks[ij++] = el.attr("src");
                    }

                    ij = 0;
                    for (Element element1 : element) {
                        String name = String.valueOf(element1.attr("title"));
                        String tv_url = element1.attr("href");
                        String imgSrc = tvLinks[ij++];
                        Television television = new Television(name, tv_url, imgSrc, false, true);
                        TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().addNewTelevision(television);

                    }
                }
                startActivity(new Intent(MainActivity.this, StartUpActivity.class));
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.app_name)
                        .setMessage(e.getMessage())
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).setNegativeButton("No", null).show();
            }
            return null;
        }
    }
}