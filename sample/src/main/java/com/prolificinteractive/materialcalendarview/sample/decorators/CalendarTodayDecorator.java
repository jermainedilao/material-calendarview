package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.sample.R;

import org.threeten.bp.LocalDate;

import java.util.HashSet;
import java.util.List;

/**
 * Decorate the date today.
 */
public class CalendarTodayDecorator implements DayViewDecorator {

    private final HashSet<CalendarDay> list = new HashSet<>();
    private CalendarDay date;
    private Drawable drawable;

    public CalendarTodayDecorator(Context context) {
        date = CalendarDay.today();
        drawable = context.getResources().getDrawable(R.drawable.background_today);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date) && !list.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
        view.setBackgroundDrawable(drawable);
    }

    public void setDateRange(List<CalendarDay> insideList) {
        list.clear();
        list.addAll(insideList);
    }

    public void clearDateRange() {
        list.clear();
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(LocalDate date) {
        this.date = CalendarDay.from(date);
    }
}
