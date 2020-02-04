package com.grocito.grocito.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.grocito.grocito.model.HomeModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<HomeModel>> homeModel;

}
