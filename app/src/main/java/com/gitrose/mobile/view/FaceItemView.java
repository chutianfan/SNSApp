package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gitrose.mobile.R;
import java.util.List;

public class FaceItemView extends RelativeLayout {
    private ImageView btndel;
    private IFaceClickBack call;
    private int columns;
    private Context context;
    private ScrollGridView gv;
    private List<Integer> resIDs;
    private List<String> resNames;

    /* renamed from: com.gitrose.mobile.view.FaceItemView.1 */
    class C03631 implements OnItemClickListener {
        C03631() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (FaceItemView.this.resNames.size() > position) {
                FaceItemView.this.call.addFace((String) FaceItemView.this.resNames.get(position));
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.FaceItemView.2 */
    class C03642 implements OnClickListener {
        C03642() {
        }

        public void onClick(View v) {
            FaceItemView.this.call.delFace();
        }
    }

    class FaceAdapter extends BaseAdapter {

        /* renamed from: com.gitrose.mobile.view.FaceItemView.FaceAdapter.1 */
        class C03651 implements OnClickListener {
            C03651() {
            }

            public void onClick(View v) {
                FaceItemView.this.call.delFace();
            }
        }

        FaceAdapter() {
        }

        public int getCount() {
            return FaceItemView.this.columns * 4;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(FaceItemView.this.context).inflate(R.layout.main_face_grid_item, null);
            int itemHeight = (FaceItemView.this.gv.getLayoutParams().height - 4) / 4;
            LinearLayout llface = (LinearLayout) convertView.findViewById(R.id.main_news_face_ll_faceimg);
            if (FaceItemView.this.resIDs.size() < position + 1) {
                convertView.setLayoutParams(new LayoutParams(-1, itemHeight));
                ImageView imface = (ImageView) convertView.findViewById(R.id.main_news_face_iv_faceimg);
                if (position < 27) {
                    //imface.setImageResource(FaceItemView.this.context.getResources().getColor(R.color.white));
                    llface.setBackgroundColor(FaceItemView.this.context.getResources().getColor(R.color.white));
                } else {
                    imface.setImageDrawable(FaceItemView.this.context.getResources().getDrawable(R.drawable.face_del));
                    llface.setBackgroundColor(FaceItemView.this.context.getResources().getColor(R.color.main_family_face_botm_bg));
                    convertView.setOnClickListener(new C03651());
                }
            } else {
                convertView.setLayoutParams(new LayoutParams(-1, itemHeight));
                ((ImageView) convertView.findViewById(R.id.main_news_face_iv_faceimg)).setImageResource(((Integer) FaceItemView.this.resIDs.get(position)).intValue());
                llface.setBackgroundDrawable(FaceItemView.this.context.getResources().getDrawable(R.drawable.face_press));
            }
            return convertView;
        }
    }

    public interface IFaceClickBack {
        void addFace(String str);

        void delFace();
    }

    public FaceItemView(Context context, List<Integer> resIds, List<String> resNames, IFaceClickBack iFaceClickBack, int column) {
        super(context);
        this.columns = 0;
        this.columns = column;
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.main_news_face_item, this);
        this.context = context;
        this.resIDs = resIds;
        this.resNames = resNames;
        this.call = iFaceClickBack;
        this.gv = (ScrollGridView) findViewById(R.id.grapeGridview);
        this.gv.setNumColumns(column);
        this.gv.setAdapter(new FaceAdapter());
        this.gv.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        this.btndel = (ImageView) findViewById(R.id.main_del_face);
        RegisterListener();
    }

    private void RegisterListener() {
        this.gv.setOnItemClickListener(new C03631());
        this.btndel.setOnClickListener(new C03642());
    }
}
