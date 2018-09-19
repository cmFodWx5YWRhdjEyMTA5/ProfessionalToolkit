package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class GetRequestResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    public boolean status;

    protected GetRequestResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        requestData = in.readParcelable(RequestData.class.getClassLoader());
    }

    public static final Creator<GetRequestResponse> CREATOR = new Creator<GetRequestResponse>() {
        @Override
        public GetRequestResponse createFromParcel(Parcel in) {
            return new GetRequestResponse(in);
        }

        @Override
        public GetRequestResponse[] newArray(int size) {
            return new GetRequestResponse[size];
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

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public RequestData requestData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeParcelable(requestData, i);
    }


    public static class RequestData implements Parcelable {
        protected RequestData(Parcel in) {
            requestList = in.createTypedArrayList(Request.CREATOR);
        }

        public static final Creator<RequestData> CREATOR = new Creator<RequestData>() {
            @Override
            public RequestData createFromParcel(Parcel in) {
                return new RequestData(in);
            }

            @Override
            public RequestData[] newArray(int size) {
                return new RequestData[size];
            }
        };

        public List<Request> getRequestList() {
            return requestList;
        }

        public void setRequestList(List<Request> requestList) {
            this.requestList = requestList;
        }

        @SerializedName("requests")
        @Expose
        public List<Request> requestList;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(requestList);
        }

        public static class Request implements Parcelable {

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

            @SerializedName("customer")
            @Expose
            public TrainerListResponse.TrainerData.Trainer trainer;

            protected Request(Parcel in) {
                RequestId = in.readString();
                CustomerId = in.readString();
                Type = in.readString();
                TrainerId = in.readString();
                PackageId = in.readString();
                CreatedOn = in.readString();
                UpdatedOn = in.readString();
                IsApproved = in.readString();
                IsDeleted = in.readString();
                trainer = in.readParcelable(TrainerListResponse.TrainerData.Trainer.class.getClassLoader());
                selectedSlotsList = in.createTypedArrayList(SelectedSlots.CREATOR);
                slot = in.readParcelable(Slot.class.getClassLoader());
            }

            public static final Creator<Request> CREATOR = new Creator<Request>() {
                @Override
                public Request createFromParcel(Parcel in) {
                    return new Request(in);
                }

                @Override
                public Request[] newArray(int size) {
                    return new Request[size];
                }
            };

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

            public TrainerListResponse.TrainerData.Trainer getTrainer() {
                return trainer;
            }

            public void setTrainer(TrainerListResponse.TrainerData.Trainer trainer) {
                this.trainer = trainer;
            }

            public List<SelectedSlots> getSelectedSlotsList() {
                return selectedSlotsList;
            }

            public void setSelectedSlotsList(List<SelectedSlots> selectedSlotsList) {
                this.selectedSlotsList = selectedSlotsList;
            }

            public Slot getSlot() {
                return slot;
            }

            public void setSlot(Slot slot) {
                this.slot = slot;
            }

            @SerializedName("selected_slots")
            @Expose
            List<SelectedSlots> selectedSlotsList;


            @SerializedName("package")
            @Expose
            public Slot slot;

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
                parcel.writeParcelable(trainer, i);
                parcel.writeTypedList(selectedSlotsList);
                parcel.writeParcelable(slot, i);
            }

            public static class Slot implements Parcelable{

                @SerializedName("SlotId")
                @Expose
                public String SlotId;

                protected Slot(Parcel in) {
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
                }

                public static final Creator<Slot> CREATOR = new Creator<Slot>() {
                    @Override
                    public Slot createFromParcel(Parcel in) {
                        return new Slot(in);
                    }

                    @Override
                    public Slot[] newArray(int size) {
                        return new Slot[size];
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
                }
            }

            public static class SelectedSlots implements Parcelable {


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


                protected SelectedSlots(Parcel in) {
                    SessionSlotId = in.readString();
                    CustomerId = in.readString();
                    PackageId = in.readString();
                    RequestId = in.readString();
                    SessionDate = in.readString();
                    StartTime = in.readString();
                    EndTime = in.readString();
                    IsDeleted = in.readString();
                }

                public static final Creator<SelectedSlots> CREATOR = new Creator<SelectedSlots>() {
                    @Override
                    public SelectedSlots createFromParcel(Parcel in) {
                        return new SelectedSlots(in);
                    }

                    @Override
                    public SelectedSlots[] newArray(int size) {
                        return new SelectedSlots[size];
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
