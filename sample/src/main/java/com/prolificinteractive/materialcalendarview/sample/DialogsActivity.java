package com.prolificinteractive.materialcalendarview.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarRangeEndDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarRangeInsideDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarRangeInsideMondayDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarRangeInsideSundayDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarRangeStartDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.CalendarTodayDecorator;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Shows off the most basic usage
 */
public class DialogsActivity extends AppCompatActivity {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_normal_dialog)
    void onNormalDialogClick() {
        new SimpleDialogFragment().show(getSupportFragmentManager(), "test-normal");
    }

    @OnClick(R.id.button_simple_dialog)
    void onSimpleCalendarDialogClick() {
        new CalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
    }

    public static class SimpleDialogFragment extends AppCompatDialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_activity_dialogs)
                .setMessage("Test Dialog")
                .setPositiveButton(android.R.string.ok, null)
                .create();
        }
    }

    public static class CalendarDialogFragment extends AppCompatDialogFragment
        implements OnDateSelectedListener, OnRangeSelectedListener, OnMonthChangedListener {

        private TextView textView;
        private CalendarRangeStartDecorator rangeStartDecorator;
        private CalendarRangeEndDecorator rangeEndDecorator;
        private CalendarRangeInsideDecorator rangeInsideDecorator;
        private CalendarRangeInsideMondayDecorator rangeInsideMondayDecorator;
        private CalendarRangeInsideSundayDecorator rangeInsideSundayDecorator;
        private CalendarTodayDecorator todayDecorator;
        private MaterialCalendarView widget;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            //inflate custom layout and get views
            //pass null as parent view because will be in dialog layout
            View view = inflater.inflate(R.layout.dialog_basic, null);

            textView = view.findViewById(R.id.textView);
            rangeStartDecorator = new CalendarRangeStartDecorator(view.getContext());
            rangeEndDecorator = new CalendarRangeEndDecorator(view.getContext());
            rangeInsideDecorator = new CalendarRangeInsideDecorator(view.getContext());
            rangeInsideSundayDecorator = new CalendarRangeInsideSundayDecorator(view.getContext());
            rangeInsideMondayDecorator = new CalendarRangeInsideMondayDecorator(view.getContext());
            todayDecorator = new CalendarTodayDecorator(view.getContext());

            widget = view.findViewById(R.id.calendarView);
            widget.setTitleFormatter(new DateFormatTitleFormatter(DateTimeFormatter.ofPattern("MMMM")));
            widget.setOnDateChangedListener(this);
            widget.setOnRangeSelectedListener(this);
            widget.setOnMonthChangedListener(this);
            widget.addDecorators(
                rangeStartDecorator,
                rangeEndDecorator,
                rangeInsideDecorator,
                rangeInsideSundayDecorator,
                rangeInsideMondayDecorator,
                todayDecorator
            );

            return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
        }

        @Override
        public void onRangeSelected(
            @NonNull final MaterialCalendarView widget,
            @NonNull final List<CalendarDay> dates
        ) {
            if (dates.size() > 0) {
                todayDecorator.setDateRange(dates);
                rangeStartDecorator.setDateRangeStartDate(dates.get(0));
                rangeEndDecorator.setDateRangeEndDate(dates.get(dates.size() - 1));
                rangeInsideDecorator.setDateRangeInsideDates(dates.subList(1, dates.size() - 1));
                rangeInsideSundayDecorator.setDateRangeInsideDates(dates.subList(1, dates.size() - 1));
                rangeInsideMondayDecorator.setDateRangeInsideDates(dates.subList(1, dates.size() - 1));
                widget.invalidateDecorators();
            }
        }

        @Override
        public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected
        ) {
            if (selected) {
                textView.setText(FORMATTER.format(date.getDate()));
            } else {
                textView.setText("");
            }

            widget.setPagingEnabled(!selected);
            clearRangeSelection(widget);
        }

        private void clearRangeSelection(@NonNull MaterialCalendarView widget) {
            todayDecorator.clearDateRange();
            rangeStartDecorator.clearDateRange();
            rangeEndDecorator.clearDateRange();
            rangeInsideDecorator.clearDateRange();
            rangeInsideSundayDecorator.clearDateRange();
            rangeInsideMondayDecorator.clearDateRange();

            widget.invalidateDecorators();
        }

        @Override
        public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
            clearRangeSelection(widget);
            widget.clearSelection();
        }
    }
}
