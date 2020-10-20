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
import com.liuguilin.framework.entity.Constants;
import com.liuguilin.framework.helper.ActivityHelper;
import com.liuguilin.framework.manager.KeyWordManager;
import com.liuguilin.framework.utils.LogUtils;
import com.liuguilin.framework.utils.SpUtils;

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
    private TextView tvSelectedAccount;


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
        tvSelectedAccount = findViewById(R.id.ac_test_login_tv_selected_account);

        btn_login.setOnClickListener(this);
        tv_user_1.setOnClickListener(this);
        tv_user_2.setOnClickListener(this);

        strPhone = tv_user_1.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                showDialog();
                BmobManager.getInstance().loginByAccount(strPhone, "123456", new SaveListener<IMUser>() {
                    @Override
                    public void done(IMUser imUser, BmobException e) {
                        dismissDialog();
                        KeyWordManager.getInstance().hideKeyWord(TestLoginActivity.this);
                        if (e == null) {
                            //登陆成功
                            startActivity(new Intent(
                                    TestLoginActivity.this, MainActivity.class));
                            //把手机号码保存下来
                            SpUtils.getInstance().putString(Constants.SP_PHONE, strPhone);
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
                tvSelectedAccount.setText("故人何必念情-17983964401\n测试密码:123456");
                break;
            case R.id.user_2:
                strPhone = tv_user_2.getText().toString();
                tvSelectedAccount.setText("没感情就绝交吧-17977964407\n测试密码:123456");
                break;

        }
    }
}
