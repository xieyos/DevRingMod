package com.xieyos.baselibs;

import android.app.Application;

public abstract class BaseApplication extends Application {
    public abstract void init(Application application);

    public abstract void initover(Application application);
}
