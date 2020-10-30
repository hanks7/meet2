package com.imooc.meet.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.imooc.meet.R;
import com.imooc.meet.model.AllFriendModel;
import com.imooc.meet.ui.UserInfoActivity;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseFragment;
import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.Friend;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.event.MessageEvent;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * FileName: AllFriendFragment
 * Founder: LiuGuiLin
 * Profile: 所有联系人
 */
public class AllFriendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View item_empty_view;
    private RecyclerView mAllFriendView;
    private SwipeRefreshLayout mAllFriendRefreshLayout;

    private CommonAdapter<AllFriendModel> mAllFriendAdapter;
    private List<AllFriendModel> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_record, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {

        item_empty_view = view.findViewById(R.id.item_empty_view);
        mAllFriendView = view.findViewById(R.id.mAllFriendView);
        mAllFriendRefreshLayout = view.findViewById(R.id.mAllFriendRefreshLayout);

        mAllFriendRefreshLayout.setOnRefreshListener(this);

        mAllFriendView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAllFriendView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mAllFriendAdapter = new CommonAdapter<>(mList, new CommonAdapter.OnBindDataListener<AllFriendModel>() {
            @Override
            public void onBindViewHolder(final AllFriendModel model, CommonViewHolder viewHolder, int type, int position) {
                viewHolder.setImageUrl(getActivity(), R.id.iv_photo, model.getUrl());
                viewHolder.setText(R.id.tv_nickname, model.getNickName());
                viewHolder.setImageResource(R.id.iv_sex, model.isSex()
                        ? R.drawable.img_boy_icon : R.drawable.img_girl_icon);
                viewHolder.setText(R.id.tv_desc, model.getDesc());

                viewHolder.itemView.setOnClickListener(v -> UserInfoActivity.startActivity(getActivity(),model.getUserId()));
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_all_friend_item;
            }
        });
        mAllFriendView.setAdapter(mAllFriendAdapter);

        queryMyFriends();
    }

    /**
     * 查询所有好友
     */
    private void queryMyFriends() {
        mAllFriendRefreshLayout.setRefreshing(true);
        BmobManager.getInstance().queryMyFriends(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                mAllFriendRefreshLayout.setRefreshing(false);
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        item_empty_view.setVisibility(View.GONE);
                        mAllFriendView.setVisibility(View.VISIBLE);
                        if (mList.size() > 0) {
                            mList.clear();
                        }
                        LogUtils.i("list:" + list.size());
                        for (int i = 0; i < list.size(); i++) {
                            Friend friend = list.get(i);
                            String id = friend.getFriendUser().getObjectId();
                            BmobManager.getInstance().queryObjectIdUser(id, new FindListener<IMUser>() {
                                @Override
                                public void done(List<IMUser> list, BmobException e) {
                                    if (e == null) {
                                        if (CommonUtils.isEmpty(list)) {
                                            IMUser imUser = list.get(0);
                                            AllFriendModel model = new AllFriendModel();
                                            model.setUserId(imUser.getObjectId());
                                            model.setUrl(imUser.getPhoto());
                                            model.setNickName(imUser.getNickName());
                                            model.setSex(imUser.isSex());
                                            model.setDesc(getString(R.string.text_all_friend_desc) + imUser.getDesc());
                                            mList.add(model);
                                            mAllFriendAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        item_empty_view.setVisibility(View.VISIBLE);
                        mAllFriendView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        if (mAllFriendRefreshLayout.isRefreshing()) {
            queryMyFriends();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()) {
            case EventManager.FLAG_UPDATE_FRIEND_LIST:
                if (!mAllFriendRefreshLayout.isRefreshing()) {
                    queryMyFriends();
                }
                break;
        }
    }
}
