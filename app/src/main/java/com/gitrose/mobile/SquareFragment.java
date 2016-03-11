package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.XListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.SearchAdapter;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.UserInfoList;
import com.handmark.pulltorefresh.library.QuickReturnType;
import com.gitrose.mobile.base.BaseFragment;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.model.TipsModel;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.QuickReturnListViewOnScrollListener;
import com.gitrose.mobile.view.ScrollGridView;
import java.util.ArrayList;

public class SquareFragment extends BaseFragment implements OnClickListener, IXListViewListener {
//    private ArrayList<SquareAd> adlist;
    private BaseDialog basedialog;
    private View cancelFansView;
    private View cancelFollowView;
    private int cancel_pos;
    private int cancel_relation;
    private View cancel_view;
    private View header_hot_ht_view;
//    private SquareHeaderModel header_model;
    private LinearLayout header_view;
    private RelativeLayout header_wheel_rl;
//    private SearchAdapter searchAdapter;
    private SearchAdapter searchAdapter;
//    private SquareHotHtModel hotHtModel;
    private TopicInfoList hotHtModel;
    private ArrayList<TopicInfo> hotHtlist;
    private UserInfoList hotUserModel;
    private ArrayList<TutuUsers> hotUserlist;
    private LinearLayout hot_ht_ll;
    private TextView hot_ht_tv;
    private TextView hot_user_tv;
    private int htlen;
    private ImageView[] imageViews;
    private int isHot;
    private int isfans;
    private ImageView iv_huati_1;
    private ImageView iv_huati_2;
    private ImageView iv_huati_3;
    private ImageView iv_huati_4;
    private ImageView iv_huati_5;
    private ImageView iv_huati_6;
    private int len;
    private XListView listView;
    OnClickListener mListAdapterOnclickClickListener;
    OnClickListener mOnclickClickListener;
    private QuickReturnListViewOnScrollListener scrollListener;
    private LinearLayout self_rank_ll;
    private TextView self_rank_tv;
//    private SquareFansModel squareFansModel;
    private LinearLayout square_ll;
//    private SquareStarAdapter starAdapter;
    private ScrollGridView star_gridview;
    private LinearLayout star_ll;
    private ArrayList<TipsModel> tips_list;
    private TextView tv_huati_1;
    private TextView tv_huati_2;
    private TextView tv_huati_3;
    private TextView tv_huati_4;
    private TextView tv_huati_5;
    private TextView tv_huati_6;
    private TextView tv_nearby;
    private TextView tv_new;
    private TextView tv_rankingList;
    private TextView tv_square;
    private TextView tv_zan;
//    private AutoScrollViewPager wheel_imgs;
    private LinearLayout wheel_ll_viewGroup;

