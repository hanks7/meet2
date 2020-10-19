package com.imooc.meet.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.meet.MainActivity;
import com.imooc.meet.R;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.helper.ActivityHelper;
import com.liuguilin.framework.manager.KeyWordManager;
import com.liuguilin.framework.utils.LogUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * FileName: TestLoginActivity
 * Founder: LiuGuiLin
 * Profile: 测试登录页面
 * <p>
 * 支持账号 密码登录
 */
public class TestLoginActivity extends BaseBackActivity implements View.OnClickListener {

    private Button btn_login;
    String strPhone;

    private TextView tv_user_1;
    private TextView tv_user_2;
    private TextView tv_user_3;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_login;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);
        tv_user_1 = findViewById(R.id.user_1);
        tv_user_2 = findViewById(R.id.user_2);
        tv_user_3 = findViewById(R.id.user_3);

        btn_login.setOnClickListener(this);
        tv_user_1.setOnClickListener(this);
        tv_user_2.setOnClickListener(this);
        tv_user_3.setOnClickListener(this);

        strPhone = tv_user_1.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:


                BmobManager.getInstance().loginByAccount(strPhone, "123456", new SaveListener<IMUser>() {
                    @Override
                    public void done(IMUser imUser, BmobException e) {
                        KeyWordManager.getInstance().hideKeyWord(TestLoginActivity.this);
                        if (e == null) {
                            //登陆成功
                            startActivity(new Intent(
                                    TestLoginActivity.this, MainActivity.class));
                            ActivityHelper.getInstance().exit();
                            finish();
                        } else {
                            LogUtils.e("Login Error:" + e.toString());
                            if (e.getErrorCode() == 101) {
                                Toast.makeText(TestLoginActivity.this, getString(R.string.text_test_login_fail), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.user_1:
                strPhone = tv_user_1.getText().toString();
                Toast.makeText(this, strPhone, Toast.LENGTH_LONG).show();
                break;
            case R.id.user_2:
                strPhone = tv_user_2.getText().toString();
                Toast.makeText(this, strPhone, Toast.LENGTH_LONG).show();
                break;
            case R.id.user_3:
                strPhone = tv_user_3.getText().toString();
                Toast.makeText(this, strPhone, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
