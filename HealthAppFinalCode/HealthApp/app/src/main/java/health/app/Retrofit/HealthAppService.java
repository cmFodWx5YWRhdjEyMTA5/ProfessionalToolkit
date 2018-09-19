package health.app.Retrofit;

import java.util.Map;

import health.app.Response.CancelSessionSlot;
import health.app.Response.ChatListResponse;
import health.app.Response.ForgotPasswordResponse;
import health.app.Response.GetRequestResponse;
import health.app.Response.GetSlotResponse;
import health.app.Response.GetUserInfoResponse;
import health.app.Response.LoginResponse;
import health.app.Response.MeasurementResponse;
import health.app.Response.MessageListResponse;
import health.app.Response.MySessionResponse;
import health.app.Response.MyTrainerListResponse;
import health.app.Response.NewMessageResponse;
import health.app.Response.NotificationCount;
import health.app.Response.NotificationResponse;
import health.app.Response.ProfileDataResponse;
import health.app.Response.ReportListResponse;
import health.app.Response.SessionListResponse;
import health.app.Response.SessionSlotResponse;
import health.app.Response.SignUpResponse;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Response.TimeSlotResponse;
import health.app.Response.TrainerByIdResponse;
import health.app.Response.TrainerCalenderResponse;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Response.TrainerListResponse;
import health.app.Response.TrainerPackageResponse;
import health.app.Response.TrainerSlotsResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Developer Six on 8/8/2017.
 */

public interface HealthAppService {
    @Multipart
    @POST("register")
    Call<SignUpResponse> signUpTrainer(@Part MultipartBody.Part imageFile,
                                @PartMap Map<String, RequestBody> field);

    @Multipart
    @POST("register")
    Call<SignUpResponse> signUpCustomer(@Part MultipartBody.Part imageFile1,
                                @PartMap Map<String, RequestBody> field1);

    @Multipart
    @POST("customer/edit_profile")
    Call<SignUpResponse> editCustomerProfile(@Part MultipartBody.Part image,
                                @PartMap Map<String, RequestBody> field);

    @Multipart
    @POST("trainer/edit_profile")
    Call<SignUpResponse> editTrainerProfile(@Part MultipartBody.Part image1,
                                             @PartMap Map<String, RequestBody> field1);

    @FormUrlEncoded
    @POST("forget")
    Call<ForgotPasswordResponse> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("customer/session_start_request_actions")
    Call<NewMessageResponse> startRequest(@Field("UserId") String userId,@Field("RequestId") String requestId,@Field("SessionSlotId") String sessionSlotId,@Field("N_Id") String nId,@Field("Status") String status,@Field("TrainerId") String trainerId);

    @FormUrlEncoded
    @POST("customer/session_complete_request_actions")
    Call<NewMessageResponse> completeRequest(@Field("UserId") String userId,@Field("RequestId") String requestId,@Field("SessionSlotId") String sessionSlotId,@Field("N_Id") String nId,@Field("Status") String status,@Field("TrainerId") String trainerId);

    @FormUrlEncoded
    @POST("common/create_time_slots")
    Call<TrainerSlotsResponse> getSlots(@Field("interval") String interval);

