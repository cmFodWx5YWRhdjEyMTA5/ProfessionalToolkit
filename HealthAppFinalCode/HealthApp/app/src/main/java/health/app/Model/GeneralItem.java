package health.app.Model;

/**
 * Created by Developer Six on 9/13/2017.
 */

public class GeneralItem extends ListItem {
    private PojoOfJsonArray pojoOfJsonArray;

    public PojoOfJsonArray getPojoOfJsonArray() {
        return pojoOfJsonArray;
    }

    public void setPojoOfJsonArray(PojoOfJsonArray pojoOfJsonArray) {
        this.pojoOfJsonArray = pojoOfJsonArray;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }


}