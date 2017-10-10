package tuongvong.appgame.android.milionnaire;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by admin on 10/9/2017.
 */

public class Leak extends Application {
    private RefWatcher refWatcher;
    static RefWatcher getRefWatcher(Context context) {
        Leak application = (Leak) context.getApplicationContext();
        return application.refWatcher;
    }
    @Override public void onCreate() {
        super.onCreate();
        //refWatcher = LeakCanary.install(this);
        //LeakCanary.install(this);
        setupLeakCanary();
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }
}

