package com.cgc.firststep.network

import com.cgc.firststep.model.FileUploadResponse
import com.cgc.firststep.model.MatchResponse
import com.cgc.firststep.model.PostApiModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface WebAPIService {

//    @Headers(
//        "Authorization: key=" + "AAAAhxgur4U:APA91bEtnpi6dJaiQVVhTlLsW0yjimrT62jaXaVRhNONXp_Fya62sJUWc0g_Qj8KXkeEBeKQoBVQXERzBGsjko5JoSLbyKdSiyFooI0D6TnluZiD4c1CZipENLIPGnjrsaXZWY1_RCbJ",
//        "Content-Type:application/json")
//    @POST("https://fcm.googleapis.com/fcm/send")
//    fun sendNotification(@Body root: RootModel): Call<ResponseBody?>?
//
//    @Headers(
//        "Authorization: key=" + "AAAAhxgur4U:APA91bEtnpi6dJaiQVVhTlLsW0yjimrT62jaXaVRhNONXp_Fya62sJUWc0g_Qj8KXkeEBeKQoBVQXERzBGsjko5JoSLbyKdSiyFooI0D6TnluZiD4c1CZipENLIPGnjrsaXZWY1_RCbJ",
//        "Content-Type:application/json")
//    @POST("https://fcm.googleapis.com/fcm/send")
//    fun sendNotification1(@Body root: RootModelTwo): Call<ResponseBody?>?


    @Headers(
        "X-Rapidapi-Key: 5fb5f25d1dmsh3dc68741c40b6ccp18b207jsn09c225f7b137",
        "X-Rapidapi-Host: cricket-live-data.p.rapidapi.com"
    )
    @GET("fixtures-by-series/606")
    fun getSchedule(): Call<MatchResponse>


    @POST("objects")
    fun postApiExample(@Body request: PostApiModel): Call<PostApiModel>


    @Multipart
    @POST("v1/files/upload")
    fun fileUpload(
        @Part image: MultipartBody.Part,
    ): Call<FileUploadResponse>


//
//
//    @GET("about-us-content")
//    fun aboutUs(@Header("Authorization") token: String): Call<ContentResponse>
//
//    @GET("privacy-policy-content")
//    fun privacyPolicy(@Header("Authorization") token: String,): Call<ContentResponse>
//

//    @GET("term-of-condition-content")
//    fun termsCondition(@Header("Authorization") token: String,): Call<ContentResponse>
//
//    @GET("faq-content")
//    fun faq(@Header("Authorization") token: String): Call<FAQResponse>
//
//
//    @GET("provider/bank-detail")
//    fun getBankDetails(@Header("Authorization") token: String): Call<BankResponse>
//

//    @POST("contact-us")
//    fun contactUs(@Header("Authorization") token: String, @Body request: RegisterRequest): Call<LoginResponse>
//
//

//    @POST("provider/changePassword")
//    fun changePassword(@Header("Authorization") token: String, @Body userLoginRequest: RegisterRequest): Call<LoginResponse>
//
//    @GET("provider/getBookingLists?param=new")
//    fun newBooking(@Header("Authorization") token: String): Call<BookingResponse>
//
//    @GET("provider/getBookingLists?param=in_progress")
//    fun inProgressBooking(@Header("Authorization") token: String): Call<BookingResponse>
//
//    @GET("provider/getBookingLists?param=completed")
//    fun pastBooking(@Header("Authorization") token: String): Call<BookingResponse>
//
//    @GET("company/getEmployeeList?param=status")
//    fun pendingEmployee(@Header("Authorization") token: String): Call<EmployeeResponse>
//
//    @GET("company/getEmployeeList")
//    fun myEmployee(@Header("Authorization") token: String): Call<EmployeeResponse>
//
//    @POST("company/acceptOrRejectEmployeeAccount")
//    fun accountApproveReject(@Header("Authorization") token: String, @Body request: RegisterRequest): Call<LoginResponse>
//
//    @GET("provider/getBookingDetail/{bookingId}")
//    fun bookingDetail(
//        @Header("Authorization") token: String,
//        @Path("bookingId") bookingId: String
//    ): Call<BookingDetailResponse>
//
//    @POST("booking_accept_or_reject")
//    fun bookingAcceptReject(@Header("Authorization") token: String, @Body request: BookingRequest): Call<LoginResponse>
//
//    @Multipart
//    @POST("start_job")
//    fun startJob(
//        @Header("Authorization") token: String,
//        @Part("booking_id") bookingId: RequestBody,
//        @Part("otp") otp: RequestBody,
//        @Part image: MultipartBody.Part,
//    ): Call<LoginResponse>
//
//    @POST("end_job")
//    fun finishJob(@Header("Authorization") token: String, @Body request: BookingRequest): Call<LoginResponse>
//
//    @POST("provider/earnings")
//    fun earningData(@Header("Authorization") token: String, @Body request: BookingRequest): Call<EarningResponse>
//
//    @GET("provider/notifications")
//    fun getNotification(@Header("Authorization") token: String): Call<NotificationResponse>
//
//    @GET("provider/wallet")
//    fun getWallet(@Header("Authorization") token: String): Call<WalletResponse>
//
//    @POST("provider/withdraw")
//    fun withdrawApi(@Header("Authorization") token: String, @Body request: BookingRequest): Call<LoginResponse>
//

//    //TODO important Contact
//    @Multipart
//    @POST("public/api/important_contact")
//    fun importantContact(
//        @Header("Authorization") token: String,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<ContactResponse>
//
//
//    //TODO Consultation
//    @POST("public/api/consultation")
//    fun getConsultation(
//        @Header("Authorization") token: String
//    ): Call<ConsultResponse>
//
//    //TODO Handbook
//    @Multipart
//    @POST("public/api/handbook")
//    fun getHandbook(
//        @Header("Authorization") token: String,
//        @Part("language_id") langId: RequestBody,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<HandbookResponse>
//
//    //TODO Video
//    @Multipart
//    @POST("public/api/video")
//    fun getVideo(
//        @Header("Authorization") token: String,
//        @Part("training_id") langId: RequestBody,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<VideoResponse>
//
//    //TODO Publication
//    @Multipart
//    @POST("public/api/publication")
//    fun getPublication(
//        @Header("Authorization") token: String,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<PublicationResponse>
//
//    //TODO Trainging
//    @Multipart
//    @POST("public/api/training")
//    fun getTraining(
//        @Header("Authorization") token: String,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<TrainingResponse>
//
//
//    //TODO Contact Us
//    @Multipart
//    @POST("public/api/contactus")
//    fun getContactUs(
//        @Header("Authorization") token: String,
//        @Part("name") name: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("subject") subject: RequestBody,
//        @Part("message") messahe: RequestBody
//    ): Call<CommonResponse>
//
//
//    //TODO CYPC
//    @POST("public/api/cypc")
//    fun getCypc(
//        @Header("Authorization") token: String
//    ): Call<CypcResponse>
//
//    //TODO Languages
//    @GET("public/api/language")
//    fun getLanguage(
//        @Header("Authorization") token: String
//    ): Call<LanguageResponse>
//
//    //TODO Languages
//    @GET("public/api/sendcaptach")
//    fun getCaptcha(
//    ): Call<CaptchaResponse>
//
//
//
//    //TODO Languages
//    @GET("public/api/maildetail")
//    fun getDetails(
//        @Header("Authorization") token: String
//    ): Call<DetailsResponse>
//
//    //TODO Subjects
//    @GET("public/api/subject")
//    fun getSubject(
//        @Header("Authorization") token: String
//    ): Call<SubjectResponse>
//
//
//
//    //TODO CYPC
//    @Multipart
//    @POST("public/api/resource")
//    fun getResource(
//        @Header("Authorization") token: String,
//        @Part("limit") limit: RequestBody,
//        @Part("pages") pages: RequestBody
//    ): Call<ResourceResponse>
//
//
//
//
//    //TODO Save Answer
//    @POST("public/api/saveanswer")
//    fun saveAnswer(
//        @Header("Authorization") token: String,
//        @Body request: AnsRequest
//    ): Call<CommonResponse>


//    //TODO Get All Plans
//    @Multipart
//    @POST("getplans")
//    fun getAllPlans(
//        @Part("token") token: RequestBody
//    ) : Call<PlanResponse>
//
//    // TODO Save user selected plan
//    @Multipart
//    @POST("chooseplan")
//    fun choosePlanApi(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("versionid") versionId: RequestBody,
//        @Part("membership") membership: RequestBody
//    ) : Call<RegisterResponse>
//
//    // TODO Save user selected plan
//    @Multipart
//    @POST("chooseplan")
//    fun updatePlanApi(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("versionid") versionId: RequestBody
//    ) : Call<RegisterResponse>
//
//    // TODO forgot password
//    @Multipart
//    @POST("forgotpassword")
//    fun forgotPassword(
//        @Part("token") token: RequestBody,
//        @Part("email") email: RequestBody
//    ) : Call<ForPassResponse>
//
//    // TODO OTP verify
//    @Multipart
//    @POST("otpverification")
//    fun otpVerify(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("otp") otp: RequestBody
//    ) : Call<ForPassResponse>
//
//    // TODO Reset password
//    @Multipart
//    @POST("resetpassword")
//    fun resetPassword(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("newpassword") newPassword: RequestBody
//    ) : Call<ForPassResponse>
//
//    // TODO Change password
//    @Multipart
//    @POST("changepassword")
//    fun changePassword(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("oldpassword") oldPassword: RequestBody,
//        @Part("newpassword") newPassword: RequestBody
//    ) : Call<ForPassResponse>
//
//
//    // TODO full profile
//    @Multipart
//    @POST("fullprofile")
//    fun fullProfile(
//        @Part("token") token: RequestBody,
//        @Part("userid") email: RequestBody
//    ) : Call<ProfileResponse>
//
//    // TODO edit profile
//    @Multipart
//    @POST("editprofile")
//    fun editProfile(
//        @Part("token") token: RequestBody,
//        @Part("userid") email: RequestBody,
//        @Part("first_name") first_name: RequestBody,
//        @Part("last_name") last_name: RequestBody,
//        @Part("gender") gender: RequestBody,
//        @Part("dob") dob: RequestBody,
//        @Part("newsletter") newsletter: RequestBody
//    ) : Call<ProfileResponse>
//
//    // TODO edit profile pic
//    @Multipart
//    @POST("editprofilepic")
//    fun editProfilePic(
//        @Part("token") token: RequestBody,
//        @Part("userid") email: RequestBody,
//        @Part file: MultipartBody.Part
//    ) : Call<ProfileResponse>
//
//    // TODO Save Post
//    @Multipart
//    @POST("savepost")
//    fun addPostOne(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("title") title: RequestBody,
//        @Part files: MutableList<MultipartBody.Part>,
//        @Part("type") type: RequestBody,
//        @Part("privacy") privacy: RequestBody,
//        @Part("status") status: RequestBody
//    ) : Call<ProfileResponse>
//
//    // TODO Save Post
//    @Multipart
//    @POST("savepost")
//    fun addPost(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("title") title: RequestBody,
//        @Part files: MutableList<MultipartBody.Part>,
//        @Part("type") type: RequestBody,
//        @Part("privacy") privacy: RequestBody,
//        @Part("status") status: RequestBody,
//        @Part thumbnail: MultipartBody.Part
//    ) : Call<ProfileResponse>
//
//    // TODO Get all post
//    @Multipart
//    @POST("allposts")
//    fun allPost(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("page") page: RequestBody
//    ) : Call<PostResponse>
//
//    // TODO Get my posts
//    @Multipart
//    @POST("myposts")
//    fun myPost(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("page") page: RequestBody
//    ) : Call<PostResponse>
//
//    //TODO Save Message
//    @Multipart
//    @POST("savemessage")
//    fun saveMessage(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("exercise_name") exerciseName: RequestBody,
//        @Part files: MutableList<MultipartBody.Part>,
//        @Part("totalload") totalLoads: RequestBody,
//        @Part("repetitions") reps: RequestBody,
//        @Part("notes") notes: RequestBody,
//        @Part("status") status: RequestBody,
//        @Part thumbnail: MultipartBody.Part
//    ) : Call<ProfileResponse>
//
//    // TODO Get all post
//    @Multipart
//    @POST("mymessages")
//    fun myMessages(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("page") page: RequestBody
//    ) : Call<MessageResponse>
//
//    @Multipart
//    @POST("allmessages")
//    fun messageDetail(
//        @Part("token") token: RequestBody,
//        @Part("id") userId: RequestBody,
//    ) : Call<MessageDetailResponse>
//
//
//    @Multipart
//    @POST("get-count")
//    fun unreadMessage(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//    ) : Call<MessageDetailResponse>
//
//    @Multipart
//    @POST("reply")
//    fun messageReply(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part files: MutableList<MultipartBody.Part>,
//        @Part("notes") notes: RequestBody,
//        @Part("cid") messageId: RequestBody,
//        @Part thumbnail: MultipartBody.Part
//    ) : Call<MessageResponse>
//
//
//    @Multipart
//    @POST("reply")
//    fun textMessageReply(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("notes") notes: RequestBody,
//        @Part("cid") messageId: RequestBody,
//    ) : Call<MessageResponse>
//
//    // TODO Privacy policy
//    @Multipart
//    @POST("privacypolicy")
//    fun getPrivacyData(
//        @Part("token") token: RequestBody,
//        @Part("type") type: RequestBody
//    ) : Call<PrivacyResponse>
//
//    // TODO Contact us
//    @Multipart
//    @POST("contactus")
//    fun contactUs(
//        @Part("token") token: RequestBody,
//        @Part("userid") userid: RequestBody,
//        @Part("name") subject: RequestBody,
//        @Part("message") message: RequestBody,
//        @Part("email") phone: RequestBody,
//    ) : Call<PrivacyResponse>
//
//    // TODO Like a post
//    @Multipart
//    @POST("likeonpost")
//    fun postLike(
//        @Part("token") token: RequestBody,
//        @Part("userid") userid: RequestBody,
//        @Part("postid") postid: RequestBody
//    ) : Call<RegisterResponse>
//
//    @Multipart
//    @POST("deletepost")
//    fun postDelete(
//        @Part("token") token: RequestBody,
//        @Part("postid") postid: RequestBody
//    ) : Call<RegisterResponse>
//
//    @Multipart
//    @POST("commentonpost")
//    fun commentOnPost(
//        @Part("token") token: RequestBody,
//        @Part("userid") userid: RequestBody,
//        @Part("postid") postid: RequestBody,
//        @Part("content") content: RequestBody
//    ) : Call<RegisterResponse>
//
//    @Multipart
//    @POST("allcomments")
//    fun allComments(
//        @Part("token") token: RequestBody,
//        @Part("postid") postid: RequestBody
//    ) : Call<CommentResponse>
//
//    @Multipart
//    @POST("userlogout")
//    fun userLogout(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody
//    ) : Call<LoginResponse>
//
//    @Multipart
//    @POST("faqs")
//    fun faq(
//        @Part("token") token: RequestBody
//    ) : Call<FAQResponse>
//
//    @Multipart
//    @POST("all-notifications")
//    fun getNotifications(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody
//    ) : Call<NotResponse>
//
//    @Multipart
//    @POST("delete-notification")
//    fun deleteNotifications(
//        @Part("token") token: RequestBody,
//        @Part("notification_id") notId: RequestBody
//    ) : Call<NotResponse>
//
//    @Multipart
//    @POST("getsingle")
//    fun postDetails(
//        @Part("token") token: RequestBody,
//        @Part("postid") postid: RequestBody,
//        @Part("userid") userId: RequestBody
//    ) : Call<SinglePostResponse>
//
//    @Multipart
//    @POST("cardio")
//    fun cardioGuidelines(
//        @Part("token") token: RequestBody,
//        @Part("month") month: RequestBody
//    ) : Call<CardioResponse>
//
//    @Multipart
//    @POST("getworkouts")
//    fun getWorkouts(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("planid") planId: RequestBody,
//        @Part("month") month: RequestBody,
//    ) : Call<WorkoutResponse>
//
//    @Multipart
//    @POST("pastworkouts")
//    fun pastWorkouts(
//        @Part("token") token: RequestBody,
//        @Part("userid") userId: RequestBody,
//        @Part("type") planId: RequestBody,
//        @Part("month") month: RequestBody,
//    ) : Call<HistoryResponse>
//
//    @Multipart
//    @POST("saveworkout")
//    fun saveWorkouts(
//        @Part("token") token: RequestBody,
//        @Part("wid") userId: RequestBody,
//        @Part("aid") aId: RequestBody,
//        @Part("userid") planId: RequestBody,
//        @Part("date") date: RequestBody,
//        @Part("set1") set1: RequestBody,
//        @Part("set2") set2: RequestBody,
//        @Part("set3") set3: RequestBody,
//        @Part("set4") set4: RequestBody,
//        @Part("set5") set5: RequestBody,
//        @Part("comments") comments: RequestBody
//    ) : Call<SaveWorkoutResponse>
//
//    @Multipart
//    @POST("editworkout")
//    fun editWorkouts(
//        @Part("token") token: RequestBody,
//        @Part("setid") setId: RequestBody,
//        @Part("aid") aId: RequestBody,
//        @Part("set1") set1: RequestBody,
//        @Part("set2") set2: RequestBody,
//        @Part("set3") set3: RequestBody,
//        @Part("set4") set4: RequestBody,
//        @Part("set5") set5: RequestBody,
//        @Part("date") date: RequestBody,
//        @Part("comments") comments: RequestBody
//    ) : Call<SaveWorkoutResponse>
//
//    @Multipart
//    @POST("getwarmups")
//    fun getWarmUps(
//        @Part("token") token: RequestBody
//    ) : Call<WarmupResponse>

}