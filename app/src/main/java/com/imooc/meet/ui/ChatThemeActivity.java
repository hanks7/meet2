package com.imooc.meet.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imooc.meet.R;
import com.liuguilin.framework.adapter.CommonAdapter;
import com.liuguilin.framework.adapter.CommonViewHolder;
import com.liuguilin.framework.base.BaseBackActivity;
import com.liuguilin.framework.entity.Constants;
import com.liuguilin.framework.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: ChatThemeActivity
 * Founder: LiuGuiLin
 * Create Date: 2019/11/21 14:52
 * Email: lgl@szokl.com.cn
 * Profile: 聊天主题
 */
public class ChatThemeActivity extends BaseBackActivity {

    private RecyclerView mThemeView;
    private List<Integer> mThemeList = new ArrayList<>();
    private CommonAdapter<Integer> mThemeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_theme);

        initView();
    }

    private void initView() {

        mThemeList.add(R.drawable.img_chat_bg_1);
        mThemeList.add(R.drawable.img_chat_bg_2);
        mThemeList.add(R.drawable.img_chat_bg_3);
        mThemeList.add(R.drawable.img_chat_bg_4);
        mThemeList.add(R.drawable.img_chat_bg_5);
        mThemeList.add(R.drawable.img_chat_bg_6);
        mThemeList.add(R.drawable.img_chat_bg_7);
        mThemeList.add(R.drawable.img_chat_bg_8);
        mThemeList.add(R.drawable.img_chat_bg_9);

        mThemeView = (RecyclerView) findViewById(R.id.mThemeView);
        mThemeView.setLayoutManager(new GridLayoutManager(this, 3));
        mThemeAdapter = new CommonAdapter<>(mThemeList, new CommonAdapter.OnBindDataListener<Integer>() {
            @Override
            public void onBindViewHolder(Integer model, CommonViewHolder viewHolder, int type, int position) {
                viewHolder.setImageResource(R.id.iv_theme, model);

                viewHolder.itemView.setOnClickListener(v -> {
                    SpUtils.getInstance().putInt(Constants.SP_CHAT_THEME, (position + 1));
                    Toast.makeText(ChatThemeActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_theme_item;
            }
        });
        mThemeView.setAdapter(mThemeAdapter);
    }
}
