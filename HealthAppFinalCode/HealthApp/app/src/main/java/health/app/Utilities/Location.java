package health.app.Utilities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Developer Six on 9/27/2017.
 */
@DatabaseTable(tableName = "locations")
public class Location {
    @DatabaseField(columnName = "addressLine")
    private  String addressLine;
    @DatabaseField(columnName = "country")
    private  String country;
    @DatabaseField(columnName = "state")
    public String state;
    @DatabaseField(columnName = "city")
    public String city;
    @DatabaseField(columnName = "latitude")
    public double latitude;
    @DatabaseField(columnName = "longitude")
    public double longitude;
    @DatabaseField(columnName = "timestamp")
    Date date;

    public Location()
    {

    }
    public Location(String countryName, String state, String city, String addressLine, double latitude, double longitude) {
        this.country = countryName;
        this.state = state;
        this.city = city;
        this.addressLine = addressLine;
        this.latitude = latitude;
        this.longitude = longitude;
        date = new Date();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{" +
                "addressLine='" + addressLine + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", date=" + date +
                '}';
    }
}
