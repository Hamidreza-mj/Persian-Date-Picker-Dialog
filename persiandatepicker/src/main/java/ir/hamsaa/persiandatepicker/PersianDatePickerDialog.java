package ir.hamsaa.persiandatepicker;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

import ir.hamsaa.persiandatepicker.api.NotificationNoteClick;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.date.PersianDateImpl;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.hamsaa.persiandatepicker.util.PersianHelper;

/**
 * Created by aliabdolahi on 1/23/17.
 */

public class PersianDatePickerDialog {

    public static final int THIS_YEAR = -1;
    public static final int THIS_MONTH = -2;
    public static final int THIS_DAY = -3;
    public static final int NO_TITLE = 0;
    public static final int DAY_MONTH_YEAR = 1;
    public static final int WEEKDAY_DAY_MONTH_YEAR = 2;

    private Context context;
    private String positiveButtonString = "تایید";
    private String negativeButtonString = "انصراف";
    private Listener listener;
    private PersianPickerListener persianPickerListener;
    private int maxYear = 0;
    private int maxMonth = 0;
    private int maxDay = 0;
    private int minYear = 0;
    private PersianPickerDate initDate = new PersianDateImpl();
    public static Typeface typeFace;
    private String todayButtonString = "امروز";
    private boolean todayButtonVisibility = false;
    private int actionColor = Color.GRAY;
    private int actionTextSize = 12;
    private int negativeTextSize = 12;
    private int todayTextSize = 12;
    private int backgroundColor = Color.WHITE;
    private int titleColor = Color.parseColor("#111111");
    private boolean cancelable = true;
    private boolean forceMode;
    private int pickerBackgroundColor;
    private int pickerBackgroundDrawable;
    private int titleType = 0;
    private boolean showInBottomSheet;
    private ObjectAnimator rotateAnimator;
    private AppCompatImageView imgNotification;

    private boolean isAnimated = false;

    private NotificationNoteClick notificationNoteClick;
    private boolean isPassed;

    private boolean enableBellView = false;

    public PersianDatePickerDialog(Context context) {
        this.context = context;
    }

    @Deprecated
    public PersianDatePickerDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public PersianDatePickerDialog setListener(PersianPickerListener listener) {
        this.persianPickerListener = listener;
        return this;
    }

    public PersianDatePickerDialog setNotificationNoteClick(NotificationNoteClick notificationNoteClick) {
        this.notificationNoteClick = notificationNoteClick;
        return this;
    }

    public PersianDatePickerDialog setMaxYear(int maxYear) {
        this.maxYear = maxYear;
        return this;
    }

    public PersianDatePickerDialog setMaxMonth(int maxMonth) {
        if (maxMonth > 12) {
            throw new RuntimeException("max month is not valid");
        }
        this.maxMonth = maxMonth;
        return this;
    }

    public PersianDatePickerDialog setMaxDay(int maxDay) {
        if (maxDay > 31) {
            throw new RuntimeException("max day is not valid");
        }
        this.maxDay = maxDay;
        return this;
    }

    public PersianDatePickerDialog setTypeFace(Typeface typeFace) {
        PersianDatePickerDialog.typeFace = typeFace;
        return this;
    }

    public PersianDatePickerDialog setMinYear(int minYear) {
        this.minYear = minYear;
        return this;
    }

    public PersianDatePickerDialog setInitDate(Long timestamp) {
        this.initDate.setDate(timestamp);
        return this;
    }

    public PersianDatePickerDialog setInitDate(Date date) {
        this.initDate.setDate(date);
        return this;
    }

    public PersianDatePickerDialog setInitDate(int persianYear, int persianMonth, int persianDay) {
        this.initDate.setDate(persianYear, persianMonth, persianDay);
        return this;
    }

    public PersianDatePickerDialog setInitDate(PersianPickerDate initDate) {
        return setInitDate(initDate, false);
    }

    public PersianDatePickerDialog setInitDate(PersianPickerDate initDate, boolean force) {
        this.forceMode = force;
        this.initDate.setDate(initDate.getTimestamp());
        return this;
    }

    @Deprecated
    public PersianDatePickerDialog setInitDate(PersianCalendar initDate) {
        return setInitDate(initDate, false);
    }

    @Deprecated
    public PersianDatePickerDialog setInitDate(PersianCalendar initDate, boolean force) {
        this.forceMode = force;
        this.initDate.setDate(
                initDate.getPersianYear(),
                initDate.getPersianMonth(),
                initDate.getPersianDay()
        );
        return this;
    }

    public PersianDatePickerDialog setPositiveButtonString(String positiveButtonString) {
        this.positiveButtonString = positiveButtonString;
        return this;
    }

    public PersianDatePickerDialog setPositiveButtonResource(@StringRes int positiveButton) {
        this.positiveButtonString = context.getString(positiveButton);
        return this;
    }

    public PersianDatePickerDialog setTodayButtonVisible(boolean todayButtonVisiblity) {
        this.todayButtonVisibility = todayButtonVisiblity;
        return this;
    }

