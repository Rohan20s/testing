package com.example.jsonextract;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleFragment() {
        // Required empty public constructor
    }


    public static ArticleFragment newInstance(String param1, String param2) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    static ArrayList<Atc> itemList=artcle.atc;
    Button next,prev;
    paginator p=new paginator();
    private int totalPages=paginator.TOTAL_NUM_ITEMS/paginator.ITEMS_PER_PAGE;
    private int currentpage=0;

//     public static void transfer(ArrayList<Atc> item){
//         itemList=item;
//     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_article, container, false);
        recyclerView=view.findViewById(R.id.RecycleA);
        prev=view.findViewById(R.id.button4);
        next=view.findViewById(R.id.button3);
        prev.setEnabled(false);
        //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new MyAdapterA(getContext(),p.generate(currentpage)));

       next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               currentpage +=1;
               recyclerView.setAdapter(new MyAdapterA(getContext(),p.generate(currentpage)));
               toggleButton();
           }
       });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentpage -=1;
                recyclerView.setAdapter(new MyAdapterA(getContext(),p.generate(currentpage)));
                toggleButton();
            }
        });

        return view;
    }

    private void toggleButton(){
        if(currentpage==totalPages){
            next.setEnabled(false);
            prev.setEnabled(true);
        }else if(currentpage==0){
            prev.setEnabled(false);
            next.setEnabled(true);
        }else if(currentpage>=1&&currentpage<=5){
            next.setEnabled(true);
            prev.setEnabled(true);
        }
    }
}