    /* renamed from: com.gitrose.mobile.SquareFragment.1 */
//    class C07831 implements OnClickListener {
//        C07831() {
//        }
//
//        public void onClick(View v) {
//            int pos = ((Integer) v.getTag(R.id.square_star_pos)).intValue();
//            if (pos > -1 && ((SquareStar) SquareFragment.this.header_model.getStarlist().get(pos)).getUid() != null) {
//                SquareFragment.this.startPersonalActivity(((SquareStar) SquareFragment.this.header_model.getStarlist().get(pos)).getUid());
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.SquareFragment.2 */
//    class C07842 implements OnClickListener {
//        C07842() {
//        }
//
//        public void onClick(View v) {
//            int pos = ((Integer) v.getTag()).intValue();
//            if (pos >= 0) {
//                switch (v.getId()) {
//                    case R.id.friends_user_head:
//                        if (SquareFragment.this.hotUserlist != null && pos < SquareFragment.this.hotUserlist.size()) {
//                            SquareFragment.this.startPersonalActivity(((SquareStar) SquareFragment.this.hotUserlist.get(pos)).getUid());
//                        }
//                    case R.id.fans_item_right_iv:
//                        int relation = ((SquareStar) SquareFragment.this.hotUserlist.get(pos)).getRelation();
//                        switch (relation) {
//                            case PersonalNewActivity.FRAGMENT_ONE /*0*/:
//                            case PersonalNewActivity.FRAGMENT_TWO /*1*/:
//                                SquareFragment.this.addFollow(pos, relation, v, 0);
//                            case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                            case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                                SquareFragment.this.cancel_pos = pos;
//                                SquareFragment.this.cancel_relation = relation;
//                                SquareFragment.this.cancel_view = v;
//                                SquareFragment.this.showCancelFollowDialog();
//                            default:
//                        }
//                    case R.id.square_hot_total_ll:
//                        HuaTi huati = new HuaTi();
//                        huati.setHuatitext(((SquareHotHtListModel) SquareFragment.this.hotHtlist.get(pos)).getIdstext());
//                        huati.setTopicid(((SquareHotHtListModel) SquareFragment.this.hotHtlist.get(pos)).getIds());
//                        huati.setType(true);
//                        Intent intent = new Intent(SquareFragment.this.getActivity(), ThemeActivity.class);
//                        intent.putExtra("huati", huati);
//                        SquareFragment.this.getActivity().startActivity(intent);
//                    default:
//                }
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.SquareFragment.3 */
//    class C07853 implements OnItemClickListener {
//        C07853() {
//        }
//
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//            position -= 2;
//            if (position >= 0) {
//                if (SquareFragment.this.isHot == 0) {
//                    HuaTi huati = new HuaTi();
//                    huati.setHuatitext(((SquareHotHtListModel) SquareFragment.this.hotHtlist.get(position)).getIdstext());
//                    huati.setTopicid(((SquareHotHtListModel) SquareFragment.this.hotHtlist.get(position)).getIds());
//                    huati.setType(true);
//                    Intent intent = new Intent(SquareFragment.this.getActivity(), ThemeActivity.class);
//                    intent.putExtra("huati", huati);
//                    intent.putExtra(Util.EXTRA_TOPICID, ((SquareHotHtListModel) SquareFragment.this.hotHtlist.get(position)).getIds());
//                    SquareFragment.this.getActivity().startActivity(intent);
//                } else if (SquareFragment.this.isHot == 1) {
//                    SquareFragment.this.startPersonalActivity(((SquareStar) SquareFragment.this.hotUserlist.get(position)).getUid());
//                }
//            }
//        }
//    }

    public static class ViewHolder {
        ImageView imageView;
    }

    /* renamed from: com.gitrose.mobile.SquareFragment.4 */
    class C14264 extends QuickReturnListViewOnScrollListener {
        C14264(QuickReturnType $anonymous0, View $anonymous1, int $anonymous2, View $anonymous3, int $anonymous4) {
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4);
        }

        protected void onRealScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

//    private class ImagePagerAdapter extends PagerAdapter {
//        private ImageLoader imageLoader;
//        private Context mContext;
//        private ArrayList<SquareAd> modelList;
//
//        /* renamed from: com.gitrose.mobile.SquareFragment.ImagePagerAdapter.1 */
//        class C07861 implements OnClickListener {
//            private final /* synthetic */ SquareAd val$model;
//
//            C07861(SquareAd squareAd) {
//                this.val$model = squareAd;
//            }
//
//            public void onClick(View v) {
//                Intent intent;
//                if (this.val$model.getIsurl() == 0) {
//                    HuaTi huati = new HuaTi();
//                    huati.setHuatitext("#" + this.val$model.getText());
//                    huati.setTopicid(this.val$model.getIds());
//                    huati.setType(true);
//                    intent = new Intent(SquareFragment.this.getActivity(), ThemeActivity.class);
//                    intent.putExtra("huati", huati);
//                    SquareFragment.this.getActivity().startActivity(intent);
//                } else if (this.val$model.getIsurl() == 1) {
//                    intent = new Intent(SquareFragment.this.getActivity(), WebViewActivity.class);
//                    intent.putExtra("web_url", this.val$model.getText());
//                    SquareFragment.this.getActivity().startActivity(intent);
//                }
//            }
//        }
//
//        public ImagePagerAdapter(Context context, ArrayList<SquareAd> modelList) {
//            this.mContext = context;
//            this.modelList = modelList;
//            this.imageLoader = ImageLoader.getInstance();
//        }
//
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager) container).removeView((View) object);
//        }
//
//        public void finishUpdate(View container) {
//        }
//
//        public int getCount() {
//            if (this.modelList == null || BaseConstants.ah.equals(this.modelList)) {
//                return 0;
//            }
//            if (this.modelList.size() != 1) {
//                return BaseConstants.f3843b;
//            }
//            return 1;
//        }
//
//        public Object instantiateItem(ViewGroup view, int position) {
//            ViewHolder viewHolder;
//            View imageLayout = null;
//            if (imageLayout == null) {
//                imageLayout = View.inflate(this.mContext, R.layout.square_index_page, null);
//                viewHolder = new ViewHolder();
//                viewHolder.imageView = (ImageView) imageLayout.findViewById(R.id.home_view_image);
//                imageLayout.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) imageLayout.getTag();
//            }
//            SquareAd model = (SquareAd) this.modelList.get(position % this.modelList.size());
//            viewHolder.imageView.getLayoutParams().height = (Integer.valueOf(((SquareAd) this.modelList.get(position % this.modelList.size())).getHeight()).intValue() * UiUtils.getInstance(this.mContext).getmScreenWidth()) / Integer.valueOf(((SquareAd) this.modelList.get(position % this.modelList.size())).getWidth()).intValue();
//            this.imageLoader.displayImage(((SquareAd) this.modelList.get(position % this.modelList.size())).getPicfiles(), viewHolder.imageView, Constant.BIG_PICTURE_OPTIONS);
//            ((ViewPager) view).addView(imageLayout, 0);
//            imageLayout.setOnClickListener(new C07861(model));
//            return imageLayout;
//        }
//
//        public boolean isViewFromObject(View view, Object object) {
//            return view.equals(object);
//        }
//
//        public void restoreState(Parcelable state, ClassLoader loader) {
//        }
//
//        public Parcelable saveState() {
//            return null;
//        }
//
//        public void startUpdate(View container) {
//        }
//    }

