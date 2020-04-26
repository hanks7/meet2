package com.imooc.meet.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imooc.meet.R;
import com.imooc.meet.model.UserInfoModel;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseUIActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.Friend;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.cloud.CloudManager;
import com.liuguilin.framework.entity.Constants;
import com.liuguilin.framework.helper.GlideHelper;
import com.liuguilin.framework.manager.DialogManager;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.view.DialogView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.calllib.RongCallCommon;

/**
 * FileName: UserInfoActivity
 * Founder: LiuGuiLin
 * Profile: 用户信息
 */
public class UserInfoActivity extends BaseUIActivity implements View.OnClickListener {

    private DialogView mAddFriendDialogView;
    private EditText et_msg;
    private TextView tv_cancel;
    private TextView tv_add_friend;

    /**
     * 1.根据传递过来的ID 查询用户信息 并且显示
     *   - 普通的信息
     *   - 构建一个RecyclerView 宫格
     * 2.建立好友关系模型
     *   与我有关系的是好友，
     *   1.在我的好友列表中
     *   2.同意了我的好友申请 BmobObject 建表
     *   3.查询所有的Friend表，其中user对应自己的列都是我的好友
     * 3.实现添加好友的提示框
     * 4.发送添加好友的消息
     *   1.自定义消息类型
     *   2.自定义协议
     *   发送文本消息 Content, 我们对文本进行处理：增加Json 定义一个标记来显示了
     *   点击提示框的发送按钮去发送
     * 5.接收好友的消息
     */

