package com.example.cloniamix.weatherapp.mvp.contract.base_presenter;

import com.example.cloniamix.weatherapp.mvp.contract.base_view.ICitiesListView;

/** Базовый презентер(родитель) при реализации MVP. Реализует подписку на ативити и отписку,
 *  а так же дает доступ к переменной активити. При реализации указывать дженерик(тип view) */
public abstract class BasePresenter<V extends ICitiesListView> implements IPresenter<V> {

    private V mView;

    /**вызывать в onStart() activity*/
    @Override
    public void subscribe(V view) {
        mView = view;
    }

    /** вызывать в onStop() activity*/
    @Override
    public void unsubscribe() {
        mView = null;
    }

    protected  V getView(){
        return mView;
    }
}
