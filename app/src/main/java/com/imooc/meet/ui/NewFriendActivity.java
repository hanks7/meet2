package com.imooc.meet.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imooc.meet.R;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.cloud.CloudManager;
import com.liuguilin.framework.db.LitePalHelper;
import com.liuguilin.framework.db.NewFriend;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * FileName: NewFriendActivity
 * Founder: LiuGuiLin
 * Profile: 新朋友
 */
public class NewFriendActivity extends BaseBackActivity {

    /**
     * 1.查询好友的申请列表
     * 2.通过适配器显示出来
     * 3.如果同意则添加对方为自己的好友
     * 4.并且发送给对方自定义的消息
     * 5.对方将我添加到好友列表
     */

    private ViewStub item_empty_view;
    private RecyclerView mNewFriendView;

    private Disposable disposable;

    private CommonAdapter<NewFriend> mNewFriendAdapter;
    private List<NewFriend> mList = new ArrayList<>();

    /**
     * 实际上这种问题还不是最高效的
     * 因为通过ID获取ImUser是存在网络延迟的
     * 我们可以通过另一种方式处理
     * 看ll_yes的点击事件
     */
    private List<IMUser> mUserList = new ArrayList<>();

    //对方用户
    private IMUser imUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        initView();
    }

    private void initView() {

        mNewFriendView = (RecyclerView) findViewById(R.id.mNewFriendView);

        mNewFriendView.setLayoutManager(new LinearLayoutManager(this));
        mNewFriendView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mNewFriendAdapter = new CommonAdapter<>(mList, new CommonAdapter.OnBindDataListener<NewFriend>() {
            @Override
            public void onBindViewHolder(final NewFriend model, final CommonViewHolder viewHolder, int type, final int position) {

                //根据Id查询用户信息
                BmobManager.getInstance().queryObjectIdUser(model.getId(), new FindListener<IMUser>() {
                    @Override
                    public void done(List<IMUser> list, BmobException e) {
                        //填充具体属性
                        if (e == null) {
                            imUser = list.get(0);
                            mUserList.add(imUser);
                            viewHolder.setImageUrl(NewFriendActivity.this, R.id.iv_photo,
                                    imUser.getPhoto());
                            viewHolder.setImageResource(R.id.iv_sex, imUser.isSex() ?
                                    R.drawable.img_boy_icon : R.drawable.img_girl_icon);
                            viewHolder.setText(R.id.tv_nickname, imUser.getNickName());
                            viewHolder.setText(R.id.tv_age, imUser.getAge()
                                    + getString(R.string.text_search_age));
                            viewHolder.setText(R.id.tv_desc, imUser.getDesc());
                            viewHolder.setText(R.id.tv_msg, model.getMsg());

                            if (model.getIsAgree() == 0) {
                                viewHolder.getView(R.id.ll_agree).setVisibility(View.GONE);
                                viewHolder.getView(R.id.tv_result).setVisibility(View.VISIBLE);
                                viewHolder.setText(R.id.tv_result, getString(R.string.text_new_friend_agree));
                            } else if (model.getIsAgree() == 1) {
                                viewHolder.getView(R.id.ll_agree).setVisibility(View.GONE);
                                viewHolder.getView(R.id.tv_result).setVisibility(View.VISIBLE);
                                viewHolder.setText(R.id.tv_result, getString(R.string.text_new_friend_no_agree));
                            }
                        }
                    }
                });

                //同意
                viewHolder.getView(R.id.ll_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 1.同意则刷新当前的Item
                         * 2.将好友添加到自己的好友列表
                         * 3.通知对方我已经同意了
                         * 4.对方将我添加到好友列表
                         * 5.刷新好友列表
                         */
                        updateItem(position, 0);
                        //将好友添加到自己的好友列表
                        //构建一个ImUSER
                        IMUser friendUser = new IMUser();
                        friendUser.setObjectId(model.getId());
                        BmobManager.getInstance().addFriend(friendUser, new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    //保存成功
                                    //通知对方
                                    CloudManager.getInstance().sendTextMessage("",
                                            CloudManager.TYPE_ARGEED_FRIEND, imUser.getObjectId());
                                    //刷新好友列表
                                    EventManager.post(EventManager.FLAG_UPDATE_FRIEND_LIST);
                                }
                            }
                        });
                    }
                });

                //拒绝
                viewHolder.getView(R.id.ll_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateItem(position, 1);
                    }
                });
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_new_friend_item;
            }
        });
        mNewFriendView.setAdapter(mNewFriendAdapter);

        queryNewFriend();
    }

    /**
     * 更新Item
     *
     * @param position
     * @param i
     */
    private void updateItem(int position, int i) {
        NewFriend newFriend = mList.get(position);
        //更新数据库
        LitePalHelper.getInstance().updateNewFriend(newFriend.getId(), i);
        //更新本地的数据源
        newFriend.setIsAgree(i);
        mList.set(position, newFriend);
        mNewFriendAdapter.notifyDataSetChanged();
    }

    /**
     * 查询新朋友
     */
    private void queryNewFriend() {

        /**
         * 在子线程中获取好友申请列表然后在主线程中更新我们的UI
         * RxJava 线程调度
         */

        disposable = Observable.create(new ObservableOnSubscribe<List<NewFriend>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewFriend>> emitter) throws Exception {
                emitter.onNext(LitePalHelper.getInstance().queryNewFriend());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewFriend>>() {
                    @Override
                    public void accept(List<NewFriend> newFriends) throws Exception {
                        //更新UI
                        if (CommonUtils.isEmpty(newFriends)) {
                            mList.addAll(newFriends);
                            mNewFriendAdapter.notifyDataSetChanged();
                        } else {
                            showViewStub();
                            mNewFriendView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 显示懒加载布局
     */
    private void showViewStub(){
        item_empty_view = findViewById(R.id.item_empty_view);
        item_empty_view.inflate();
    }
}
