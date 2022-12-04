package wang.switchy.hin2n.storage.db.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;


import wang.switchy.hin2n.Hin2nApplication;

public class Hin2nSettings {
    private static Hin2nSettings instance = new Hin2nSettings();
    private boolean hasProxySetPermission = false;
    private Handler mainHandler;

    private Hin2nSettings() {
    }

    public static Hin2nSettings getInstance() {
        return instance;
    }

    public void toggleProxy() {
        boolean status = !getProxyStatus();
        String proxy = status ? getProxy() : ":0";

        if (isHasProxySetPermission() && getAppContext() != null) {
            Settings.Global.putString(getAppContext().getContentResolver(), Settings.Global.HTTP_PROXY, proxy);

            SharedPreferences n2nSp = getSharedPreferences();
            SharedPreferences.Editor editor = n2nSp.edit();
            editor.putBoolean("current_proxy_state", status).apply();
        } else {
            Toast.makeText(getAppContext(), "No Proxy setting permission", Toast.LENGTH_SHORT).show();
        }


        if (mainHandler != null) {
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = status;

            mainHandler.sendMessage(msg);
        }
    }

    public boolean getProxyStatus() {
        boolean status = getSharedPreferences().getBoolean("current_proxy_state", false);
        return status;
    }

    public String getProxy() {
        SharedPreferences n2nSp = getSharedPreferences();
        if (n2nSp == null) {
            return ":0";
        }
        Long currentSettingId = n2nSp.getLong("current_setting_id", -1);
        if (currentSettingId == -1) {
            return ":0";
        }

        String proxy = Hin2nApplication.getInstance().getDaoSession().getN2NSettingModelDao().load(currentSettingId).getProxy();
        return proxy;
    }

    private Context getAppContext() {
        return Hin2nApplication.getInstance().getApplicationContext();
    }

    private SharedPreferences getSharedPreferences() {
        return getAppContext().getSharedPreferences("Hin2n", Context.MODE_PRIVATE);
    }

    public boolean isHasProxySetPermission() {
        return hasProxySetPermission;
    }

    public void setHasProxySetPermission(boolean hasProxySetPermission) {
        this.hasProxySetPermission = hasProxySetPermission;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }
}
