package com.imooc.meet.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

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
import com.liuguilin.framework.bmob.PrivateSet;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * FileName: ContactFirendActivity
 * Founder: LiuGuiLin
 * Profile: 从通讯录导入
 */
public class ContactFirendActivity extends BaseBackActivity {

    private Disposable disposable;

    private RecyclerView mContactView;
    private Map<String, String> mContactMap = new HashMap<>();

    private CommonAdapter<AddFriendModel> mContactAdapter;
    private List<AddFriendModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_friend);

        initView();
    }

    private void initView() {
        mContactView = (RecyclerView) findViewById(R.id.mContactView);
        mContactView.setLayoutManager(new LinearLayoutManager(this));
        mContactView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mContactAdapter = new CommonAdapter<>(mList, new CommonAdapter.OnBindDataListener<AddFriendModel>() {
            @Override
            public void onBindViewHolder(final AddFriendModel model, CommonViewHolder viewHolder, int type, int position) {
                //设置头像
                viewHolder.setImageUrl(ContactFirendActivity.this, R.id.iv_photo, model.getPhoto());
                //设置性别
                viewHolder.setImageResource(R.id.iv_sex,
                        model.isSex() ? R.drawable.img_boy_icon : R.drawable.img_girl_icon);
                //设置昵称
                viewHolder.setText(R.id.tv_nickname, model.getNickName());
                //年龄
                viewHolder.setText(R.id.tv_age, model.getAge() + getString(R.string.text_search_age));
                //设置描述
                viewHolder.setText(R.id.tv_desc, model.getDesc());

                if (model.isContact()) {
                    viewHolder.getView(R.id.ll_contact_info).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_contact_name, model.getContactName());
                    viewHolder.setText(R.id.tv_contact_phone, model.getContactPhone());
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfoActivity.startActivity(ContactFirendActivity.this,
                                model.getUserId());
                    }
                });
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_search_user_item;
            }
        });

        mContactView.setAdapter(mContactAdapter);

        loadUser();
    }

    /**
     * 加载用户
     */
    private void loadUser() {

        /**
         * 1.拿到用户的联系人列表
         * 2.查询我们的PrivateSet
         * 3.过滤一遍联系人列表
         * 4.去显示
         */

        disposable = Observable.create(new ObservableOnSubscribe<List<PrivateSet>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<PrivateSet>> emitter) throws Exception {
                //加载联系人
                loadContact();
                //查询我们的PrivateSet
                BmobManager.getInstance().queryPrivateSet(new FindListener<PrivateSet>() {
                    @Override
                    public void done(List<PrivateSet> list, BmobException e) {
                        if (e == null) {
                            emitter.onNext(list);
                            emitter.onComplete();
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PrivateSet>>() {
                    @Override
                    public void accept(List<PrivateSet> privateSets) throws Exception {
                        fixprivateSets(privateSets);
                        //这里判断无数据
                    }
                });
    }

    /**
     * 解析私有库的内容进行联系人过滤
     *
     * @param privateSets
     */
    private void fixprivateSets(List<PrivateSet> privateSets) {
        LogUtils.i("fixprivateSets:" + privateSets.size());
        List<String> userListPhone = new ArrayList<>();

        if (CommonUtils.isEmpty(privateSets)) {
            for (int i = 0; i < privateSets.size(); i++) {
                PrivateSet sets = privateSets.get(i);
                String phone = sets.getPhone();
                userListPhone.add(phone);
            }
        }

        //拿到了后台所有字段的电话号码
        if (mContactMap.size() > 0) {
            for (final Map.Entry<String, String> entry : mContactMap.entrySet()) {
                //过滤：判断你当前的号码在私有库是否存在
                if (userListPhone.contains(entry.getValue())) {
                    continue;
                }
                LogUtils.i("load...");
                BmobManager.getInstance().queryPhoneUser(entry.getValue(), new FindListener<IMUser>() {
                    @Override
                    public void done(List<IMUser> list, BmobException e) {
                        if (e == null) {
                            if (CommonUtils.isEmpty(list)) {
                                IMUser imUser = list.get(0);
                                addContent(imUser, entry.getKey(), entry.getValue());
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 加载联系人
     */
    private void loadContact() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                , null, null, null, null);
        String name;
        String phone;
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            LogUtils.i("name:" + name + " phone:" + phone);

            phone = phone.replace(" ", "").replace("-", "");
            mContactMap.put(name, phone);
        }
    }

    /**
     * 添加内容
     *
     * @param imUser
     */
    private void addContent(IMUser imUser, String name, String phone) {
        AddFriendModel model = new AddFriendModel();
        model.setType(AddFriendActivity.TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.isSex());
        model.setAge(imUser.getAge());
        model.setNickName(imUser.getNickName());
        model.setDesc(imUser.getDesc());

        model.setContact(true);
        model.setContactName(name);
        model.setContactPhone(phone);

        mList.add(model);
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable();
    }

    //解除绑定
    private void disposable() {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void onBackPressed() {
        disposable();
        super.onBackPressed();
    }

}
