package com.example.jsonextract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Edits;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase dbase;
    String url="https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp%7Cuser%7Curl&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8";
    String ctgUrl="https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List%20of&format=json";
    ArrayList<Img> img=new ArrayList<Img>();

    // ui design
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    static int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbase=openOrCreateDatabase("img.db", MODE_PRIVATE, null);
        createImageTable();


        dbclass db=new dbclass();
        db.execute(url);

        //category intialize;
        category c=new category();
        c.d_b=openOrCreateDatabase("ctg.db", MODE_PRIVATE, null);
        c.createArticleTable();
        category.dbclass dn=c.new dbclass();
        dn.execute(ctgUrl);

        c.fetch();

       artcle a=new artcle();
       a.d_b=openOrCreateDatabase("atc.db", MODE_PRIVATE, null);
       a.createArticleTable();
       artcle.dbclass d=a.new dbclass();
       d.execute(a.url);
       a.fetch();
//         if(temp==0){
//             temp=1;
//             startActivity(new Intent(MainActivity.this,Article.class));
//         }

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);

        fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        HomeFragment.transfer(fetchi());
        fragmentTransaction.commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new ArticleFragment();
                        break;
                    case 2:
                        fragment = new CategoryFragment();
                        break;

                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




}



    class dbclass extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {


                JSONObject jo=new JSONObject(s);
                JSONObject D1=jo.getJSONObject("query");
                JSONObject D2=D1.getJSONObject("pages");

                Iterator x = D2.keys();
                JSONArray jsonArray = new JSONArray();
                while (x.hasNext()){
                    String key = (String) x.next();
                   jsonArray.put(D2.get(key));
                }
                String d="";
                String pageid="";
                String title="";
                String timestamp="";
                String url="";
                String descriptionurl="";

//                String insert="INSERT INTO Images \n" +
//                        "(pageId, title,timeStamp, url,descriptionUrl)\n" +
//                        "VALUES \n" +
//                        "(?, ?, ?, ?,?) WHERE NOT EXISTS (SELECT name FROM table_listnames )LIMIT 1;";

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject j=jsonArray.getJSONObject(i);
                    pageid=j.getString("pageid");
                    title=j.getString("title");

                    JSONArray ja=j.getJSONArray("imageinfo");

                    for(int k=0;k<1;k++){
                    JSONObject j2=ja.getJSONObject(k);
                    timestamp=j2.getString("timestamp");
                    url=j2.getString("url");
                    descriptionurl=j2.getString("descriptionurl");

                    String insert=   " INSERT INTO Images (pageId, title, timeStamp,url,descriptionUrl) SELECT * FROM (SELECT $pageid,$title,$timestamp,$url,$descriptionurl) AS tmp WHERE NOT EXISTS ( SELECT pageId FROM Images WHERE pageId = $pageid) LIMIT 1;";


                        dbase.execSQL(insert,new String[]{pageid, title, timestamp, url,descriptionurl});
                    }
//                    d=d+"\n"+j.getString("imageinfo");
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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




    private void createImageTable() {
        dbase.execSQL("CREATE TABLE IF NOT EXISTS Images " +
                "(\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    pageId varchar(200) NOT NULL,\n" +
                "    title varchar(200) NOT NULL,\n" +
                "    timeStamp varchar(200) NOT NULL,\n" +
                "    url varchar(200) NOT NULL,\n" +
                "    descriptionUrl Varchar(200) NOT NULL\n" +
                ");"

        );
    }

    private ArrayList<Img> fetchi(){
        Cursor cursorEmployees = dbase.rawQuery("SELECT * FROM Images", null);
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                img.add(new Img(
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4),
                        cursorEmployees.getString(5)
                ));
            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();

        return img;
    }

}
