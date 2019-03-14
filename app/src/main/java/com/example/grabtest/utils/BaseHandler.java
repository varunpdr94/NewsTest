package com.example.grabtest.utils;

import android.view.View;

/**
 * Created by Abhishek V on 07/12/2017.
 */

public interface BaseHandler<T> {
    void onClick(View view, T data);
}
