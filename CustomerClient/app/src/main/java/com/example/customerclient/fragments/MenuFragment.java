package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customerclient.R;
import com.example.customerclient.activities.JsonPlaceHolderApi;
import com.example.customerclient.activities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    private TextView textViewResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        textViewResult = view.findViewById(R.id.text_view_result);
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("resmessage", response.message());
                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                String content = "";
                for (Post post : posts) {
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d("asd", t.getMessage());
            }
        });

        //textViewResult = view.findViewById(R.id.text_view_result);
        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.text_view_result);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
//
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                Log.d("resmessage", response.message());
//                if(!response.isSuccessful()){
//                    textViewResult.setText("code: " + response.code());
//                    return;
//                }
//                List<Post> posts = response.body();
//
//                String content = "";
//                for(Post post : posts){
//
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n\n";
//
//                    textViewResult.append(content);
//
//                }
//                Log.d("asd", content);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//                Log.d("asd", t.getMessage());
//            }
//        });
//
//    }
}


