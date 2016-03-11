package com.gitrose.mobile.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.gitrose.mobile.R;
import com.gitrose.mobile.dao.AreaDao;
import com.gitrose.mobile.model.City;
import com.gitrose.mobile.model.Province;
import com.gitrose.mobile.model.Zone;
import com.gitrose.mobile.picker.NumberPicker.OnValueChangeListener;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;

public class AreaPicker extends FrameLayout {
    private AreaDao areaDao;
    private List<Zone> areas;
    private List<String> cityNames;
    private List<City> citys;
    private boolean isOpenArea;
    private NumberPicker mAreaPicker;
    private NumberPicker mCityPicker;
    private Context mContext;
    private NumberPicker mProvincePicker;
    private List<Province> provinces;
    private List<String> zonesNames;

    /* renamed from: com.gitrose.mobile.picker.AreaPicker.1 */
    class C05431 implements OnValueChangeListener {
        C05431() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            AreaPicker.this.updateCity(((Province) AreaPicker.this.provinces.get(newVal)).getProSort());
        }
    }

    /* renamed from: com.gitrose.mobile.picker.AreaPicker.2 */
    class C05442 implements OnValueChangeListener {
        C05442() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (AreaPicker.this.isOpenArea) {
                AreaPicker.this.updateArea(((City) AreaPicker.this.citys.get(newVal)).getCitySort(), picker);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.picker.AreaPicker.3 */
    class C05453 implements OnValueChangeListener {
        C05453() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        }
    }

    public AreaPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cityNames = new ArrayList();
        this.zonesNames = new ArrayList();
        this.isOpenArea = false;
        this.mContext = context;
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_area_picker, this, true);
        this.mProvincePicker = (NumberPicker) findViewById(R.id.province);
        this.mCityPicker = (NumberPicker) findViewById(R.id.city);
        this.mAreaPicker = (NumberPicker) findViewById(R.id.zone);
        if (this.isOpenArea) {
            this.mAreaPicker.setVisibility(View.VISIBLE);
            updateArea(((City) this.citys.get(0)).getCitySort(), this.mAreaPicker);
        } else {
            this.mAreaPicker.setVisibility(View.GONE);
        }
        this.areaDao = new AreaDao(getContext());
        this.provinces = this.areaDao.getAllProvinces();
        int size = this.provinces.size();
        List<String> proNames = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            proNames.add(((Province) this.provinces.get(i)).getProName());
        }
        this.mProvincePicker.setMinValue(0);
        this.mProvincePicker.setMaxValue(size - 1);
        this.mProvincePicker.setValue(0);
        this.mProvincePicker.setDisplayedValues(proNames);
        updateCity(((Province) this.provinces.get(0)).getProSort());
        this.mProvincePicker.setOnValueChangedListener(new C05431());
        this.mCityPicker.setOnValueChangedListener(new C05442());
        this.mAreaPicker.setOnValueChangedListener(new C05453());
    }

    private void updateCity(int proId) {
        this.cityNames.clear();
        this.zonesNames.clear();
        this.citys = this.areaDao.getAllCityByProId(proId);
        int size = this.citys.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.cityNames.add(((City) this.citys.get(i)).getCityName());
            }
            int citySort = ((City) this.citys.get(0)).getCitySort();
            if (this.isOpenArea) {
                updateArea(citySort, this.mAreaPicker);
            } else if (size == 1 && this.areaDao.getAllZoneByCityId(citySort).size() > 0) {
                updateArea(citySort, this.mCityPicker);
                return;
            }
            this.mCityPicker.setMinValue(0);
            this.mCityPicker.setMaxValue(size - 1);
            this.mCityPicker.setValue(0);
        } else {
            if (this.isOpenArea) {
                updateArea(-1, this.mAreaPicker);
            }
            //this.cityNames.add(BuildConfig.FLAVOR);
            this.mCityPicker.setMinValue(0);
            this.mCityPicker.setMaxValue(0);
            this.mCityPicker.setValue(0);
        }
        this.mCityPicker.setDisplayedValues(this.cityNames);
    }

    private void updateArea(int cityId, NumberPicker picker) {
        int size;
        if (cityId != -1) {
            this.areas = this.areaDao.getAllZoneByCityId(cityId);
            size = this.areas.size();
        } else {
            size = 0;
            this.areas.clear();
        }
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.zonesNames.add(((Zone) this.areas.get(i)).getZoneName());
            }
            picker.setMinValue(0);
            picker.setValue(0);
            picker.setMaxValue(size - 1);
        } else {
            //this.zonesNames.add(BuildConfig.FLAVOR);
            picker.setMinValue(0);
            picker.setValue(0);
            picker.setMaxValue(0);
        }
        picker.setDisplayedValues(this.zonesNames);
    }

    public AreaPicker(Context context) {
        this(context, null);
    }

    public String[] getArea() {
        String[] areas = new String[3];
        areas[0] = ((Province) this.provinces.get(this.mProvincePicker.getValue())).getProName();
        if (this.cityNames.size() <= 0 || (this.cityNames.size() <= 1 && this.zonesNames.size() != 0)) {
            areas[1] = (String) this.zonesNames.get(this.mCityPicker.getValue());
        } else {
            areas[1] = this.citys.size() > 0 ? ((City) this.citys.get(this.mCityPicker.getValue())).getCityName() : " ";
        }
        String str = (!this.isOpenArea || this.areas.size() <= 0) ? " " : (String) this.zonesNames.get(this.mAreaPicker.getValue());
        areas[2] = str;
        return areas;
    }

    public String getAreaName() {
        String[] areas = getArea();
        //String str = BuildConfig.FLAVOR;
//        for (String area : areas) {
//            str = new StringBuilder(String.valueOf(str)).append(area).append(' ').toString();
//        }
//        return str;
        return "";
    }
}
