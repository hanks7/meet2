package com.imooc.meet.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imooc.meet.R;
import com.imooc.meet.model.AddFriendModel;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.manager.KeyWordManager;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * FileName: AddFriendActivity
 * Founder: LiuGuiLin
 * Profile: 添加好友
 */
public class AddFriendActivity extends BaseBackActivity implements View.OnClickListener {

    //标题
    public static final int TYPE_TITLE = 0;
    //内容
    public static final int TYPE_CONTENT = 1;

    /**
     * 1.模拟用户数据
     * 2.根据条件查询
     * 3.推荐好友
     */

    private LinearLayout ll_to_contact;
    private EditText et_phone;
    private ImageView iv_search;
    private RecyclerView mRecyclerView;

    private View include_empty_view;

    private CommonAdapter mAddFriendAdapter;
    private List<AddFriendModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        include_empty_view = findViewById(R.id.include_empty_view);

        ll_to_contact = (LinearLayout) findViewById(R.id.ll_to_contact);

        et_phone = (EditText) findViewById(R.id.et_phone);
        iv_search = (ImageView) findViewById(R.id.iv_search);

        mRecyclerView = (RecyclerView) findViewById(R.id.mSearchResultView);

        ll_to_contact.setOnClickListener(this);
        iv_search.setOnClickListener(this);

        //列表的实现
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAddFriendAdapter = new CommonAdapter<>(mList, new CommonAdapter.OnMoreBindDataListener<AddFriendModel>() {
            @Override
            public int getItemType(int position) {
                return mList.get(position).getType();
            }

            @Override
            public void onBindViewHolder(final AddFriendModel model, CommonViewHolder viewHolder, int type, int position) {
                if (type == TYPE_TITLE) {
                    viewHolder.setText(R.id.tv_title, model.getTitle());
                } else if (type == TYPE_CONTENT) {
                    //设置头像
                    viewHolder.setImageUrl(AddFriendActivity.this, R.id.iv_photo, model.getPhoto());
                    //设置性别
                    viewHolder.setImageResource(R.id.iv_sex,
                            model.isSex() ? R.drawable.img_boy_icon : R.drawable.img_girl_icon);
                    //设置昵称
                    viewHolder.setText(R.id.tv_nickname, model.getNickName());
                    //年龄
                    viewHolder.setText(R.id.tv_age, model.getAge() + getString(R.string.text_search_age));
                    //设置描述
                    viewHolder.setText(R.id.tv_desc, model.getDesc());

                    //点击事件
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UserInfoActivity.startActivity(AddFriendActivity.this, model.getUserId());
                        }
                    });
                }
            }

            @Override
            public int getLayoutId(int type) {
                if (type == TYPE_TITLE) {
                    return R.layout.layout_search_title_item;
                } else if (type == TYPE_CONTENT) {
                    return R.layout.layout_search_user_item;
                }
                return 0;
            }
        });

        mRecyclerView.setAdapter(mAddFriendAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转到从通讯录导入
            case R.id.ll_to_contact:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //处理权限
                    if (checkPermissions(Manifest.permission.READ_CONTACTS)) {
                        startActivity(new Intent(this, ContactFirendActivity.class));
                    } else {
                        requestPermission(new String[]{Manifest.permission.READ_CONTACTS});
                    }
                } else {
                    startActivity(new Intent(this, ContactFirendActivity.class));
                }
                break;
            case R.id.iv_search:
                queryPhoneUser();
                break;
        }
    }

    /**
     * 通过电话号码查询
     */
    private void queryPhoneUser() {
        //1.获取电话号码
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //2.过滤自己
        String phoneNumber = BmobManager.getInstance().getUser().getMobilePhoneNumber();
        LogUtils.i("phoneNumber:" , phoneNumber);
        if (phone.equals(phoneNumber)) {
            Toast.makeText(this, getString(R.string.text_add_friend_no_me), Toast.LENGTH_SHORT).show();
            return;
        }

        //3.查询
        BmobManager.getInstance().queryPhoneUser(phone, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                KeyWordManager.getInstance().hideKeyWord(AddFriendActivity.this);
                if (e != null) {
                    return;
                }
                if (CommonUtils.isEmpty(list)) {
                    IMUser imUser = list.get(0);
                    include_empty_view.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    //每次你查询有数据的话则清空
                    mList.clear();

                    addTitle(getString(R.string.text_add_friend_title));
                    addContent(imUser);
                    mAddFriendAdapter.notifyDataSetChanged();

                    //推荐
                    pushUser(phone);
                } else {
                    //显示空数据
                    include_empty_view.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 推荐好友
     *
     * @param phone 过滤所查询的电话号码
     */
    private void pushUser(String phone) {
        //查询所有的好友 取100个
        BmobManager.getInstance().queryAllUser(new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        addTitle(getString(R.string.text_add_friend_content));
                        int num = (list.size() <= 100) ? list.size() : 100;
                        for (int i = 0; i < num; i++) {
                            //也不能自己推荐给自己
                            String phoneNumber = BmobManager.getInstance().getUser().getMobilePhoneNumber();
                            if (list.get(i).getMobilePhoneNumber().equals(phoneNumber)) {
                                //跳过本次循环
                                continue;
                            }
                            //也不能查询到所查找的好友
                            if (list.get(i).getMobilePhoneNumber().equals(phone)) {
                                //跳过本次循环
                                continue;
                            }

                            addContent(list.get(i));
                        }
                        mAddFriendAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 添加头部
     *
     * @param title
     */
    private void addTitle(String title) {
        AddFriendModel model = new AddFriendModel();
        model.setType(TYPE_TITLE);
        model.setTitle(title);
        mList.add(model);
    }

    /**
     * 添加内容
     *
     * @param imUser
     */
    private void addContent(IMUser imUser) {
        AddFriendModel model = new AddFriendModel();
        model.setType(TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.isSex());
        model.setAge(imUser.getAge());
        model.setNickName(imUser.getNickName());
        model.setDesc(imUser.getDesc());
        mList.add(model);
    }
}
