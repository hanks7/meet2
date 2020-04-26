package com.imooc.meet.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.imooc.meet.R;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.helper.FileHelper;
import com.liuguilin.framework.manager.DialogManager;
import com.liuguilin.framework.utils.LogUtils;
import com.liuguilin.framework.view.DialogView;
import com.liuguilin.framework.view.LodingView;

import java.io.File;

import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * FileName: FirstUploadActivity
 * Founder: LiuGuiLin
 * Profile: 头像上传
 */
public class FirstUploadActivity extends BaseBackActivity implements View.OnClickListener {

    /**
     * 跳转
     *
     * @param mActivity
     */
    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, FirstUploadActivity.class);
        mActivity.startActivity(intent);
    }

    private File uploadFile = null;

    private TextView tv_camera;
    private TextView tv_ablum;
    private TextView tv_cancel;

    //圆形头像
    private CircleImageView iv_photo;
    private EditText et_nickname;
    private Button btn_upload;

    private LodingView mLodingView;
    private DialogView mPhotoSelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_upload);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        initPhotoView();

        iv_photo = (CircleImageView) findViewById(R.id.iv_photo);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        btn_upload = (Button) findViewById(R.id.btn_upload);

        iv_photo.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        btn_upload.setEnabled(false);

        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    btn_upload.setEnabled(uploadFile != null);
                } else {
                    btn_upload.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 初始化选择框
     */
    private void initPhotoView() {

        mLodingView = new LodingView(this);
        mLodingView.setLodingText(getString(R.string.text_upload_photo_loding));

        mPhotoSelectView = DialogManager.getInstance()
                .initView(this, R.layout.dialog_select_photo, Gravity.BOTTOM);

        tv_camera = (TextView) mPhotoSelectView.findViewById(R.id.tv_camera);
        tv_camera.setOnClickListener(this);
        tv_ablum = (TextView) mPhotoSelectView.findViewById(R.id.tv_ablum);
        tv_ablum.setOnClickListener(this);
        tv_cancel = (TextView) mPhotoSelectView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                DialogManager.getInstance().hide(mPhotoSelectView);
                if (!checkPermissions(Manifest.permission.CAMERA)) {
                    requestPermission(new String[]{Manifest.permission.CAMERA});
                } else {
                    //跳转到相机
                    FileHelper.getInstance().toCamera(this);
                }
                break;
            case R.id.tv_ablum:
                DialogManager.getInstance().hide(mPhotoSelectView);
                //跳转到相册
                FileHelper.getInstance().toAlbum(this);
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance().hide(mPhotoSelectView);
                break;
            case R.id.iv_photo:
                //显示选择提示框
                DialogManager.getInstance().show(mPhotoSelectView);
                break;
            case R.id.btn_upload:
                uploadPhoto();
                break;
        }
    }

    /**
     * 上传头像
     */
    private void uploadPhoto() {
        //如果条件没有满足，是走不到这里的
        String nickName = et_nickname.getText().toString().trim();
        mLodingView.show();
        BmobManager.getInstance().uploadFirstPhoto(nickName, uploadFile, new BmobManager.OnUploadPhotoListener() {
            @Override
            public void OnUpdateDone() {
                mLodingView.hide();
                EventManager.post(EventManager.EVENT_REFRE_TOKEN_STATUS);
                finish();
            }

            @Override
            public void OnUpdateFail(BmobException e) {
                mLodingView.hide();
                Toast.makeText(FirstUploadActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.i("requestCode:" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FileHelper.CAMEAR_REQUEST_CODE) {
                try {
                    FileHelper.getInstance().startPhotoZoom(this, FileHelper.getInstance().getTempFile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == FileHelper.ALBUM_REQUEST_CODE) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = FileHelper.getInstance().getRealPathFromURI(this, uri);
                    if (!TextUtils.isEmpty(path)) {
                        uploadFile = new File(path);
                        try {
                            FileHelper.getInstance().startPhotoZoom(this, uploadFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (requestCode == FileHelper.CAMERA_CROP_RESULT) {
                LogUtils.i("CAMERA_CROP_RESULT");
                uploadFile = new File(FileHelper.getInstance().getCropPath());
                LogUtils.i("uploadPhotoFile:" + uploadFile.getPath());
            }
            //设置头像
            if (uploadFile != null) {
                Bitmap mBitmap = BitmapFactory.decodeFile(uploadFile.getPath());
                iv_photo.setImageBitmap(mBitmap);

                //判断当前的输入框
                String nickName = et_nickname.getText().toString().trim();
                btn_upload.setEnabled(!TextUtils.isEmpty(nickName));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
