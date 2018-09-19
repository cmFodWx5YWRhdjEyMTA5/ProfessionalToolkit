package health.app.CalenderCount;

import java.io.Serializable;

/**
 * Created by Developer Six on 11/21/2017.
 */

public class Calendar implements Serializable {

    /**年*/
    private int year;

    /**月*/
    private int month;

    /**日*/
    private int day;

    /**是否是本月*/
    private boolean isCurrentMonth;

    /**是否是今天*/
    private boolean isCurrentDay;//

    /**农历*/
    private String lunar;

    /**计划，可以用来标记当天是否有任务*/
    private String scheme;

    /**各种自定义标记颜色、没有则选择默认颜色*/
    private int schemeColor;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        this.isCurrentMonth = currentMonth;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }


    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getSchemeColor() {
        return schemeColor;
    }

    public void setSchemeColor(int schemeColor) {
        this.schemeColor = schemeColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Calendar) {
            if (((Calendar) o).getYear() == year && ((Calendar) o).getMonth() == month && ((Calendar) o).getDay() == day)
                return true;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return year + "" + (month < 10 ? "0" + month : month) + "" + (day < 10 ? "0" + day : day);
    }
}
