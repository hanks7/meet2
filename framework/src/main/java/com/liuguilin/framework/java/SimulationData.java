package com.liuguilin.framework.java;

import com.liuguilin.framework.bmob.IMUser;
import com.liuguilin.framework.utils.LogUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * FileName: SimulationData
 * Founder: LiuGuiLin
 * Profile: 模拟数据
 *
 * 使用方法：
 * SimulationData.testData();
 * 注意：使用了之后用户本地表会发生变化，需要卸载重新安装
 */
public class SimulationData {

    private static final String[] mGirlPhone = {
            "18970962301",
            "18970964402",
            "18970964403",
            "18970964404",
            "18970964405",
            "18970964406",
            "18970964407",
            "18970964409",
            "18970964411",
            "18970964421",
            "18970964431",
            "18970964441",
            "18970964451",
            "18970964461",
            "18970964471",
            "18970964481",
            "18970964491",
            "18970964401",
            "18960964411",
            "18950964411",
            "18940964411",
            "18930964411",
            "18920964411",
            "18910964411",
            "18910864411",
            "18910764411",
            "18910664411",
            "18910654411",
            "18910644411",
            "18910634411",
            "18910624411",
            "18910614411",
            "18910604411",
            "18910601411",
            "18910602411",
            "18910903411",
            "18910605411",
            "18910606411",
            "18910603411",
            "18910603211",
            "18910603111",
            "18910603011",
            "18910603001",
            "18910603000",
            "18510603000",
            "17510603000",
            "16510603000",
            "15510603000",
            "14510603000",
            "13510603000",
            "12510603000",
            "11510603000"};

    private static final String[] mGirlName = {"吃货最怕饿梦", "爱做梦的小孩", "超萌杀手", "甜心教主", "偏执的傲", "半呆半萌半幼稚", "蠢萌蠢萌的二货", "开启童真模式", "画个圈圈鄙视你"
            , "萌量不足", "宅久天然呆", "我为你“萌”", "瞬间脑空白i", "神经一样的妞", "微笑的糖果", "棒棒糖之恋", "懵懂的幼稚", "蹋上丶光棍路", "拨打寂寞专线", "萌仔=￣ω￣=", "萌哒哒的傲气"
            , "“野蛮小可爱°", "做梦的季节", "Ω糖果罐子", "草莓棉花糖", "爱哭爱闹爱撒娇", "懒懒的感觉", "单色冰淇淋的爱丶", "♂ 光着脚丫逃跑-°", "﹏゛不着调调的小女人ヽ", "天生萌货", "幸运符号"
            , "萌怪咖", "天然呆。", "快乐宝贝", "可爱的小丫头", "乖小兔殿下", "闭上眼装傻ゅ", "侑點尐任性", "万花丛中我最拽", "萌妹撒", "洛雪儿", "萌小喵", "呆萌迷糊", " の酱酱酱>=<", "无泪的小猪"
            , "小脸红扑扑", "大大大大太阳", "兔子嫑爱", "软酱奶糖", "我迷了鹿", "大檬檬 "};

