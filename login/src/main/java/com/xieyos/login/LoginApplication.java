package com.xieyos.login;

import android.app.Application;

import com.ljy.devring.DevRing;
import com.ljy.devring.util.FileUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xieyos.baselibs.BaseApplication;
import com.xieyos.baselibs.login.ServiceFactory;
import com.xieyos.login.constant.UrlConstants;
import com.xieyos.login.service.AccountService;

public class LoginApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initDevRing();
        init(this);
        initover(this);
    }

    /**
     * DevRing 基础框架构建
     */
    private void initDevRing() {
        //*********1.初始化**********
        DevRing.init(this);


        //*********2.根据你的需求进行相关模块的全局配置，下面对每个配置方法进行了说明**********

        //网络请求模块
        DevRing.configureHttp()//配置retrofit
                .setBaseUrl(UrlConstants.BASE_URL)//设置BaseUrl
                .setConnectTimeout(15)//设置请求超时时长，单位秒
                .setIsUseCookie(false)//是否开启Cookie
//                .setMapHeader(mapHeader)//设置全局的header信息
//                .setIsUseCache(true)//设置是否启用缓存，默认不启用
//                .setCacheFolder(file)//设置缓存地址，传入的file需为文件夹，默认保存在/storage/emulated/0/Android/data/com.xxx.xxx/cache/retrofit_http_cache下
//                .setCacheSize(size)//设置缓存大小，单位byte，默认20M
//                .setCacheTimeWithNet(time)//设置有网络时缓存保留时长，单位秒，默认60秒
//                .setCacheTimeWithoutNet(time)//设置无网络时缓存保留时长，单位秒，默认一周
//                .setIsUseRetryWhenError(true)//设置是否开启失败重试功能，目前仅支持普通的网络请求，上传下载不支持。默认不开启
//                .setMaxRetryCount(2)//设置失败后重试的最大次数，默认3次
//                .setTimeRetryDelay(5)//设置失败后重试的延迟时长，单位秒，默认3秒
                .setIsUseLog(true);//设置是否开启Log，默认不开启
        //如果提供的配置方法还无法满足你的需求，那可以通过以下方法获取builder进行你的定制
//        DevRing.configureHttp().getOkHttpClientBuilder();
//        DevRing.configureHttp().getRetrofitBuilder();


        //图片加载模块（可替换，demo演示了如何使用Fresco替换默认的Glide）
        DevRing.configureImage()//配置默认的Glide
//        DevRing.configureImage(new FrescoManager()) //传入Fresco管理者进行替换，注意，替换为Fresco后，相关的ImageView需换成SimpleDraweeView
                .setLoadingResId(R.mipmap.ic_image_load)//设置“加载中”状态时显示的图片
                .setErrorResId(R.mipmap.ic_image_load)//设置“加载失败”状态时显示的图片
                .setIsShowTransition(true)//设置是否开启状态切换时的过渡动画，默认false
//                .setIsUseOkhttp(false)//设置是否使用okhttp3作为网络组件，默认true
//                .setMemoryCacheSize(size)//设置内存缓存大小，不建议设置，使用框架默认设置的大小即可
//                .setBitmapPoolSize(size)//设置Bitmap池大小，设置内存缓存大小的话一般这个要一起设置，不建议设置，使用框架默认设置的大小即可
//                .setDiskCacheFile(file)//设置具体的磁盘缓存地址，传入的file需为文件夹
//                .setDiskCacheSize(200*1024*1024)//设置磁盘缓存大小，单位byte，默认250M
                .setIsDiskCacheExternal(false);//设置磁盘缓存地址是否在外部存储中，默认false


        //事件总线模块（可替换，demo演示了如何使用RxBus替换默认的EventBus）
//        DevRing.configureBus()//配置默认的EventBus
//                .setIndex(new MyEventBusIndex())//设置用于加速的Index，关于index加速可以查看https://www.jianshu.com/p/6fb4d78db19b
//                .setIsUseIndex(true);//设置是否使用index进行加速
        //DevRing.configureBus(new RxBusManager());//传入RxBus的管理者进行替换


        //数据库模块（可替换，demo演示了如何使用原生数据库替换默认的GreenDao）
//        DevRing.configureDB(new GreenDBManager());//传入GreenDao数据库的管理者
        //DevRing.configureDB(new NativeDBManager());//传入原生数据库的管理者

        //缓存模块
        DevRing.configureCache()//配置缓存
//                .setDiskCacheMaxSize(50*1024*1024)//设置磁盘缓存最大缓存大小，单位为byte，默认无上限
//                .setDiskCacheMaxCount(10)//设置磁盘缓存的文件夹数量上限，默认无上限
                //配置磁盘缓存的地址，传入的File需为文件夹，默认保存在/data/user/0/com.xxx.xxx/cache下
                .setDiskCacheFolder(FileUtil.getDirectory(FileUtil.getExternalCacheDir(this), "disk_cache"));

        //Logger日志模块初始化
        FormatStrategy diskFormatStrategy = CsvFormatStrategy.newBuilder()
                .tag("XIEYOS")   // 全局的tag
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(diskFormatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否打印线程号,默认true
                .methodCount(2)         // 展示几个方法数,默认2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(logcat) //是否更换打印输出,默认为logcat
                .tag("XIEYOS")   // 全局的tag
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        //其他模块
        DevRing.configureOther()//配置其他
//                .setIsUseCrashDiary(false)//设置是否开启崩溃日志功能，默认不开启
//                .setCrashDiaryFolder(file)//设置崩溃日志的地址，传入的file需为文件夹，默认保存在/storage/emulated/0/Android/data/com.xxx.xxx/cache/crash_log下
                .setIsShowRingLog(false);//设置是否显示Ringlog打印的内容，默认true

        //*********3.开始构建**********
        DevRing.create();
    }

    @Override
    public void init(Application application) {
        ServiceFactory.getInstance().setLoginService(new AccountService(true, "123456"));
    }

    @Override
    public void initover(Application application) {

    }
}
