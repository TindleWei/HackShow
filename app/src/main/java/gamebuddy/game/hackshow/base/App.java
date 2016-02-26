package gamebuddy.game.hackshow.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * describe
 * created by tindle
 * created time 16/2/26 下午3:09
 */
public class App extends Application {

    public static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initLeanCloud();
    }

    public void initLeanCloud() {
        AVOSCloud.useAVCloudCN();
        AVOSCloud.initialize(this,
                "y73VO5nsto7O5CD2xCbQ2B5W-gzGzoHsz",
                "YqoXBuk0HXvGxd5CViKTqNN9");
//        AVObject.registerSubclass(User.class);
//        AVObject.registerSubclass(ChatMessage.class);
//        AVObject.registerSubclass(AddRequest.class);
//        AVObject.registerSubclass(GroupMessage.class);
//        AVObject.registerSubclass(ChatGroup.class);
//        AVInstallation.getCurrentInstallation().saveInBackground();
////        PushService.setDefaultPushCallback(this, MsgActivity.class);
//        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
    }
}
