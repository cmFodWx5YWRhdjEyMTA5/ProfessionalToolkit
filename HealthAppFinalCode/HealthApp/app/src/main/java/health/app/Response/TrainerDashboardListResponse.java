package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 8/31/2017.
 */

public class TrainerDashboardListResponse implements Parcelable{
    @SerializedName("status")
    @Expose
    public boolean status;

    protected TrainerDashboardListResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        approvedDataList = in.createTypedArrayList(ApprovedData.CREATOR);
    }

    public static final Creator<TrainerDashboardListResponse> CREATOR = new Creator<TrainerDashboardListResponse>() {
        @Override
        public TrainerDashboardListResponse createFromParcel(Parcel in) {
            return new TrainerDashboardListResponse(in);
        }

        @Override
        public TrainerDashboardListResponse[] newArray(int size) {
            return new TrainerDashboardListResponse[size];
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

    public List<ApprovedData> getApprovedDataList() {
        return approvedDataList;
    }

    public void setApprovedDataList(List<ApprovedData> approvedDataList) {
        this.approvedDataList = approvedDataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<ApprovedData> approvedDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeTypedList(approvedDataList);
    }

    public static class ApprovedData implements Parcelable{

        @SerializedName("RequestId")
        @Expose
        public String RequestId;


        @SerializedName("CustomerId")
        @Expose
        public String CustomerId;

        @SerializedName("Type")
        @Expose
        public String Type;

        @SerializedName("TrainerId")
        @Expose
        public String TrainerId;

        @SerializedName("PackageId")
        @Expose
        public String PackageId;

        @SerializedName("CreatedOn")
        @Expose
        public String CreatedOn;

        @SerializedName("UpdatedOn")
        @Expose
        public String UpdatedOn;

        @SerializedName("IsApproved")
        @Expose
        public String IsApproved;

        @SerializedName("IsDeleted")
        @Expose
        public String IsDeleted;

        @SerializedName("first_name")
        @Expose
        public String first_name;

        @SerializedName("last_name")
        @Expose
        public String last_name;

        @SerializedName("profile_image")
        @Expose
        public String profile_image;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("training_type")
        @Expose
        public String training_type;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("latitude")
        @Expose
        public String latitude;

        @SerializedName("longitude")
        @Expose
        public String longitude;

        @SerializedName("age")
        @Expose
        public String age;

        @SerializedName("short_bio")
        @Expose
        public String short_bio;

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

        @SerializedName("Hips")
        @Expose
        public String Hips;

        @SerializedName("Neck")
        @Expose
        public String Neck;

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String requestId) {
            RequestId = requestId;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getTrainerId() {
            return TrainerId;
        }

        public void setTrainerId(String trainerId) {
            TrainerId = trainerId;
        }

        public String getPackageId() {
            return PackageId;
        }

        public void setPackageId(String packageId) {
            PackageId = packageId;
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

        public String getIsApproved() {
            return IsApproved;
        }

        public void setIsApproved(String isApproved) {
            IsApproved = isApproved;
        }

        public String getIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            IsDeleted = isDeleted;
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

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTraining_type() {
            return training_type;
        }

        public void setTraining_type(String training_type) {
            this.training_type = training_type;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getShort_bio() {
            return short_bio;
        }

        public void setShort_bio(String short_bio) {
            this.short_bio = short_bio;
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

        public String getHips() {
            return Hips;
        }

        public void setHips(String hips) {
            Hips = hips;
        }

        public String getNeck() {
            return Neck;
        }

        public void setNeck(String neck) {
            Neck = neck;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public List<Packages> getPackagesList() {
            return packagesList;
        }

        public void setPackagesList(List<Packages> packagesList) {
            this.packagesList = packagesList;
        }

        @SerializedName("gender")
        @Expose
        public String gender;

        @SerializedName("packages")
        @Expose
        public List<Packages> packagesList;

        public String getImage_base_url() {
            return image_base_url;
        }

        public void setImage_base_url(String image_base_url) {
            this.image_base_url = image_base_url;
        }

        @SerializedName("image_base_url")
        @Expose
        public String image_base_url;

        protected ApprovedData(Parcel in) {
            RequestId = in.readString();
            CustomerId = in.readString();
            Type = in.readString();
            TrainerId = in.readString();
            PackageId = in.readString();
            CreatedOn = in.readString();
            UpdatedOn = in.readString();
            IsApproved = in.readString();
            IsDeleted = in.readString();
            first_name = in.readString();
            last_name = in.readString();
            profile_image = in.readString();
            phone = in.readString();
            training_type = in.readString();
            email = in.readString();
            latitude = in.readString();
            longitude = in.readString();
            age = in.readString();
            short_bio = in.readString();
            fat = in.readString();
            weight = in.readString();
            height = in.readString();
            Waist = in.readString();
            Hips = in.readString();
            Neck = in.readString();
            gender = in.readString();
            packagesList = in.createTypedArrayList(Packages.CREATOR);
            image_base_url = in.readString();
        }

        public static final Creator<ApprovedData> CREATOR = new Creator<ApprovedData>() {
            @Override
            public ApprovedData createFromParcel(Parcel in) {
                return new ApprovedData(in);
            }

            @Override
            public ApprovedData[] newArray(int size) {
                return new ApprovedData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(RequestId);
            parcel.writeString(CustomerId);
            parcel.writeString(Type);
            parcel.writeString(TrainerId);
            parcel.writeString(PackageId);
            parcel.writeString(CreatedOn);
            parcel.writeString(UpdatedOn);
            parcel.writeString(IsApproved);
            parcel.writeString(IsDeleted);
            parcel.writeString(first_name);
            parcel.writeString(last_name);
            parcel.writeString(profile_image);
            parcel.writeString(phone);
            parcel.writeString(training_type);
            parcel.writeString(email);
            parcel.writeString(latitude);
            parcel.writeString(longitude);
            parcel.writeString(age);
            parcel.writeString(short_bio);
            parcel.writeString(fat);
            parcel.writeString(weight);
            parcel.writeString(height);
            parcel.writeString(Waist);
            parcel.writeString(Hips);
            parcel.writeString(Neck);
            parcel.writeString(gender);
            parcel.writeTypedList(packagesList);
            parcel.writeString(image_base_url);
        }


        public static class Packages implements Parcelable{

            @SerializedName("SlotId")
            @Expose
            public String SlotId;

            @SerializedName("TrainerId")
            @Expose
            public String TrainerId;

            @SerializedName("SessionTime")
            @Expose
            public String SessionTime;

            @SerializedName("SessionPrice")
            @Expose
            public String SessionPrice;

            @SerializedName("SlotTitle")
            @Expose
            public String SlotTitle;

            @SerializedName("IsOccupied")
            @Expose
            public String IsOccupied;

            @SerializedName("CustomerId")
            @Expose
            public String CustomerId;

            @SerializedName("CreatedOn")
            @Expose
            public String CreatedOn;

            @SerializedName("UpdatedOn")
            @Expose
            public String UpdatedOn;

            @SerializedName("IsDeleted")
            @Expose
            public String IsDeleted;

            @SerializedName("NoOfSlots")
            @Expose
            public String NoOfSlots;

            @SerializedName("SessionDays")
            @Expose
            public String SessionDays;

            @SerializedName("NoOfDays")
            @Expose
            public String NoOfDays;

            @SerializedName("RequestId")
            @Expose
            public String RequestId;

            @SerializedName("Type")
            @Expose
            public String Type;

            @SerializedName("PackageId")
            @Expose
            public String PackageId;

            @SerializedName("IsApproved")
            @Expose
            public String IsApproved;

            @SerializedName("first_name")
            @Expose
            public String first_name;

            @SerializedName("last_name")
            @Expose
            public String last_name;

            @SerializedName("profile_image")
            @Expose
            public String profile_image;

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

            public String getNoOfSlots() {
                return NoOfSlots;
            }

            public void setNoOfSlots(String noOfSlots) {
                NoOfSlots = noOfSlots;
            }

            public String getSessionDays() {
                return SessionDays;
            }

            public void setSessionDays(String sessionDays) {
                SessionDays = sessionDays;
            }

            public String getNoOfDays() {
                return NoOfDays;
            }

            public void setNoOfDays(String noOfDays) {
                NoOfDays = noOfDays;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String requestId) {
                RequestId = requestId;
            }

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }

            public String getPackageId() {
                return PackageId;
            }

            public void setPackageId(String packageId) {
                PackageId = packageId;
            }

            public String getIsApproved() {
                return IsApproved;
            }

            public void setIsApproved(String isApproved) {
                IsApproved = isApproved;
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

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public List<SelectSlots> getSelectSlotsList() {
                return selectSlotsList;
            }

            public void setSelectSlotsList(List<SelectSlots> selectSlotsList) {
                this.selectSlotsList = selectSlotsList;
            }

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("selected_slots")
            @Expose
            public List<SelectSlots> selectSlotsList;

            protected Packages(Parcel in) {
                SlotId = in.readString();
                TrainerId = in.readString();
                SessionTime = in.readString();
                SessionPrice = in.readString();
                SlotTitle = in.readString();
                IsOccupied = in.readString();
                CustomerId = in.readString();
                CreatedOn = in.readString();
                UpdatedOn = in.readString();
                IsDeleted = in.readString();
                NoOfSlots = in.readString();
                SessionDays = in.readString();
                NoOfDays = in.readString();
                RequestId = in.readString();
                Type = in.readString();
                PackageId = in.readString();
                IsApproved = in.readString();
                first_name = in.readString();
                last_name = in.readString();
                profile_image = in.readString();
                phone = in.readString();
                selectSlotsList = in.createTypedArrayList(SelectSlots.CREATOR);
            }

            public static final Creator<Packages> CREATOR = new Creator<Packages>() {
                @Override
                public Packages createFromParcel(Parcel in) {
                    return new Packages(in);
                }

                @Override
                public Packages[] newArray(int size) {
                    return new Packages[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(SlotId);
                parcel.writeString(TrainerId);
                parcel.writeString(SessionTime);
                parcel.writeString(SessionPrice);
                parcel.writeString(SlotTitle);
                parcel.writeString(IsOccupied);
                parcel.writeString(CustomerId);
                parcel.writeString(CreatedOn);
                parcel.writeString(UpdatedOn);
                parcel.writeString(IsDeleted);
                parcel.writeString(NoOfSlots);
                parcel.writeString(SessionDays);
                parcel.writeString(NoOfDays);
                parcel.writeString(RequestId);
                parcel.writeString(Type);
                parcel.writeString(PackageId);
                parcel.writeString(IsApproved);
                parcel.writeString(first_name);
                parcel.writeString(last_name);
                parcel.writeString(profile_image);
                parcel.writeString(phone);
                parcel.writeTypedList(selectSlotsList);
            }

            public static class SelectSlots implements Parcelable{

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

                @SerializedName("StartTime")
                @Expose
                public String StartTime;

                @SerializedName("EndTime")
                @Expose
                public String EndTime;

                @SerializedName("IsDeleted")
                @Expose
                public String IsDeleted;

                public String getStatus() {
                    return Status;
                }

                public void setStatus(String status) {
                    Status = status;
                }

                @SerializedName("Status")
                @Expose
                public String Status;

                protected SelectSlots(Parcel in) {
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

                public static final Creator<SelectSlots> CREATOR = new Creator<SelectSlots>() {
                    @Override
                    public SelectSlots createFromParcel(Parcel in) {
                        return new SelectSlots(in);
                    }

                    @Override
                    public SelectSlots[] newArray(int size) {
                        return new SelectSlots[size];
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
    }
}
