package wang.switchy.hin2n.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.permissionx.guolindev.PermissionX;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import wang.switchy.hin2n.Hin2nApplication;
import wang.switchy.hin2n.R;
import wang.switchy.hin2n.adapter.TermAdapter;
import wang.switchy.hin2n.event.ConnectingEvent;
import wang.switchy.hin2n.event.ErrorEvent;
import wang.switchy.hin2n.event.LogChangeEvent;
import wang.switchy.hin2n.event.StartEvent;
import wang.switchy.hin2n.event.StopEvent;
import wang.switchy.hin2n.event.SupernodeDisconnectEvent;
import wang.switchy.hin2n.model.EdgeStatus;
import wang.switchy.hin2n.model.N2NSettingInfo;
import wang.switchy.hin2n.service.N2NService;
import wang.switchy.hin2n.storage.db.base.Hin2nSettings;
import wang.switchy.hin2n.storage.db.base.model.N2NSettingModel;
import wang.switchy.hin2n.template.BaseTemplate;
import wang.switchy.hin2n.template.CommonTitleTemplate;
import wang.switchy.hin2n.tool.IOUtils;
import wang.switchy.hin2n.tool.N2nTools;
import wang.switchy.hin2n.tool.ShareUtils;
import wang.switchy.hin2n.tool.ThreadUtils;

public class MainActivity extends BaseActivity {
    private Hin2nSettings settings = Hin2nSettings.getInstance();
    private N2NSettingModel mCurrentSettingInfo;
    private RelativeLayout mCurrentSettingItem;
    private TextView mCurrentSettingName;
    private TextView mLogAction;
    private NestedScrollView mScrollLogAction;
    private ImageView mConnectBtn;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LinearLayout mLeftMenu;
    private String logTxtPath;
    private CheckBox mStartAtBoot;
    private CheckBox mUseProxy;
    private RecyclerView mRecyclerView;
    private TermAdapter mTermAdapter;
    private Handler mHandler;

    List<String> term = new ArrayList<>();

    private static final int REQUECT_CODE_SDCARD = 1;
    private static final int REQUECT_CODE_VPN = 2;
    private static final int REQUEST_CODE_VPN_FOR_START_AT_BOOT = 3;

