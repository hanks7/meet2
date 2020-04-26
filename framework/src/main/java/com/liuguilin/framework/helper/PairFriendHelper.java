package com.liuguilin.framework.helper;

import com.liuguilin.framework.bmob.BmobManager;
import com.liuguilin.framework.bmob.FateSet;
import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.utils.CommonUtils;
import com.liuguilin.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * FileName: PairFriendHelper
 * Founder: LiuGuiLin
 * Profile: 匹配好友
 */
public class PairFriendHelper {

    private static volatile PairFriendHelper mInstance = null;

    //延迟时间 单位s
    private static final int DELAY_TIME = 2;

    //随机类
    private Random mRandom;
    //自己的ID
    private String meUserId;
    //自己的对象
    private IMUser meIMUser;

    //RxJava
    private Disposable mDisposable;

    //轮询次数
    private int FateNumber = 0;

    //接口
    private OnPairResultListener onPairResultListener;

    public void setOnPairResultListener(OnPairResultListener onPairResultListener) {
        this.onPairResultListener = onPairResultListener;
    }

    private PairFriendHelper() {
        mRandom = new Random();

        if(BmobManager.getInstance().isLogin()){
            meIMUser = BmobManager.getInstance().getUser();
            meUserId = meIMUser.getObjectId();
        }
    }

    public static PairFriendHelper getInstance() {
        if (mInstance == null) {
            synchronized (PairFriendHelper.class) {
                if (mInstance == null) {
                    mInstance = new PairFriendHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 0：从用户组随机抽取一位好友
     * 1：深度匹配：资料的相似度
     * 2：缘分 同一时刻搜索的
     * 3：年龄相似的异性
     *
     * @param index
     */
    public void pairUser(int index, List<IMUser> list) {
        if(!BmobManager.getInstance().isLogin()){
            if(onPairResultListener != null){
                onPairResultListener.OnPairFailListener();
            }
            return;
        }
        switch (index) {
            case 0:
                randomUser(list);
                break;
            case 1:
                soulUser(list);
                break;
            case 2:
                fateUser();
                break;
            case 3:
                loveUser(list);
                break;
        }
    }

    /**
     * 恋爱匹配
     *
     * @param list
     */
    private void loveUser(List<IMUser> list) {

        /**
         * 1.抽取所有的用户
         * 2.根据性别抽取出异性
         * 3.根据年龄再抽取
         * 4.可以有一些附加条件：爱好 星座 ~~
         * 5.计算出来
         */

        List<IMUser> _love_user = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            IMUser imUser = list.get(i);

            //过滤自己
            if (imUser.getObjectId().equals(meUserId)) {
                //跳过本次循环
                continue;
            }

            //异性
            if (imUser.isSex() != meIMUser.isSex()) {
                _love_user.add(imUser);
            }
        }

        //异性保存成功
        if (CommonUtils.isEmpty(_love_user)) {
            final List<String> _love_id = new ArrayList<>();
            //计算年龄
            for (int i = 0; i < _love_user.size(); i++) {
                IMUser imUser = _love_user.get(i);

                // 24 - 29 = -5
                if (Math.abs(imUser.getAge() - meIMUser.getAge()) <= 3) {
                    _love_id.add(imUser.getObjectId());
                }
            }
            if (_love_id.size() > 0) {

                //在这里增加更多的判断条件

                //延迟发送
                rxJavaParingResult(new OnRxJavaResultListener() {
                    @Override
                    public void rxJavaResult() {
                        int _r = mRandom.nextInt(_love_id.size());
                        onPairResultListener.OnPairListener(_love_id.get(_r));
                    }
                });
            }else {
                onPairResultListener.OnPairFailListener();
            }
        } else {
            onPairResultListener.OnPairFailListener();
        }
    }

    /**
     * 缘分匹配好友
     */
    private void fateUser() {

        /**
         * 1.创建库
         * 2.将自己添加进去
         * 3.轮询查找好友
         * 4.15s
         * 5.查询到了之后则反馈给外部
         * 6.将自己删除
         */

        BmobManager.getInstance().addFateSet(new SaveListener<String>() {
            @Override
            public void done(final String s, BmobException e) {
                if (e == null) {
                    //轮询
                    mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    queryFateSet(s);
                                }
                            });
                }
            }
        });
    }

