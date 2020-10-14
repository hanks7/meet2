package com.imooc.meet.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imooc.meet.R;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.helper.FileHelper;
import com.liuguilin.framework.helper.GlideHelper;
import com.liuguilin.framework.manager.DialogManager;
import com.liuguilin.framework.utils.LogUtils;
import com.liuguilin.framework.view.DialogView;
import com.liuguilin.framework.view.LodingView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * FileName: MeInfoActivity
 * Founder: LiuGuiLin
 * Profile: 我的信息
 */
public class MeInfoActivity extends BaseBackActivity implements View.OnClickListener {

    //基本信息
    private CircleImageView iv_user_photo;
    private EditText et_nickname;
    private TextView tv_user_sex;
    private LinearLayout ll_user_sex;
    private TextView tv_user_age;
    private LinearLayout ll_user_age;
    private EditText et_user_desc;
    private TextView tv_user_birthday;
    private LinearLayout ll_user_birthday;
    private TextView tv_user_constellation;
    private LinearLayout ll_user_constellation;
    private TextView tv_user_hobby;
    private LinearLayout ll_user_hobby;
    private TextView tv_user_status;
    private LinearLayout ll_user_status;
    private RelativeLayout ll_photo;

    //头像选择框
    private DialogView mPhotoDialog;
    private TextView tv_camera;
    private TextView tv_ablum;
    private TextView tv_photo_cancel;

    //性别选择框
    private DialogView mSexDialog;
    private TextView tv_boy;
    private TextView tv_girl;
    private TextView tv_sex_cancel;

    //年龄选择框
    private DialogView mAgeDialog;
    private RecyclerView mAgeView;
    private TextView tv_age_cancel;
    private CommonAdapter<Integer> mAgeAdapter;
    private List<Integer> mAgeList = new ArrayList<>();

    //生日选择框
    private DialogView mBirthdayDialog;
    private DatePicker mDatePicker;

    //星座选择框
    private DialogView mConstellationDialog;
    private RecyclerView mConstellationnView;
    private TextView tv_constellation_cancel;
    private CommonAdapter<String> mConstellationAdapter;
    private List<String> mConstellationList = new ArrayList<>();

    //状态选择框
    private DialogView mStatusDialog;
    private RecyclerView mStatusView;
    private TextView tv_status_cancel;
    private CommonAdapter<String> mStatusAdapter;
    private List<String> mStatusList = new ArrayList<>();

    //爱好选择框
    private DialogView mHobbyDialog;
    private RecyclerView mHobbyView;
    private TextView tv_hobby_cancel;
    private CommonAdapter<String> mHobbyAdapter;
    private List<String> mHobbyList = new ArrayList<>();

    //头像文件
    private File uploadPhotoFile;