    /**
     * 跳转
     *
     * @param mContext
     * @param userId
     */
    public static void startActivity(Context mContext, String userId) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.putExtra(Constants.INTENT_USER_ID, userId);
        mContext.startActivity(intent);
    }

    private RelativeLayout ll_back;

    private CircleImageView iv_user_photo;
    private TextView tv_nickname;
    private TextView tv_desc;

    private RecyclerView mUserInfoView;
    private CommonAdapter<UserInfoModel> mUserInfoAdapter;
    private List<UserInfoModel> mUserInfoList = new ArrayList<>();

    private Button btn_add_friend;
    private Button btn_chat;
    private Button btn_audio_chat;
    private Button btn_video_chat;

    private LinearLayout ll_is_friend;

    //个人信息颜色
    private int[] mColor = {0x881E90FF, 0x8800FF7F, 0x88FFD700, 0x88FF6347, 0x88F08080, 0x8840E0D0};

    //用户ID
    private String userId = "";

    private IMUser imUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
    }

    private void initView() {

        initAddFriendDialog();

        //获取用户ID
        userId = getIntent().getStringExtra(Constants.INTENT_USER_ID);

        ll_back = (RelativeLayout) findViewById(R.id.ll_back);
        iv_user_photo = (CircleImageView) findViewById(R.id.iv_user_photo);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        mUserInfoView = (RecyclerView) findViewById(R.id.mUserInfoView);
        btn_add_friend = (Button) findViewById(R.id.btn_add_friend);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_audio_chat = (Button) findViewById(R.id.btn_audio_chat);
        btn_video_chat = (Button) findViewById(R.id.btn_video_chat);
        ll_is_friend = (LinearLayout) findViewById(R.id.ll_is_friend);

        ll_back.setOnClickListener(this);
        btn_add_friend.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_audio_chat.setOnClickListener(this);
        btn_video_chat.setOnClickListener(this);
        iv_user_photo.setOnClickListener(this);

        //列表
        mUserInfoAdapter = new CommonAdapter<>(mUserInfoList, new CommonAdapter.OnBindDataListener<UserInfoModel>() {
            @Override
            public void onBindViewHolder(UserInfoModel model, CommonViewHolder viewHolder, int type, int position) {
                //viewHolder.setBackgroundColor(R.id.ll_bg, model.getBgColor());
                viewHolder.getView(R.id.ll_bg).setBackgroundColor(model.getBgColor());
                viewHolder.setText(R.id.tv_type, model.getTitle());
                viewHolder.setText(R.id.tv_content, model.getContent());
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_user_info_item;
            }
        });
        mUserInfoView.setLayoutManager(new GridLayoutManager(this, 3));
        mUserInfoView.setAdapter(mUserInfoAdapter);

        queryUserInfo();
    }

    /**
     * 添加好友的提示框
     */
    private void initAddFriendDialog() {
        mAddFriendDialogView = DialogManager.getInstance().initView(this, R.layout.dialog_send_friend);

        et_msg = (EditText) mAddFriendDialogView.findViewById(R.id.et_msg);
        tv_cancel = (TextView) mAddFriendDialogView.findViewById(R.id.tv_cancel);
        tv_add_friend = (TextView) mAddFriendDialogView.findViewById(R.id.tv_add_friend);

        et_msg.setText(getString(R.string.text_me_info_tips) + BmobManager.getInstance().getUser().getNickName());

        tv_cancel.setOnClickListener(this);
        tv_add_friend.setOnClickListener(this);
    }

    /**
     * 查询用户信息
     */
    private void queryUserInfo() {
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        //查询用户信息
        BmobManager.getInstance().queryObjectIdUser(userId, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        imUser = list.get(0);
                        updateUserInfo(imUser);
                    }
                }
            }
        });
        //判断好友关系
        BmobManager.getInstance().queryMyFriends(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        //你有一个好友列表
                        for (int i = 0; i < list.size(); i++) {
                            Friend friend = list.get(i);
                            //判断这个对象中的id是否跟我目前的userId相同
                            if (friend.getFriendUser().getObjectId().equals(userId)) {
                                //你们是好友关系
                                btn_add_friend.setVisibility(View.GONE);
                                ll_is_friend.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param imUser
     */
    private void updateUserInfo(IMUser imUser) {
        //设置基本属性
        GlideHelper.loadUrl(UserInfoActivity.this, imUser.getPhoto(),
                iv_user_photo);
        tv_nickname.setText(imUser.getNickName());
        tv_desc.setText(imUser.getDesc());

        //性别 年龄 生日 星座 爱好 单身状态
        addUserInfoModel(mColor[0], getString(R.string.text_me_info_sex), imUser.isSex() ? getString(R.string.text_me_info_boy) : getString(R.string.text_me_info_girl));
        addUserInfoModel(mColor[1], getString(R.string.text_me_info_age), imUser.getAge() + getString(R.string.text_search_age));
        addUserInfoModel(mColor[2], getString(R.string.text_me_info_birthday), imUser.getBirthday());
        addUserInfoModel(mColor[3], getString(R.string.text_me_info_constellation), imUser.getConstellation());
        addUserInfoModel(mColor[4], getString(R.string.text_me_info_hobby), imUser.getHobby());
        addUserInfoModel(mColor[5], getString(R.string.text_me_info_status), imUser.getStatus());
        //刷新数据
        mUserInfoAdapter.notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param color
     * @param title
     * @param content
     */
    private void addUserInfoModel(int color, String title, String content) {
        UserInfoModel model = new UserInfoModel();
        model.setBgColor(color);
        model.setTitle(title);
        model.setContent(content);
        mUserInfoList.add(model);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_friend:
                String msg = et_msg.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    msg = getString(R.string.text_user_info_add_friend);
                    return;
                }
                CloudManager.getInstance().sendTextMessage(msg,
                        CloudManager.TYPE_ADD_FRIEND, userId);
                DialogManager.getInstance().hide(mAddFriendDialogView);
                Toast.makeText(this, getString(R.string.text_user_resuest_succeed), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance().hide(mAddFriendDialogView);
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_user_photo:
                ImagePreviewActivity.startActivity(this, true, imUser.getPhoto());
                break;
            case R.id.btn_add_friend:
                DialogManager.getInstance().show(mAddFriendDialogView);
                break;
            case R.id.btn_chat:
                ChatActivity.startActivity(UserInfoActivity.this,
                        userId, imUser.getNickName(), imUser.getPhoto());
                break;
            case R.id.btn_audio_chat:
                //窗口权限
                if (!checkWindowPermissions()) {
                    requestWindowPermissions();
                } else {
                    CloudManager.getInstance().startAudioCall(this, userId);
                }
                break;
            case R.id.btn_video_chat:
                if (!checkWindowPermissions()) {
                    requestWindowPermissions();
                } else {
                    CloudManager.getInstance().startVideoCall(this, userId);
                }
                break;
        }
    }
}
