package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.sample.R;

import java.util.HashSet;

/**
 * Decorate the end date of date range.
 */
public class CalendarRangeEndDecorator implements DayViewDecorator {

    private final HashSet<CalendarDay> list = new HashSet<>();
    private final Drawable drawable;
    private final int color;

    public CalendarRangeEndDecorator(final Context context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_range_end_selector);
        color = ContextCompat.getColor(context, R.color.navy);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return list.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));
    }

    /**
     * We're changing the dates, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDateRangeEndDate(final CalendarDay end) {
        list.clear();
        list.add(end);
    }

    public void clearDateRange() {
        list.clear();
    }
}
