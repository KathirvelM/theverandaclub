package com.verandah.club.base;


import com.verandah.club.PresenterInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.ModelInterface;

public abstract class BasePresenter implements PresenterInterface {

    protected AppRepository appRepository = BaseApplication.getAppRepository();

    public void close() {
        ModelInterface modelInterface = getModelInterface();
        if(modelInterface!=null)modelInterface.close();
    }


}
