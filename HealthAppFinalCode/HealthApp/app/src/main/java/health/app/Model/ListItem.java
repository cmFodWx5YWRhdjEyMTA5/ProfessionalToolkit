package health.app.Model;

/**
 * Created by Developer Six on 9/13/2017.
 */

public abstract class ListItem {
    public static final int TYPE_DATE = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();
}
