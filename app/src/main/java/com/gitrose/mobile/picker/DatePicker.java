package com.gitrose.mobile.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
//import com.sea_monster.core.exception.InternalException;
import com.gitrose.mobile.R;
import com.gitrose.mobile.picker.NumberPicker.OnValueChangeListener;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatePicker extends FrameLayout {
    private boolean isThisMonth;
    private boolean isThisYear;
    private Calendar mCalendar;
    private Context mContext;
    private NumberPicker mDayPicker;
    private List<String> mMonthDisplay;
    private NumberPicker mMonthPicker;
    private Calendar mToadyCalendar;
    private NumberPicker mYearPicker;

    /* renamed from: com.gitrose.mobile.picker.DatePicker.1 */
    class C05461 implements OnValueChangeListener {
        C05461() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//            if (!DatePicker.this.isThisYear || newVal < DatePicker.this.mToadyCalendar.get(2)) {
//                DatePicker.this.isThisMonth = false;
//                DatePicker.this.mCalendar.set(2, newVal);
//            } else {
//                DatePicker.this.isThisMonth = true;
//                DatePicker.this.mMonthPicker.setValue(DatePicker.this.mToadyCalendar.get(2));
//                DatePicker.this.mCalendar.set(2, DatePicker.this.mToadyCalendar.get(2));
//            }
//            DatePicker.this.updateDate();
        }
    }

    /* renamed from: com.gitrose.mobile.picker.DatePicker.2 */
    class C05472 implements OnValueChangeListener {
        C05472() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//            if (DatePicker.this.isThisYear && DatePicker.this.isThisMonth && newVal >= DatePicker.this.mToadyCalendar.get(5)) {
//                DatePicker.this.mCalendar.set(5, DatePicker.this.mToadyCalendar.get(5));
//                DatePicker.this.mDayPicker.setValue(DatePicker.this.mToadyCalendar.get(5));
//            } else {
//                DatePicker.this.mCalendar.set(5, newVal);
//            }
//            DatePicker.this.updateDate();
        }
    }

    /* renamed from: com.gitrose.mobile.picker.DatePicker.3 */
    class C05483 implements OnValueChangeListener {
        C05483() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//            if (newVal >= DatePicker.this.mToadyCalendar.get(1)) {
//                DatePicker.this.mYearPicker.setValue(DatePicker.this.mToadyCalendar.get(1) - 14);
//                DatePicker.this.mCalendar.set(1, DatePicker.this.mToadyCalendar.get(1) - 14);
//            } else {
//                DatePicker.this.isThisYear = false;
//                DatePicker.this.mCalendar.set(1, newVal);
//            }
//            DatePicker.this.updateDate();
        }
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.mContext = context;
//        this.mCalendar = Calendar.getInstance();
//        this.mToadyCalendar = Calendar.getInstance();
//        initMonthDisplay();
//        ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.layout_date_picker, this, true);
//        this.mDayPicker = (NumberPicker) findViewById(R.id.date_day);
//        this.mMonthPicker = (NumberPicker) findViewById(R.id.date_month);
//        this.mYearPicker = (NumberPicker) findViewById(R.id.date_year);
//        this.mDayPicker.setMinValue(1);
//        this.mDayPicker.setMaxValue(31);
//        this.mDayPicker.setValue(this.mCalendar.get(5));
//        this.mDayPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//        this.mMonthPicker.setMinValue(0);
//        this.mMonthPicker.setMaxValue(11);
//        this.mMonthPicker.setDisplayedValues(this.mMonthDisplay);
//        this.mMonthPicker.setValue(this.mCalendar.get(2));
//        this.mYearPicker.setMinValue(1950);
//        this.mYearPicker.setMaxValue(InternalException.VALID_EXCEPTION_CODE);
//        this.mYearPicker.setValue(this.mCalendar.get(1));
//        this.mMonthPicker.setOnValueChangedListener(new C05461());
//        this.mDayPicker.setOnValueChangedListener(new C05472());
//        this.mYearPicker.setOnValueChangedListener(new C05483());
        updateDate();
    }

    private void initMonthDisplay() {
        this.mMonthDisplay = new ArrayList();
        this.mMonthDisplay.add("\u4e00\u6708");
        this.mMonthDisplay.add("\u4e8c\u6708");
        this.mMonthDisplay.add("\u4e09\u6708");
        this.mMonthDisplay.add("\u56db\u6708");
        this.mMonthDisplay.add("\u4e94\u6708");
        this.mMonthDisplay.add("\u516d\u6708");
        this.mMonthDisplay.add("\u4e03\u6708");
        this.mMonthDisplay.add("\u516b\u6708");
        this.mMonthDisplay.add("\u4e5d\u6708");
        this.mMonthDisplay.add("\u5341\u6708");
        this.mMonthDisplay.add("\u5341\u4e00\u6708");
        this.mMonthDisplay.add("\u5341\u4e8c\u6708");
    }

    private void updateDate() {
//        this.mDayPicker.setMinValue(this.mCalendar.getActualMinimum(5));
//        this.mDayPicker.setMaxValue(this.mCalendar.getActualMaximum(5));
//        this.mDayPicker.setValue(this.mCalendar.get(5));
//        this.mMonthPicker.setValue(this.mCalendar.get(2));
//        this.mYearPicker.setValue(this.mCalendar.get(1));
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public String getDate() {
        return new StringBuilder(String.valueOf(this.mYearPicker.getValue())).append(SocializeConstants.OP_DIVIDER_MINUS).append(this.mMonthPicker.getValue() + 1).append(SocializeConstants.OP_DIVIDER_MINUS).append(this.mDayPicker.getValue()).toString();
    }

//    public int getDay() {
//        return this.mCalendar.get(5);
//    }
//
//    public int getMonth() {
//        return this.mCalendar.get(2);
//    }
//
//    public int getYear() {
//        return this.mCalendar.get(1);
//    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
        updateDate();
    }
}
