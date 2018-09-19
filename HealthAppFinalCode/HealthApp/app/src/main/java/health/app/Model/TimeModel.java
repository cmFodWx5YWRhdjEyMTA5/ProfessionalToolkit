package health.app.Model;

import java.util.List;

/**
 * Created by Developer Six on 10/25/2017.
 */

public class TimeModel {
    String date;String start;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    String end;

    public List<String> getStartEnd() {
        return startEnd;
    }

    public void setStartEnd(List<String> startEnd) {
        this.startEnd = startEnd;
    }

    List<String> startEnd;
}