    //加载View
    private LodingView mLodingView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_info;
    }

    @Override
    protected void init() {
        initView();

        initPhotoDialog();
        initSexDialog();
        initAgeDialog();
        initBirthdayDialog();
        initConstellationDialog();
        initHobbyDialog();
        initStatusDialog();
    }
    /**
     * 状态选择
     */
    private void initStatusDialog() {

        String[] sArray = getResources().getStringArray(R.array.StatusArray);
        for (int i = 0; i < sArray.length; i++) {
            mStatusList.add(sArray[i]);
        }

        mStatusDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_constellation, Gravity.BOTTOM);
        mStatusView = mStatusDialog.findViewById(R.id.mConstellationnView);
        tv_status_cancel = mStatusDialog.findViewById(R.id.tv_cancel);

        mStatusView.setLayoutManager(new LinearLayoutManager(this));
        mStatusView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mStatusAdapter = new CommonAdapter<>(mStatusList, new CommonAdapter.OnBindDataListener<String>() {

            @Override
            public void onBindViewHolder(final String model, CommonViewHolder hodler, int type, int position) {
                hodler.setText(R.id.tv_age_text, model);

                hodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().hide(mStatusDialog);
                        tv_user_status.setText(model);
                    }
                });
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_me_age_item;
            }
        });
        mStatusView.setAdapter(mStatusAdapter);

        tv_status_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mStatusDialog);
            }
        });
    }

    /**
     * 爱好选择
     */
    private void initHobbyDialog() {

        String[] hArray = getResources().getStringArray(R.array.HobbyArray);
        for (int i = 0; i < hArray.length; i++) {
            mHobbyList.add(hArray[i]);
        }

        mHobbyDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_constellation, Gravity.BOTTOM);
        mHobbyView = mHobbyDialog.findViewById(R.id.mConstellationnView);
        tv_hobby_cancel = mHobbyDialog.findViewById(R.id.tv_cancel);

        mHobbyView.setLayoutManager(new GridLayoutManager(this, 4));
        mHobbyView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mHobbyView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mHobbyAdapter = new CommonAdapter<>(mHobbyList, new CommonAdapter.OnBindDataListener<String>() {
            @Override
            public void onBindViewHolder(final String model, CommonViewHolder hodler, int type, int position) {
                hodler.setText(R.id.tv_age_text, model);

                hodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().hide(mHobbyDialog);
                        tv_user_hobby.setText(model);
                    }
                });
            }

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_me_age_item;
            }
        });
        mHobbyView.setAdapter(mHobbyAdapter);

        tv_hobby_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mHobbyDialog);
            }
        });
    }

    /**
     * 星座选择
     */
    private void initConstellationDialog() {

        String[] cArray = getResources().getStringArray(R.array.ConstellatioArray);
        for (int i = 0; i < cArray.length; i++) {
            mConstellationList.add(cArray[i]);
        }

        mConstellationDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_constellation, Gravity.BOTTOM);
        mConstellationnView = mConstellationDialog.findViewById(R.id.mConstellationnView);
        tv_constellation_cancel = mConstellationDialog.findViewById(R.id.tv_cancel);

        mConstellationnView.setLayoutManager(new GridLayoutManager(this, 4));
        mConstellationnView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mConstellationnView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mConstellationAdapter = new CommonAdapter<>(mConstellationList, new CommonAdapter.OnBindDataListener<String>() {
            @Override
            public void onBindViewHolder(final String model, CommonViewHolder hodler, int type, int position) {
                hodler.setText(R.id.tv_age_text, model);

                hodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().hide(mConstellationDialog);
                        tv_user_constellation.setText(model);
                    }
                });
            }

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_me_age_item;
            }
        });
        mConstellationnView.setAdapter(mConstellationAdapter);

        tv_constellation_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mConstellationDialog);
            }
        });
    }

    /**
     * 生日选择
     */
    private void initBirthdayDialog() {

        //自定义主题
        //DPTManager.getInstance().initCalendar(new DatePickerTheme());

        mBirthdayDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_birthday, Gravity.BOTTOM);
        mDatePicker = mBirthdayDialog.findViewById(R.id.mDatePicker);
        //设置默认时间
        mDatePicker.setDate(1995, 5);
        //设置选择模式：单选
        mDatePicker.setMode(DPMode.SINGLE);
        mDatePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {

            @Override
            public void onDatePicked(String date) {
                DialogManager.getInstance().hide(mBirthdayDialog);
                tv_user_birthday.setText(date);
            }
        });
    }

    /**
     * 年龄选择
     */
    private void initAgeDialog() {

        for (int i = 0; i < 100; i++) {
            mAgeList.add(i);
        }

        mAgeDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_age, Gravity.BOTTOM);
        mAgeView = (RecyclerView) mAgeDialog.findViewById(R.id.mAgeView);
        tv_age_cancel = (TextView) mAgeDialog.findViewById(R.id.tv_cancel);

        mAgeView.setLayoutManager(new LinearLayoutManager(this));
        mAgeView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAgeAdapter = new CommonAdapter<>(mAgeList, new CommonAdapter.OnBindDataListener<Integer>() {
            @Override
            public void onBindViewHolder(final Integer model, CommonViewHolder hodler, int type, int position) {
                hodler.setText(R.id.tv_age_text, model + "");

                hodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().hide(mAgeDialog);
                        tv_user_age.setText(model + "");
                    }
                });
            }

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_me_age_item;
            }
        });
        mAgeView.setAdapter(mAgeAdapter);

        tv_age_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mAgeDialog);
            }
        });
    }

    /**
     * 头像选择
     */
    private void initPhotoDialog() {
        mPhotoDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_photo, Gravity.BOTTOM);

        tv_camera = (TextView) mPhotoDialog.findViewById(R.id.tv_camera);
        tv_ablum = (TextView) mPhotoDialog.findViewById(R.id.tv_ablum);
        tv_photo_cancel = (TextView) mPhotoDialog.findViewById(R.id.tv_cancel);

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mPhotoDialog);
                FileHelper.getInstance().toCamera(MeInfoActivity.this);
            }
        });
        tv_ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mPhotoDialog);
                FileHelper.getInstance().toAlbum(MeInfoActivity.this);
            }
        });
        tv_photo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mPhotoDialog);
            }
        });
    }

    /**
     * 性别选择
     */
    private void initSexDialog() {
        mSexDialog = DialogManager.getInstance().initView(this, R.layout.dialog_select_sex, Gravity.BOTTOM);
        tv_boy = (TextView) mSexDialog.findViewById(R.id.tv_boy);
        tv_girl = (TextView) mSexDialog.findViewById(R.id.tv_girl);
        tv_sex_cancel = (TextView) mSexDialog.findViewById(R.id.tv_cancel);

        tv_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mSexDialog);
                tv_user_sex.setText(getString(R.string.text_me_info_boy));
            }
        });
        tv_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mSexDialog);
                tv_user_sex.setText(getString(R.string.text_me_info_girl));
            }
        });
        tv_sex_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().hide(mSexDialog);
            }
        });
    }

    private void initView() {

        mLodingView = new LodingView(this);

        iv_user_photo = (CircleImageView) findViewById(R.id.iv_user_photo);

        et_nickname = (EditText) findViewById(R.id.et_nickname);

        tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
        ll_user_sex = (LinearLayout) findViewById(R.id.ll_user_sex);

        tv_user_age = (TextView) findViewById(R.id.tv_user_age);
        ll_user_age = (LinearLayout) findViewById(R.id.ll_user_age);

        et_user_desc = (EditText) findViewById(R.id.et_user_desc);

        tv_user_birthday = (TextView) findViewById(R.id.tv_user_birthday);
        ll_user_birthday = (LinearLayout) findViewById(R.id.ll_user_birthday);

        tv_user_constellation = (TextView) findViewById(R.id.tv_user_constellation);
        ll_user_constellation = (LinearLayout) findViewById(R.id.ll_user_constellation);

        tv_user_hobby = (TextView) findViewById(R.id.tv_user_hobby);
        ll_user_hobby = (LinearLayout) findViewById(R.id.ll_user_hobby);

        tv_user_status = (TextView) findViewById(R.id.tv_user_status);
        ll_user_status = (LinearLayout) findViewById(R.id.ll_user_status);

        ll_photo = (RelativeLayout) findViewById(R.id.ll_photo);

        iv_user_photo.setOnClickListener(this);
        ll_user_sex.setOnClickListener(this);
        ll_user_age.setOnClickListener(this);
        ll_user_birthday.setOnClickListener(this);
        ll_user_constellation.setOnClickListener(this);
        ll_user_hobby.setOnClickListener(this);
        ll_user_status.setOnClickListener(this);
        ll_photo.setOnClickListener(this);

        loadUserInfo();
    }

    /**
     * 加载用户信息
     */
    private void loadUserInfo() {
        IMUser imUser = BmobManager.getInstance().getUser();

        GlideHelper.loadUrl(this, imUser.getPhoto(), iv_user_photo);

        et_nickname.setText(imUser.getNickName());

        tv_user_sex.setText(imUser.isSex() ? getString(R.string.text_me_info_boy) : getString(R.string.text_me_info_girl));
        tv_user_age.setText(imUser.getAge() + "");
        et_user_desc.setText(imUser.getDesc());

        tv_user_birthday.setText(imUser.getBirthday());
        tv_user_constellation.setText(imUser.getConstellation());
        tv_user_hobby.setText(imUser.getHobby());
        tv_user_status.setText(imUser.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.me_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            final IMUser imUser = BmobManager.getInstance().getUser();
            mLodingView.show(getString(R.string.text_me_info_update));
            if (uploadPhotoFile != null) {
                final BmobFile file = new BmobFile(uploadPhotoFile);
                file.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            imUser.setPhoto(file.getFileUrl());
                            updateUser(imUser);
                        }
                    }
                });
            } else {
                updateUser(imUser);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新用户信息
     *
     * @param imUser
     */
    private void updateUser(IMUser imUser) {
        //名称不能为空
        String nickName = et_nickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, getString(R.string.text_update_nickname_null), Toast.LENGTH_SHORT).show();
            mLodingView.hide();
            return;
        }

        String desc = et_user_desc.getText().toString().trim();
        String sex = tv_user_sex.getText().toString();
        String age = tv_user_age.getText().toString();
        String birthday = tv_user_birthday.getText().toString();
        String constellation = tv_user_constellation.getText().toString();
        String hobby = tv_user_hobby.getText().toString();
        String status = tv_user_status.getText().toString();

        imUser.setNickName(nickName);
        imUser.setDesc(desc);
        imUser.setSex(sex.equals(getString(R.string.text_me_info_boy)) ? true : false);
        imUser.setAge(Integer.parseInt(age));
        imUser.setBirthday(birthday);
        imUser.setConstellation(constellation);
        imUser.setHobby(hobby);
        imUser.setStatus(status);
        imUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                mLodingView.hide();
                if (e == null) {
                    //同步缓存
                    BmobManager.getInstance().fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                EventManager.post(EventManager.EVENT_REFRE_ME_INFO);
                                finish();
                            } else {
                                Toast.makeText(MeInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MeInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_photo:
            case R.id.ll_photo:
                DialogManager.getInstance().show(mPhotoDialog);
                break;
            case R.id.ll_user_sex:
                DialogManager.getInstance().show(mSexDialog);
                break;
            case R.id.ll_user_age:
                DialogManager.getInstance().show(mAgeDialog);
                break;
            case R.id.ll_user_birthday:
                DialogManager.getInstance().show(mBirthdayDialog);
                break;
            case R.id.ll_user_constellation:
                DialogManager.getInstance().show(mConstellationDialog);
                break;
            case R.id.ll_user_hobby:
                DialogManager.getInstance().show(mHobbyDialog);
                break;
            case R.id.ll_user_status:
                DialogManager.getInstance().show(mStatusDialog);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
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
                        uploadPhotoFile = new File(path);
                        try {
                            FileHelper.getInstance().startPhotoZoom(this, uploadPhotoFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (requestCode == FileHelper.CAMERA_CROP_RESULT) {
                uploadPhotoFile = new File(FileHelper.getInstance().getCropPath());
                LogUtils.i("uploadPhotoFile:" + uploadPhotoFile.getPath());
            }
            if (uploadPhotoFile != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(uploadPhotoFile.getPath());
                iv_user_photo.setImageBitmap(bitmap);
            }
        }
    }
}
