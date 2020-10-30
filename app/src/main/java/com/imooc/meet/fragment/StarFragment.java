package com.imooc.meet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.meet.R;
import com.imooc.meet.adapter.CloudTagAdapter;
import com.imooc.meet.model.StarModel;
import com.imooc.meet.ui.AddFriendActivity;
import com.imooc.meet.ui.QrCodeActivity;
import com.imooc.meet.ui.UserInfoActivity;
import com.liuguilin.framework.base.BaseFragment;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.cloud.CloudManager;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.event.MessageEvent;
import com.liuguilin.framework.helper.PairFriendHelper;
import com.liuguilin.framework.manager.DialogManager;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.utils.LogUtils;
import com.liuguilin.framework.view.DialogView;
import com.liuguilin.framework.view.LodingView;
import com.moxun.tagcloudlib.view.TagCloudView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * FileName: StarFragment
 * Founder: LiuGuiLin
 * Profile: 星球
 */
public class StarFragment extends BaseFragment implements View.OnClickListener {

    //二维码结果
    private static final int REQUEST_CODE = 1235;

    private TextView tv_star_title;
    private ImageView iv_camera;
    private ImageView iv_add;

    private TagCloudView mCloudView;

    private LinearLayout ll_random;
    private LinearLayout ll_soul;
    private LinearLayout ll_fate;
    private LinearLayout ll_love;

    private CloudTagAdapter mCloudTagAdapter;
    private List<StarModel> mStarList = new ArrayList<>();

    private LodingView mLodingView;

    private DialogView mNullDialogView;
    private TextView tv_null_text;
    private TextView tv_null_cancel;

    //连接状态
    private TextView tv_connect_status;

