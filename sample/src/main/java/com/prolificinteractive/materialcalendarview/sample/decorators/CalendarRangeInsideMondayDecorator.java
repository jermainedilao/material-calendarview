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

import org.threeten.bp.DayOfWeek;

import java.util.HashSet;
import java.util.List;

/**
 * Decorates date inside date range if it falls on Monday.
 */
public class CalendarRangeInsideMondayDecorator implements DayViewDecorator {

    private final HashSet<CalendarDay> list = new HashSet<>();
    private final Drawable drawable;
    private final int color;

    public CalendarRangeInsideMondayDecorator(final Context context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_range_inside_monday_selector);
        color = ContextCompat.getColor(context, R.color.navy);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return list.contains(day) && day.getDate().getDayOfWeek() == DayOfWeek.MONDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));
    }

    /**
     * We're changing the dates, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDateRangeInsideDates(List<CalendarDay> insideList) {
        list.clear();
        list.addAll(insideList);
    }

    public void clearDateRange() {
        list.clear();
    }
}