//    private class ViewPagerListener implements OnPageChangeListener {
//        private ArrayList<SquareAd> modelList;
//
//        public ViewPagerListener(ArrayList<SquareAd> modelList) {
//            this.modelList = modelList;
//        }
//
//        public void onPageScrollStateChanged(int arg0) {
//        }
//
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//
//        public void onPageSelected(int position) {
//            for (int i = 0; i < SquareFragment.this.imageViews.length; i++) {
//                SquareFragment.this.imageViews[position % this.modelList.size()].setImageResource(R.drawable.lunbo_select);
//                if (position % this.modelList.size() != i) {
//                    SquareFragment.this.imageViews[i].setImageResource(R.drawable.lunbo_unselect);
//                }
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.SquareFragment.5 */
//    class C17375 extends QGHttpHandler<SquareHeaderModel> {
//        C17375(Context $anonymous0) {
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(SquareHeaderModel data) {
//            SquareFragment.this.header_model = data;
//            if (data.getAdlist() != null) {
//                SquareFragment.this.adlist = data.getAdlist();
//                SquareFragment.this.initViewPagerHeight(SquareFragment.this.adlist);
//                SquareFragment.this.initViewpagerSmallIcon(SquareFragment.this.adlist);
//                SquareFragment.this.wheel_imgs.setAdapter(new ImagePagerAdapter(SquareFragment.this.getActivity(), SquareFragment.this.adlist));
//                SquareFragment.this.wheel_imgs.setCurrentItem(500 - (AutoCoverFlowHelper.RAPID_DELAY_TIME % SquareFragment.this.adlist.size()), false);
//                if (SquareFragment.this.adlist.size() > 1) {
//                    SquareFragment.this.wheel_imgs.startAutoScroll();
//                    SquareFragment.this.wheel_imgs.setInterval(com.tencent.imsdk.BaseConstants.DEFAULT_MSG_TIMEOUT);
//                }
//                SquareFragment.this.wheel_imgs.setOnPageChangeListener(new ViewPagerListener(SquareFragment.this.adlist));
//                SquareFragment.this.header_wheel_rl.setVisibility(0);
//            } else {
//                SquareFragment.this.header_wheel_rl.setVisibility(8);
//            }
//            if (data.getHothtlist() != null) {
//                SquareFragment.this.header_hot_ht_view.setVisibility(0);
//                SquareFragment.this.initHt(data.getHothtlist());
//            } else {
//                SquareFragment.this.header_hot_ht_view.setVisibility(8);
//            }
//            if (data.getStarlist() != null) {
//                SquareFragment.this.star_ll.setVisibility(0);
//                SquareFragment.this.starAdapter = new SquareStarAdapter(SquareFragment.this.getActivity(), data.getStarlist());
//                SquareFragment.this.starAdapter.setOnClick(SquareFragment.this.mOnclickClickListener);
//                SquareFragment.this.star_gridview.setAdapter(SquareFragment.this.starAdapter);
//                return;
//            }
//            SquareFragment.this.star_ll.setVisibility(8);
//        }
//    }

    /* renamed from: com.gitrose.mobile.SquareFragment.6 */
    class GetTopicSearchInfoHandler extends QGHttpHandler<TopicInfoList> {
        private final String val$direction;

        GetTopicSearchInfoHandler(Context $anonymous0, boolean $anonymous1, String str) {

            super($anonymous0, $anonymous1);
            this.val$direction = str;

        }

        public void onGetDataSuccess(TopicInfoList data) {
            SquareFragment.this.hotHtModel = data;

            if (data.getList() != null && data.getList().size() > 0) {
//                if (this.val$direction.equals(SearchActivity.UP)) {
//                    SquareFragment.this.hotHtlist.addAll(0, data.getList());
//                } else {
//                    SquareFragment.this.hotHtlist.addAll(data.getList());
//                }

                SquareFragment.this.hotHtlist.clear();
                SquareFragment.this.hotHtlist.addAll(data.getList());
            }

            SquareFragment.this.searchAdapter.SetList(SquareFragment.this.hotHtlist, 0);
        }

        public void onFinish() {
            SquareFragment.this.listView.setPullLoadEnable(true);
        }
    }

    /* renamed from: com.gitrose.mobile.SquareFragment.7 */
    class GetUsersInfoHandler extends QGHttpHandler<UserInfoList> {
        private final /* synthetic */ String val$direction;

        GetUsersInfoHandler(Context $anonymous0, boolean $anonymous1, String str) {

            super($anonymous0, $anonymous1);
            this.val$direction = str;

        }

        public void onGetDataSuccess(UserInfoList data) {
            SquareFragment.this.hotUserModel = data;
            if (data.getFollowlist() != null && data.getFollowlist().size() > 0) {
//                if (this.val$direction.equals(SearchActivity.UP)) {
//                    SquareFragment.this.hotUserlist.addAll(0, data.getList());
//                    return;
//                }
                SquareFragment.this.hotUserlist.clear();
                SquareFragment.this.hotUserlist.addAll(data.getFollowlist());
                SquareFragment.this.searchAdapter.SetUserList(SquareFragment.this.hotUserlist, 1);
            }
        }

        public void onFinish() {
            SquareFragment.this.listView.setPullLoadEnable(false);
        }
    }

    /* renamed from: com.gitrose.mobile.SquareFragment.8 */
    class C17408 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$pos;
        private final /* synthetic */ int val$relation;
        private final /* synthetic */ View val$v;

        C17408(Context $anonymous0, boolean $anonymous1, int i, int i2, View view) {

            super($anonymous0, $anonymous1);
            this.val$pos = i;
            this.val$relation = i2;
            this.val$v = view;

        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onGetDataSuccess(String data) {
//            if (data != null && !data.equals(BaseConstants.ah)) {
//                ((SquareStar) SquareFragment.this.hotUserlist.get(this.val$pos)).setRelation(Integer.parseInt(data));
//                SquareFragment.sendFollowBroadcast(SquareFragment.this.getActivity(), ((SquareStar) SquareFragment.this.hotUserlist.get(this.val$pos)).getUid(), data, Constant.FOLLOW_USER_ACTION);
//                if (this.val$relation == 0) {
//                    this.val$v.setBackgroundResource(R.drawable.fans_i_follow_button);
//                } else if (this.val$relation == 1) {
//                    this.val$v.setBackgroundResource(R.drawable.fans_2_follow_button);
//                }
//            }
        }
    }

    /* renamed from: com.gitrose.mobile.SquareFragment.9 */
    class C17419 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$pos;
        private final /* synthetic */ View val$v;

        C17419(Context $anonymous0, boolean $anonymous1, int i, View view) {

            super($anonymous0, $anonymous1);
            this.val$pos = i;
            this.val$v = view;

        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onGetDataSuccess(String data) {
//            if (!(data == null || data.equals(BaseConstants.ah))) {
//                ((SquareStar) SquareFragment.this.hotUserlist.get(this.val$pos)).setRelation(Integer.parseInt(data));
//                SquareFragment.sendFollowBroadcast(SquareFragment.this.getActivity(), ((SquareStar) SquareFragment.this.hotUserlist.get(this.val$pos)).getUid(), data, Constant.UNFOLLOW_USER_ACTION);
//            }
//            this.val$v.setBackgroundResource(R.drawable.fans_follow_me_button);
        }
    }

    public SquareFragment() {
        this.len = 150;
        this.htlen = 50;
        this.isHot = 0;
//        this.mOnclickClickListener = new C07831();
        this.isfans = 0;
//        this.mListAdapterOnclickClickListener = new C07842();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_square_fragment, null);
        initView(view);
//        getHeaderData();
        getHotHtDate(SearchActivity.UP, "", new StringBuilder(String.valueOf(this.htlen)).toString());
//        getHotUserDate(SearchActivity.UP, "", new StringBuilder(String.valueOf(this.len)).toString());
        return view;
    }

    private void initView(View view) {
//        this.square_ll = (LinearLayout) view.findViewById(R.id.ll_square_top);
//        this.tv_square = (TextView) view.findViewById(R.id.tv_square);
//        this.tv_square.setOnClickListener(this);
//        this.tv_square.setSelected(true);
//        this.tv_zan = (TextView) view.findViewById(R.id.tv_zan);
//        this.tv_zan.setOnClickListener(this);
//        this.tv_rankingList = (TextView) view.findViewById(R.id.tv_rankingList);
//        this.tv_rankingList.setOnClickListener(this);
//        this.tv_nearby = (TextView) view.findViewById(R.id.tv_nearby);
//        this.tv_nearby.setOnClickListener(this);
        this.listView = (XListView) view.findViewById(R.id.square_listview);
        this.listView.setPullLoadEnable(false);
        this.listView.setPullRefreshEnable(false);
        this.listView.setXListViewListener(this);
        this.header_view = (LinearLayout)view.findViewById(R.id.square_hot_huati_ll);
//        this.header_view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_square_fragment_header, null);
//        this.listView.addHeaderView(this.header_view);
//        this.listView.setAdapter(null);
        addListViewListener();
//        this.header_wheel_rl = (RelativeLayout) this.header_view.findViewById(R.id.square_wheel_rl);
//        this.wheel_imgs = (AutoScrollViewPager) this.header_view.findViewById(R.id.vp_square_imgs);
//        this.wheel_ll_viewGroup = (LinearLayout) this.header_view.findViewById(R.id.ll_viewGroup);
//        this.header_hot_ht_view = this.header_view.findViewById(R.id.square_hot_huati_rl);
//        this.tv_huati_1 = (TextView) this.header_view.findViewById(R.id.tv_huati_1);
//        this.iv_huati_1 = (ImageView) this.header_view.findViewById(R.id.iv_huati_1);
//        this.tv_huati_2 = (TextView) this.header_view.findViewById(R.id.tv_huati_2);
//        this.iv_huati_2 = (ImageView) this.header_view.findViewById(R.id.iv_huati_2);
//        this.tv_huati_3 = (TextView) this.header_view.findViewById(R.id.tv_huati_3);
//        this.iv_huati_3 = (ImageView) this.header_view.findViewById(R.id.iv_huati_3);
//        this.tv_huati_4 = (TextView) this.header_view.findViewById(R.id.tv_huati_4);
//        this.iv_huati_4 = (ImageView) this.header_view.findViewById(R.id.iv_huati_4);
//        this.tv_huati_5 = (TextView) this.header_view.findViewById(R.id.tv_huati_5);
//        this.iv_huati_5 = (ImageView) this.header_view.findViewById(R.id.iv_huati_5);
//        this.tv_huati_6 = (TextView) this.header_view.findViewById(R.id.tv_huati_6);
//        this.iv_huati_6 = (ImageView) this.header_view.findViewById(R.id.iv_huati_6);
//        this.hot_ht_ll = (LinearLayout) this.header_view.findViewById(R.id.square_hot_huati_ll);
        this.hot_ht_tv = (TextView) this.header_view.findViewById(R.id.square_hot_huati_tv);
        this.hot_user_tv = (TextView) this.header_view.findViewById(R.id.square_hot_user_tv);
//        this.self_rank_tv = (TextView) this.header_view.findViewById(R.id.square_hot_user_self_rank_tv);
//        this.self_rank_ll = (LinearLayout) this.header_view.findViewById(R.id.square_hot_user_self_rank_ll);
        this.hot_ht_tv.setOnClickListener(this);
        this.hot_user_tv.setOnClickListener(this);
//        this.header_view.findViewById(R.id.rl_huati_tilte).setVisibility(View.GONE);
//        this.star_ll = (LinearLayout) this.header_view.findViewById(R.id.square_super_star_ll);
//        this.star_gridview = (ScrollGridView) this.header_view.findViewById(R.id.square_super_star_gv);
        this.searchAdapter = new SearchAdapter(getActivity());
        this.hot_ht_tv.setSelected(true);
        this.hotHtlist = new ArrayList();
        this.hotUserlist = new ArrayList();
        this.listView.setAdapter(this.searchAdapter);
//        this.searchAdapter.setOnClickListener(this.mListAdapterOnclickClickListener);
//        this.listView.setOnItemClickListener(new C07853());
        this.basedialog = new BaseDialog(getActivity());
    }

    private void addListViewListener() {
        int headerHeight = getResources().getDimensionPixelSize(R.dimen.action_bar_height);
        this.scrollListener = new C14264(QuickReturnType.HEADER, this.header_view, -headerHeight, null, getResources().getDimensionPixelSize(R.dimen.home_foot_height));
        this.scrollListener.setCanSlideInIdleScrollState(true);
        this.listView.setOnScrollListener(this.scrollListener);
    }

    public void onRefresh() {
    }

    public void onLoadMore() {
        if (this.isHot == 0 && this.hotHtModel != null) {
            getHotHtDate(SearchActivity.DOWN, "", new StringBuilder(String.valueOf(this.htlen)).toString());
        }
    }

    private void getHeaderData() {
//        QGHttpRequest.getInstance().getAdList(getActivity(), new C17375(getActivity()));
    }

    private void getHotHtDate(String direction, String starthtid, String len) {
        QGHttpRequest.getInstance().getHomeHotOrFriendList(getActivity(), "", "", "", direction, true, new GetTopicSearchInfoHandler(getActivity(), false, direction));
//        QGHttpRequest.getInstance().getSquareHotHt(getActivity(), direction, starthtid, len, new GetTopicSearchInfoHandler(getActivity(), false, direction));
    }

    private void getHotUserDate(String direction, String starthtid, String len) {
        QGHttpRequest.getInstance().GetUsersInfoRequest(getActivity(), direction, starthtid, len, new GetUsersInfoHandler(getActivity(), false, direction));
    }

//    private void initViewPagerHeight(ArrayList<SquareAd> picModelList) {
//        if (picModelList != null && picModelList.size() > 0) {
//            int i = 0;
//            while (i < picModelList.size()) {
//                SquareAd picModel = (SquareAd) picModelList.get(i);
//                if (getActivity() != null) {
//                    this.wheel_imgs.getLayoutParams().height = (Integer.valueOf(picModel.getHeight()).intValue() * UiUtils.getInstance(getActivity()).getmScreenWidth()) / Integer.valueOf(picModel.getWidth()).intValue();
//                    i++;
//                } else {
//                    return;
//                }
//            }
//        }
//    }

//    private void initViewpagerSmallIcon(ArrayList<SquareAd> picModelList) {
//        this.imageViews = new ImageView[picModelList.size()];
//        for (int i = 0; i < picModelList.size(); i++) {
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setLayoutParams(new LayoutParams(-2, -2));
//            imageView.setPadding(5, 5, 5, 5);
//            this.imageViews[i] = imageView;
//            if (i == 0) {
//                this.imageViews[i].setImageResource(R.drawable.lunbo_select);
//            } else {
//                this.imageViews[i].setImageResource(R.drawable.lunbo_unselect);
//            }
//            this.wheel_ll_viewGroup.addView(this.imageViews[i]);
//        }
//    }

    public void onResume() {
        super.onResume();
//        if (this.wheel_imgs != null) {
//            this.wheel_imgs.startAutoScroll();
//        }
    }

    public void onPause() {
        super.onPause();
//        if (this.wheel_imgs != null) {
//            this.wheel_imgs.stopAutoScroll();
//        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.square_hot_huati_tv:
                this.isHot = 0;
                this.hot_ht_tv.setSelected(true);
                this.hot_user_tv.setSelected(false);
//                this.self_rank_ll.setVisibility(View.GONE);
//                this.searchAdapter.setList(this.hotHtlist);
                getHotHtDate(SearchActivity.UP, "", new StringBuilder(String.valueOf(this.htlen)).toString());
                break;
            case R.id.square_hot_user_tv:
                this.isHot = 1;
                this.hot_ht_tv.setSelected(false);
                this.hot_user_tv.setSelected(true);
//                if (this.hotUserModel != null) {
//                    if (this.hotUserModel.getOwnrank() == 0) {
//                        this.self_rank_tv.setText(getString(R.string.hot_user_no_rank));
//                    } else {
//                        this.self_rank_tv.setText(Html.fromHtml(getString(R.string.hot_user_rank_start) + " <font color='#f24c4c'>" + this.hotUserModel.getOwnrank() + "</font> " + getString(R.string.hot_user_rank_end)));
//                    }
//                    this.self_rank_ll.setVisibility(0);
//                    this.searchAdapter.setList(this.hotUserlist, 1);
//                }
                getHotUserDate(SearchActivity.UP, "", new StringBuilder(String.valueOf(this.htlen)).toString());
                break;
            case R.id.tv_square:
            case R.id.tv_zan:
            case R.id.tv_nearby:
            case R.id.tv_rankingList:
                if (getActivity() != null) {
                    ((SquareActivity) getActivity()).refreshFragment(v);
                }
                break;
            case R.id.pop_tv_submit:
                delFollow(this.cancel_pos, this.cancel_relation, this.cancel_view, 0);
                this.basedialog.dismiss();
                break;
            case R.id.pop_tv_cancel:
                this.basedialog.dismiss();
                break;
            case R.id.pop_tv_submit_fanslist:
                delFollow(this.cancel_pos, this.cancel_relation, this.cancel_view, 1);
                this.basedialog.dismiss();
                break;
            case R.id.pop_tv_cancel_fanslist:
                this.basedialog.dismiss();
                break;
            case R.id.iv_huati_2:
            case R.id.tv_huati_2:
            case R.id.iv_huati_3:
            case R.id.tv_huati_3:
            case R.id.iv_huati_1:
            case R.id.tv_huati_1:
            case R.id.iv_huati_4:
            case R.id.tv_huati_4:
            case R.id.iv_huati_5:
            case R.id.tv_huati_5:
            case R.id.iv_huati_6:
            case R.id.tv_huati_6:
//                SquareHt shuati = (SquareHt) v.getTag();
//                HuaTi huati = new HuaTi();
//                huati.setContent(shuati.getContent());
//                huati.setHuatitext(shuati.getIdstext());
//                huati.setTopicid(shuati.getTopicid());
//                huati.setType(true);
//                Intent intent = new Intent(getActivity(), ThemeActivity.class);
//                intent.putExtra("huati", huati);
//                getActivity().startActivity(intent);
//                animationForNew();
                break;
            default:
                break;
        }
    }

    public void showCancelFollowDialog() {
        if (this.cancelFollowView == null) {
            this.cancelFollowView = View.inflate(getActivity(), R.layout.dialog_base_layout, null);
            ((TextView) this.cancelFollowView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.sure_cancel_follow));
            this.cancelFollowView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
            this.cancelFollowView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        }
        this.basedialog.show(this.cancelFollowView);
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), PersonalNewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, user_id);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

