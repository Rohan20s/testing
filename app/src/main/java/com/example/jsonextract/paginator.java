package com.example.jsonextract;


import java.util.ArrayList;


public class paginator {
    public static final int TOTAL_NUM_ITEMS=Article.atc.size();
    public static  final int ITEMS_PER_PAGE=10;
    public static final int ITEMS_REMAINING=TOTAL_NUM_ITEMS%ITEMS_PER_PAGE;
    public static final int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;

    public ArrayList<Atc> generate(int currentpage) {
         int startItem=currentpage*ITEMS_PER_PAGE+1;
         int numOfData=ITEMS_PER_PAGE;
         ArrayList<Atc> art = new ArrayList<Atc>();
         artcle a=new artcle();

         if(currentpage==LAST_PAGE&&ITEMS_REMAINING>0){
             for (int i=startItem;i<startItem+ITEMS_REMAINING;i++){
                 art.add(Article.atc.get(i));
             }
         }
         else{
             for(int i=startItem;i<startItem+numOfData;i++){
                 art.add(Article.atc.get(i));
             }
         }
return  art;
    }
}
