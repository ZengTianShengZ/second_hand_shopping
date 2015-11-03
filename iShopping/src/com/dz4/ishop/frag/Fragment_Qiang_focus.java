package com.dz4.ishop.frag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

import com.dz4.ishop.adapter.QiangListAdapter;
import com.dz4.ishop.domain.QiangItem;
import com.dz4.ishop.domain.User;
import com.dz4.ishop.listener.TitlechangeListener;
import com.dz4.ishop.test.QiangYu;
import com.dz4.ishop.utils.Constant;
import com.dz4.ishop.utils.LogUtils;
import com.dz4.ishopping.R;
import com.dz4.support.widget.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
/**
 * 
 * �ѹ�ע��ǽ
 * @author MZone
 *
 */
public class Fragment_Qiang_focus extends BaseFragment {
	
	
	private View contentView ;
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private TextView networkTips;
	private ProgressBar progressbar;
	
	private ArrayList<QiangItem> mListItems;
	private QiangListAdapter mQiangListAdapter;
	private enum RefreshType {Refresh,Loadmore};
	private RefreshType mRefreshType=RefreshType.Loadmore;
	private int pageNum;
	public static BaseFragment newInstance(int position) {
		// TODO �Զ����ɵķ������
		BaseFragment fragment = new Fragment_Qiang_focus();
		Bundle bundle = new Bundle();
		bundle.putInt("page", position);
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		contentView = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_qiang, null, false);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//contentView = inflater.inflate(R.layout.fragment_qiang, null);
		super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}
	@Override
	public void processHandlerMessage(Message msg) {
		// TODO �Զ����ɵķ������
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onViewCreated(view, savedInstanceState);
	}
	@Override
	public void initView() {
		// TODO �Զ����ɵķ������
		mPullRefreshListView = (PullToRefreshListView)contentView
				.findViewById(R.id.pull_refresh_list);
		networkTips = (TextView)contentView.findViewById(R.id.networkTips);
		progressbar = (ProgressBar)contentView.findViewById(R.id.progressBar);
		actualListView = mPullRefreshListView.getRefreshableView();
	}

	@Override
	public void initData() {
		// TODO �Զ����ɵķ������
		mPullRefreshListView.setMode(Mode.BOTH);
		mListItems =new ArrayList<QiangItem>();
		mQiangListAdapter = new QiangListAdapter(getContext(),mListItems);
		actualListView.setAdapter(mQiangListAdapter);
		if(mListItems.size() == 0){
			mRefreshType=RefreshType.Refresh;
			pageNum=0;
			loadData();
		}	
	}
	
	@Override
	public void initEvent() {
		// TODO �Զ����ɵķ������
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase refreshView) {
				// TODO �Զ����ɵķ������
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mRefreshType=RefreshType.Refresh;
				pageNum=0;
				loadData();
			}
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase refreshView) {
				// TODO �Զ����ɵķ������
				mRefreshType=RefreshType.Loadmore;
				loadData();
				
			}
		});
	}
	
	protected void loadData() {
		// TODO �Զ����ɵķ������
		setLoadingState(LOADING);
		BmobQuery<QiangYu> query = new BmobQuery<QiangYu>();
		query.order("-createdAt");
//		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
		query.addWhereLessThan("createdAt", date);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.include("author");
		query.findObjects(getActivity(), new FindListener<QiangYu>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO �Զ����ɵķ������
				pageNum--;
				setLoadingState(LOADING_FAILED);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(List<QiangYu> list) {
				// TODO �Զ����ɵķ������
				if(list.size()!=0&&list.get(list.size()-1)!=null){
					if(mRefreshType==RefreshType.Refresh){
						mListItems.clear();
					}
					if(list.size()<Constant.NUMBERS_PER_PAGE){
					}
					List<QiangItem> Targetlist = ClassCastInterface(list);
					mListItems.addAll(Targetlist);
					mQiangListAdapter.notifyDataSetChanged();
					
					setLoadingState(LOADING_COMPLETED);
					mPullRefreshListView.onRefreshComplete();
				}else{
					pageNum--;
					setLoadingState(LOADING_COMPLETED);
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
		
	}
	protected List<QiangItem> ClassCastInterface(
			List<QiangYu> list) {
		// TODO �Զ����ɵķ������
		ArrayList<QiangItem> targetlist=null;
		if(list!=null){
			targetlist = new ArrayList<QiangItem>();
			for(QiangYu qy:list){
				User user = new User();
				QiangItem mQiangItem = new QiangItem();
				user.setUsername(qy.getAuthor().getUsername());
				user.setIcon(qy.getAuthor().getAvatar());
				user.setFavorite(qy.getAuthor().getFavorite());
				user.setSex(qy.getAuthor().getSex());
				mQiangItem.setAnthor(user);
				mQiangItem.setContent(qy.getContent());
				mQiangItem.setComment(qy.getComment());
				mQiangItem.setHate(qy.getHate());
				mQiangItem.setLove(qy.getLove());
				mQiangItem.setFocus(qy.getMyFav());
				mQiangItem.setRelation(qy.getRelation());
				mQiangItem.setContentfigureurl(qy.getContentfigureurl());
				mQiangItem.setShare(qy.getShare());
				mQiangItem.setMyLove(qy.getMyLove());
				targetlist.add(mQiangItem);
				user=null;
				mQiangItem=null;
			}
		}
		return targetlist;
	}
	private static final int LOADING = 1;
	private static final int LOADING_COMPLETED = 2;
	private static final int LOADING_FAILED =3;
	private static final int NORMAL = 4;
	public void setLoadingState(int state){
		switch (state) {
		case LOADING:
			if(mListItems.size() == 0){
				mPullRefreshListView.setVisibility(View.GONE);
				progressbar.setVisibility(View.VISIBLE);
			}
			networkTips.setVisibility(View.GONE);
			
			break;
		case LOADING_COMPLETED:
			networkTips.setVisibility(View.GONE);
			progressbar.setVisibility(View.GONE);
			
		    mPullRefreshListView.setVisibility(View.VISIBLE);
		    mPullRefreshListView.setMode(Mode.BOTH);

			
			break;
		case LOADING_FAILED:
			if(mListItems.size()==0){
				mPullRefreshListView.setVisibility(View.VISIBLE);
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				networkTips.setVisibility(View.VISIBLE);
			}
			progressbar.setVisibility(View.GONE);
			break;
		case NORMAL:
			
			break;
		default:
			break;
		}
	}
}