    private static final String[] mGirlPhoto = {"http://b-ssl.duitang.com/uploads/item/201704/20/20170420191558_YCic5.jpeg"
            , "http://cdn.duitang.com/uploads/item/201410/26/20141026191422_yEKyd.thumb.700_0.jpeg"
            , "http://cdn.duitang.com/uploads/item/201407/24/20140724190906_MCkXs.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201802/25/20180225184943_ZRAdx.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201507/07/20150707211955_J58AM.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201810/18/20181018162951_kgwzm.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201712/29/20171229203626_X5y8S.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201503/31/20150331155006_kd5rr.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201612/08/20161208204750_rS8N4.jpeg"
            , "http://imgnews.mumayi.com/file/2019/07/16/3a88379755e2782fb2e58c2e20e6e16a.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201612/07/20161207205953_XWxmZ.thumb.700_0.jpeg"
            , "http://img.52z.com/upload/news/image/20190730/20190730012945_77036.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201612/14/20161214221015_54XzW.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201409/30/20140930134136_wk4Jz.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566458254512&di=6c8acdbf86777c61c655bdcc1a7ff9f5&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801123644_spopq.jpg"
            , "http://img4q.duitang.com/uploads/item/201401/17/20140117103922_vQX2G.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201504/26/201504262856_sWQJX.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201610/03/20161003201001_jGitY.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201607/27/20160727143727_v5kRZ.jpeg"
            , "http://cdn.duitang.com/uploads/blog/201404/22/20140422142715_8GtUk.thumb.600_0.jpeg"
            , "http://cdn.duitang.com/uploads/blog/201404/22/20140422142715_8GtUk.thumb.600_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201512/14/20151214212928_RSGtj.thumb.700_0.jpeg"
            , "http://img5.imgtn.bdimg.com/it/u=1766699166,1649752907&fm=26&gp=0.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201606/12/20160612141706_ZuEez.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201803/04/20180304165637_RzBQW.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201608/16/20160816100317_freX8.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201607/19/20160719215827_Wts5C.jpeg"
            , "http://pic.baike.soso.com/ugc/baikepic2/29883/20150811164729-997575824.jpg/0"
            , "http://pic.baike.soso.com/ugc/baikepic2/29883/20150811164729-997575824.jpg/0"
            , "http://b-ssl.duitang.com/uploads/item/201812/10/20181210163023_xXazM.thumb.700_0.jpeg"
            , "http://imgnews.mumayi.com/file/2019/07/08/df9ec017338e1b2ee5394a18e1ad4500.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201708/07/20170807100933_dQjyh.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201502/13/20150213221657_hFdLn.jpeg"
            , "http://cdn.duitang.com/uploads/item/201406/07/20140607210859_2yLCL.thumb.700_0.png"
            , "http://b-ssl.duitang.com/uploads/item/201602/20/20160220011414_HAwhY.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201809/09/20180909141218_sfqyp.png"
            , "http://b-ssl.duitang.com/uploads/item/201609/12/20160912193354_2zctw.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201802/16/20180216162338_mUtHA.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201502/17/20150217001432_FzCEe.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201801/20/20180120124447_ahPUQ.jpeg"
            , "http://pic.rmb.bdstatic.com/56ef993c1f5178800dcdcb6f79ad7623.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201611/24/20161124105036_BdUP4.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201708/07/20170807003521_2tEPY.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201512/18/20151218205726_Sns5r.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201601/26/20160126161157_Xd8Gk.png"
            , "http://img5.duitang.com/uploads/item/201509/25/20150925095619_h42S3.jpeg"
            , "http://pic.rmb.bdstatic.com/3f0d86862e2545242a3d0cea252298ab.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201504/05/20150405H1151_MnHjd.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201412/30/20141230172716_AyFuU.thumb.700_0.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1566448247&di=24b7d0c772a991136f7a118702993d50&src=http://b-ssl.duitang.com/uploads/item/201706/25/20170625034549_32tME.thumb.700_0.png"
            , "http://b-ssl.duitang.com/uploads/item/201707/15/20170715095610_vRHVW.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201503/01/20150301111024_A8xTG.jpeg"};

