package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class MyTrainerListResponse implements Parcelable{
    @SerializedName("status")
    @Expose
    public boolean status;

    protected MyTrainerListResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        myTrainerDataList = in.createTypedArrayList(MyTrainerData.CREATOR);
    }

    public static final Creator<MyTrainerListResponse> CREATOR = new Creator<MyTrainerListResponse>() {
        @Override
        public MyTrainerListResponse createFromParcel(Parcel in) {
            return new MyTrainerListResponse(in);
        }

        @Override
        public MyTrainerListResponse[] newArray(int size) {
            return new MyTrainerListResponse[size];
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

    public List<MyTrainerData> getMyTrainerDataList() {
        return myTrainerDataList;
    }

    public void setMyTrainerDataList(List<MyTrainerData> myTrainerDataList) {
        this.myTrainerDataList = myTrainerDataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<MyTrainerData> myTrainerDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeTypedList(myTrainerDataList);
    }

    public static class MyTrainerData implements Parcelable  {

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

        @SerializedName("slot_address")
        @Expose
        public String slot_address;

        @SerializedName("first_name")
        @Expose
        public String first_name;

        public String getUniqueId() {
            return UniqueId;
        }

        public void setUniqueId(String uniqueId) {
            UniqueId = uniqueId;
        }

        @SerializedName("UniqueId")
        @Expose
        public String UniqueId;

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

        public String getSlot_address() {
            return slot_address;
        }

        public void setSlot_address(String slot_address) {
            this.slot_address = slot_address;
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

        public List<Sessions> getSessionsList() {
            return sessionsList;
        }

        public void setSessionsList(List<Sessions> sessionsList) {
            this.sessionsList = sessionsList;
        }

        @SerializedName("packages")
        @Expose
        public List<Sessions> sessionsList;

        protected MyTrainerData(Parcel in) {
            RequestId = in.readString();
            CustomerId = in.readString();
            Type = in.readString();
            TrainerId = in.readString();
            PackageId = in.readString();
            CreatedOn = in.readString();
            UpdatedOn = in.readString();
            IsApproved = in.readString();
            IsDeleted = in.readString();
            slot_address = in.readString();
            first_name = in.readString();
            UniqueId=in.readString();
            last_name = in.readString();
            profile_image = in.readString();
            phone = in.readString();
            training_type = in.readString();
            email = in.readString();
            latitude = in.readString();
            longitude = in.readString();
            sessionsList = in.createTypedArrayList(Sessions.CREATOR);
        }

        public static final Creator<MyTrainerData> CREATOR = new Creator<MyTrainerData>() {
            @Override
            public MyTrainerData createFromParcel(Parcel in) {
                return new MyTrainerData(in);
            }

            @Override
            public MyTrainerData[] newArray(int size) {
                return new MyTrainerData[size];
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
            parcel.writeString(slot_address);
            parcel.writeString(first_name);
            parcel.writeString(UniqueId);
            parcel.writeString(last_name);
            parcel.writeString(profile_image);
            parcel.writeString(phone);
            parcel.writeString(training_type);
            parcel.writeString(email);
            parcel.writeString(latitude);
            parcel.writeString(longitude);
            parcel.writeTypedList(sessionsList);
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

            @SerializedName("phone")
            @Expose
            public String phone;

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

            public List<SelectedSlot1> getSelectedSlot1List() {
                return selectedSlot1List;
            }

            public void setSelectedSlot1List(List<SelectedSlot1> selectedSlot1List) {
                this.selectedSlot1List = selectedSlot1List;
            }

            @SerializedName("selected_slots")
            @Expose
            List<SelectedSlot1> selectedSlot1List;

            protected Sessions(Parcel in) {
                SlotId = in.readString();
                TrainerId = in.readString();
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
                selectedSlot1List = in.createTypedArrayList(SelectedSlot1.CREATOR);
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
                parcel.writeString(Address);
                parcel.writeString(SlotDescription);
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
                parcel.writeTypedList(selectedSlot1List);
            }


            public static class SelectedSlot1 implements Parcelable {

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

                protected SelectedSlot1(Parcel in) {
                    SessionSlotId = in.readString();
                    CustomerId = in.readString();
                    PackageId = in.readString();
                    RequestId = in.readString();
                    SessionDate = in.readString();
                    StartTime = in.readString();
                    EndTime = in.readString();
                    IsDeleted = in.readString();
                }

                public static final Creator<SelectedSlot1> CREATOR = new Creator<SelectedSlot1>() {
                    @Override
                    public SelectedSlot1 createFromParcel(Parcel in) {
                        return new SelectedSlot1(in);
                    }

                    @Override
                    public SelectedSlot1[] newArray(int size) {
                        return new SelectedSlot1[size];
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
                }
            }
        }
    }
}
