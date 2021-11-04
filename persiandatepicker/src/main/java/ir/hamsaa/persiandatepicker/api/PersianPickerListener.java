package ir.hamsaa.persiandatepicker.api;

public interface PersianPickerListener {

    void onDateSelected(PersianPickerDate persianPickerDate, boolean isPassed);

    void onDismissed();
}
