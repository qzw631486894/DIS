package com.kongrongqi.shopmall.modules.me;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.AccountScanActivity;
import com.kongrongqi.shopmall.modules.account.ReplaceIMEIActivity;
import com.kongrongqi.shopmall.modules.me.IView.IChangeDevicesView;
import com.kongrongqi.shopmall.modules.me.presenter.ChangeDevicesPresenter;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 创建日期：2017/5/20 0020 on 17:26
 * 作者:penny
 */
public class ChangeDevicesActivity extends BaseMVPActivity<ChangeDevicesPresenter> implements IChangeDevicesView ,EasyPermissions.PermissionCallbacks{

    private EditText machineCode_guzhang;
    private ImageView scan1;
    private Button replace_enter;

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 2;
    public static int SCAN= 10001;



    public static void lunch(Context con) {
        Intent intent = new Intent(con, ChangeDevicesActivity.class);
        con.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_change_devices;
    }

    @Override
    protected ChangeDevicesPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ChangeDevicesPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }
    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText("替换设备");
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {

        machineCode_guzhang = (EditText) findViewById(R.id.machineCode_guzhang);
        scan1 = (ImageView) findViewById(R.id.scan1);
        RelativeLayout rl_scan = (RelativeLayout) findViewById(R.id.rl_scan);
        LinearLayout account_hint = (LinearLayout) findViewById(R.id.account_hint);
        account_hint.setVisibility(View.GONE);

        replace_enter = (Button) findViewById(R.id.replace_enter);
        replace_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = machineCode_guzhang.getText().toString();
                if(!TextUtils.isEmpty(num))
                    getPresenter().getCheckMachineCode(num);

            }
        });

        rl_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeDevicesActivity.this,MeIMEIScanActivity.class);
                startActivityForResult(intent,SCAN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode==SCAN){ //替换设备返回
            String machineCode = data.getStringExtra(ReplaceIMEIActivity.MACHINE_CODE);
            this.machineCode_guzhang.setText(machineCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @Override
    public void onChildKeyBack() {
        this.finish();
    }
}