//    private void initHt(List<SquareHt> huaTiList) {
//        if (huaTiList != null && huaTiList.size() > 0) {
//            SquareHt huaTi1 = (SquareHt) huaTiList.get(0);
//            ImageLoader.getInstance().displayImage(huaTi1.getContent(), this.iv_huati_1, Constant.AVATAR_OPTIONS);
//            this.tv_huati_1.setText(huaTi1.getIdstext());
//            this.iv_huati_1.setOnClickListener(this);
//            this.tv_huati_1.setOnClickListener(this);
//            this.iv_huati_1.setTag(huaTi1);
//            this.tv_huati_1.setTag(huaTi1);
//            SquareHt huaTi2 = (SquareHt) huaTiList.get(1);
//            ImageLoader.getInstance().displayImage(huaTi2.getContent(), this.iv_huati_2, Constant.AVATAR_OPTIONS);
//            this.tv_huati_2.setText(huaTi2.getIdstext());
//            this.iv_huati_2.setOnClickListener(this);
//            this.tv_huati_2.setOnClickListener(this);
//            this.iv_huati_2.setTag(huaTi2);
//            this.tv_huati_2.setTag(huaTi2);
//            SquareHt huaTi3 = (SquareHt) huaTiList.get(2);
//            ImageLoader.getInstance().displayImage(huaTi3.getContent(), this.iv_huati_3, Constant.AVATAR_OPTIONS);
//            this.tv_huati_3.setText(huaTi3.getIdstext());
//            this.iv_huati_3.setOnClickListener(this);
//            this.tv_huati_3.setOnClickListener(this);
//            this.iv_huati_3.setTag(huaTi3);
//            this.tv_huati_3.setTag(huaTi3);
//            SquareHt huaTi4 = (SquareHt) huaTiList.get(3);
//            ImageLoader.getInstance().displayImage(huaTi4.getContent(), this.iv_huati_4, Constant.AVATAR_OPTIONS);
//            this.tv_huati_4.setText(huaTi4.getIdstext());
//            this.iv_huati_4.setOnClickListener(this);
//            this.tv_huati_4.setOnClickListener(this);
//            this.iv_huati_4.setTag(huaTi4);
//            this.tv_huati_4.setTag(huaTi4);
//            SquareHt huaTi5 = (SquareHt) huaTiList.get(4);
//            ImageLoader.getInstance().displayImage(huaTi5.getContent(), this.iv_huati_5, Constant.AVATAR_OPTIONS);
//            this.tv_huati_5.setText(huaTi5.getIdstext());
//            this.iv_huati_5.setOnClickListener(this);
//            this.tv_huati_5.setOnClickListener(this);
//            this.iv_huati_5.setTag(huaTi5);
//            this.tv_huati_5.setTag(huaTi5);
//            SquareHt huaTi6 = (SquareHt) huaTiList.get(5);
//            ImageLoader.getInstance().displayImage(huaTi6.getContent(), this.iv_huati_6, Constant.AVATAR_OPTIONS);
//            this.tv_huati_6.setText(huaTi6.getIdstext());
//            this.iv_huati_6.setOnClickListener(this);
//            this.tv_huati_6.setOnClickListener(this);
//            this.iv_huati_6.setTag(huaTi6);
//            this.tv_huati_6.setTag(huaTi6);
//        }
//    }

    private void addFollow(int pos, int relation, View v, int isfans) {
        if (isfans == 0) {
//            QGHttpRequest.getInstance().addFollowRequest(getActivity(), ((SquareStar) this.hotUserlist.get(pos)).getUid(), new C17408(getActivity(), false, pos, relation, v));
        }
    }

    private void delFollow(int pos, int relation, View v, int isfans) {
        if (isfans == 0) {
//            QGHttpRequest.getInstance().delFollowRequest(getActivity(), ((SquareStar) this.hotUserlist.get(pos)).getUid(), new C17419(getActivity(), false, pos, v));
        }
    }

    private static void sendFollowBroadcast(Context mContext, String userId, String relation, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("to_uid", userId);
        intent.putExtra("relation", relation);
        mContext.sendBroadcast(intent);
    }

    public void onReciverBroadcast(String uid, String relation) {
//        if (this.hotUserlist != null) {
//            boolean need_refresh_user = false;
//            int y = 0;
//            while (y < this.hotUserlist.size()) {
//                if (uid.equals(((SquareStar) this.hotUserlist.get(y)).getUid()) && ((SquareStar) this.hotUserlist.get(y)).getRelation() != Integer.parseInt(relation)) {
//                    ((SquareStar) this.hotUserlist.get(y)).setRelation(Integer.parseInt(relation));
//                    need_refresh_user = true;
//                    break;
//                }
//                y++;
//            }
//            if (this.isHot == 1 && need_refresh_user) {
//                this.searchAdapter.notifyDataSetChanged();
//            }
//        }
    }
}