    private static final String[] mGirlDesc = {"叮咚，你有新的爱意请注意查收。", "对你，漫山遍野的喜欢。", "我希望你做个甜甜的梦，我的意思是梦到我。",
            "喜欢你，比喜欢肥宅快乐水还多。", "累吗，点开乐园又看到我这个你得不到的女人。", "请收好今晚的月亮和我爱你。", "我真是太可爱了，连蚊子都要亲我一口。",
            "最近很想你，最远也是。", "其实我想一路小跑，跳到你的身上，吧唧亲你一口。", "我行我素，超甜还酷。", "没时间跟你斤斤计较，我还要忙着可爱。", " 不开心是最可怕的催老剂，你要做一个爱笑的小仙女。",
            "看你骨骼惊奇，是块和我谈恋爱的好料。", "好不好啊，真是一个温柔的词，像在摸着你的小脑袋。", "我要去宇宙了，回来摘星星给你。", "不是我喜欢的样子你都有，而是你所有的样子我都喜欢。",
            "背水举世皆敌，还是想把你拥入怀里。", "我还是想当被你喜欢的小猪，只管吃吃睡睡。", "星星藏眼睛，把喜欢给你。", " 我要把你捧在手心里，然后默默合拢手心，看我不憋死你。",
            "想做你的猫可以被你喂养还可以跟你睡觉。", "台风要来了请大家关好门窗万一你对象被吹到我家我是不会还的。", "喜欢你是一件麻烦的事，可我偏偏喜欢找麻烦。",
            "我喜欢你，不是情话，是心里话。", " 我没有喜欢的类型 你是什么样子 我就喜欢什么样子。", "你早晚都会是我的，只有中午不是。", "今后的路，我希望你好好走下去，而我，坐车。",
            "趁你心里一暖，捏成爱我的形状。", "你好坏呀，偷偷进了我的心，还在里面反锁了。", "你不和我聊天是怕我偷你表情包嘛。", "别追公交车了，追我吧我跑得慢还有点可爱。",
            "我只想把我所以的温柔给你。", "我想做你迷人的前女友，怀念不可得，日思不可求", "就是很喜欢你啊，喜欢你喜欢的不得了。", "想做一个被你宠爱的小姑娘，不开心就埋你怀里撒娇。",
            "所有的棒棒糖换一个你怎么样？", " 风往耳朵里灌，沙往眼睛里钻，而你，在心里乱蹿。", "我是沙，你是雕，我们在一起，就是沙雕。", " 我这种人，不配有朋友，无论男女，无论性取向，最后都会爱上我。",
            "我的被子好软好香好舒服 要不要一起盖。", "我告诉你啊，今晚别关窗户我想偷偷住进你梦里。", "离睡着还差一万九千七百八十次想你。", "肉长出来还可以减，但那些零食过期就不能再吃了。",
            "我终究是你跑百里路也追不到的姑娘。", "所谓缘分就是，我想接吻，而你正好有嘴，你说巧不巧。", " 您好，这里有一箱情愿，请您签收一下。", "小哥哥我给你卖个萌，你给我一个小爱心好不好",
            "你挺喜欢我那是你不了解我，你要是了解我你都得爱死我。", "你要是再说一句，我就把你的心偷走。", "偷偷告诉我，你是不是会魔法，让我好喜欢你。", "我要依偎在你怀里，做你一人的猫。", " 我整夜阑珊满脑子是你，才知道什么叫做着迷"};

