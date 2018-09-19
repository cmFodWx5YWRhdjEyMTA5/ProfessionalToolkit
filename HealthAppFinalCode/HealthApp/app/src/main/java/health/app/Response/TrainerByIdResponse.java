package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer Six on 10/6/2017.
 */

public class TrainerByIdResponse implements Parcelable{

    @SerializedName("data")
    @Expose
    public TrainerIdData trainerIdDataList;

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TrainerIdData getTrainerIdDataList() {
        return trainerIdDataList;
    }

    public void setTrainerIdDataList(TrainerIdData trainerIdDataList) {
        this.trainerIdDataList = trainerIdDataList;
    }


    protected TrainerByIdResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        trainerIdDataList = in.readParcelable(TrainerIdData.class.getClassLoader());
    }

    public static final Creator<TrainerByIdResponse> CREATOR = new Creator<TrainerByIdResponse>() {
        @Override
        public TrainerByIdResponse createFromParcel(Parcel in) {
            return new TrainerByIdResponse(in);
        }

        @Override
        public TrainerByIdResponse[] newArray(int size) {
            return new TrainerByIdResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeParcelable(trainerIdDataList, i);
    }


    public static class TrainerIdData implements Parcelable{


        @SerializedName("trainer")
        @Expose
        public TrainerId trainerId;

        public TrainerId getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(TrainerId trainerId) {
            this.trainerId = trainerId;
        }

        public List<TrainerPackageResponse.PackageData> getTrainerPackageList() {
            return trainerPackageList;
        }

        public void setTrainerPackageList(List<TrainerPackageResponse.PackageData> trainerPackageList) {
            this.trainerPackageList = trainerPackageList;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        @SerializedName("packages")
        @Expose
        List<TrainerPackageResponse.PackageData> trainerPackageList;

        @SerializedName("base_url")
        @Expose
        public String base_url;

        protected TrainerIdData(Parcel in) {
            trainerId = in.readParcelable(TrainerId.class.getClassLoader());
            trainerPackageList = in.createTypedArrayList(TrainerPackageResponse.PackageData.CREATOR);
            base_url = in.readString();
        }

        public static final Creator<TrainerIdData> CREATOR = new Creator<TrainerIdData>() {
            @Override
            public TrainerIdData createFromParcel(Parcel in) {
                return new TrainerIdData(in);
            }

            @Override
            public TrainerIdData[] newArray(int size) {
                return new TrainerIdData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(trainerId, i);
            parcel.writeTypedList(trainerPackageList);
            parcel.writeString(base_url);
        }

        public static class TrainerId implements Parcelable {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("user_type")
            @Expose
            public String user_type;

            @SerializedName("UniqueId")
            @Expose
            public String UniqueId;

            @SerializedName("email")
            @Expose
            public String email;

            @SerializedName("password")
            @Expose
            public String password;

            @SerializedName("first_name")
            @Expose
            public String first_name;

            @SerializedName("last_name")
            @Expose
            public String last_name;

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("age")
            @Expose
            public String age;

            @SerializedName("fat")
            @Expose
            public String fat;

            @SerializedName("weight")
            @Expose
            public String weight;

            @SerializedName("height")
            @Expose
            public String height;


            @SerializedName("Waist")
            @Expose
            public String Waist;

            @SerializedName("Neck")
            @Expose
            public String Neck;

            @SerializedName("Hips")
            @Expose
            public String Hips;


            @SerializedName("gender")
            @Expose
            public String gender;

            @SerializedName("daily_activity")
            @Expose
            public String daily_activity;

            @SerializedName("birthday")
            @Expose
            public String birthday;

            @SerializedName("profile_image")
            @Expose
            public String profile_image;


            @SerializedName("short_bio")
            @Expose
            public String short_bio;

            @SerializedName("training_type")
            @Expose
            public String training_type;

            @SerializedName("android_token")
            @Expose
            public String android_token;

            @SerializedName("latitude")
            @Expose
            public String latitude;

            @SerializedName("longitude")
            @Expose
            public String longitude;

            @SerializedName("created_at")
            @Expose
            public String created_at;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getUniqueId() {
                return UniqueId;
            }

            public void setUniqueId(String uniqueId) {
                UniqueId = uniqueId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getFat() {
                return fat;
            }

            public void setFat(String fat) {
                this.fat = fat;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWaist() {
                return Waist;
            }

            public void setWaist(String waist) {
                Waist = waist;
            }

            public String getNeck() {
                return Neck;
            }

            public void setNeck(String neck) {
                Neck = neck;
            }

            public String getHips() {
                return Hips;
            }

            public void setHips(String hips) {
                Hips = hips;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDaily_activity() {
                return daily_activity;
            }

            public void setDaily_activity(String daily_activity) {
                this.daily_activity = daily_activity;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getShort_bio() {
                return short_bio;
            }

            public void setShort_bio(String short_bio) {
                this.short_bio = short_bio;
            }

            public String getTraining_type() {
                return training_type;
            }

            public void setTraining_type(String training_type) {
                this.training_type = training_type;
            }

            public String getAndroid_token() {
                return android_token;
            }

            public void setAndroid_token(String android_token) {
                this.android_token = android_token;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(String is_deleted) {
                this.is_deleted = is_deleted;
            }

            @SerializedName("updated_at")
            @Expose
            public String updated_at;

            @SerializedName("is_deleted")
            @Expose
            public String is_deleted;

            protected TrainerId(Parcel in) {
                id = in.readString();
                user_type = in.readString();
                UniqueId = in.readString();
                email = in.readString();
                password = in.readString();
                first_name = in.readString();
                last_name = in.readString();
                phone = in.readString();
                age = in.readString();
                fat = in.readString();
                weight = in.readString();
                height = in.readString();
                Waist = in.readString();
                Neck = in.readString();
                Hips = in.readString();
                gender = in.readString();
                daily_activity = in.readString();
                birthday = in.readString();
                profile_image = in.readString();
                short_bio = in.readString();
                training_type = in.readString();
                android_token = in.readString();
                latitude = in.readString();
                longitude = in.readString();
                created_at = in.readString();
                updated_at = in.readString();
                is_deleted = in.readString();
            }

            public static final Creator<TrainerId> CREATOR = new Creator<TrainerId>() {
                @Override
                public TrainerId createFromParcel(Parcel in) {
                    return new TrainerId(in);
                }

                @Override
                public TrainerId[] newArray(int size) {
                    return new TrainerId[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(id);
                parcel.writeString(user_type);
                parcel.writeString(UniqueId);
                parcel.writeString(email);
                parcel.writeString(password);
                parcel.writeString(first_name);
                parcel.writeString(last_name);
                parcel.writeString(phone);
                parcel.writeString(age);
                parcel.writeString(fat);
                parcel.writeString(weight);
                parcel.writeString(height);
                parcel.writeString(Waist);
                parcel.writeString(Neck);
                parcel.writeString(Hips);
                parcel.writeString(gender);
                parcel.writeString(daily_activity);
                parcel.writeString(birthday);
                parcel.writeString(profile_image);
                parcel.writeString(short_bio);
                parcel.writeString(training_type);
                parcel.writeString(android_token);
                parcel.writeString(latitude);
                parcel.writeString(longitude);
                parcel.writeString(created_at);
                parcel.writeString(updated_at);
                parcel.writeString(is_deleted);
            }
        }

    }
}
