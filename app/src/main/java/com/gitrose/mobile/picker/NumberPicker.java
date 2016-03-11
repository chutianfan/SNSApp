package com.gitrose.mobile.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClientOption;
import com.gitrose.mobile.R;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.umeng.socialize.common.SocializeConstants;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumberPicker extends LinearLayout {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = 2130903117;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final char[] DIGIT_CHARACTERS;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    public static final Formatter TWO_DIGIT_FORMATTER = null;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final ImageButton mDecrementButton;
    private boolean mDecrementVirtualButtonPressed;
    private List<String> mDisplayedValues;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private final ImageButton mIncrementButton;
    private boolean mIncrementVirtualButtonPressed;
    private boolean mIngonreMoveEvents;
    private int mInitialScrollOffset;
    private final EditText mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private final Drawable mSelectionDivider;
    private final int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private final Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private boolean mShowSoftInputOnTap;
    private final int mSolidColor;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;
    private OnInputTextValueChangedListener onInputTextValueChangedListener;

    /* renamed from: com.gitrose.mobile.picker.NumberPicker.2 */
    class C03562 implements OnClickListener {
        C03562() {
        }

        public void onClick(View v) {
            NumberPicker.this.hideSoftInput();
            NumberPicker.this.mInputText.clearFocus();
            if (v.getId() == R.id.increment) {
                NumberPicker.this.changeValueByOne(true);
            } else {
                NumberPicker.this.changeValueByOne(false);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.picker.NumberPicker.3 */
    class C03573 implements OnLongClickListener {
        C03573() {
        }

        public boolean onLongClick(View v) {
            NumberPicker.this.hideSoftInput();
            NumberPicker.this.mInputText.clearFocus();
            if (v.getId() == R.id.increment) {
                NumberPicker.this.postChangeCurrentByOneFromLongPress(true, 0);
            } else {
                NumberPicker.this.postChangeCurrentByOneFromLongPress(false, 0);
            }
            return true;
        }
    }

    class BeginSoftInputOnLongPressCommand implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        public void run() {
            NumberPicker.this.showSoftInput();
            NumberPicker.this.mIngonreMoveEvents = true;
        }
    }

    class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        private void setStep(boolean increment) {
            this.mIncrement = increment;
        }

        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker.this.postDelayed(this, NumberPicker.this.mLongPressUpdateInterval);
        }
    }

    public static class CustomEditText extends EditText {
        public CustomEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void onEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
            if (actionCode == 6) {
                clearFocus();
            }
        }
    }

    public interface Formatter {
        String format(int i);
    }

    class InputTextFilter extends NumberKeyListener {
        InputTextFilter() {
        }

        public int getInputType() {
            return NumberPicker.SELECTOR_MIDDLE_ITEM_INDEX;
        }

        protected char[] getAcceptedChars() {
            return NumberPicker.DIGIT_CHARACTERS;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            CharSequence filtered = null;
//            String result;
//            if (NumberPicker.this.mDisplayedValues == null) {
//                filtered = super.filter(source, start, end, dest, dstart, dend);
//                if (filtered == null) {
//                    filtered = source.subSequence(start, end);
//                }
//                result = new StringBuilder(String.valueOf(String.valueOf(dest.subSequence(0, dstart)))).append(filtered).append(dest.subSequence(dend, dest.length())).toString();
////                if (BuildConfig.FLAVOR.equals(result)) {
////                    return result;
////                }
//                return NumberPicker.this.getSelectedPos(result) > NumberPicker.this.mMaxValue ? BuildConfig.FLAVOR : filtered;
//            } else {
//                filtered = String.valueOf(source.subSequence(start, end));
//                if (TextUtils.isEmpty(filtered)) {
//                    return BuildConfig.FLAVOR;
//                }
//                result = new StringBuilder(String.valueOf(String.valueOf(dest.subSequence(0, dstart)))).append(filtered).append(dest.subSequence(dend, dest.length())).toString();
//                String str = String.valueOf(result).toLowerCase();
//                for (String val : NumberPicker.this.mDisplayedValues) {
//                    if (val.toLowerCase().startsWith(str)) {
//                        NumberPicker.this.postSetSelectionCommand(result.length(), val.length());
//                        return val.subSequence(dstart, val.length());
//                    }
//                }
//                return BuildConfig.FLAVOR;
//            }

            return filtered;
        }
    }

    interface OnInputTextValueChangedListener {
        void onValueChanged();
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(NumberPicker numberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int i, int i2);
    }

    class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS;
        private final int MODE_TAPPED;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
            this.MODE_PRESS = BUTTON_INCREMENT;
            this.MODE_TAPPED = BUTTON_DECREMENT;
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            NumberPicker.this.mDecrementVirtualButtonPressed = false;
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        public void buttonPressDelayed(int button) {
            cancel();
            this.mMode = BUTTON_INCREMENT;
            this.mManagedButton = button;
            NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int button) {
            cancel();
            this.mMode = BUTTON_DECREMENT;
            this.mManagedButton = button;
            NumberPicker.this.post(this);
        }

        public void run() {
            switch (this.mMode) {
                case BUTTON_INCREMENT /*1*/:
                    switch (this.mManagedButton) {
                        case BUTTON_INCREMENT /*1*/:
                            NumberPicker.this.mIncrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                        case BUTTON_DECREMENT /*2*/:
                            NumberPicker.this.mDecrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                        default:
                    }
                case BUTTON_DECREMENT /*2*/:
                    NumberPicker numberPicker;
                    switch (this.mManagedButton) {
                        case BUTTON_INCREMENT /*1*/:
                            if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            numberPicker = NumberPicker.this;
                            //numberPicker.mIncrementVirtualButtonPressed = numberPicker.mIncrementVirtualButtonPressed ^ BUTTON_INCREMENT;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                        case BUTTON_DECREMENT /*2*/:
                            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            numberPicker = NumberPicker.this;
                            //numberPicker.mDecrementVirtualButtonPressed = numberPicker.mDecrementVirtualButtonPressed ^ BUTTON_INCREMENT;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                        default:
                    }
                default:
            }
        }
    }

    class SetSelectionCommand implements Runnable {
        private int mSelectionEnd;
        private int mSelectionStart;

        SetSelectionCommand() {
        }

        public void run() {
            NumberPicker.this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    /* renamed from: com.gitrose.mobile.picker.NumberPicker.1 */
    class C05491 implements Formatter {
        final Object[] mArgs;
        final StringBuilder mBuilder;
        final java.util.Formatter mFmt;

        C05491() {
            this.mBuilder = new StringBuilder();
            this.mFmt = new java.util.Formatter(this.mBuilder, Locale.US);
            this.mArgs = new Object[NumberPicker.SELECTOR_MIDDLE_ITEM_INDEX];
        }

        public String format(int value) {
            this.mArgs[0] = Integer.valueOf(value);
            this.mBuilder.delete(0, this.mBuilder.length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }
    }

    static {
        DIGIT_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //TWO_DIGIT_FORMATTER = new C05491();
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.numberPickerStyle);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mSelectorIndices = new int[SELECTOR_WHEEL_ITEM_COUNT];
        this.mVirtualButtonPressedDrawable = null;
        this.mInitialScrollOffset = ExploreByTouchHelper.INVALID_ID;
        this.mScrollState = 0;
//        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.NumberPicker, defStyle, 0);
//        this.mHasSelectorWheel = true;
//        this.mSolidColor = attributesArray.getColor(6, 0);
//        this.mSelectionDivider = attributesArray.getDrawable(SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT);
//        this.mSelectionDividerHeight = attributesArray.getDimensionPixelSize(9, (int) TypedValue.applyDimension(SELECTOR_MIDDLE_ITEM_INDEX, 2.0f, getResources().getDisplayMetrics()));
//        this.mSelectionDividersDistance = attributesArray.getDimensionPixelSize(10, (int) TypedValue.applyDimension(SELECTOR_MIDDLE_ITEM_INDEX, 48.0f, getResources().getDisplayMetrics()));
//        this.mMinHeight = attributesArray.getDimensionPixelSize(5, SIZE_UNSPECIFIED);
//        this.mMaxHeight = attributesArray.getDimensionPixelSize(SELECTOR_WHEEL_ITEM_COUNT, SIZE_UNSPECIFIED);
//        if (this.mMinHeight == SIZE_UNSPECIFIED || this.mMaxHeight == SIZE_UNSPECIFIED || this.mMinHeight <= this.mMaxHeight) {
//            this.mMinWidth = attributesArray.getDimensionPixelSize(4, SIZE_UNSPECIFIED);
//            this.mMaxWidth = attributesArray.getDimensionPixelSize(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT, SIZE_UNSPECIFIED);
//            if (this.mMinWidth == SIZE_UNSPECIFIED || this.mMaxWidth == SIZE_UNSPECIFIED || this.mMinWidth <= this.mMaxWidth) {
//                this.mComputeMaxWidth = this.mMaxWidth == SIZE_UNSPECIFIED;
//                attributesArray.recycle();
//                this.mPressedStateHelper = new PressedStateHelper();
//                setWillNotDraw(!this.mHasSelectorWheel);
//                ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_number_picker, this, true)
//                OnClickListener onClickListener = new C03562();
//                OnLongClickListener onLongClickListener = new C03573();
//                if (this.mHasSelectorWheel) {
//                    this.mIncrementButton = null;
//                } else {
//                    this.mIncrementButton = (ImageButton) findViewById(R.id.increment);
//                    this.mIncrementButton.setOnClickListener(onClickListener);
//                    this.mIncrementButton.setOnLongClickListener(onLongClickListener);
//                }
//                if (this.mHasSelectorWheel) {
//                    this.mDecrementButton = null;
//                } else {
//                    this.mDecrementButton = (ImageButton) findViewById(R.id.decrement);
//                    this.mDecrementButton.setOnClickListener(onClickListener);
//                    this.mDecrementButton.setOnLongClickListener(onLongClickListener);
//                }
//                this.mInputText = (EditText) findViewById(R.id.numberpicker_input);
//                EditText editText = this.mInputText;
//                InputFilter[] inputFilterArr = new InputFilter[SELECTOR_MIDDLE_ITEM_INDEX];
//                inputFilterArr[0] = new InputTextFilter();
//                editText.setFilters(inputFilterArr);
//                this.mInputText.setRawInputType(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
//                this.mInputText.setImeOptions(6);
//                ViewConfiguration configuration = ViewConfiguration.get(context);
//                this.mTouchSlop = configuration.getScaledTouchSlop();
//                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
//                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity() / SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT;
//                this.mTextSize = (int) this.mInputText.getTextSize();
//                Paint paint = new Paint();
//                paint.setAntiAlias(true);
//                paint.setTextAlign(Align.CENTER);
//                paint.setTextSize((float) this.mTextSize);
//                paint.setTypeface(this.mInputText.getTypeface());
//                paint.setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, SIZE_UNSPECIFIED));
//                this.mSelectorWheelPaint = paint;
//                this.mFlingScroller = new Scroller(getContext(), null, true);
//                this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
//                updateInputTextView();
//                return;
//            }
//            throw new IllegalArgumentException("minWidth > maxWidth");
//        }
        throw new IllegalArgumentException("minHeight > maxHeight");
    }

    public void setOnInputTextValueChangedListener(OnInputTextValueChangedListener onInputTextValueChangedListener) {
        this.onInputTextValueChangedListener = onInputTextValueChangedListener;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mHasSelectorWheel) {
            int msrdWdth = getMeasuredWidth();
            int msrdHght = getMeasuredHeight();
            int inptTxtMsrdWdth = this.mInputText.getMeasuredWidth();
            int inptTxtMsrdHght = this.mInputText.getMeasuredHeight();
            int inptTxtLeft = (msrdWdth - inptTxtMsrdWdth) / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT;
            int inptTxtTop = (msrdHght - inptTxtMsrdHght) / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT;
            this.mInputText.layout(inptTxtLeft, inptTxtTop, inptTxtLeft + inptTxtMsrdWdth, inptTxtTop + inptTxtMsrdHght);
            if (changed) {
                initializeSelectorWheel();
                initializeFadingEdges();
                this.mTopSelectionDividerTop = ((getHeight() - this.mSelectionDividersDistance) / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT) - this.mSelectionDividerHeight;
                this.mBottomSelectionDividerBottom = (this.mTopSelectionDividerTop + (this.mSelectionDividerHeight * UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT)) + this.mSelectionDividersDistance;
                return;
            }
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mHasSelectorWheel) {
            super.onMeasure(makeMeasureSpec(widthMeasureSpec, this.mMaxWidth), makeMeasureSpec(heightMeasureSpec, this.mMaxHeight));
            int widthSize = resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), widthMeasureSpec);
            int heightSize = resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), heightMeasureSpec);
            setMeasuredDimension(widthSize, heightSize);
            this.mInputText.measure(widthSize, heightSize);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int amountToScroll = scroller.getFinalY() - scroller.getCurrY();
        int overshootAdjustment = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + amountToScroll) % this.mSelectorElementHeight);
        if (overshootAdjustment == 0) {
            return false;
        }
        if (Math.abs(overshootAdjustment) > this.mSelectorElementHeight / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT) {
            if (overshootAdjustment > 0) {
                overshootAdjustment -= this.mSelectorElementHeight;
            } else {
                overshootAdjustment += this.mSelectorElementHeight;
            }
        }
        scrollBy(0, amountToScroll + overshootAdjustment);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mHasSelectorWheel || !isEnabled()) {
            return false;
        }
        switch (event.getActionMasked()) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                removeAllCallbacks();
                this.mInputText.setVisibility(View.INVISIBLE);
                float y = event.getY();
                this.mLastDownEventY = y;
                this.mLastDownOrMoveEventY = y;
                this.mLastDownEventTime = event.getEventTime();
                this.mIngonreMoveEvents = false;
                this.mShowSoftInputOnTap = false;
                if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    if (this.mScrollState == 0) {
                        this.mPressedStateHelper.buttonPressDelayed(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
                    }
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom) && this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(SELECTOR_MIDDLE_ITEM_INDEX);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!this.mFlingScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollStateChange(0);
                    return true;
                } else if (!this.mAdjustScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    return true;
                } else if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    hideSoftInput();
                    postChangeCurrentByOneFromLongPress(false, (long) ViewConfiguration.getLongPressTimeout());
                    return true;
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom)) {
                    hideSoftInput();
                    postChangeCurrentByOneFromLongPress(true, (long) ViewConfiguration.getLongPressTimeout());
                    return true;
                } else {
                    this.mShowSoftInputOnTap = true;
                    postBeginSoftInputOnLongPressCommand();
                    return true;
                }
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !this.mHasSelectorWheel) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        switch (event.getActionMasked()) {
            case SELECTOR_MIDDLE_ITEM_INDEX /*1*/:
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                //velocityTracker.computeCurrentVelocity(LocationClientOption.MIN_SCAN_SPAN, (float) this.mMaximumFlingVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(initialVelocity) > this.mMinimumFlingVelocity) {
                    fling(initialVelocity);
                    onScrollStateChange(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
                } else {
                    int eventY = (int) event.getY();
                    long deltaTime = event.getEventTime() - this.mLastDownEventTime;
                    if (((int) Math.abs(((float) eventY) - this.mLastDownEventY)) > this.mTouchSlop || deltaTime >= ((long) ViewConfiguration.getTapTimeout())) {
                        ensureScrollWheelAdjusted();
                    } else if (this.mShowSoftInputOnTap) {
                        this.mShowSoftInputOnTap = false;
                        showSoftInput();
                    } else {
                        int selectorIndexOffset = (eventY / this.mSelectorElementHeight) + SIZE_UNSPECIFIED;
                        if (selectorIndexOffset > 0) {
                            changeValueByOne(true);
                            this.mPressedStateHelper.buttonTapped(SELECTOR_MIDDLE_ITEM_INDEX);
                        } else if (selectorIndexOffset < 0) {
                            changeValueByOne(false);
                            this.mPressedStateHelper.buttonTapped(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
                        }
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT /*2*/:
                if (!this.mIngonreMoveEvents) {
                    float currentMoveY = event.getY();
                    if (this.mScrollState == SELECTOR_MIDDLE_ITEM_INDEX) {
                        scrollBy(0, (int) (currentMoveY - this.mLastDownOrMoveEventY));
                        invalidate();
                    } else if (((int) Math.abs(currentMoveY - this.mLastDownEventY)) > this.mTouchSlop) {
                        removeAllCallbacks();
                        onScrollStateChange(SELECTOR_MIDDLE_ITEM_INDEX);
                    }
                    this.mLastDownOrMoveEventY = currentMoveY;
                    break;
                }
                break;
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case SELECTOR_MIDDLE_ITEM_INDEX /*1*/:
            case SELECTOR_WHEEL_ITEM_COUNT /*3*/:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
//            case SocializeConstants.OP_SHARE_TO_INSTAGRAM /*23*/:
//            case BDLocation.TypeOffLineLocation /*66*/:
//                removeAllCallbacks();
//                break;
        }
        return super.dispatchKeyEvent(event);
    }

    public boolean dispatchTrackballEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case SELECTOR_MIDDLE_ITEM_INDEX /*1*/:
            case SELECTOR_WHEEL_ITEM_COUNT /*3*/:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTrackballEvent(event);
    }

    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currentScrollerY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currentScrollerY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currentScrollerY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton.setEnabled(enabled);
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton.setEnabled(enabled);
        }
    }

    public void scrollBy(int x, int y) {
        int[] selectorIndices = this.mSelectorIndices;
        if (!this.mWrapSelectorWheel && y > 0 && selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else if (this.mWrapSelectorWheel || y >= 0 || selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX] < this.mMaxValue) {
            this.mCurrentScrollOffset += y;
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
                this.mCurrentScrollOffset -= this.mSelectorElementHeight;
                decrementSelectorIndices(selectorIndices);
                setValueInternal(selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX], true);
                if (!this.mWrapSelectorWheel && selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset < (-this.mSelectorTextGapHeight)) {
                this.mCurrentScrollOffset += this.mSelectorElementHeight;
                incrementSelectorIndices(selectorIndices);
                setValueInternal(selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX], true);
                if (!this.mWrapSelectorWheel && selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX] >= this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
        } else {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
    }

    public int getSolidColor() {
        return this.mSolidColor;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        this.mOnValueChangeListener = onValueChangedListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setFormatter(Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
            updateInputTextView();
        }
    }

    public void setValue(int value) {
        setValueInternal(value, true);
    }

    private void showSoftInput() {
//        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (inputMethodManager != null) {
//            this.mInputText.requestFocus();
//            inputMethodManager.showSoftInput(this.mInputText, 0);
//        }
    }

    private void hideSoftInput() {
//        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (inputMethodManager != null && inputMethodManager.isActive(this.mInputText)) {
//            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
//            if (this.mHasSelectorWheel) {
//                this.mInputText.setVisibility(View.INVISIBLE);
//            }
//        }
    }

    private void tryComputeMaxWidth() {
        if (this.mComputeMaxWidth) {
            int maxTextWidth = 0;
            int i;
            if (this.mDisplayedValues == null) {
                float maxDigitWidth = 0.0f;
                for (i = 0; i <= 9; i += SELECTOR_MIDDLE_ITEM_INDEX) {
                    float digitWidth = this.mSelectorWheelPaint.measureText(String.valueOf(i));
                    if (digitWidth > maxDigitWidth) {
                        maxDigitWidth = digitWidth;
                    }
                }
                int numberOfDigits = 0;
                for (int current = this.mMaxValue; current > 0; current /= 10) {
                    numberOfDigits += SELECTOR_MIDDLE_ITEM_INDEX;
                }
                maxTextWidth = (int) (((float) numberOfDigits) * maxDigitWidth);
            } else {
                int valueCount = this.mDisplayedValues.size();
                for (i = 0; i < valueCount; i += SELECTOR_MIDDLE_ITEM_INDEX) {
                    float textWidth = this.mSelectorWheelPaint.measureText((String) this.mDisplayedValues.get(i));
                    if (textWidth > ((float) maxTextWidth)) {
                        maxTextWidth = (int) textWidth;
                    }
                }
            }
            maxTextWidth += this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
            if (this.mMaxWidth != maxTextWidth) {
                if (maxTextWidth > this.mMinWidth) {
                    this.mMaxWidth = maxTextWidth;
                } else {
                    this.mMaxWidth = this.mMinWidth;
                }
                invalidate();
            }
        }
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        boolean wrappingAllowed = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length;
        if ((!wrapSelectorWheel || wrappingAllowed) && wrapSelectorWheel != this.mWrapSelectorWheel) {
            this.mWrapSelectorWheel = wrapSelectorWheel;
        }
    }

    public void setOnLongPressUpdateInterval(long intervalMillis) {
        this.mLongPressUpdateInterval = intervalMillis;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int minValue) {
        if (this.mMinValue != minValue) {
            if (minValue < 0) {
                throw new IllegalArgumentException("minValue must be >= 0");
            }
            this.mMinValue = minValue;
            if (this.mMinValue > this.mValue) {
                this.mValue = this.mMinValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        if (this.mMaxValue != maxValue) {
            if (maxValue < 0) {
                throw new IllegalArgumentException("maxValue must be >= 0");
            }
            this.mMaxValue = maxValue;
            if (this.mMaxValue < this.mValue) {
                this.mValue = this.mMaxValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public List<String> getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(List<String> displayedValues) {
        this.mDisplayedValues = displayedValues;
        if (this.mDisplayedValues != null) {
            this.mInputText.setRawInputType(524289);
        } else {
            this.mInputText.setRawInputType(UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
        }
        updateInputTextView();
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    protected void onDetachedFromWindow() {
        removeAllCallbacks();
    }

    protected void onDraw(Canvas canvas) {
        if (this.mHasSelectorWheel) {
            float x = (float) ((getRight() - getLeft()) / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
            float y = (float) this.mCurrentScrollOffset;
            int[] selectorIndices = this.mSelectorIndices;
            for (int i = 0; i < selectorIndices.length; i += SELECTOR_MIDDLE_ITEM_INDEX) {
                String scrollSelectorValue = (String) this.mSelectorIndexToStringCache.get(selectorIndices[i]);
                if (i != SELECTOR_MIDDLE_ITEM_INDEX || this.mInputText.getVisibility() != View.VISIBLE) {
                    canvas.drawText(scrollSelectorValue, x, y, this.mSelectorWheelPaint);
                }
                y += (float) this.mSelectorElementHeight;
            }
            if (this.mSelectionDivider != null) {
                int topOfTopDivider = this.mTopSelectionDividerTop;
                this.mSelectionDivider.setBounds(0, topOfTopDivider, getRight(), topOfTopDivider + this.mSelectionDividerHeight);
                this.mSelectionDivider.draw(canvas);
                int bottomOfBottomDivider = this.mBottomSelectionDividerBottom;
                this.mSelectionDivider.setBounds(0, bottomOfBottomDivider - this.mSelectionDividerHeight, getRight(), bottomOfBottomDivider);
                this.mSelectionDivider.draw(canvas);
                return;
            }
            return;
        }
        super.onDraw(canvas);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        super.addFocusables(views, direction, focusableMode);
    }

    private int makeMeasureSpec(int measureSpec, int maxSize) {
        if (maxSize == SIZE_UNSPECIFIED) {
            return measureSpec;
        }
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        switch (mode) {
            case ExploreByTouchHelper.INVALID_ID /*-2147483648*/:
                //return MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), 1073741824);
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                //return MeasureSpec.makeMeasureSpec(maxSize, 1073741824);
            case 1073741824:
                return measureSpec;
            default:
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    private int resolveSizeAndStateRespectingMinSize(int minSize, int measuredSize, int measureSpec) {
        if (minSize != SIZE_UNSPECIFIED) {
            return resolveSize(Math.max(minSize, measuredSize), measureSpec);
        }
        return measuredSize;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] selectorIndices = this.mSelectorIndices;
        int current = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i += SELECTOR_MIDDLE_ITEM_INDEX) {
            int selectorIndex = current + (i + SIZE_UNSPECIFIED);
            if (this.mWrapSelectorWheel) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex);
            }
            selectorIndices[i] = selectorIndex;
            ensureCachedScrollSelectorValue(selectorIndices[i]);
        }
    }

    private void setValueInternal(int current, boolean notifyChange) {
        if (this.mValue != current) {
            if (this.mWrapSelectorWheel) {
                current = getWrappedSelectorIndex(current);
            } else {
                current = Math.min(Math.max(current, this.mMinValue), this.mMaxValue);
            }
            int previous = this.mValue;
            this.mValue = current;
            updateInputTextView();
            if (notifyChange) {
                notifyChange(previous, current);
            }
            initializeSelectorWheelIndices();
            this.mInputText.setVisibility(View.INVISIBLE);
            invalidate();
        }
    }

    private void changeValueByOne(boolean increment) {
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(View.INVISIBLE);
            if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
                moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            this.mPreviousScrollerY = 0;
            if (increment) {
                this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, SNAP_SCROLL_DURATION);
            } else {
                this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, SNAP_SCROLL_DURATION);
            }
            invalidate();
        } else if (increment) {
            setValueInternal(this.mValue + SELECTOR_MIDDLE_ITEM_INDEX, true);
        } else {
            setValueInternal(this.mValue + SIZE_UNSPECIFIED, true);
        }
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] selectorIndices = this.mSelectorIndices;
        this.mSelectorTextGapHeight = (int) ((((float) ((getBottom() - getTop()) - (selectorIndices.length * this.mTextSize))) / ((float) selectorIndices.length)) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mInitialScrollOffset = (this.mInputText.getBaseline() + this.mInputText.getTop()) - (this.mSelectorElementHeight * SELECTOR_MIDDLE_ITEM_INDEX);
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((getBottom() - getTop()) - this.mTextSize) / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT);
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != SELECTOR_MIDDLE_ITEM_INDEX) {
            updateInputTextView();
        }
    }

    private void onScrollStateChange(int scrollState) {
        if (this.mScrollState != scrollState) {
            this.mScrollState = scrollState;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChange(this, scrollState);
            }
        }
    }

    private void fling(int velocityY) {
        this.mPreviousScrollerY = 0;
        if (velocityY > 0) {
            this.mFlingScroller.fling(0, 0, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    private int getWrappedSelectorIndex(int selectorIndex) {
        if (selectorIndex > this.mMaxValue) {
            return (this.mMinValue + ((selectorIndex - this.mMaxValue) % (this.mMaxValue - this.mMinValue))) + SIZE_UNSPECIFIED;
        }
        if (selectorIndex < this.mMinValue) {
            return (this.mMaxValue - ((this.mMinValue - selectorIndex) % (this.mMaxValue - this.mMinValue))) + SELECTOR_MIDDLE_ITEM_INDEX;
        }
        return selectorIndex;
    }

    private void incrementSelectorIndices(int[] selectorIndices) {
        for (int i = 0; i < selectorIndices.length + SIZE_UNSPECIFIED; i += SELECTOR_MIDDLE_ITEM_INDEX) {
            selectorIndices[i] = selectorIndices[i + SELECTOR_MIDDLE_ITEM_INDEX];
        }
        int nextScrollSelectorIndex = selectorIndices[selectorIndices.length - 2] + SELECTOR_MIDDLE_ITEM_INDEX;
        if (this.mWrapSelectorWheel && nextScrollSelectorIndex > this.mMaxValue) {
            nextScrollSelectorIndex = this.mMinValue;
        }
        selectorIndices[selectorIndices.length + SIZE_UNSPECIFIED] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    private void decrementSelectorIndices(int[] selectorIndices) {
        for (int i = selectorIndices.length + SIZE_UNSPECIFIED; i > 0; i += SIZE_UNSPECIFIED) {
            selectorIndices[i] = selectorIndices[i + SIZE_UNSPECIFIED];
        }
        int nextScrollSelectorIndex = selectorIndices[SELECTOR_MIDDLE_ITEM_INDEX] + SIZE_UNSPECIFIED;
        if (this.mWrapSelectorWheel && nextScrollSelectorIndex < this.mMinValue) {
            nextScrollSelectorIndex = this.mMaxValue;
        }
        selectorIndices[0] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    private void ensureCachedScrollSelectorValue(int selectorIndex) {
        SparseArray<String> cache = this.mSelectorIndexToStringCache;
        String scrollSelectorValue = (String) cache.get(selectorIndex);
        if (scrollSelectorValue == null) {
            if (selectorIndex < this.mMinValue || selectorIndex > this.mMaxValue) {
                //scrollSelectorValue = BuildConfig.FLAVOR;
            } else if (this.mDisplayedValues != null) {
                int displayedValueIndex = selectorIndex - this.mMinValue;
                if (this.mDisplayedValues.size() > displayedValueIndex) {
                    scrollSelectorValue = (String) this.mDisplayedValues.get(displayedValueIndex);
                }
            } else {
                scrollSelectorValue = formatNumber(selectorIndex);
            }
            cache.put(selectorIndex, scrollSelectorValue);
        }
    }

    private String formatNumber(int value) {
        if (this.mFormatter != null) {
            return this.mFormatter.format(value);
        }
        return String.valueOf(value);
    }

    private void validateInputTextView(View v) {
        String str = String.valueOf(((TextView) v).getText());
        if (TextUtils.isEmpty(str)) {
            updateInputTextView();
        } else {
            setValueInternal(getSelectedPos(str.toString()), true);
        }
    }

    private boolean updateInputTextView() {
//        String text = this.mDisplayedValues == null ? formatNumber(this.mValue) : this.mDisplayedValues.size() > this.mValue - this.mMinValue ? (String) this.mDisplayedValues.get(this.mValue - this.mMinValue) : BuildConfig.FLAVOR;
//        if (TextUtils.isEmpty(text) || text.equals(this.mInputText.getText().toString())) {
//            return false;
//        }
//        this.mInputText.setText(text);
//        if (this.onInputTextValueChangedListener != null) {
//            this.onInputTextValueChangedListener.onValueChanged();
//        }
        return true;
    }

    private void notifyChange(int previous, int current) {
        if (this.mOnValueChangeListener != null) {
            this.mOnValueChangeListener.onValueChange(this, previous, this.mValue);
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean increment, long delayMillis) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(increment);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, delayMillis);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        if (this.mBeginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        if (this.mSetSelectionCommand != null) {
            removeCallbacks(this.mSetSelectionCommand);
        }
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private int getSelectedPos(String value) {
        if (this.mDisplayedValues == null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return this.mMinValue;
            }
        }
        for (int i = 0; i < this.mDisplayedValues.size(); i += SELECTOR_MIDDLE_ITEM_INDEX) {
            value = value.toLowerCase();
            if (((String) this.mDisplayedValues.get(i)).toLowerCase().startsWith(value)) {
                return this.mMinValue + i;
            }
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e2) {
            return this.mMinValue;
        }
    }

    private void postSetSelectionCommand(int selectionStart, int selectionEnd) {
        if (this.mSetSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand();
        } else {
            removeCallbacks(this.mSetSelectionCommand);
        }
        this.mSetSelectionCommand.mSelectionStart = selectionStart;
        this.mSetSelectionCommand.mSelectionEnd = selectionEnd;
        post(this.mSetSelectionCommand);
    }

    private boolean ensureScrollWheelAdjusted() {
        int deltaY = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (deltaY == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        if (Math.abs(deltaY) > this.mSelectorElementHeight / UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT) {
            int i;
            if (deltaY > 0) {
                i = -this.mSelectorElementHeight;
            } else {
                i = this.mSelectorElementHeight;
            }
            deltaY += i;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, deltaY, SELECTOR_ADJUSTMENT_DURATION_MILLIS);
        invalidate();
        return true;
    }
}
