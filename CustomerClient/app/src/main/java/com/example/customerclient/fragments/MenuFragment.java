package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customerclient.R;
import com.example.customerclient.activities.HomescreenActivity;
import com.example.customerclient.activities.JsonPlaceHolderApi;
import com.example.customerclient.activities.Post;
import com.example.customerclient.testing_stuff.Table;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    /******/
    private FirebaseFirestore db;
    private String restId;
    private ListView listView;
    private TextView textView;
    private ArrayList<String> menuHeaders;
    /******/

    private TextView textViewResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        textView = view.findViewById(R.id.restaurant_name);

        restId = HomescreenActivity.getRestId();
        Log.d("oncv", restId);
        db = FirebaseFirestore.getInstance();

        String str = "Welcome to " + restId;
        textView.setText(str);

        menuHeaders = new ArrayList<>();
        getHeaders();
        Log.v("jel", String.valueOf(menuHeaders.size()));

        String[] list = {"header 1", "header 2", "header 3", "header 4", "header 5"};

        listView = view.findViewById(R.id.menu_frag_list);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list
        );

        listView.setAdapter(listViewAdapter);








//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                Log.d("resmessage", response.message());
//                if (!response.isSuccessful()) {
//                    textViewResult.setText("code: " + response.code());
//                    return;
//                }
//                List<Post> posts = response.body();
//                String content = "";
//                for (Post post : posts) {
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n\n";
//                    textViewResult.append(content);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//                Log.d("asd", t.getMessage());
//            }
//        });

        //textViewResult = view.findViewById(R.id.text_view_result);
        return view;
    }

    private void getHeaders(){
        db.collection("headings").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : docList) {
                                Map<String, Object> header = d.getData();
                                Log.d("entered", header.get("restaurantId").toString());
                                if (header.get("restaurantId").equals(restId)) {
                                    Log.d("enteredIn", d.getId().toString());
                                    menuHeaders.add(header.get("name").toString());
                                }
                            }
                        }
                    }
                });
    }

}


