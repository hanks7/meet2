package com.imooc.meet.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imooc.meet.R;
import com.imooc.meet.fragment.chat.AllFriendFragment;
import com.imooc.meet.fragment.chat.CallRecordFragment;
import com.imooc.meet.fragment.chat.ChatRecordFragment;
import com.liuguilin.framework.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: ChatFragment
 * Founder: LiuGuiLin
 * Profile: 聊天
 */
public class ChatFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitle;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private ChatRecordFragment mChatRecordFragment;
    private CallRecordFragment mCallRecordFragment;
    private AllFriendFragment mAllFriendFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //聊天记录 通话记录 全部好友
        mTitle = new String[]{getString(R.string.text_chat_tab_title_1)
                , getString(R.string.text_chat_tab_title_2)
                , getString(R.string.text_chat_tab_title_3)};

        mChatRecordFragment = new ChatRecordFragment();
        mCallRecordFragment = new CallRecordFragment();
        mAllFriendFragment = new AllFriendFragment();

        mFragmentList.add(mChatRecordFragment);
        mFragmentList.add(mCallRecordFragment);
        mFragmentList.add(mAllFriendFragment);

        mTabLayout = view.findViewById(R.id.mTabLayout);
        mViewPager = view.findViewById(R.id.mViewPager);

        for (int i = 0; i < mTitle.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitle[i]));
        }

        mViewPager.setOffscreenPageLimit(mTitle.length);
        mViewPager.setAdapter(new ChatPagerAdapter(getFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                defTabStyle(tab, 20);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //默认第一个选中
        defTabStyle(mTabLayout.getTabAt(0), 20);
    }

    /**
     * 设置Tab样式
     *
     * @param tab
     * @param size
     */
    private void defTabStyle(TabLayout.Tab tab, int size) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_tab_text, null);
        TextView tv_tab = view.findViewById(R.id.tv_tab);
        tv_tab.setText(tab.getText());
        tv_tab.setTextColor(Color.WHITE);
        tv_tab.setTextSize(size);
        tab.setCustomView(tv_tab);
    }

    class ChatPagerAdapter extends FragmentStatePagerAdapter {

        public ChatPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }
}
