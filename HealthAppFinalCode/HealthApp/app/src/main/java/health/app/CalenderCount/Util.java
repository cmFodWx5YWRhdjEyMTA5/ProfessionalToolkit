package health.app.CalenderCount;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Developer Six on 11/21/2017.
 */

public class Util {
    @SuppressLint("SimpleDateFormat")
    static int getDate(String formatStr, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return Integer.parseInt(format.format(date));
    }

    static int getMonthDaysCount(int year, int month) {
        int count = 0;
        java.util.Calendar date = java.util.Calendar.getInstance();
        date.set(year, month - 1, 1);

        //判断大月份
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            count = 31;
        }

        //判断小月
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            count = 30;
        }

        //判断平年与闰年
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                count = 29;
            } else {
                count = 28;
            }
        }
        return count;
    }

    @SuppressWarnings("unused")
    @SuppressLint("WrongConstant")
    @Deprecated
    static int getCardHeight(int year, int month) {
        java.util.Calendar date = java.util.Calendar.getInstance();
        date.set(year, month - 1, 1);
        int firstDayOfWeek = date.get(java.util.Calendar.DAY_OF_WEEK) - 1;//月第一天为星期几,星期天 == 0
        int mDaysCount = Util.getMonthDaysCount(year, month);
        date.set(year, month - 1, mDaysCount);
        int mLastCount = date.get(java.util.Calendar.DAY_OF_WEEK) - 1;//月最后一天为星期几,星期天 == 0
        int nextMonthDaysOffset = 6 - mLastCount;//下个月的日偏移天数
        //return 6 * BaseCalendarCardView.mItemHeight;
        return 0;
        //return (firstDayOfWeek + mDaysCount + nextMonthDaysOffset) / 7 * CalendarCardViewV2.mItemHeight;
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
