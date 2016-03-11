package com.gitrose.mobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.adapter.SortGroupMemberAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.ContactsUserInfo;
import com.gitrose.mobile.model.GroupMemberBean;
import com.gitrose.mobile.utils.CharacterParser;
import com.gitrose.mobile.utils.ContactsUtils;
import com.gitrose.mobile.utils.PinyinComparator;
import com.gitrose.mobile.view.ClearEditText;
import com.gitrose.mobile.view.SideBar;
import com.gitrose.mobile.view.SideBar.OnTouchingLetterChangedListener;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsFriendsActivity extends BaseActivity implements SectionIndexer {
    private static ContactsFriendsActivity contactsFriendsActivity;
    private List<GroupMemberBean> SourceDateList;
    private SortGroupMemberAdapter adapter;
    private CharacterParser characterParser;
    private TextView dialog;
    private int lastFirstVisibleItem;
    private ClearEditText mClearEditText;
    private PinyinComparator pinyinComparator;
    private SideBar sideBar;
    private ListView sortListView;
    private TextView title;
    private LinearLayout titleLayout;
    private TextView tvNofriends;

    /* renamed from: com.gitrose.mobile.ContactsFriendsActivity.2 */
    class C02612 implements OnItemClickListener {
        C02612() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Toast.makeText(ContactsFriendsActivity.this.getApplication(), ((GroupMemberBean) ContactsFriendsActivity.this.adapter.getItem(position)).getName(), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsFriendsActivity.3 */
    class C02623 implements OnScrollListener {
        C02623() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int section = ContactsFriendsActivity.this.getSectionForPosition(firstVisibleItem);
            int nextSecPosition = ContactsFriendsActivity.this.getPositionForSection(ContactsFriendsActivity.this.getSectionForPosition(firstVisibleItem + 1));
            if (firstVisibleItem != ContactsFriendsActivity.this.lastFirstVisibleItem) {
                MarginLayoutParams params = (MarginLayoutParams) ContactsFriendsActivity.this.titleLayout.getLayoutParams();
                params.topMargin = 0;
                ContactsFriendsActivity.this.titleLayout.setLayoutParams(params);
                ContactsFriendsActivity.this.title.setText(((GroupMemberBean) ContactsFriendsActivity.this.SourceDateList.get(ContactsFriendsActivity.this.getPositionForSection(section))).getSortLetters());
            }
            if (nextSecPosition == firstVisibleItem + 1) {
                View childView = view.getChildAt(0);
                if (childView != null) {
                    int titleHeight = ContactsFriendsActivity.this.titleLayout.getHeight();
                    int bottom = childView.getBottom();
//                    params = (MarginLayoutParams) ContactsFriendsActivity.this.titleLayout.getLayoutParams();
//                    if (bottom < titleHeight) {
//                        params.topMargin = (int) ((float) (bottom - titleHeight));
//                        ContactsFriendsActivity.this.titleLayout.setLayoutParams(params);
//                    } else if (params.topMargin != 0) {
//                        params.topMargin = 0;
//                        ContactsFriendsActivity.this.titleLayout.setLayoutParams(params);
//                    }
                }
            }
            ContactsFriendsActivity.this.lastFirstVisibleItem = firstVisibleItem;
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsFriendsActivity.4 */
    class C02634 implements TextWatcher {
        C02634() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ContactsFriendsActivity.this.titleLayout.setVisibility(View.GONE);
            ContactsFriendsActivity.this.filterData(s.toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsFriendsActivity.1 */
    class C05101 implements OnTouchingLetterChangedListener {
        C05101() {
        }

        public void onTouchingLetterChanged(String s) {
            int position = ContactsFriendsActivity.this.adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                ContactsFriendsActivity.this.sortListView.setSelection(position);
            }
        }
    }

    public ContactsFriendsActivity() {
        this.lastFirstVisibleItem = -1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_friends);
        contactsFriendsActivity = this;
        initViews();
    }

    public static ContactsFriendsActivity getContactsFriendsActivity() {
        return contactsFriendsActivity;
    }

    public void updateView() {
        if (this.adapter != null) {
            int position = this.adapter.mCurrentPosition;
            String relationS = ((GroupMemberBean) this.SourceDateList.get(position)).getRelation();
            int relation = 1;
            if (relationS != null) {
                relation = Integer.parseInt(relationS);
            }
            switch (relation) {
                case 0:
                case 1:
                    ((GroupMemberBean) this.SourceDateList.get(position)).setRelation("3");
                    break;
                case 2:
                case 3:
                    ((GroupMemberBean) this.SourceDateList.get(position)).setRelation(Constant.SYSTEM_UID);
                    break;
            }
            this.adapter.updateListView(this.SourceDateList);
        }
    }

    private void initViews() {
        this.titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        this.title = (TextView) findViewById(R.id.title_layout_catalog);
        this.tvNofriends = (TextView) findViewById(R.id.title_layout_no_friends);
        this.characterParser = CharacterParser.getInstance();
        this.pinyinComparator = new PinyinComparator();
        this.sideBar = (SideBar) findViewById(R.id.sidrbar);
        this.dialog = (TextView) findViewById(R.id.dialog);
        this.sideBar.setTextView(this.dialog);
        this.sideBar.setOnTouchingLetterChangedListener(new C05101());
        this.sortListView = (ListView) findViewById(R.id.country_lvcountry);
        this.sortListView.setOnItemClickListener(new C02612());
        List listCui = ContactsUtils.readContacts2(this);
        if (listCui != null && listCui.size() > 0) {
            this.title.setVisibility(View.VISIBLE);
            this.SourceDateList = filledData(listCui);
            Collections.sort(this.SourceDateList, this.pinyinComparator);
            this.adapter = new SortGroupMemberAdapter(this, this.SourceDateList);
            this.sortListView.setAdapter(this.adapter);
            this.sortListView.setOnScrollListener(new C02623());
            this.mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
            this.mClearEditText.addTextChangedListener(new C02634());
        }
    }

    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList();
        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            String sortString = this.characterParser.getSelling(date[i]).substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private List<GroupMemberBean> filledData(List<ContactsUserInfo> list) {
        List<GroupMemberBean> mSortList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(((ContactsUserInfo) list.get(i)).getName());
            sortModel.setHead_img(((ContactsUserInfo) list.get(i)).getHead());
            sortModel.setPhonenumber(((ContactsUserInfo) list.get(i)).getPhonenumber().replace(" ", BuildConfig.FLAVOR));
            sortModel.setDevicesId(ContactsUtils.getUDID(this));
            sortModel.setMy_tutu_id(MyApplication.getInstance().getUserinfo().getUid());
            if (((ContactsUserInfo) list.get(i)).getRelation() != null) {
                sortModel.setRelation(((ContactsUserInfo) list.get(i)).getRelation());
            } else {
                sortModel.setRelation("-1");
            }
            if (((ContactsUserInfo) list.get(i)).getTutuid() != null) {
                sortModel.setTutuid(((ContactsUserInfo) list.get(i)).getTutuid());
            }
            String sortString = this.characterParser.getSelling(((ContactsUserInfo) list.get(i)).getName()).substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            if (sortModel.getRelation() == null) {
                sortModel.setRelation("-1");
            } else if (sortModel.getRelation().equals("0") || sortModel.getRelation().equals(Constant.SYSTEM_UID)) {
                sortModel.setSortLetters(" \u597d\u53cb\u8bf7\u6c42");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private void filterData(String filterStr) {
        List<GroupMemberBean> filterDateList = new ArrayList();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = this.SourceDateList;
            this.tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : this.SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || this.characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        Collections.sort(filterDateList, this.pinyinComparator);
        this.adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            this.tvNofriends.setVisibility(View.VISIBLE);
        }
    }

    public Object[] getSections() {
        return null;
    }

    public int getSectionForPosition(int position) {
        if (this.SourceDateList == null || this.SourceDateList.size() <= 0) {
            return 0;
        }
        return ((GroupMemberBean) this.SourceDateList.get(position)).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < this.SourceDateList.size(); i++) {
            if (((GroupMemberBean) this.SourceDateList.get(i)).getSortLetters().toUpperCase().charAt(0) == section) {
                return i;
            }
        }
        return -1;
    }

    public void onActionBar(View view) {
        switch (view.getId()) {
            case R.id.contacts_friends_back:
                finish();
            default:
        }
    }
}