    @Override
    protected BaseTemplate createTemplate() {
        CommonTitleTemplate titleTemplate = new CommonTitleTemplate(this, getString(R.string.app_name));
        titleTemplate.mRightImg.setImageResource(R.mipmap.ic_add);
        titleTemplate.mRightImg.setVisibility(View.VISIBLE);
        titleTemplate.mRightImg.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingDetailsActivity.class);
            intent.putExtra("type", SettingDetailsActivity.TYPE_SETTING_ADD);
            startActivity(intent);
        });

        titleTemplate.mLeftImg.setImageResource(R.mipmap.ic_menu);
        titleTemplate.mLeftImg.setVisibility(View.VISIBLE);
        titleTemplate.mLeftImg.setVisibility(View.VISIBLE);
        titleTemplate.mLeftImg.setOnClickListener(view -> {
            if (mDrawerLayout.isDrawerOpen(mLeftMenu)) {
                mDrawerLayout.closeDrawer(mLeftMenu);
            } else {
                mDrawerLayout.openDrawer(mLeftMenu);
            }
        });

        return titleTemplate;
    }

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.open, R.string.close) {
            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.setClickable(true);
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mLeftMenu = findViewById(R.id.ll_menu_left);

        mConnectBtn = findViewById(R.id.iv_connect_btn);
//        mLogAction = (TextView) findViewById(R.id.tv_log_action);
//        mScrollLogAction = (NestedScrollView) findViewById(R.id.scroll_log_action);
        mRecyclerView = findViewById(R.id.scroll_log_action);

        if (N2NService.INSTANCE == null) {
            mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);
        } else {
            EdgeStatus.RunningStatus status = N2NService.INSTANCE.getCurrentStatus();
            if (status == EdgeStatus.RunningStatus.CONNECTED) {
                mConnectBtn.setImageResource(R.mipmap.ic_state_connect);
            } else if (status == EdgeStatus.RunningStatus.SUPERNODE_DISCONNECT) {
                mConnectBtn.setImageResource(R.mipmap.ic_state_supernode_diconnect);
            } else {
                mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);
            }
        }

        mConnectBtn.setOnClickListener(view -> {
            if (mCurrentSettingName.getText().equals(getResources().getString(R.string.no_setting))) {
                Toast.makeText(mContext, "no setting selected", Toast.LENGTH_SHORT).show();
                return;
            }

            EdgeStatus.RunningStatus status = N2NService.INSTANCE == null ? EdgeStatus.RunningStatus.DISCONNECT : N2NService.INSTANCE.getCurrentStatus();
            if (N2NService.INSTANCE != null && status != EdgeStatus.RunningStatus.DISCONNECT && status != EdgeStatus.RunningStatus.FAILED) {
                /* Asynchronous call */
                mConnectBtn.setClickable(false);
                mConnectBtn.setImageResource(R.mipmap.ic_state_connect_change);
                N2NService.INSTANCE.stop(null);
            } else {
                mConnectBtn.setClickable(false);
                mConnectBtn.setImageResource(R.mipmap.ic_state_connect_change);
                Intent vpnPrepareIntent = VpnService.prepare(MainActivity.this);
                if (vpnPrepareIntent != null) {
                    startActivityForResult(vpnPrepareIntent, REQUECT_CODE_VPN);
                } else {
                    onActivityResult(REQUECT_CODE_VPN, RESULT_OK, null);
                }
            }
        });

        mCurrentSettingItem = findViewById(R.id.rl_current_setting_item);
        mCurrentSettingItem.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ListActivity.class)));

        mCurrentSettingName = findViewById(R.id.tv_current_setting_name);
        mCurrentSettingName.setText(R.string.no_setting);

        mStartAtBoot = findViewById(R.id.check_box_start_at_boot);
        mUseProxy = findViewById(R.id.check_box_use_proxy);

        SharedPreferences n2nSp = getSharedPreferences("Hin2n", Context.MODE_PRIVATE);
        if (n2nSp.getBoolean("start_at_boot", false))
            mStartAtBoot.setChecked(true);

        mStartAtBoot.setOnClickListener(v -> {
            if (mStartAtBoot.isChecked()) {
                Intent vpnPrepareIntent = VpnService.prepare(MainActivity.this);
                if (vpnPrepareIntent != null) {
                    startActivityForResult(vpnPrepareIntent, REQUEST_CODE_VPN_FOR_START_AT_BOOT);
                    return;
                }
            }
            SharedPreferences n2nSp1 = getSharedPreferences("Hin2n", MODE_PRIVATE);
            n2nSp1.edit().putBoolean("start_at_boot", mStartAtBoot.isChecked()).apply();
        });

        mUseProxy.setChecked(settings.getProxyStatus());
        mUseProxy.setOnClickListener(v -> {
            settings.toggleProxy();
        });

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTermAdapter = new TermAdapter(null);
        mRecyclerView.setAdapter(mTermAdapter);
        mTermAdapter.setNewInstance(term);
        initLeftMenu();

        mHandler = new Handler(msg -> {
            switch (msg.what) {
                case 1:
                    boolean status = (boolean) msg.obj;
                    mUseProxy.setChecked(status);
                    break;
                default:
                    break;
            }

            return true;
        });

        settings.setMainHandler(mHandler);
        PermissionX.init(MainActivity.this).permissions(Manifest.permission.WRITE_SECURE_SETTINGS)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        settings.setHasProxySetPermission(true);
                    } else {
                        settings.setHasProxySetPermission(false);
                    }
                });
    }

    private void initLeftMenu() {
        TextView appVersion = findViewById(R.id.tv_app_version);
        appVersion.setText(N2nTools.getVersionName(this));

        RelativeLayout shareItem = findViewById(R.id.rl_share);
        shareItem.setOnClickListener(view -> {
            Logger.d("shareItem onClick~");

            if (Build.VERSION.SDK_INT >= 23) {
                PermissionX.init(this).permissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                        .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                ShareUtils.doOnClickShareItem(MainActivity.this);
                            } else {
                                Toast.makeText(MainActivity.this, "I NEED PERMISSIONS!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                ShareUtils.doOnClickShareItem(MainActivity.this);
            }
//            if (Build.VERSION.SDK_INT >= 23) {
//                AndPermission.with(MainActivity.this)
//                        .runtime()
//                        .permission(Permission.READ_EXTERNAL_STORAGE, Permission.ACCESS_FINE_LOCATION, Permission.READ_PHONE_STATE)
//                        .onGranted(data -> ShareUtils.doOnClickShareItem(MainActivity.this))
//                        .onDenied(data -> Toast.makeText(MainActivity.this, "I NEED PERMISSIONS!", Toast.LENGTH_SHORT).show()).start();
//            } else {
//                ShareUtils.doOnClickShareItem(MainActivity.this);
//            }

        });
        shareItem.setVisibility(View.GONE);     // @TODO 暂时不显示

        RelativeLayout contactItem = findViewById(R.id.rl_contact);
        contactItem.setOnClickListener(view -> ShareUtils.joinQQGroup(MainActivity.this));

        RelativeLayout feedbackItem = findViewById(R.id.rl_feedback);
        feedbackItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.WEB_VIEW_TYPE, WebViewActivity.TYPE_WEB_VIEW_FEEDBACK);
            startActivity(intent);
        });

        RelativeLayout checkUpdateItem = findViewById(R.id.rl_check_update);
        checkUpdateItem.setOnClickListener(view -> {
//                Beta.checkUpgrade();
        });

        RelativeLayout aboutItem = findViewById(R.id.rl_about);
        aboutItem.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.WEB_VIEW_TYPE, WebViewActivity.TYPE_WEB_VIEW_ABOUT);
            startActivity(intent);
        });
    }


    @Override
    protected int getContentLayout() {
        Configuration mConfiguration = getResources().getConfiguration();
        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return R.layout.activity_main_land;
        }
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUECT_CODE_VPN && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, N2NService.class);
            Bundle bundle = new Bundle();
            N2NSettingInfo n2NSettingInfo = new N2NSettingInfo(mCurrentSettingInfo);
            bundle.putParcelable("n2nSettingInfo", n2NSettingInfo);
            intent.putExtra("Setting", bundle);

            startService(intent);
        } else if (requestCode == REQUEST_CODE_VPN_FOR_START_AT_BOOT) {
            mStartAtBoot = findViewById(R.id.check_box_start_at_boot);
            if (mStartAtBoot.isChecked()) {
                if (resultCode == RESULT_OK) {
                    SharedPreferences n2nSp = getSharedPreferences("Hin2n", MODE_PRIVATE);
                    n2nSp.edit().putBoolean("start_at_boot", true).apply();
                } else {
                    mStartAtBoot.setChecked(false);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences n2nSp = getSharedPreferences("Hin2n", MODE_PRIVATE);
        logTxtPath = n2nSp.getString("current_log_path", "");
        mTermAdapter.getData().clear();
        mTermAdapter.notifyDataSetChanged();
        showLog(true);

        Long currentSettingId = n2nSp.getLong("current_setting_id", -1);
        if (currentSettingId != -1) {
            mCurrentSettingInfo = Hin2nApplication.getInstance().getDaoSession().getN2NSettingModelDao().load(currentSettingId);
            if (mCurrentSettingInfo != null) {
                mCurrentSettingName.setText(mCurrentSettingInfo.getName());
                mStartAtBoot = findViewById(R.id.check_box_start_at_boot);
                mStartAtBoot.setClickable(true);
            } else {
                mCurrentSettingName.setText(R.string.no_setting);
                mStartAtBoot = findViewById(R.id.check_box_start_at_boot);
                mStartAtBoot.setClickable(false);
                mStartAtBoot.setChecked(false);
            }

            mConnectBtn.setVisibility(View.VISIBLE);
            if (N2NService.INSTANCE == null) {
                mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);
            } else {
                EdgeStatus.RunningStatus status = N2NService.INSTANCE.getCurrentStatus();
                if (status == EdgeStatus.RunningStatus.CONNECTED) {
                    mConnectBtn.setImageResource(R.mipmap.ic_state_connect);
                } else if (status == EdgeStatus.RunningStatus.SUPERNODE_DISCONNECT) {
                    mConnectBtn.setImageResource(R.mipmap.ic_state_supernode_diconnect);
                } else {
                    mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);
                }
            }
        } else {
            mStartAtBoot = findViewById(R.id.check_box_start_at_boot);
            mStartAtBoot.setClickable(false);
            mStartAtBoot.setChecked(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences n2nSp = getSharedPreferences("Hin2n", MODE_PRIVATE);
        n2nSp.edit().putString("current_log_path", logTxtPath).apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showLog(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }


//        Log.d(TAG,"I'm destroyed");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(StartEvent event) {
        mTermAdapter.getData().clear();
        mTermAdapter.notifyDataSetChanged();
        showLog(true);
        mConnectBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConnectBtn.setImageResource(R.mipmap.ic_state_connect);
                mConnectBtn.setClickable(true);

            }
        }, 400);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopEvent(StopEvent event) {
        showLog(false);
        mConnectBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);
                mConnectBtn.setClickable(true);
            }
        }, 200);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent event) {
        showLog(false);
        mConnectBtn.setVisibility(View.VISIBLE);
        mConnectBtn.setImageResource(R.mipmap.ic_state_disconnect);

        Toast.makeText(mContext, getString(R.string.toast_connect_failed), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectingEvent(ConnectingEvent event) {
        mConnectBtn.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSupernodeDisconnectEvent(SupernodeDisconnectEvent event) {
        mConnectBtn.setVisibility(View.VISIBLE);
        mConnectBtn.setImageResource(R.mipmap.ic_state_supernode_diconnect);
        Toast.makeText(mContext, getString(R.string.toast_disconnect_and_retry), Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogChangeEvent(final LogChangeEvent event) {
        logTxtPath = event.getTxtPath();
//        showLog(true);
    }

    private void showLog(boolean showLog) {
        ThreadUtils.cachedThreadExecutor(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(logTxtPath)) {
                    IOUtils.readTxtLimits(showLog, logTxtPath, 1024 * 5, mTermAdapter);
                }
            }
        });
    }
}
