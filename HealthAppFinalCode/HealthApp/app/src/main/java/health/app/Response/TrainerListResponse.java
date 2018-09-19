package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 8/28/2017.
 */

public class TrainerListResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    public boolean status;

    protected TrainerListResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        dataList = in.createTypedArrayList(TrainerData.CREATOR);
    }

    public static final Creator<TrainerListResponse> CREATOR = new Creator<TrainerListResponse>() {
        @Override
        public TrainerListResponse createFromParcel(Parcel in) {
            return new TrainerListResponse(in);
        }

        @Override
        public TrainerListResponse[] newArray(int size) {
            return new TrainerListResponse[size];
        }
    };

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

    public List<TrainerData> getDataList() {
        return dataList;
    }

    public void setDataList(List<TrainerData> dataList) {
        this.dataList = dataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<TrainerData> dataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeTypedList(dataList);
    }


    public static class TrainerData implements Parcelable{

        protected TrainerData(Parcel in) {
            trainer = in.readParcelable(Trainer.class.getClassLoader());
            timeSlotsList = in.createTypedArrayList(Slots.CREATOR);
        }

        public static final Creator<TrainerData> CREATOR = new Creator<TrainerData>() {
            @Override
            public TrainerData createFromParcel(Parcel in) {
                return new TrainerData(in);
            }

            @Override
            public TrainerData[] newArray(int size) {
                return new TrainerData[size];
            }
        };

        public Trainer getTrainer() {
            return trainer;
        }

        public void setTrainer(Trainer trainer) {
            this.trainer = trainer;
        }

        public List<Slots> getTimeSlotsList() {
            return timeSlotsList;
        }

        public void setTimeSlotsList(List<Slots> timeSlotsList) {
            this.timeSlotsList = timeSlotsList;
        }

        @SerializedName("trainer")
        @Expose
        public Trainer trainer;

        @SerializedName("selected_slots")
        @Expose
        public List<Slots> timeSlotsList;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(trainer, i);
            parcel.writeTypedList(timeSlotsList);
        }


        public static class Trainer implements Parcelable{
            @SerializedName("id")
            @Expose
            public String id;

            protected Trainer(Parcel in) {
                id = in.readString();
                user_type = in.readString();
                email = in.readString();
                first_name = in.readString();
                last_name = in.readString();
                phone = in.readString();
                age = in.readString();
                fat = in.readString();
                weight = in.readString();
                height = in.readString();
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

            public static final Creator<Trainer> CREATOR = new Creator<Trainer>() {
                @Override
                public Trainer createFromParcel(Parcel in) {
                    return new Trainer(in);
                }

                @Override
                public Trainer[] newArray(int size) {
                    return new Trainer[size];
                }
            };

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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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

            @SerializedName("user_type")
            @Expose
            public String user_type;

            @SerializedName("email")
            @Expose
            public String email;

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


            @SerializedName("updated_at")
            @Expose
            public String updated_at;

            @SerializedName("is_deleted")
            @Expose
            public String is_deleted;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(id);
                parcel.writeString(user_type);
                parcel.writeString(email);
                parcel.writeString(first_name);
                parcel.writeString(last_name);
                parcel.writeString(phone);
                parcel.writeString(age);
                parcel.writeString(fat);
                parcel.writeString(weight);
                parcel.writeString(height);
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


        public static class Slots implements Parcelable{

            @SerializedName("SessionSlotId")
            @Expose
            public String SessionSlotId;

            @SerializedName("CustomerId")
            @Expose
            public String CustomerId;

            @SerializedName("PackageId")
            @Expose
            public String PackageId;

            @SerializedName("RequestId")
            @Expose
            public String RequestId;

            @SerializedName("SessionDate")
            @Expose
            public String SessionDate;

            @SerializedName("StartTime")
            @Expose
            public String StartTime;

            public String getSessionSlotId() {
                return SessionSlotId;
            }

            public void setSessionSlotId(String sessionSlotId) {
                SessionSlotId = sessionSlotId;
            }

            public String getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(String customerId) {
                CustomerId = customerId;
            }

            public String getPackageId() {
                return PackageId;
            }

            public void setPackageId(String packageId) {
                PackageId = packageId;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String requestId) {
                RequestId = requestId;
            }

            public String getSessionDate() {
                return SessionDate;
            }

            public void setSessionDate(String sessionDate) {
                SessionDate = sessionDate;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String startTime) {
                StartTime = startTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String endTime) {
                EndTime = endTime;
            }

            public String getIsDeleted() {
                return IsDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                IsDeleted = isDeleted;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }

            @SerializedName("EndTime")
            @Expose
            public String EndTime;

            @SerializedName("IsDeleted")
            @Expose
            public String IsDeleted;

            @SerializedName("Status")
            @Expose
            public String Status;

            protected Slots(Parcel in) {
                SessionSlotId = in.readString();
                CustomerId = in.readString();
                PackageId = in.readString();
                RequestId = in.readString();
                SessionDate = in.readString();
                StartTime = in.readString();
                EndTime = in.readString();
                IsDeleted = in.readString();
                Status = in.readString();
            }

            public static final Creator<Slots> CREATOR = new Creator<Slots>() {
                @Override
                public Slots createFromParcel(Parcel in) {
                    return new Slots(in);
                }

                @Override
                public Slots[] newArray(int size) {
                    return new Slots[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(SessionSlotId);
                parcel.writeString(CustomerId);
                parcel.writeString(PackageId);
                parcel.writeString(RequestId);
                parcel.writeString(SessionDate);
                parcel.writeString(StartTime);
                parcel.writeString(EndTime);
                parcel.writeString(IsDeleted);
                parcel.writeString(Status);
            }
        }
}