    private static final int[] mGirlAge = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 18, 16, 17, 18,
            19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 25, 26, 22, 20, 21, 23};

    private static final String[] mGirlBirthday = {"1991-8-8", "1997-5-13", "1998-9-23", "1991-10-21", "1991-10-21",
            "1991-6-15", "1991-8-2", "1991-7-8", "1994-2-24", "1996-11-14", "1997-5-24", "1995-9-21", "1996-6-21", "1992-9-21", "1992-7-3",
            "1992-7-20", "1992-9-3", "1992-6-9", "1993-5-16", "1995-6-15", "1996-6-25", "1996-8-21", "1997-1-21", "1993-10-21", "1993-8-20",
            "1993-8-25", "1993-10-4", "1993-5-10", "1992-8-7", "1994-5-16", "1995-7-26", "1998-7-21", "1998-3-21", "1994-11-21", "1994-9-21",
            "1994-9-6", "1994-11-5", "1994-4-25", "1991-9-5", "1993-3-17", "1994-8-27", "1991-6-21", "1999-5-21", "1995-12-21", "1995-10-6",
            "1995-10-9", "1995-12-6", "1995-3-20", "1998-10-5", "1992-1-18", "1993-9-28", "1992-5-21"};

    private static final String[] mGirlConste = {
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "处女座", "天秤座", "天蝎座", "射手座"};

    private static final String[] mGirlHobby = {
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "游戏", "发呆", "卖萌", "网恋"};

    private static final String[] mGirlStatus = {
            "已婚", "单身", "热恋", "单身",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单恋",
            "已婚", "单身", "单身", "单身",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋"};

    private static final String[] mBoyPhone = {"17983964401", "17982964402", "17981964403", "17980964404", "17979964405", "17978964406", "17977964407", "17976964409"
            , "17975964411", "17974964421", "17973964431", "17972964441", "1797164451", "17970964461", "17969964471", "17968964481", "17967964491", "17966964401"
            , "17965964411", "17964964411", "17963964411", "17966964411", "17961964411", "17960964411", "17959864411", "17958764411", "17957664411", "17956654411"
            , "17955644411", "17954634411", "17953624411", "17952614411", "17951604411", "17950601411", "17949602411", "17948603411", "17947605411", "17946606411"
            , "17945603411", "17944603211", "17943603111", "17942603011", "17941603001", "17940603000", "17939603000", "17938603000", "17937603000", "17936603000"
            , "17935603000", "17934603000", "17933603000", "17932603000"};

    private static final String[] mBoyName = {"故人何必念情", "只谈情不闲聊", "哥、不愿将就", "是我闯入你的生活", "孤独,是一种态度", "最深的爱/最沉默", "没感情就绝交吧。",
            "待我发光闪瞎你", "你可以滚了!", "笑你作", "没心没肺的我很累", "乱世杀手", "宁无拥人", "你独揽我", "奇迹少年", "温柔一只鬼", "需索欲望", "缠绵勾画", "呼之欲出",
            "稍纵即逝", "局外人", "噬月者", "没感情就绝交吧。", "爱情,我戒了", "人海一粒渣@", "滚开我要变身了", "情感防御", "世俗", "退到无路可退", "时间冲淡一切回忆", "心在流浪",
            "丢了天真失了美好", "自甘堕落", "我把心寄错了地址", "脸红派", "软肋", "禁忌", "触碰岁月", "一派天真", "探测极限", "绊绊绊绊死你", "你的怜悯我不需要i", "我要逆袭!",
            "陪你浪迹天涯", "努力吧", "想为自己拼一次", "不拼不博,三年白活", "少年当自强", "我叫坚强我不哭", "安守", "三爷", "怎忧"};

    private static final String[] mBoyPhoto = {
            "http://b-ssl.duitang.com/uploads/item/201804/29/20180429210111_gtsnf.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201803/15/20180315180731_umedu.jpeg"
            , "http://img4.duitang.com/uploads/item/201410/01/20141001151803_SeJ3B.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201509/19/20150919180024_mQvRW.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566458168063&di=d2e75dcc9916cb0f952f631d4fa96d62&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20181201%2Fd3b374babc734361b7f4f6578f7f6af7.jpeg"
            , "http://hbimg.b0.upaiyun.com/5bd10b8f1c24535f1f9806969203646ca406cc87251ba-gp3V2h_fw658"
            , "http://b-ssl.duitang.com/uploads/item/201801/28/20180128174403_XKTFc.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201605/14/20160514183509_QYd2j.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201607/10/20160710074608_HdFam.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201708/07/20170807150206_xzCMT.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201512/06/20151206180721_T2iJx.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201703/02/20170302151902_FM8PV.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201502/13/20150213004658_XPne4.jpeg"
            , "http://img5q.duitang.com/uploads/item/201504/04/20150404H0759_GRZKa.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201611/19/20161119132113_iuW2G.thumb.700_0.jpeg"
            , "http://img3.imgtn.bdimg.com/it/u=951011087,593656084&fm=11&gp=0.jpg"
            , "http://img4.imgtn.bdimg.com/it/u=4261901439,2439890501&fm=11&gp=0.jpg"
            , "http://cdn.duitang.com/uploads/item/201511/16/20151116201202_2Nnhr.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201611/12/20161112135446_aiBNh.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201508/10/20150810000747_JiPUa.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201502/21/20150221234306_RjZwG.jpeg"
            , "http://images.liqucn.com/img/h21/h48/img_localize_4c867bc54887d25a97057ed72d8e111c_400x400.png"
            , "http://imgnews.mumayi.com/file/2019/07/29/b97ee850351435a83ea73fe22b54006b.jpg"
            , "http://imgnews.mumayi.com/file/2019/07/11/5fce0c4b79d054f81eea562e3ba769db.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201611/12/20161112220110_sTUSr.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201803/10/20180310112233_wwtah.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201511/29/20151129103955_z8NaB.jpeg"
            , "http://img3.duitang.com/uploads/item/201502/03/20150203144941_BMPWu.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201807/31/20180731152128_syiui.thumb.700_0.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201609/13/20160913191835_ZyXjv.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201512/25/20151225213504_efRFz.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201512/11/20151211131943_x8eHN.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201705/22/20170522003649_xFc5m.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201607/19/20160719231744_XJN4w.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201603/27/20160327104619_iktwC.thumb.700_0.jpeg"
            , "http://cdn.duitang.com/uploads/item/201502/12/20150212143800_EKHEj.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201702/04/20170204174627_VP4yJ.jpeg"
            , "http://pic2.zhimg.com/v2-e7807853bb7b3d49f883fe367413328d_b.jpg"
            , "http://p1.qzone.la/upload/9/94177465-aec2-4e67-86c7-ebdfb70e732a.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201703/08/20170308202015_jREtW.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201507/28/20150728202141_ZLHyN.thumb.700_0.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201808/02/20180802231508_wchcp.jpg"
            , "http://img3.duitang.com/uploads/item/201601/25/20160125233709_STthZ.jpeg"
            , "http://b-ssl.duitang.com/uploads/item/201608/12/20160812051230_ySxAV.thumb.700_0.jpeg"
            , "http://img5.duitang.com/uploads/item/201408/14/20140814165959_VCtan.thumb.700_0.png"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1566448219&di=1ca89a341c0939cc3339ba9e017227e0&src=http://5b0988e595225.cdn.sohucs.com/images/20171215/5d9c7f00d882473b8bb3b039611d99ad.jpeg"
            , "http://img.52z.com/upload/news/image/20181224/20181224092534_14054.jpg"
            , "http://img3.duitang.com/uploads/item/201601/12/20160112200717_YE5GT.jpeg"
            , "http://img3.duitang.com/uploads/item/201512/13/20151213224957_5vdwe.thumb.700_0.jpeg"
            , "http://img.52z.com/upload/news/image/20180423/20180423052858_22336.jpg"
            , "http://b-ssl.duitang.com/uploads/item/201609/28/20160928230144_QARdX.thumb.700_0.png"
            , "http://img5.duitang.com/uploads/item/201505/19/20150519080940_FCW2K.jpeg"};

    private static final String[] mBoyDesc = {"我又不是10086，凭什么24小时为你待机。", "青春就是努力奔跑，然后华丽的跌倒", "我被子生病了，要好好照顾它", "我掐指一算，发现你命里缺我。"
            , "我的青春期遇上了我妈的更年期。", "欲考及格 必承其累 ---复习者们", "我的优点是：我很帅。我的缺点是：我帅的不明显。", "心狠手辣的我舔了一下自己的手指被辣哭了。",
            "今日才知晓，竟是有那么多人稀罕我的一辈子，偏偏捧到他手里，偏偏他不要。", "过年最老的一句话：今年过节不收礼呀，收礼就收脑白金!", "只要心还愿攀登，就没有到不了的高度。",
            "我要拼命的笑，笑到灵魂都颤抖。", "我爱你，关你啥事，有本事你也爱我试试？。", "爱情就如卫生纸，别老是扯", "其实最好的日子，无非是你在闹，他在笑。",
            "哪里跌倒哪里爬起，总是在那跌倒，我怀疑那有里个坑", "原来我们小时候真的好性感，连裤衩都不穿。", "做人要做刘备，因为诸葛亮是和他混的。", "主动久了，每个人都会累",
            "世界上本没有胖子，瘦的人多了，也就有了胖子!", "爱情都败给了所谓的友情", "天若有情天亦老， 女人多情死的早", "请不要叫我宅男，请叫我闭家锁;请不要叫我宅女，请叫我居里夫人。",
            "虽然没有男朋友，但是有闺蜜相陪，也很幸福!", "陌生人的骂，我可以以牙还牙，熟悉的人伤，我却招架不了", "世界上最痛苦的事就是--笑脸相迎你最讨厌的人.",
            "回头爱你,真的太难", "每个人都有神经病，我属于比较严重的那种", "我现在能放下的只有筷子了。", "老天请让我再长高五厘米，我愿意以瘦十斤的代价来交换。",
            "喝药递瓶，上吊给绳，跳楼的挥着小手绢送行", "其实我也算不错 要不要试试喜欢我", "所谓棋逢高手就是小三和正牌叫板的画面，非常和谐画面。", "你要知道 我强悍的皮囊下 是个易崩溃的人",
            "前有宝马开路，后有奔驰尾随，中间毛驴插过", "脾气不好是因为没睡饱 我睡饱了的话可萌了", "我拿一辈子跟你赌，你最好别让我输。", "我的东西从来不与人分享 因为我想赢所以你必须输",
            "别说你很爱我，有本事你过年带我见你爸妈去。", "拒绝了一个很喜欢我的人 他们就说 你真贱", "如果你有个梦想就必须捍卫它。", "逼自己优秀，将来骄傲的生活。",
            "天下不会掉馅饼，撸起袖子加油干。", "做别人认定你做不了的那个人。", "靠山山会倒，靠水水会流，靠自己永久不倒。", "你不努力一把，怎么知道自己多优秀。",
            "愿你的今天胜过昨天。", "愿我们都有一个不辜负的人生", "别给自己留退路，要不然会失去奋斗的念头。", "我想努力成为能够待在他身边的人。", "现在的努力为了以后的出人头地。", "你要乖，要长大，要不负众望。"};

    private static final int[] mBoyAge = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 18, 16, 17, 18,
            19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 25, 26, 22, 20, 21, 23};

    private static final String[] mBoyBirthday = {"1991-8-8", "1997-5-13", "1997-9-23", "1991-10-21", "1991-10-21",
            "1991-6-15", "1992-8-2", "1988-7-8", "1994-2-24", "1996-11-14", "1996-5-24", "1995-9-21", "1996-6-21", "1992-9-21", "1992-7-3",
            "1992-7-20", "1995-9-3", "1996-6-9", "1993-5-16", "1995-6-15", "1996-6-25", "1996-8-21", "1997-1-21", "1993-10-21", "1993-8-20",
            "1993-8-25", "1996-10-4", "1993-5-10", "1992-8-7", "1994-5-16", "1995-7-26", "1998-7-21", "1998-3-21", "1994-11-21", "1994-9-21",
            "1994-9-6", "1994-11-5", "1994-4-25", "1991-9-5", "1993-3-17", "1994-8-27", "1991-6-21", "1999-5-21", "1995-12-21", "1995-10-6",
            "1995-10-9", "1995-12-6", "1995-3-20", "1998-10-5", "1992-1-18", "1993-9-28", "1992-5-21"};

    private static final String[] mBoyConste = {
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水平座", "双鱼座",
            "处女座", "天秤座", "天蝎座", "射手座"};

    private static final String[] mBoyHobby = {
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "运动", "音乐", "电影", "绘画", "小说", "冒险", "拼图", "收藏", "游戏", "发呆", "卖萌", "网恋",
            "游戏", "发呆", "卖萌", "网恋"};

    private static final String[] mBoyStatus = {
            "已婚", "单身", "热恋", "单身",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋",
            "单身", "单身", "单身", "单恋",
            "已婚", "单身", "单身", "单身",
            "单身", "单身", "单身", "单身",
            "已婚", "单身", "热恋", "单恋"};

    /**
     * 模拟数据
     */
    public static void testData() {
        for (int i = 0; i < mGirlPhone.length; i++) {
            testData(mBoyPhone[i], mBoyName[i], mBoyPhoto[i], true, mBoyDesc[i], mBoyAge[i], mBoyBirthday[i], mBoyConste[i], mBoyHobby[i], mBoyStatus[i]);
            testData(mGirlPhone[i], mGirlName[i], mGirlPhoto[i], false, mGirlDesc[i], mGirlAge[i], mGirlBirthday[i], mGirlConste[i], mGirlHobby[i], mGirlStatus[i]);
        }
    }

    //因为拿的是我们本地对象模拟，所以需要清除数据
    private static void testData(String phone, String nickName, String photoUrl, boolean sex, String desc, int age, String birthday, String constellation, String hobby, String status) {
        IMUser user = new IMUser();

        user.setTokenNickName(nickName);
        user.setTokenPhoto(photoUrl);

        user.setUsername(phone);
        user.setPassword("123456");

        user.setNickName(nickName);
        user.setPhoto(photoUrl);
        user.setSex(sex);
        user.setDesc(desc);

        user.setMobilePhoneNumber(phone);
        user.setMobilePhoneNumberVerified(true);

        user.setAge(age);
        user.setBirthday(birthday);
        user.setConstellation(constellation);
        user.setHobby(hobby);
        user.setStatus(status);

        user.signUp(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    LogUtils.e("Error:" + e.toString());
                }
            }
        });

    }
}
