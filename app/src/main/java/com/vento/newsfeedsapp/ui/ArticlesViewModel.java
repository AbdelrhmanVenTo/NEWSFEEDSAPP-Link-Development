package com.vento.newsfeedsapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.vento.newsfeedsapp.appUtils.BaseViewModel;
import com.vento.newsfeedsapp.appUtils.Constants;
import com.vento.newsfeedsapp.appUtils.MutableHelper;
import com.vento.newsfeedsapp.network.ApiManager;
import com.vento.newsfeedsapp.ui.model.ArticlesResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ArticlesViewModel extends BaseViewModel {

    private CompositeDisposable compositeDisposable ;

    public ArticlesViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();

    }

    public void articlesAssociatedPress(){
        Disposable disposable = ApiManager.getAPIs().articlesAssociatedPress("associated-press",Constants.apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<ArticlesResponse>(){

                    @Override
                    public void onSuccess(ArticlesResponse response){
                        if (response.getStatus().equals("ok")){
                            getMutableLiveData().setValue(new
                                    MutableHelper(Constants.AssociatedPress_SUCCESS,response.getArticles()));
                        }else {
                            getMutableLiveData().setValue(new MutableHelper(
                                    Constants.AssociatedPress_FAIL,null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMutableLiveData().setValue(new MutableHelper(Constants
                                .NETWORK_CONNECTION_ERROR,null));
                    }
                });


        compositeDisposable.add(disposable);

    }

    public void articlesTheNextWeb(){
        Disposable disposable = ApiManager.getAPIs().articlesTheNextWeb("the-next-web",Constants.apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<ArticlesResponse>(){

                    @Override
                    public void onSuccess(ArticlesResponse response){
                        if (response.getStatus().equals("ok")){
                            getMutableLiveData().setValue(new
                                    MutableHelper(Constants.theNextWeb_SUCCESS,response.getArticles()));
                        }else {
                            getMutableLiveData().setValue(new MutableHelper(
                                    Constants.theNextWeb_FAIL,null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMutableLiveData().setValue(new MutableHelper(Constants
                                .NETWORK_CONNECTION_ERROR,null));
                    }
                });


        compositeDisposable.add(disposable);

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
