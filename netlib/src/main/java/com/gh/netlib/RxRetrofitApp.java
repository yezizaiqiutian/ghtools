package com.gh.netlib;

import android.app.Application;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-13.
 * @from:
 */
public class RxRetrofitApp {

    private static Application application;
    private static boolean debug;

    public static void init(Application app,boolean debug){
        setApplication(app);
        setDebug(debug);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }

}
