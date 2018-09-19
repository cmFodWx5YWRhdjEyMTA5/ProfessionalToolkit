package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 10/26/2017.
 */

public class MySessionResponse implements Parcelable {
    @SerializedName("data")
    @Expose
    public SessionData sessionData;

    @SerializedName("status")
    @Expose
    public boolean status;

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

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    @SerializedName("msg")
    @Expose
    public String msg;



    protected MySessionResponse(Parcel in) {
        sessionData = in.readParcelable(SessionData.class.getClassLoader());
        status = in.readByte() != 0;
        msg = in.readString();

    }

    public static final Creator<MySessionResponse> CREATOR = new Creator<MySessionResponse>() {
        @Override
        public MySessionResponse createFromParcel(Parcel in) {
            return new MySessionResponse(in);
        }

        @Override
        public MySessionResponse[] newArray(int size) {
            return new MySessionResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(sessionData, i);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);

    }

    public static class SessionData implements Parcelable{
        @SerializedName("img_url")
        @Expose
        public String img_url;

        @SerializedName("packages")
        @Expose
        public List<PackageList> packagesList;



        protected SessionData(Parcel in) {
            img_url = in.readString();
            packagesList = in.createTypedArrayList(PackageList.CREATOR);
        }

        public static final Creator<SessionData> CREATOR = new Creator<SessionData>() {
            @Override
            public SessionData createFromParcel(Parcel in) {
                return new SessionData(in);
            }

            @Override
            public SessionData[] newArray(int size) {
                return new SessionData[size];
            }
        };

        public List<PackageList> getPackagesList() {
            return packagesList;
        }

        public void setPackagesList(List<PackageList> packagesList) {
            this.packagesList = packagesList;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(img_url);
            parcel.writeTypedList(packagesList);

        }

        public static class PackageList implements Parcelable {

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

            public String getSessionDate() {
                return SessionDate;
            }

            public void setSessionDate(String sessionDate) {
                SessionDate = sessionDate;
            }

            @SerializedName("SessionDate")
            @Expose
            public String SessionDate;

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

            public List<OccupiedList> getOccupiedList() {
                return occupiedList;
            }

            public void setOccupiedList(List<OccupiedList> occupiedList) {
                this.occupiedList = occupiedList;
            }

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("occupied_dates")
            @Expose
            public List<OccupiedList> occupiedList;

            public int getPendingSession() {
                return pendingSession;
            }

            public void setPendingSession(int pendingSession) {
                this.pendingSession = pendingSession;
            }

            public int getBookedSession() {
                return bookedSession;
            }

            public void setBookedSession(int bookedSession) {
                this.bookedSession = bookedSession;
            }

            @SerializedName("pending_session")
            @Expose
            public int pendingSession;

            @SerializedName("booked_session")
            @Expose
            public int bookedSession;

            public static class OccupiedList implements Parcelable {

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

                @SerializedName("EndTime")
                @Expose
                public String EndTime;

                @SerializedName("IsDeleted")
                @Expose
                public String IsDeleted;

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

                @SerializedName("Status")
                @Expose
                public String Status;

                protected OccupiedList(Parcel in) {
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

                public static final Creator<OccupiedList> CREATOR = new Creator<OccupiedList>() {
                    @Override
                    public OccupiedList createFromParcel(Parcel in) {
                        return new OccupiedList(in);
                    }

                    @Override
                    public OccupiedList[] newArray(int size) {
                        return new OccupiedList[size];
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

            protected PackageList(Parcel in) {
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
                SessionDate = in.readString();
                Type = in.readString();
                PackageId = in.readString();
                IsApproved = in.readString();
                first_name = in.readString();
                last_name = in.readString();
                profile_image = in.readString();
                phone = in.readString();
                occupiedList = in.createTypedArrayList(OccupiedList.CREATOR);
                pendingSession = in.readInt();
                bookedSession = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(SlotId);
                dest.writeString(TrainerId);
                dest.writeString(SessionTime);
                dest.writeString(SessionPrice);
                dest.writeString(SlotTitle);
                dest.writeString(IsOccupied);
                dest.writeString(CustomerId);
                dest.writeString(CreatedOn);
                dest.writeString(UpdatedOn);
                dest.writeString(IsDeleted);
                dest.writeString(NoOfSlots);
                dest.writeString(SessionDays);
                dest.writeString(NoOfDays);
                dest.writeString(RequestId);
                dest.writeString(SessionDate);
                dest.writeString(Type);
                dest.writeString(PackageId);
                dest.writeString(IsApproved);
                dest.writeString(first_name);
                dest.writeString(last_name);
                dest.writeString(profile_image);
                dest.writeString(phone);
                dest.writeTypedList(occupiedList);
                dest.writeInt(pendingSession);
                dest.writeInt(bookedSession);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<PackageList> CREATOR = new Creator<PackageList>() {
                @Override
                public PackageList createFromParcel(Parcel in) {
                    return new PackageList(in);
                }

                @Override
                public PackageList[] newArray(int size) {
                    return new PackageList[size];
                }
            };
        }
    }
}
