package com.lvqingyang.imagestyletransfer.Community;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.GoogleDotView;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseFragment;
import com.lvqingyang.imagestyletransfer.bean.Picture;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import org.lasque.tusdk.core.TuSdk;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.lvqingyang.imagestyletransfer.R.id.refreshLayout;


/**
 * 　　┏┓　　  ┏┓+ +
 * 　┏┛┻━ ━ ━┛┻┓ + +
 * 　┃　　　             ┃
 * 　┃　　　━　　   ┃ ++ + + +
 * ████━████     ┃+
 * 　┃　　　　　　  ┃ +
 * 　┃　　　┻　　  ┃
 * 　┃　　　　　　  ┃ + +
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃
 * 　　　┃　　　┃ + + + +
 * 　　　┃　　　┃
 * 　　　┃　　　┃ +  神兽保佑
 * 　　　┃　　　┃    代码无bug！
 * 　　　┃　　　┃　　+
 * 　　　┃　 　　┗━━━┓ + +
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━神兽出没━━━━━━
 * Author：LvQingYang
 * Date：2017/7/5
 * Email：biloba12345@gamil.com
 * Info：
 */

public class PictureFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SolidRVBaseAdapter mAdapter;
    private int mType;
    private static final String KEY_TYPE = "TYPE";
    private List<Picture> mPictures=new ArrayList<>();
    private static final String TAG = "PictureFragment";
    private int mPageCount;
    private TwinklingRefreshLayout mRefreshLayout;
    //    private static final String BOUNDLE_COUNT = "BOUNDLE_COUNT";
//    private Bundle savedState;
//    private static final String KEY_SAVED_STAYE = "SAVED_STAYE";

    public static PictureFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE,type);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture,container,false);
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pic_rv);
        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(refreshLayout);
    }

    @Override
    protected void setListener() {
        BallPulseView bpv=new BallPulseView(getActivity());
        bpv.setAnimatingColor(getResources().getColor(R.color.colorAccent));
        mRefreshLayout.setBottomView(bpv);
        mRefreshLayout.setHeaderView(new GoogleDotView(getActivity()));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadPics();
            }
        });
    }

    @Override
    protected void initData() {
        if (mPageCount==0) {
            mType=getArguments().getInt(KEY_TYPE);
            mAdapter=new SolidRVBaseAdapter<Picture>(getActivity(), mPictures) {
                @Override
                protected void onBindDataToView(SolidCommonViewHolder holder, Picture bean) {
                    holder.setImageFromInternet(R.id.iv,bean.getImgUrl());
                }

                @Override
                public int getItemLayoutID(int viewType) {
                    return R.layout.item_picture;
                }

                @Override
                protected void onItemClick(View view, int position, Picture bean) {
                    super.onItemClick(view, position, bean);
                    PictureDetailActivity.start(mContext, mPictures, position);
                }
            };

            loadPics();
        }
    }

    @Override
    protected void setData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void getBundleExtras(Bundle bundle) {
    }

    private void loadPics(){
        BmobQuery<Picture> query = new BmobQuery<Picture>();
        query.include("poster");
        query.addWhereEqualTo("type", mType-1)
            .setSkip(10*mPageCount) // 忽略前10条数据（即第一页数据结果）
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            .setLimit(10)
            //执行查询方法
            .findObjects(new FindListener<Picture>() {
            @Override
            public void done(List<Picture> object, BmobException e) {
                if(e==null){
                    mRefreshLayout.finishLoadmore();
                    mPageCount++;
                    if (object.size()>0) {
                        mAdapter.addItems(object);
                    }else {
                        TuSdk.messageHub().showToast(getActivity(), R.string.no_more);
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState: ");
//        // Save State Here
//        saveStateToArguments();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d(TAG, "onDestroyView: ");
//        // Save State Here
//        saveStateToArguments();
//    }
//
//    private void saveStateToArguments() {
//        if (getView() != null)
//            savedState = saveState();
//        if (savedState != null) {
//            Bundle b = getArguments();
//            b.putBundle(KEY_SAVED_STAYE, savedState);
//        }
//    }
//
//    ////////////////////
//    // Don't Touch !!
//    ////////////////////
//
//    private boolean restoreStateFromArguments() {
//        Bundle b = getArguments();
//        savedState = b.getBundle(KEY_SAVED_STAYE);
//        if (savedState != null) {
//            restoreState();
//            return true;
//        }
//        return false;
//    }
//
//    /////////////////////////////////
//    // Restore Instance State Here
//    /////////////////////////////////
//
//    private void restoreState() {
//        if (savedState != null) {
//            mPageCount=
//            onRestoreState(savedState);
//        }
//    }
//
//    //////////////////////////////
//    // Save Instance State Here
//    //////////////////////////////
//
//    private Bundle saveState() {
//        Bundle state = new Bundle();
//        state.putInt(BOUNDLE_COUNT,mPageCount);
//        return state;
//    }

}
