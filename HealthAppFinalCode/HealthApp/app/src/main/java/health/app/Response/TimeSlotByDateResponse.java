package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 9/4/2017.
 */

public class TimeSlotByDateResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    public boolean status;

    protected TimeSlotByDateResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        dataResponse = in.readParcelable(DataResponse.class.getClassLoader());
    }

    public static final Creator<TimeSlotByDateResponse> CREATOR = new Creator<TimeSlotByDateResponse>() {
        @Override
        public TimeSlotByDateResponse createFromParcel(Parcel in) {
            return new TimeSlotByDateResponse(in);
        }

        @Override
        public TimeSlotByDateResponse[] newArray(int size) {
            return new TimeSlotByDateResponse[size];
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

    public DataResponse getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public DataResponse dataResponse;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeParcelable(dataResponse, i);
    }


    public static class DataResponse implements Parcelable {

        @SerializedName("time_slots")
        @Expose
        public List<TimeSlotByDate> timeSlotList;

        protected DataResponse(Parcel in) {
            timeSlotList = in.createTypedArrayList(TimeSlotByDate.CREATOR);
            allSlotsList = in.createTypedArrayList(AllSlots.CREATOR);
            trainerByDate = in.readParcelable(TrainerByDate.class.getClassLoader());
        }

        public static final Creator<DataResponse> CREATOR = new Creator<DataResponse>() {
            @Override
            public DataResponse createFromParcel(Parcel in) {
                return new DataResponse(in);
            }

            @Override
            public DataResponse[] newArray(int size) {
                return new DataResponse[size];
            }
        };

        public List<TimeSlotByDate> getTimeSlotList() {
            return timeSlotList;
        }

        public void setTimeSlotList(List<TimeSlotByDate> timeSlotList) {
            this.timeSlotList = timeSlotList;
        }

        public List<AllSlots> getAllSlotsList() {
            return allSlotsList;
        }

        public void setAllSlotsList(List<AllSlots> allSlotsList) {
            this.allSlotsList = allSlotsList;
        }

        public TrainerByDate getTrainerByDate() {
            return trainerByDate;
        }

        public void setTrainerByDate(TrainerByDate trainerByDate) {
            this.trainerByDate = trainerByDate;
        }

        @SerializedName("all_slots")
        @Expose
        public List<AllSlots> allSlotsList;

        @SerializedName("user")
        @Expose
        public TrainerByDate trainerByDate;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(timeSlotList);
            parcel.writeTypedList(allSlotsList);
            parcel.writeParcelable(trainerByDate, i);
        }


        public static class TimeSlotByDate implements Parcelable {
            @SerializedName("SlotId")
            @Expose
            public String SlotId;

            @SerializedName("TrainerId")
            @Expose
            public String TrainerId;

            @SerializedName("SlotDate")
            @Expose
            public String SlotDate;

            @SerializedName("StartTime")
            @Expose
            public String StartTime;

            @SerializedName("EndTime")
            @Expose
            public String EndTime;

            @SerializedName("SessionTime")
            @Expose
            public String SessionTime;

            @SerializedName("SessionPrice")
            @Expose
            public String SessionPrice;

            @SerializedName("SlotTitle")
            @Expose
            public String SlotTitle;

            @SerializedName("Address")
            @Expose
            public String Address;

            @SerializedName("RequestId")
            @Expose
            public String RequestId;

            @SerializedName("SlotDescription")
            @Expose
            public String SlotDescription;

            @SerializedName("IsOccupied")
            @Expose
            public String IsOccupied;

            @SerializedName("CustomerId")
            @Expose
            public String CustomerId;

            @SerializedName("CreatedOn")
            @Expose
            public String CreatedOn;

            protected TimeSlotByDate(Parcel in) {
                SlotId = in.readString();
                TrainerId = in.readString();
                SlotDate = in.readString();
                StartTime = in.readString();
                EndTime = in.readString();
                SessionTime = in.readString();
                SessionPrice = in.readString();
                SlotTitle = in.readString();
                Address = in.readString();
                RequestId = in.readString();
                SlotDescription = in.readString();
                IsOccupied = in.readString();
                CustomerId = in.readString();
                CreatedOn = in.readString();
                UpdatedOn = in.readString();
                IsDeleted = in.readString();
            }

            public static final Creator<TimeSlotByDate> CREATOR = new Creator<TimeSlotByDate>() {
                @Override
                public TimeSlotByDate createFromParcel(Parcel in) {
                    return new TimeSlotByDate(in);
                }

                @Override
                public TimeSlotByDate[] newArray(int size) {
                    return new TimeSlotByDate[size];
                }
            };

            public String getSlotId() {
                return SlotId;
            }

            public void setSlotId(String slotId) {
                SlotId = slotId;
            }

            public String getTrainerId() {
                return TrainerId;
            }

            public void setTrainerId(String trainerId) {
                TrainerId = trainerId;
            }

            public String getSlotDate() {
                return SlotDate;
            }

            public void setSlotDate(String slotDate) {
                SlotDate = slotDate;
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

            public String getSessionTime() {
                return SessionTime;
            }

            public void setSessionTime(String sessionTime) {
                SessionTime = sessionTime;
            }

            public String getSessionPrice() {
                return SessionPrice;
            }

            public void setSessionPrice(String sessionPrice) {
                SessionPrice = sessionPrice;
            }

            public String getSlotTitle() {
                return SlotTitle;
            }

            public void setSlotTitle(String slotTitle) {
                SlotTitle = slotTitle;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String requestId) {
                RequestId = requestId;
            }

            public String getSlotDescription() {
                return SlotDescription;
            }

            public void setSlotDescription(String slotDescription) {
                SlotDescription = slotDescription;
            }

            public String getIsOccupied() {
                return IsOccupied;
            }

            public void setIsOccupied(String isOccupied) {
                IsOccupied = isOccupied;
            }

            public String getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(String customerId) {
                CustomerId = customerId;
            }

            public String getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(String createdOn) {
                CreatedOn = createdOn;
            }

            public String getUpdatedOn() {
                return UpdatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                UpdatedOn = updatedOn;
            }

            public String getIsDeleted() {
                return IsDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                IsDeleted = isDeleted;
            }

            @SerializedName("UpdatedOn")
            @Expose
            public String UpdatedOn;

            @SerializedName("IsDeleted")
            @Expose
            public String IsDeleted;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(SlotId);
                parcel.writeString(TrainerId);
                parcel.writeString(SlotDate);
                parcel.writeString(StartTime);
                parcel.writeString(EndTime);
                parcel.writeString(SessionTime);
                parcel.writeString(SessionPrice);
                parcel.writeString(SlotTitle);
                parcel.writeString(Address);
                parcel.writeString(RequestId);
                parcel.writeString(SlotDescription);
                parcel.writeString(IsOccupied);
                parcel.writeString(CustomerId);
                parcel.writeString(CreatedOn);
                parcel.writeString(UpdatedOn);
                parcel.writeString(IsDeleted);
            }
        }

        public static class AllSlots implements Parcelable{
            @SerializedName("SlotId")
            @Expose
            public String SlotId;

            @SerializedName("TrainerId")
            @Expose
            public String TrainerId;

            @SerializedName("SlotDate")
            @Expose
            public String SlotDate;

            @SerializedName("StartTime")
            @Expose
            public String StartTime;

            @SerializedName("EndTime")
            @Expose
            public String EndTime;

            @SerializedName("SessionTime")
            @Expose
            public String SessionTime;

            @SerializedName("SessionPrice")
            @Expose
            public String SessionPrice;

            @SerializedName("SlotTitle")
            @Expose
            public String SlotTitle;

            @SerializedName("Address")
            @Expose
            public String Address;

            @SerializedName("SlotDescription")
            @Expose
            public String SlotDescription;

            @SerializedName("IsOccupied")
            @Expose
            public String IsOccupied;

            @SerializedName("CustomerId")
            @Expose
            public String CustomerId;

            @SerializedName("CreatedOn")
            @Expose
            public String CreatedOn;

            protected AllSlots(Parcel in) {
                SlotId = in.readString();
                TrainerId = in.readString();
                SlotDate = in.readString();
                StartTime = in.readString();
                EndTime = in.readString();
                SessionTime = in.readString();
                SessionPrice = in.readString();
                SlotTitle = in.readString();
                Address = in.readString();
                SlotDescription = in.readString();
                IsOccupied = in.readString();
                CustomerId = in.readString();
                CreatedOn = in.readString();
                UpdatedOn = in.readString();
                IsDeleted = in.readString();
                RequestId = in.readString();
            }

            public static final Creator<AllSlots> CREATOR = new Creator<AllSlots>() {
                @Override
                public AllSlots createFromParcel(Parcel in) {
                    return new AllSlots(in);
                }

                @Override
                public AllSlots[] newArray(int size) {
                    return new AllSlots[size];
                }
            };

            public String getSlotId() {
                return SlotId;
            }

            public void setSlotId(String slotId) {
                SlotId = slotId;
            }

            public String getTrainerId() {
                return TrainerId;
            }

            public void setTrainerId(String trainerId) {
                TrainerId = trainerId;
            }

            public String getSlotDate() {
                return SlotDate;
            }

            public void setSlotDate(String slotDate) {
                SlotDate = slotDate;
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

            public String getSessionTime() {
                return SessionTime;
            }

            public void setSessionTime(String sessionTime) {
                SessionTime = sessionTime;
            }

            public String getSessionPrice() {
                return SessionPrice;
            }

            public void setSessionPrice(String sessionPrice) {
                SessionPrice = sessionPrice;
            }

            public String getSlotTitle() {
                return SlotTitle;
            }

            public void setSlotTitle(String slotTitle) {
                SlotTitle = slotTitle;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            public String getSlotDescription() {
                return SlotDescription;
            }

            public void setSlotDescription(String slotDescription) {
                SlotDescription = slotDescription;
            }

            public String getIsOccupied() {
                return IsOccupied;
            }

            public void setIsOccupied(String isOccupied) {
                IsOccupied = isOccupied;
            }

            public String getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(String customerId) {
                CustomerId = customerId;
            }

            public String getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(String createdOn) {
                CreatedOn = createdOn;
            }

            public String getUpdatedOn() {
                return UpdatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                UpdatedOn = updatedOn;
            }

            public String getIsDeleted() {
                return IsDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                IsDeleted = isDeleted;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String requestId) {
                RequestId = requestId;
            }

            @SerializedName("UpdatedOn")
            @Expose
            public String UpdatedOn;

            @SerializedName("IsDeleted")
            @Expose
            public String IsDeleted;

            @SerializedName("RequestId")
            @Expose
            public String RequestId;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(SlotId);
                parcel.writeString(TrainerId);
                parcel.writeString(SlotDate);
                parcel.writeString(StartTime);
                parcel.writeString(EndTime);
                parcel.writeString(SessionTime);
                parcel.writeString(SessionPrice);
                parcel.writeString(SlotTitle);
                parcel.writeString(Address);
                parcel.writeString(SlotDescription);
                parcel.writeString(IsOccupied);
                parcel.writeString(CustomerId);
                parcel.writeString(CreatedOn);
                parcel.writeString(UpdatedOn);
                parcel.writeString(IsDeleted);
                parcel.writeString(RequestId);
            }
        }

        public static class TrainerByDate implements Parcelable{

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

            protected TrainerByDate(Parcel in) {
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

            public static final Creator<TrainerByDate> CREATOR = new Creator<TrainerByDate>() {
                @Override
                public TrainerByDate createFromParcel(Parcel in) {
                    return new TrainerByDate(in);
                }

                @Override
                public TrainerByDate[] newArray(int size) {
                    return new TrainerByDate[size];
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
