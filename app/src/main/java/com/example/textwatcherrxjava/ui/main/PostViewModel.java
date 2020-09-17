package com.example.textwatcherrxjava.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.textwatcherrxjava.data.PostClient;
import com.example.textwatcherrxjava.pojo.PostModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<PostModel>> postsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> posts = new MutableLiveData<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "PostViewModel";

    public void getPosts() {
        Single<List<PostModel>> observable = PostClient.getINSTANCE().getPosts()
                /**to prevent overhead on the main thread so the observable use the background thread and the observer is on the
                 * main thread*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->postsMutableLiveData.setValue(o),e-> Log.d(TAG, "getPosts: onError: "+e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        /**to clear the composite disposable*/
        compositeDisposable.clear();
    }
}