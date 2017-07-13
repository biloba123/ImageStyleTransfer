package com.lvqingyang.imagestyletransfer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lvqingyang.imagestyletransfer.Community.CommunityActivity;
import com.lvqingyang.imagestyletransfer.Setting.SettingActivity;
import com.lvqingyang.imagestyletransfer.User.UserActivity;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.tool.ComponentHelper;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mCameraIv;
    private ImageView mPhotoIv;
    private ImageView mCommunityIv;
    private ImageView mSettingIv;
    private ImageView mUserIv;
    private static final String TAG = "MainActivity";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        // 异步方式初始化滤镜管理器
        // 需要等待滤镜管理器初始化完成，才能使用所有功能
        TuSdk.messageHub().setStatus(this, R.string.lsq_initing);
        TuSdk.checkFilterManager(mFilterManagerDelegate);

        mCameraIv = (ImageView) findViewById(R.id.camera_iv);
        mPhotoIv = (ImageView) findViewById(R.id.photo_iv);
        mCommunityIv = (ImageView) findViewById(R.id.communtiy_iv);
        mSettingIv = (ImageView) findViewById(R.id.setting_iv);
        mUserIv = (ImageView) findViewById(R.id.user_iv);
    }

    @Override
    protected void setListener() {
        mCameraIv.setOnClickListener(this);
        mPhotoIv.setOnClickListener(this);
        mCommunityIv.setOnClickListener(this);
        mUserIv.setOnClickListener(this);
        mSettingIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bmob.initialize(this, "bf3fef2683cecf005c55a4f6ce32c643");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);


//        BmobQuery<Picture> query = new BmobQuery<Picture>();
//        query
////            .setSkip(10) // 忽略前10条数据（即第一页数据结果）
//                //返回50条数据，如果不加上这条语句，默认返回10条数据
//                .setLimit(200)
//                //执行查询方法
//                .findObjects(new FindListener<Picture>() {
//                    @Override
//                    public void done(List<Picture> list, BmobException e) {
//                        if(e==null){
//                            Log.d(TAG, "done: "+list.size());
//                            List<BmobObject> bs=new ArrayList<BmobObject>();
//                            for (int i = 0; i < list.size(); i++) {
//                                Picture pic=list.get(i);
//                                String url=pic.getImgUrl();
//                                if (url.endsWith(".jpg")&&!url.contains("sinaimg")&&!url.contains("bmob")) {
//                                    pic.setImgUrl(url.replace(".jpg",""));
//                                    bs.add(pic);
//                                }
//                                new BmobBatch().updateBatch(bs).doBatch(new QueryListListener<BatchResult>() {
//
//                                    @Override
//                                    public void done(List<BatchResult> o, BmobException e) {
//                                        if(e==null){
//                                            for(int i=0;i<o.size();i++){
//                                                BatchResult result = o.get(i);
//                                                BmobException ex =result.getError();
//                                                if(ex==null){
//                                                    Log.d(TAG, "第"+i+"个数据批量更新成功："+result.getUpdatedAt());
//                                                }else{
//                                                    Log.d(TAG, "第"+i+"个数据批量更新失败："+ex.getMessage()+","+ex.getErrorCode());
//                                                }
//                                            }
//                                        }else{
//                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                                        }
//                                    }
//                                });
//                            }
//
//                        }else{
//                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                        }
//                    }
//                });

//
//        List<BmobObject> pics=new ArrayList<>();
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/cb2f95277d8821ab777c911ff57015a10f97723e13c20-fGo5Qm_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/a2ec3dbcc30a7f3b7259d46087bbf8cc4e62c482c0a0e-TWcLoD_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/5356f5972294d6ed31dcd246edef0f5a107f28b225c5f-Vg4Tik_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/e0305da843472a47f87a728f38b94992c59a37981b9ff-7lx8DG_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/e70aa20cb6fa722a42ec5fd9fced861d242b10bff54a-p6w3ew_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/2956855d299fd5ae15dc0ca150d6def6a6ddcd464baf0-glBK1o_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/f15f947a623874073a6024d25071760f38684d2112951-qGdFnR_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/46117d724b7021263334eb409f287c603e8805212555d-iMr3Cl_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/2ec6a7b654ba479bd1699fe246a7d8bdd91a87f91341d-FjboLF_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/24ea92b4820ff76d03ab4a2a5c2745868737e0e14936a-Olfa5j_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/e4fba148741954b1593d36e54a3f2963754e33942e0f8-523zaL_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/d04ddb7fa260f3362ac689648b8097d1520712af9c631-OAiAHl_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/e97624cae4bae85f569a5d58722a058fad5d015b604e8-qAFFWE_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/ab3a3a525c8ea2f1bca28f2783d9c1750b55cd4522f47-aKXSqD_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/7ff9c44ddd550a6fe20402ba308eeae12d706c7febc6-YeSAiB_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/966b293c39cbe1d9dd3bf04ec8c3d29eb8b43d7b131f9-AK42Yy_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/ea601473cc60d44773e5b770bb7e29b971cdcd5c58b0-blFXwR_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/22beb05960b9797c3c7220bee86d96d34ac745e3b812-AyO1v3_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/107cd452652adac47ca15ae58188b40bdd93f959783ac-HtRKcl_fw658"));
//        pics.add(new Picture(7,"","https://hbimg.b0.upaiyun.com/b7885b0e6d564a8d3d2c9b13ce237776fdf04ffc21525-T6is6H_fw658"));
//
//        new BmobBatch().insertBatch(pics).doBatch(new QueryListListener<BatchResult>() {
//
//                    @Override
//                    public void done(List<BatchResult> o, BmobException e) {
//                        if(e==null){
//                            for(int i=0;i<o.size();i++){
//                                BatchResult result = o.get(i);
//                                BmobException ex =result.getError();
//                                if(ex==null){
//                                    Log.d(TAG, "第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
//                                }else{
//                                    Log.d(TAG, "第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
//                                }
//                            }
//                        }else{
//                            Log.d("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                        }
//                    }
//                });

    }

    @Override
    protected void setData() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private FilterManager.FilterManagerDelegate mFilterManagerDelegate = new FilterManager.FilterManagerDelegate()
    {
        @Override
        public void onFilterManagerInited(FilterManager manager)
        {
            TuSdk.messageHub().showSuccess(MainActivity.this, R.string.lsq_inited);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_iv:
                ComponentHelper.showPhotoEdit(this);
                break;
            case R.id.camera_iv:
                ComponentHelper.showCamera(this);
                break;
            case R.id.communtiy_iv:
                CommunityActivity.start(this);
                break;
            case R.id.user_iv:
                UserActivity.start(this);
                break;
            case R.id.setting_iv:
                SettingActivity.start(this);
                break;
        }
    }
}
