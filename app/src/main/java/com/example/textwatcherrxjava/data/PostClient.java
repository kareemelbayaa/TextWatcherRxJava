package com.example.textwatcherrxjava.data;

import com.example.textwatcherrxjava.pojo.PostModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private PostInterface postInterface;
    private static PostClient INSTANCE;

    public PostClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                /**to make it return an observable rather than call*/
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        postInterface = retrofit.create(PostInterface.class);
    }

    public static PostClient getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new PostClient();
        }
        return INSTANCE;
    }

    public Single<List<PostModel>> getPosts(){
        return postInterface.getPosts();
    }
}