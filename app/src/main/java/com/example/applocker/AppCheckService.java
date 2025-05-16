package com.example.applocker;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.List;

public class AppCheckService extends Service {
    private Handler handler = new Handler();
    private String lastApp = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(checkAppRunnable);
        return START_STICKY;
    }

    private Runnable checkAppRunnable = new Runnable() {
        @Override
        public void run() {
            String currentApp = getForegroundApp();
            if (!currentApp.equals(lastApp)) {
                lastApp = currentApp;
                if (isLockedApp(currentApp)) {
                    Intent i = new Intent(AppCheckService.this, LockActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }
            handler.postDelayed(this, 1000);
        }
    };

    private boolean isLockedApp(String pkg) {
        return pkg.equals("com.whatsapp") || pkg.equals("com.instagram.android");
    }

    private String getForegroundApp() {
        UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 10000, time);
        if (appList == null || appList.isEmpty()) return "";
        UsageStats recent = null;
        for (UsageStats usageStats : appList) {
            if (recent == null || usageStats.getLastTimeUsed() > recent.getLastTimeUsed()) {
                recent = usageStats;
            }
        }
        return recent != null ? recent.getPackageName() : "";
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}