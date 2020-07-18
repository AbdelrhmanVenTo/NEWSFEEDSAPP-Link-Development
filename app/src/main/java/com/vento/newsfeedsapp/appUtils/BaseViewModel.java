package com.vento.newsfeedsapp.appUtils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel {

    private MutableLiveData<MutableHelper> mutableLiveData ;


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<MutableHelper> getMutableLiveData() {
        if(mutableLiveData==null)mutableLiveData=new MutableLiveData<>();
        return mutableLiveData;
    }



}