    //全部好友
    private List<IMUser> mAllUserList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, null);
        initView(view);
        return view;
    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void initView(View view) {

        mLodingView = new LodingView(getActivity());
        mLodingView.setCancelable(false);

        mNullDialogView = DialogManager.getInstance().initView(getActivity(), R.layout.layout_star_null_item, Gravity.BOTTOM);
        tv_null_text = mNullDialogView.findViewById(R.id.tv_null_text);
        tv_null_cancel = mNullDialogView.findViewById(R.id.tv_cancel);

        tv_null_cancel.setOnClickListener(v -> DialogManager.getInstance().hide(mNullDialogView));

        iv_camera = view.findViewById(R.id.iv_camera);
        iv_add = view.findViewById(R.id.iv_add);
        tv_connect_status = view.findViewById(R.id.tv_connect_status);

        tv_star_title = view.findViewById(R.id.tv_star_title);

        mCloudView = view.findViewById(R.id.mCloudView);

        ll_random = view.findViewById(R.id.ll_random);
        ll_soul = view.findViewById(R.id.ll_soul);
        ll_fate = view.findViewById(R.id.ll_fate);
        ll_love = view.findViewById(R.id.ll_love);

        iv_camera.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        ll_random.setOnClickListener(this);
        ll_soul.setOnClickListener(this);
        ll_fate.setOnClickListener(this);
        ll_love.setOnClickListener(this);

        //数据绑定
        if (null != getActivity()) {
            mCloudTagAdapter = new CloudTagAdapter(getActivity(), mStarList);
            mCloudView.setAdapter(mCloudTagAdapter);
        }

        //监听点击事件
        mCloudView.setOnTagClickListener((parent, view1, position) -> startUserInfo(mStarList.get(position).getUserId()));

        //绑定接口
        PairFriendHelper.getInstance().setOnPairResultListener(new PairFriendHelper.OnPairResultListener() {

            @Override
            public void OnPairListener(String userId) {
                startUserInfo(userId);
            }

            @Override
            public void OnPairFailListener() {
                mLodingView.hide();
                Toast.makeText(getActivity(), getString(R.string.text_pair_null), Toast.LENGTH_SHORT).show();
            }
        });

        loadStarUser();
    }

    /**
     * 跳转用户信息
     *
     * @param userId
     */
    private void startUserInfo(String userId) {
        mLodingView.hide();
        UserInfoActivity.startActivity(getActivity(), userId);
    }

    /**
     * 加载星球用户
     */
    private void loadStarUser() {
        LogUtils.i("loadStarUser");
        /**
         * 我们从用户库中取抓取一定的好友进行匹配
         */
        BmobManager.getInstance().queryAllUser(new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                LogUtils.i("done");
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {

                        if (mAllUserList.size() > 0) {
                            mAllUserList.clear();
                        }

                        if (mStarList.size() > 0) {
                            mStarList.clear();
                        }

                        mAllUserList = list;

                        //这里是所有的用户 只适合我们现在的小批量
                        int index = 50;
                        if (list.size() <= 50) {
                            index = list.size();
                        }
                        //直接填充
                        for (int i = 0; i < index; i++) {
                            IMUser imUser = list.get(i);
                            saveStarUser(imUser.getObjectId(),
                                    imUser.getNickName(),
                                    imUser.getPhoto());
                        }
                        LogUtils.i("done...");
                        //当请求数据已经加载出来的时候判断是否连接服务器
                        if (CloudManager.getInstance().isConnect()) {
                            //已经连接，并且星球加载，则隐藏
                            tv_connect_status.setVisibility(View.GONE);
                        }
                        mCloudTagAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 保存星球用户
     *
     * @param userId
     * @param nickName
     * @param photoUrl
     */
    private void saveStarUser(String userId, String nickName, String photoUrl) {
        StarModel model = new StarModel();
        model.setUserId(userId);
        model.setNickName(nickName);
        model.setPhotoUrl(photoUrl);
        mStarList.add(model);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera:
                //扫描
                Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_add:
                //添加好友
                startActivity(new Intent(getActivity(), AddFriendActivity.class));
                break;
            case R.id.ll_random:
                //随机匹配
                pairUser(0);
                break;
            case R.id.ll_soul:

                if (TextUtils.isEmpty(BmobManager.getInstance().getUser().getConstellation())) {
                    tv_null_text.setText(getString(R.string.text_star_par_tips_1));
                    DialogManager.getInstance().show(mNullDialogView);
                    return;
                }

                if (BmobManager.getInstance().getUser().getAge() == 0) {
                    tv_null_text.setText(getString(R.string.text_star_par_tips_2));
                    DialogManager.getInstance().show(mNullDialogView);
                    return;
                }

                if (TextUtils.isEmpty(BmobManager.getInstance().getUser().getHobby())) {
                    tv_null_text.setText(getString(R.string.text_star_par_tips_3));
                    DialogManager.getInstance().show(mNullDialogView);
                    return;
                }

                if (TextUtils.isEmpty(BmobManager.getInstance().getUser().getStatus())) {
                    tv_null_text.setText(getString(R.string.text_star_par_tips_4));
                    DialogManager.getInstance().show(mNullDialogView);
                    return;
                }

                //灵魂匹配
                pairUser(1);
                break;
            case R.id.ll_fate:
                //缘分匹配
                pairUser(2);
                break;
            case R.id.ll_love:
                //恋爱匹配
                pairUser(3);
                break;
        }
    }

    /**
     * 匹配规则
     *
     * @param index
     */
    private void pairUser(int index) {
        switch (index) {
            case 0:
                mLodingView.show(getString(R.string.text_pair_random));
                break;
            case 1:
                mLodingView.show(getString(R.string.text_pair_soul));
                break;
            case 2:
                mLodingView.show(getString(R.string.text_pair_fate));
                break;
            case 3:
                mLodingView.show(getString(R.string.text_pair_love));
                break;
        }
        if (CommonUtils.isEmpty(mAllUserList)) {
            //计算
            PairFriendHelper.getInstance().pairUser(index, mAllUserList);
        } else {
            mLodingView.hide();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    LogUtils.i("result：" + result);
                    //Meet#c7a9b4794f
                    if (!TextUtils.isEmpty(result)) {
                        //是我们自己的二维码
                        if (result.startsWith("Meet")) {
                            String[] split = result.split("#");
                            //LogUtils.i("split:" + split.toString());
                            if (split.length >= 2) {
                                try {
                                    UserInfoActivity.startActivity(getActivity(), split[1]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.text_toast_error_qrcode), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.text_toast_error_qrcode), Toast.LENGTH_SHORT).show();
                    }

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), getString(R.string.text_qrcode_fail), Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PairFriendHelper.getInstance().disposable();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getType() == EventManager.EVENT_SERVER_CONNECT_STATUS) {
            if (event.isConnectStatus()) {
                if (CommonUtils.isEmpty(mStarList)) {
                    tv_connect_status.setVisibility(View.GONE);
                }
            } else {
                tv_connect_status.setText(getString(R.string.text_star_pserver_fail));
            }
        }
    }
}
