package com.example.grabtest.utils;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxViewObservable {

    private static int mVisibleThrashHold = 4;

    private RxViewObservable() {
        // no instance
    }

    public static Observable<Boolean> recyclerViewObservable(RecyclerView recyclerView, final LinearLayoutManager layoutManager) {

        final BehaviorSubject<Boolean> subject = BehaviorSubject.create();

        try {

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                int visibleItemCount, totalItemCount, pastVisibleItems;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (layoutManager != null) {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            subject.onNext(true);
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e("exeception",e.toString());
        }
        return subject;
    }

    /*
     *
     * For checking the scroll direction and on the basis of that we can show hide some items
     * viewVisibility flag is true if you want to show any view eg - bottom-bar, toolbar
     *
     */
    public static Observable<Boolean> recyclerViewScrollObservable(RecyclerView recyclerView) {

        final PublishSubject<Boolean> subject = PublishSubject.create();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        subject.onNext(true);
                        break;
                    default:
                        subject.onNext(false);
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return subject;
    }

}