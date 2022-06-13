package com.example.jsonextract;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class category {

    SQLiteDatabase d_b;
   static ArrayList<Ctg> ctg=new ArrayList<Ctg>();
  static String h="";

     class  dbclass extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {


                JSONArray jAry=new JSONObject(s).getJSONObject("query").getJSONArray("allcategories");

                for (int i=0;i<jAry.length();i++){
                    JSONObject jobj=jAry.getJSONObject(i);
                    String Category=jobj.getString("*");
                    String insert=   " INSERT INTO Categories (title) SELECT * FROM (SELECT $Category) AS tmp WHERE NOT EXISTS ( SELECT title FROM Categories WHERE title = $Category) LIMIT 1;";
                    d_b.execSQL(insert,new String[]{Category});
                }



//                JSONObject D1=jobj.getJSONObject("query");
//                JSONObject D2=D1.getJSONObject("pages");
//
//
//                Iterator x = D2.keys();
//                JSONArray jsonArray = new JSONArray();
//                while (x.hasNext()){
//                    String key = (String) x.next();
//                    jsonArray.put(D2.get(key));
//                }
//
//                for (int i=0;i<jsonArray.length();i++){
//                    JSONObject jo=jsonArray.getJSONObject(i);
//                    String title=jo.getString("title");
//
//                    JSONArray ja=jo.getJSONArray("revisions");
//
//                    for (int j=0;j<1;j++){
//                        JSONObject jo2=ja.getJSONObject(j);
//                        String desc=jo2.getString("*");
//                        String insert=   " INSERT INTO Articles (title,description) SELECT * FROM (SELECT $title,$description) AS tmp WHERE NOT EXISTS ( SELECT title FROM Articles WHERE title = $title) LIMIT 1;";
//                        d_b.execSQL(insert,new String[]{ title,desc});
//                    }

//                }




            } catch (Exception e) {
                Log.i("hh",e.getMessage());
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
        d_b.execSQL("CREATE TABLE IF NOT EXISTS Categories " +
                "(\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    title varchar(200) NOT NULL);"

        );
    }

    public ArrayList<Ctg> fetch(){
        Cursor cursorEmployees = d_b.rawQuery("SELECT * FROM Categories", null);
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                ctg.add(new Ctg(
                        cursorEmployees.getString(1)

                ));



            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        return ctg;
    }
}