    public PersianDatePickerDialog setTodayButton(String todayButton) {
        this.todayButtonString = todayButton;
        return this;
    }

    public PersianDatePickerDialog setTodayButtonResource(@StringRes int todayButton) {
        this.todayButtonString = context.getString(todayButton);
        return this;
    }

    public PersianDatePickerDialog setTodayTextSize(int sizeInt) {
        this.todayTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerDialog setNegativeButton(String negativeButton) {
        this.negativeButtonString = negativeButton;
        return this;
    }

    public PersianDatePickerDialog setNegativeButtonResource(@StringRes int negativeButton) {
        this.negativeButtonString = context.getString(negativeButton);
        return this;
    }

    public PersianDatePickerDialog setNegativeTextSize(int sizeInt) {
        this.negativeTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerDialog setActionTextColor(@ColorInt int colorInt) {
        this.actionColor = colorInt;
        return this;
    }


    public PersianDatePickerDialog setActionTextColorResource(@ColorRes int colorInt) {
        this.actionColor = ContextCompat.getColor(context, colorInt);
        return this;
    }

    public PersianDatePickerDialog setActionTextSize(int sizeInt) {
        this.actionTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerDialog setAllButtonsTextSize(int sizeInt) {
        this.actionTextSize = sizeInt;
        this.negativeTextSize = sizeInt;
        this.todayTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    private PersianDatePickerDialog setBackgroundColor(@ColorInt int bgColor) {
        this.backgroundColor = bgColor;
        return this;
    }

    public PersianDatePickerDialog setTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public PersianDatePickerDialog setPickerBackgroundColor(@ColorInt int color) {
        this.pickerBackgroundColor = color;
        return this;
    }

    public PersianDatePickerDialog setPickerBackgroundDrawable(@DrawableRes int drawableBg) {
        this.pickerBackgroundDrawable = drawableBg;
        return this;
    }

    public PersianDatePickerDialog setTitleType(int titleType) {
        this.titleType = titleType;
        return this;
    }

    public PersianDatePickerDialog setShowInBottomSheet(boolean b) {
        this.showInBottomSheet = b;
        return this;
    }

    public PersianDatePickerDialog setEnableBellView(boolean enableBellView) {
        this.enableBellView = enableBellView;
        return this;
    }

    public boolean isEnableBellView() {
        return enableBellView;
    }

    public void show() {
        View v = View.inflate(context, R.layout.dialog_picker, null);
        final PersianDatePicker datePickerView = v.findViewById(R.id.datePicker);
        final TextView dateText = v.findViewById(R.id.dateText);
        imgNotification = v.findViewById(R.id.aImgNotification);
        final MaterialButton positiveButton = v.findViewById(R.id.positive_button);
        final MaterialButton negativeButton = v.findViewById(R.id.negative_button);
        final MaterialButton todayButton = v.findViewById(R.id.today_button);
        final LinearLayout container = v.findViewById(R.id.container);

        if (enableBellView) {
            rotateAnimator = ObjectAnimator.ofFloat(
                    imgNotification,
                    "rotation",
                    0, 20, 0, -20, 0, 20, 0, -20, 0, 20, 0, -20, 0
            );

            rotateAnimator.setDuration(700);
        }

        imgNotification.setOnClickListener(view -> {
            if (notificationNoteClick != null)
                notificationNoteClick.onClick(isPassed);
        });

        container.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.sheet));

        dateText.setTextColor(titleColor);

        imgNotification.setVisibility(enableBellView ? View.VISIBLE : View.GONE);

        if (pickerBackgroundColor != 0) {
            datePickerView.setBackgroundColor(pickerBackgroundColor);
        } else if (pickerBackgroundDrawable != 0) {
            datePickerView.setBackgroundDrawable(pickerBackgroundDrawable);
        }

        if (maxYear > 0) {
            datePickerView.setMaxYear(maxYear);
        } else if (maxYear == THIS_YEAR) {
            maxYear = new PersianDateImpl().getPersianYear();
            datePickerView.setMaxYear(maxYear);
        }

        if (maxMonth > 0) {
            datePickerView.setMaxMonth(maxMonth);
        } else if (maxMonth == THIS_MONTH) {
            maxMonth = new PersianDateImpl().getPersianMonth();
            datePickerView.setMaxMonth(maxMonth);
        }

        if (maxDay > 0) {
            datePickerView.setMaxDay(maxDay);
        } else if (maxDay == THIS_DAY) {
            maxDay = new PersianDateImpl().getPersianDay();
            datePickerView.setMaxDay(maxDay);
        }

        if (minYear > 0) {
            datePickerView.setMinYear(minYear);
        } else if (minYear == THIS_YEAR) {
            minYear = new PersianDateImpl().getPersianYear();
            datePickerView.setMinYear(minYear);
        }

        if (initDate != null) {
            int initYear = initDate.getPersianYear();
            if (initYear > maxYear || initYear < minYear) {
                Log.e("PERSIAN CALENDAR", "init year is more/less than minYear/maxYear");
                if (forceMode) {
                    datePickerView.setDisplayPersianDate(initDate);
                }
            } else {
                datePickerView.setDisplayPersianDate(initDate);
            }

        }

        if (typeFace != null) {
            dateText.setTypeface(typeFace);
            positiveButton.setTypeface(typeFace);
            negativeButton.setTypeface(typeFace);
            todayButton.setTypeface(typeFace);
            datePickerView.setTypeFace(typeFace);
        }

        positiveButton.setTextSize(actionTextSize);
        negativeButton.setTextSize(negativeTextSize);
        todayButton.setTextSize(todayTextSize);

//        positiveButton.setTextColor(actionColor);
//        negativeButton.setTextColor(actionColor);
//        todayButton.setTextColor(actionColor);

        positiveButton.setText(positiveButtonString);
        negativeButton.setText(negativeButtonString);
        todayButton.setText(todayButtonString);

        if (todayButtonVisibility) {
            todayButton.setVisibility(View.VISIBLE);
        }

        updateView(dateText, datePickerView.getPersianDate());

        datePickerView.setOnDateChangedListener(new PersianDatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(int newYear, int newMonth, int newDay) {
                updateView(dateText, datePickerView.getPersianDate());
            }
        });


        final AppCompatDialog dialog;
        if (showInBottomSheet) {
            dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
            dialog.setContentView(v);
            dialog.setCancelable(cancelable);
        } else {
            dialog = new AlertDialog.Builder(context)
                    .setView(v)
                    .setCancelable(cancelable)
                    .create();
        }

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDismissed();
                }
                dialog.dismiss();
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for backward compatibility, still support this
                if (listener != null) {
                    listener.onDateSelected(datePickerView.getDisplayPersianDate());
                }

                if (persianPickerListener != null) {
                    persianPickerListener.onDateSelected(datePickerView.getPersianDate(), isPassed);
                }
                dialog.dismiss();
            }
        });

        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerView.setDisplayDate(new Date());

                if (maxYear > 0) {
                    datePickerView.setMaxYear(maxYear);
                }

                if (minYear > 0) {
                    datePickerView.setMinYear(minYear);
                }

                dateText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateView(dateText, datePickerView.getPersianDate());
                    }
                }, 100);
            }
        });

        dialog.show();
    }

    private void updateView(TextView dateText, PersianPickerDate persianDate) {
        String date;
        switch (titleType) {
            case NO_TITLE:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dateText.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 30);
                dateText.setLayoutParams(layoutParams);
                break;
            case DAY_MONTH_YEAR:
                date = persianDate.getPersianDay() + " " +
                        persianDate.getPersianMonthName() + " " +
                        persianDate.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            case WEEKDAY_DAY_MONTH_YEAR:
                date = persianDate.getPersianDayOfWeekName() + " " +
                        persianDate.getPersianDay() + " " +
                        persianDate.getPersianMonthName() + " " +
                        persianDate.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            default:
                Log.d("PersianDatePickerDialog", "never should be here");
                break;
        }

        handlePassedDate(persianDate);
    }

    private void handlePassedDate(PersianPickerDate persianDate) {
        PersianDateImpl currentDate = new PersianDateImpl();
        currentDate.setDate(System.currentTimeMillis());

        if (enableBellView) {
            if (selectedDateIsPassed(currentDate, persianDate)) {
                isAnimated = false;
                isPassed = true;
                imgNotification.setImageResource(R.drawable.ic_outline_notification_off);
                imgNotification.setColorFilter(Color.parseColor("#A1A1A1"));
                imgNotification.setRotation(0);
                rotateAnimator.cancel();
            } else {
                isPassed = false;
                imgNotification.setColorFilter(Color.parseColor("#1F8EF3"));
                imgNotification.setImageResource(R.drawable.ic_outline_notification_active);

                if (!isAnimated) {
                    isAnimated = true;
                    rotateAnimator.start();
                }
            }
        } else {
            isPassed = selectedDateIsPassed(currentDate, persianDate);
        }
    }

    private boolean selectedDateIsPassed(PersianPickerDate currentDate, PersianPickerDate selectedDate) {
        int thisYear = currentDate.getPersianYear();
        int thisMonth = currentDate.getPersianMonth();
        int thisDay = currentDate.getPersianDay();

        int selectedYear = selectedDate.getPersianYear();
        int selectedMonth = selectedDate.getPersianMonth();
        int selectedDay = selectedDate.getPersianDay();

        if (selectedYear < thisYear) { //passed
            return true;
        } else if (selectedYear == thisYear) {

            if (selectedMonth < thisMonth) //passed
                return true;
            else if (selectedMonth == thisMonth)
                return selectedDay < thisDay; //true -> passed | false -> not passed
            else //not passed
                return false;

        } else { //not passed
            return false;
        }
    }

}