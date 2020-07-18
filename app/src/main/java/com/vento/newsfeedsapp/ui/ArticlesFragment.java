package com.vento.newsfeedsapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vento.newsfeedsapp.MainActivity;
import com.vento.newsfeedsapp.R;
import com.vento.newsfeedsapp.appUtils.Constants;
import com.vento.newsfeedsapp.appUtils.MutableHelper;
import com.vento.newsfeedsapp.appUtils.base.BaseFragment;
import com.vento.newsfeedsapp.databinding.FragmentArticlesBinding;
import com.vento.newsfeedsapp.ui.model.ArticlesItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static android.content.ContentValues.TAG;

public class ArticlesFragment extends BaseFragment {

    Context context;
    Activity activity ;
    View rootView;
    FragmentArticlesBinding binding;
    ArticlesViewModel viewModel;
    ArticlesNewsAdapter adapter;
    List<ArticlesItem> itemsAssociatedPress = new ArrayList<>();
    List<ArticlesItem> itemsTheNextWeb = new ArrayList<>();
    List<ArticlesItem> allListArticles;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil
                .inflate(inflater, R.layout.fragment_articles, container, false);
        init();
        return rootView;
    }
    private void init(){
        viewModel = new ViewModelProvider(this).get(ArticlesViewModel.class);
        binding.setLifecycleOwner(this);
        rootView = binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        initView();
        viewModel.articlesAssociatedPress();
        viewModel.articlesTheNextWeb();
        clickListener();
    }

    private void initView(){
        adapter = new ArticlesNewsAdapter(null);
        binding.recViewArticles.setLayoutManager(new LinearLayoutManager(context));
        binding.recViewArticles.setAdapter(adapter);
    }


        private void clickListener (){
            viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<MutableHelper>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onChanged(MutableHelper mutableHelper) {
                    if (mutableHelper.key== Constants.AssociatedPress_FAIL||mutableHelper.key== Constants.theNextWeb_FAIL){
                        showMessageOk("Error","No News Found","ok");
                    }else if (mutableHelper.key==Constants.AssociatedPress_SUCCESS){
                        itemsAssociatedPress = (List<ArticlesItem>) mutableHelper.value;
                        Log.e(TAG, "itemsAssociatedPress: "+itemsAssociatedPress.toString() );
                    }else if (mutableHelper.key==Constants.theNextWeb_SUCCESS){
                        itemsTheNextWeb = (List<ArticlesItem>) mutableHelper.value;
                        Log.e(TAG, "itemsTheNextWeb: "+itemsTheNextWeb.toString() );

                    }else if (mutableHelper.key==Constants.NETWORK_CONNECTION_ERROR){
                        showMessageOk("Error","Network Error","ok");
                    }

                    Set<ArticlesItem> set = new LinkedHashSet<>(itemsTheNextWeb);
                    set.addAll(itemsAssociatedPress);
                    allListArticles = new ArrayList<>(set);

                    adapter = new ArticlesNewsAdapter(allListArticles);
                    binding.recViewArticles.setAdapter(adapter);

                    adapter.setOnItemClickListener(new ArticlesNewsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos, ArticlesItem articlesItemList) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("author", articlesItemList.getAuthor());
                            intent.putExtra("title", articlesItemList.getTitle());
                            intent.putExtra("description", articlesItemList.getDescription());
                            intent.putExtra("getUrlToImage", articlesItemList.getUrlToImage());
                            intent.putExtra("getUrl", articlesItemList.getUrl());
                            startActivity(intent);
                        }
                    });



                }
            });

    }
}