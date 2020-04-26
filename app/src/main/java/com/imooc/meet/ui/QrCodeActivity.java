package com.imooc.meet.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.meet.R;
import com.liuguilin.framework.base.BaseUIActivity;
import com.liuguilin.framework.helper.FileHelper;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * FileName: QrCodeActivity
 * Founder: LiuGuiLin
 * Profile: 二维码扫描界面
 */
public class QrCodeActivity extends BaseUIActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE = 1234;

    private CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };

    //返回键
    private ImageView iv_back;
    //相册选择
    private TextView iv_to_ablum;
    //闪光灯
    private ImageView iv_flashlight;

    //是否打开闪光灯
    private boolean isOpenLight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initQrCode();
        initView();
    }

    /**
     * 初始化二维码
     */
    private void initQrCode() {
        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_qrcode);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 初始化View
     */
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_to_ablum = (TextView) findViewById(R.id.iv_to_ablum);
        iv_flashlight = (ImageView) findViewById(R.id.iv_flashlight);

        iv_back.setOnClickListener(this);
        iv_to_ablum.setOnClickListener(this);
        iv_flashlight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_to_ablum:
                openAblum();
                break;
            case R.id.iv_flashlight:
                try {
                    isOpenLight = !isOpenLight;
                    CodeUtils.isLightEnable(isOpenLight);
                    iv_flashlight.setImageResource(isOpenLight ? R.drawable.img_flashlight_p : R.drawable.img_flashlight);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 打开相册
     */
    private void openAblum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                String path = FileHelper.getInstance()
                        .getRealPathFromURI(QrCodeActivity.this, uri);
                try {
                    CodeUtils.analyzeBitmap(path, new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            analyzeCallback.onAnalyzeSuccess(mBitmap, result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            analyzeCallback.onAnalyzeFailed();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