    @FormUrlEncoded
    @POST("customer/cancel_slot_details")
    Call<CancelSessionSlot> getSlotById(@Field("SessionSlotId") String sessionSlotId,@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_all_measurements")
    Call<MeasurementResponse> getAllMeasurement(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> customerLogin(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("token") String api_token,
                                      @Field("longitude") String longitude,
                                      @Field("latitude") String latitude,
                                      @Field("token_type")String token_type,
                                      @Field("user_type")String user_type);

    @FormUrlEncoded
    @POST("customer/get_trainers")
    Call<TrainerListResponse> getTrainers(@Field("UserId") String userId,
                                          @Field("longitude") String longitude,
                                          @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST("trainer/send_start_session_request")
    Call<NewMessageResponse> startSession(@Field("UserId") String userId,
                                          @Field("CustomerId") String customerId,
                                          @Field("SessionSlotId") String sessionSlotId);

    @FormUrlEncoded
    @POST("customer/send_request")
    Call<GetSlotResponse> sendPackage(@Field("UserId")String userId,
                                      @Field("PackageId") String packageId,
                                      @Field("TrainerId") String trainerId,
                                      @Field("CustomerSessionSlots") String customerSessionSlots);

    @FormUrlEncoded
    @POST("customer/session_slot_cancel_request_actions")
    Call<NewMessageResponse> addNewSlots(@Field("EndTime")String endTime,
                                         @Field("PackageId") String packageId,
                                         @Field("RequestId") String requestId,
                                         @Field("SessionDate") String sessionDate,
                                         @Field("SessionSlotId") String sessionSlotId,
                                         @Field("StartTime") String startTime,
                                         @Field("TrainerId") String trainerId,
                                         @Field("UserId") String userId
    );

    @FormUrlEncoded
    @POST("customer/get_trainers")
    Call<TrainerListResponse> getTrainersList(@Field("UserId") String userId,
                                              @Field("longitude") String longitude,
                                              @Field("latitude") String latitude,
                                              @Field("first_name") String firstName,
                                              @Field("last_name") String lastName,
                                              @Field("phone") String phone,
                                              @Field("training_type") String trainingType,
                                              @Field("SlotDate") String SlotDate,
                                              @Field("StartTime") String startTime,
                                              @Field("EndTime") String endTime);



    @FormUrlEncoded
    @POST("messages/get_inbox_messages")
    Call<MessageListResponse> getInboxMessage(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_packages")
    Call<TrainerPackageResponse> getTrainerPackage(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_all_measurements")
    Call<ReportListResponse> getReport(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_trainer_by_id")
    Call<TrainerByIdResponse>trainerById(@Field("UniqueId") String uniqueId,@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_session_records")
    Call<MySessionResponse> getSessions(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_complete_session_records")
    Call<MySessionResponse> customerCompleteSession(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_completed_session_records")
    Call<MySessionResponse> trainerCompleteSession(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("messages/get_chat_list")
    Call<ChatListResponse> getChatList(@Field("UserId") String userId,@Field("SenderId") String senderId,@Field("Page") String page);

    @FormUrlEncoded
    @POST("customer/send_request")
    Call<NewMessageResponse> sendRequest(@Field("UserId") String userId, @Field("SlotId") String slotId, @Field("TrainerId") String trainerId);

    @FormUrlEncoded
    @POST("trainer/get_requests")
    Call<GetRequestResponse> getRequest(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("messages/get_sent_messages")
    Call<MessageListResponse> getSentMessage(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_user_data")
    Call<ProfileDataResponse> getCustomerData(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_user_data")
    Call<ProfileDataResponse> getTrainerData(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("common/user_info")
    Call<GetUserInfoResponse> getData(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("notifications")
    Call<NotificationResponse> getNotifications(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_approved_requests")
    Call<TrainerDashboardListResponse> getClients(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("notifications/get_notification_count")
    Call<NotificationCount> getCount(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("trainer/get_dashboard_calender")
    Call<TrainerCalenderResponse> getCalenderCountTrainer(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_dashboard_calender")
    Call<TrainerCalenderResponse> getCalenderCountClienr(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("customer/get_approved_trainers")
    Call<MyTrainerListResponse> getTrainer(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("messages/delete_message")
    Call<SignUpResponse> deleteMessage(@Field("UserId") String userId,@Field("MsgId") String msgId);

    @FormUrlEncoded
    @POST("trainer/delete_package")
    Call<TrainerPackageResponse> deletePackage(@Field("UserId") String userId,@Field("SlotId") String slotId);

    @FormUrlEncoded
    @POST("common/logout")
    Call<NewMessageResponse> logoutUser(@Field("UserId") String userId, @Field("token") String token, @Field("token_type") String tokenType);


    @FormUrlEncoded
    @POST("common/get_timeslot_by_date")
    Call<TimeSlotByDateResponse> timeSlotByDate(@Field("UserId") String userId, @Field("SlotDate") String SlotDate);

    @FormUrlEncoded
    @POST("customer/get_session_records")
    Call<SessionListResponse> getSessionByDate(@Field("UserId") String userId, @Field("Date") String SlotDate);

    @FormUrlEncoded
    @POST("trainer/get_sessions_by_date")
    Call<MySessionResponse> getTSessionDate(@Field("UserId") String userId, @Field("Date") String SlotDate);

    @FormUrlEncoded
    @POST("customer/get_sessions_by_date")
    Call<MySessionResponse> getCSessionDate(@Field("UserId") String userId, @Field("Date") String SlotDate);

    @FormUrlEncoded
    @POST("customer/get_sessions_by_trainer_and_date")
    Call<SessionSlotResponse> getSessionByCustomer(@Field("UserId") String userId, @Field("Date") String slotDate, @Field("RequestId") String requestId);

    @FormUrlEncoded
    @POST("trainer/get_sessions_by_customer_and_date")
    Call<SessionSlotResponse> getSessionByTrainer(@Field("UserId") String userId, @Field("Date") String slotDate, @Field("RequestId") String requestId);

    @FormUrlEncoded
    @POST("customer/get_session_records")
    Call<MySessionResponse> getAllSession(@Field("UserId") String userId);

    @FormUrlEncoded
    @POST("common/get_timeslot_by_date")
    Call<TimeSlotByDateResponse> timeSlotByDateCustomer(@Field("UserId") String userId, @Field("SlotDate") String SlotDate,@Field("CustomerId") String customerId);
    @FormUrlEncoded
    @POST("common/change_password")
    Call<NewMessageResponse> changePassword(@Field("UserId") String userId, @Field("old_password") String oldPassword, @Field("password") String newPassword);

    @FormUrlEncoded
    @POST("messages/send_message")
    Call<NewMessageResponse> sendMessage(@Field("UserId") String userId, @Field("RecieverId") String receiverId, @Field("Message") String message);

    @FormUrlEncoded
    @POST("trainer/add_measurements")
    Call<MeasurementResponse> addStats(@Field("UserId") String userId,@Field("CustomerId") String customerId, @Field("Weight") String weight, @Field("Height") String height,@Field("Waist") String waist,@Field("Neck") String neck,@Field("BMI") String BMI,@Field("Hips") String Hips,@Field("Arm") String Arm,@Field("Chest") String Chest,@Field("Calf") String Calf,@Field("Thigh") String Thigh,@Field("Date") String date);

    @FormUrlEncoded
    @POST("customer/add_measurements")
    Call<MeasurementResponse> addMeasurement(@Field("UserId") String userId, @Field("Weight") String weight, @Field("Height") String height,@Field("Waist") String waist,@Field("Neck") String neck,@Field("BMI") String BMI,@Field("Hips") String Hips,@Field("Date") String date);
    @FormUrlEncoded
    @POST("trainer/delete_slot")
    Call<TimeSlotResponse> deleteSlotTrainer(@Field("UserId") String userId, @Field("SlotId") String slotId);

    @FormUrlEncoded
    @POST("customer/session_slot_cancel_request_actions")
    Call<TimeSlotResponse> cancelSession(@Field("UserId") String userId, @Field("StartTime") String startTime,@Field("EndTime") String endTime,@Field("RequestId") String requestId,@Field("TrainerId") String trainerId,@Field("PackageId") String packageId,@Field("SessionSlotId") String sessionSlotId,@Field("SessionDate") String sessionDate);

    @FormUrlEncoded
    @POST("trainer/cancle_time_slot")
    Call<TimeSlotResponse> trainercancelSession(@Field("UserId") String userId, @Field("SessionSlotId") String sessionSlotId,@Field("CustomerId") String customerId);

    @FormUrlEncoded
    @POST("trainer/add_time_slots")
    Call<TimeSlotResponse> addSlotsTrainer(@Field("UserId") String userId, @Field("SlotTitle") String slotTitle, @Field("SlotDescription") String slotDescription, @Field("SlotDate") String slotDate, @Field("StartTime") String startTime, @Field("EndTime") String endTime, @Field("Address") String address);

    @FormUrlEncoded
    @POST("trainer/add_time_slots")
    Call<TimeSlotResponse> addTrainerPackage( @Field("SessionTime") String sessionTime, @Field("SessionPrice") String sessionPrice, @Field("SlotTitle") String slotTitle, @Field("SessionDays") String sessionDays, @Field("NoOfDays") String noOfDays, @Field("UserId") String userId,@Field("NoOfSlots") String noOfSlots);

    @FormUrlEncoded
    @POST("trainer/add_time_slots")
    Call<TimeSlotResponse> updateTrainerPackage( @Field("SessionTime") String sessionTime, @Field("SessionPrice") String sessionPrice, @Field("SlotTitle") String slotTitle, @Field("SessionDays") String sessionDays, @Field("NoOfDays") String noOfDays, @Field("UserId") String userId,@Field("NoOfSlots") String noOfSlots,@Field("SlotId")String slotId);

    @Multipart
    @POST("trainer/add_time_slots")
    Call<TimeSlotResponse> addSlotsByTrainer(@PartMap Map<String, RequestBody> field);
    @FormUrlEncoded
    @POST("messages/send_message")
    Call<NewMessageResponse> sendMessageToClient(@Field("UserId") String userId, @Field("RecieverId") String receiverId, @Field("Message") String message);

    @FormUrlEncoded
    @POST("messages/send_message")
    Call<NewMessageResponse> sendMessageToTrainer(@Field("UserId") String userId, @Field("RecieverId") String receiverId, @Field("Message") String message);

    @FormUrlEncoded
    @POST("trainer/request_actions")
    Call<NewMessageResponse> acceptRequest(@Field("UserId") String userId,@Field("RequestId") String requestId, @Field("PackageId") String slotId, @Field("CustomerId") String customerId,@Field("Status") String status);

    @FormUrlEncoded
    @POST("trainer/request_actions")
    Call<NewMessageResponse> rejectRequest(@Field("UserId") String userId,@Field("RequestId") String requestId, @Field("PackageId") String slotId, @Field("CustomerId") String customerId,@Field("Status") String status);

    @FormUrlEncoded
    @POST("trainer/mark_as_complete")
    Call<NewMessageResponse> markSession(@Field("UserId") String userId,@Field("CustomerId") String customerId, @Field("SessionSlotId") String sessionSlotId);
}