    /**
     * 查询缘分池
     *
     * @param id
     */
    private void queryFateSet(final String id) {
        BmobManager.getInstance().queryFateSet(new FindListener<FateSet>() {
            @Override
            public void done(List<FateSet> list, BmobException e) {
                FateNumber++;
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        //如果>1才说明有人在匹配
                        if (list.size() > 1) {
                            disposable();
                            //过滤自己
                            for (int i = 0; i < list.size(); i++) {
                                FateSet fateSet = list.get(i);
                                if (fateSet.getUserId().equals(meUserId)) {
                                    list.remove(i);
                                    break;
                                }
                            }
                            //最终结果
                            int _r = mRandom.nextInt(list.size());
                            onPairResultListener.OnPairListener(list.get(_r).getUserId());
                            //删除自己
                            deleteFateSet(id);
                            FateNumber = 0;
                        } else {
                            LogUtils.i("FateNumber:" + FateNumber);
                            //超时
                            if (FateNumber >= 15) {
                                disposable();
                                deleteFateSet(id);
                                onPairResultListener.OnPairFailListener();
                                FateNumber = 0;
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 删除指定的缘分池
     *
     * @param id
     */
    private void deleteFateSet(String id) {
        BmobManager.getInstance().delFateSet(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtils.i("Delete");
                }
            }
        });
    }

    /**
     * 灵魂匹配好友
     *
     * @param list
     */
    private void soulUser(List<IMUser> list) {
        List<String> _list_objectId = new ArrayList<>();
        // 四要素：星座 年龄 爱好 状态
        for (int i = 0; i < list.size(); i++) {
            IMUser imUser = list.get(i);

            //过滤自己
            if (imUser.getObjectId().equals(meUserId)) {
                //跳过本次循环
                continue;
            }

            //匹配星座
            if (imUser.getConstellation().equals(meIMUser.getConstellation())) {
                _list_objectId.add(imUser.getObjectId());
            }

            //匹配年龄
            if (imUser.getAge() == meIMUser.getAge()) {
                _list_objectId.add(imUser.getObjectId());
            }

            //匹配爱好
            if (imUser.getHobby().equals(meIMUser.getHobby())) {
                _list_objectId.add(imUser.getObjectId());
            }

            //单身状态
            if (imUser.getStatus().equals(meIMUser.getStatus())) {
                _list_objectId.add(imUser.getObjectId());
            }
        }

        //计算重复的ID

        /**
         * JAVA问题：List中重复的ID如何计算？
         */

        //定义Map
        Map<String, Integer> _map = new HashMap<>();
        //遍历List
        for (String str : _list_objectId) {
            //定义基础的次数
            Integer i = 1;
            //根据ID获取map的键值 如果不等于空 说明有数据 并且在原基础上自增1
            if (_map.get(str) != null) {
                i = _map.get(str) + 1;
            }
            //如果等于空？
            _map.put(str, i);
        }

        //LogUtils.i("_map:" + _map.toString());

        //如何获得最佳的对象？
        final List<String> _soul_list = mapComperTo(4, _map);

        //LogUtils.i("_soul_list:" + _soul_list.toString());

        if (CommonUtils.isEmpty(_soul_list)) {
            //计算
            rxJavaParingResult(new OnRxJavaResultListener() {
                @Override
                public void rxJavaResult() {
                    //随机数
                    int _r = mRandom.nextInt(_soul_list.size());
                    onPairResultListener.OnPairListener(_soul_list.get(_r));
                }
            });
        } else {
            onPairResultListener.OnPairFailListener();
        }
    }

    /**
     * Map计算将传入的size对应的key传出
     *
     * @param size
     * @param _map
     */
    private List<String> mapComperTo(int size, Map<String, Integer> _map) {
        List<String> _list_key = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : _map.entrySet()) {
            String _key = entry.getKey();
            Integer _values = entry.getValue();
            if (_values == size) {
                _list_key.add(_key);
            }
        }
        if (_list_key.size() == 0) {
            size = size - 1;
            if (size == 0) {
                return null;
            }
            return mapComperTo(size, _map);
        }
        return _list_key;
    }


    /**
     * 随机匹配好友
     *
     * @param list
     */
    private void randomUser(final List<IMUser> list) {
        /**
         * 1.获取到全部的用户组
         * 2.过滤自己
         * 3.开始随机
         * 4.根据随机的数值拿到对应的对象ID
         * 5.接口回传
         */

        //过滤自己
        for (int i = 0; i < list.size(); i++) {
            //对象ID == 我的ID
            if (list.get(i).getObjectId().equals(meUserId)) {
                list.remove(i);
            }
        }

        //处理结果
        rxJavaParingResult(new OnRxJavaResultListener() {
            @Override
            public void rxJavaResult() {
                //接收2s后的通知
                //随机数
                int _r = mRandom.nextInt(list.size());
                IMUser imUser = list.get(_r);
                if (imUser != null) {
                    onPairResultListener.OnPairListener(imUser.getObjectId());
                }
            }
        });
    }

    /**
     * 异步线程处理结果
     *
     * @param listener
     */
    private void rxJavaParingResult(final OnRxJavaResultListener listener) {
        //延迟
        mDisposable = Observable
                .timer(DELAY_TIME, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        listener.rxJavaResult();
                    }
                });
    }

    public interface OnPairResultListener {

        //匹配成功
        void OnPairListener(String userId);

        //匹配失败
        void OnPairFailListener();
    }

    public interface OnRxJavaResultListener {

        void rxJavaResult();
    }

    /**
     * 销毁
     */
    public void disposable() {
        if (mDisposable != null) {
            if(!mDisposable.isDisposed()){
                mDisposable.dispose();
            }
        }
    }
}
