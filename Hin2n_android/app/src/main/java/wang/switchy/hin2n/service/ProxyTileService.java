package wang.switchy.hin2n.service;

import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;

import wang.switchy.hin2n.Hin2nApplication;
import wang.switchy.hin2n.activity.MainActivity;
import wang.switchy.hin2n.storage.db.base.Hin2nSettings;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProxyTileService extends TileService {
    private static final String TAG = ProxyTileService.class.getSimpleName();
    private Hin2nSettings settings = Hin2nSettings.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();

        if (settings.getMainHandler() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityAndCollapse(intent);

        } else {
            refresh();
        }
    }

    public void refresh() {
        settings.toggleProxy();

        updateTile();
    }

    private void updateTile() {
        int state = settings.getProxyStatus() ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE;
        getQsTile().setState(state);
        String label = settings.getProxy();

        getQsTile().setLabel(state == 1 ? "no proxy" : label);
        // 刷新显示
        getQsTile().updateTile();
    }
}

