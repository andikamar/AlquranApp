package org.dika.alquranapp.base;


public class BasePresenter<V> {

    public V mView;

    public void attach(V view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }
}
