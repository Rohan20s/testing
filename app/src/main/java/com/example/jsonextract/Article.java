package com.example.jsonextract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Article extends AppCompatActivity {
    // ui design

    TextView tv;
    SQLiteDatabase d_b;
    String url="https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions%7Cimages&rvprop=content&grnlimit=10";
   static ArrayList<com.example.jsonextract.Atc> atc=new ArrayList<com.example.jsonextract.Atc>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        tv=findViewById(R.id.textView);
        d_b=openOrCreateDatabase("atc.db", MODE_PRIVATE, null);
        createArticleTable();


        dbclass db=new dbclass();
        db.execute(url);
        ArticleFragment.transfer(fetch());
       startActivity(new Intent(Article.this,MainActivity.class));

    }

    class dbclass extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {


                JSONObject jobj=new JSONObject(s);

                JSONObject D1=jobj.getJSONObject("query");
                JSONObject D2=D1.getJSONObject("pages");


                Iterator x = D2.keys();
                JSONArray jsonArray = new JSONArray();
                while (x.hasNext()){
                    String key = (String) x.next();
                    jsonArray.put(D2.get(key));
                }

                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jo=jsonArray.getJSONObject(i);
                    String title=jo.getString("title");

                    JSONArray ja=jo.getJSONArray("revisions");

                    for (int j=0;j<1;j++){
                        JSONObject jo2=ja.getJSONObject(j);
                        String desc=jo2.getString("*");
                        String insert=   " INSERT INTO Articles (title,description) SELECT * FROM (SELECT $title,$description) AS tmp WHERE NOT EXISTS ( SELECT title FROM Articles WHERE title = $title) LIMIT 1;";
                        d_b.execSQL(insert,new String[]{ title,desc});
                    }

                }




            } catch (Exception e) {
                Log.i("hh",e.getMessage());
                tv.setText(e.getMessage());

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer data= new StringBuffer();
                String line;
                while((line=br.readLine())!=null){
                    data.append(line+"\n");
                }
                br.close();
                return data.toString();
            }catch (Exception e){
                return e.getMessage();
            }

        }
    }

    public void createArticleTable() {
        d_b.execSQL("CREATE TABLE IF NOT EXISTS Articles " +
                "(\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    title varchar(200) NOT NULL,\n" +
                "    description Varchar NOT NULL\n" +
                ");"

        );
    }

    public ArrayList<Atc> fetch(){
        Cursor cursorEmployees = d_b.rawQuery("SELECT * FROM Articles", null);
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                atc.add(new Atc(
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2)

                ));

            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
      return atc;
    }

}