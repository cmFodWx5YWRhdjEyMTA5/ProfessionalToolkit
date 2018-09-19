package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer Six on 10/4/2017.
 */

public class SessionListResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    public boolean status;

    protected SessionListResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        sessionData = in.readParcelable(SessionData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(msg);
        dest.writeParcelable(sessionData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SessionListResponse> CREATOR = new Creator<SessionListResponse>() {
        @Override
        public SessionListResponse createFromParcel(Parcel in) {
            return new SessionListResponse(in);
        }

        @Override
        public SessionListResponse[] newArray(int size) {
            return new SessionListResponse[size];
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

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public SessionData sessionData;

    public static class SessionData implements Parcelable{
        protected SessionData(Parcel in) {
            sessionsList = in.createTypedArrayList(Sessions.CREATOR);
            imgUrl = in.readString();
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

        public List<Sessions> getSessionsList() {
            return sessionsList;
        }

        public void setSessionsList(List<Sessions> sessionsList) {
            this.sessionsList = sessionsList;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @SerializedName("packages")
        @Expose
        public List<Sessions> sessionsList = new ArrayList<>();

        @SerializedName("img_url")
        @Expose
        public String imgUrl;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(sessionsList);
            parcel.writeString(imgUrl);
        }

        public static class Sessions implements Parcelable {

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

            protected Sessions(Parcel in) {
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
                occupiedDateList = in.createTypedArrayList(OccupiedDate.CREATOR);
            }

            public static final Creator<Sessions> CREATOR = new Creator<Sessions>() {
                @Override
                public Sessions createFromParcel(Parcel in) {
                    return new Sessions(in);
                }

                @Override
                public Sessions[] newArray(int size) {
                    return new Sessions[size];
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

            public List<OccupiedDate> getOccupiedDateList() {
                return occupiedDateList;
            }

            public void setOccupiedDateList(List<OccupiedDate> occupiedDateList) {
                this.occupiedDateList = occupiedDateList;
            }

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("occupied_dates")
            @Expose
            List<OccupiedDate> occupiedDateList;

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
                parcel.writeTypedList(occupiedDateList);
            }


            public static class OccupiedDate implements Parcelable{

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

                protected OccupiedDate(Parcel in) {
                    SessionSlotId = in.readString();
                    CustomerId = in.readString();
                    PackageId = in.readString();
                    RequestId = in.readString();
                    SessionDate = in.readString();
                    StartTime = in.readString();
                    EndTime = in.readString();
                    IsDeleted = in.readString();
                }

                public static final Creator<OccupiedDate> CREATOR = new Creator<OccupiedDate>() {
                    @Override
                    public OccupiedDate createFromParcel(Parcel in) {
                        return new OccupiedDate(in);
                    }

                    @Override
                    public OccupiedDate[] newArray(int size) {
                        return new OccupiedDate[size];
                    }
                };

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

                @SerializedName("EndTime")
                @Expose
                public String EndTime;

                @SerializedName("IsDeleted")
                @Expose
                public String IsDeleted;

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
                }
            }
        }
    }
}
