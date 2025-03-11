package com.cgc.firststep.network

import com.cgc.firststep.model.FileUploadResponse
import com.cgc.firststep.model.MatchResponse
import com.cgc.firststep.model.PostApiModel
import okhttp3.MultipartBody
import retrofit2.Call

class WebAPIManager private constructor() {

    private val mApiService: WebAPIService = WebAPIServiceFactory.newInstance()
        .makeServiceFactory()

    companion object {

        private var INSTANCE: WebAPIManager? = null

        val instance: WebAPIManager
            get() {
                if (INSTANCE == null) {
                    INSTANCE =
                        WebAPIManager()
                }
                return INSTANCE as WebAPIManager
            }
    }


    fun getSchedule(): Call<MatchResponse> {
        return mApiService.getSchedule()
    }

    fun postApiExample(request: PostApiModel): Call<PostApiModel> {
        return mApiService.postApiExample(request)
    }

    fun fileUpload(
        image: MultipartBody.Part
    ): Call<FileUploadResponse> {
        return mApiService.fileUpload(
            image,
        )
    }


//    fun aboutUs(token: String): Call<ContentResponse> {
//        return mApiService.aboutUs(token)
//    }
//
//    fun privacyPolicy(token: String): Call<ContentResponse> {
//        return mApiService.privacyPolicy(token)
//    }
//
//    fun termsCondition(token: String): Call<ContentResponse> {
//        return mApiService.termsCondition(token)
//    }
//
//    fun faq(token: String): Call<FAQResponse> {
//        return mApiService.faq(token)
//    }
//
//    fun getBankDetails(token: String): Call<BankResponse> {
//        return mApiService.getBankDetails(token)
//    }
//
//    fun updateBankDetails(token: String, request :RegisterRequest): Call<LoginResponse> {
//        return mApiService.updateBankDetails(token, request)
//    }
//
//    fun contactUs(token: String, request :RegisterRequest): Call<LoginResponse> {
//        return mApiService.contactUs(token, request)
//    }
//

//
//    fun changePassword(token: String, request :RegisterRequest): Call<LoginResponse> {
//        return mApiService.changePassword(token, request)
//    }
//
//    fun newBooking(token: String): Call<BookingResponse> {
//        return mApiService.newBooking(token)
//    }
//
//    fun inProgressBooking(token: String): Call<BookingResponse> {
//        return mApiService.inProgressBooking(token)
//    }
//
//    fun pastBooking(token: String): Call<BookingResponse> {
//        return mApiService.pastBooking(token)
//    }
//
//    fun myEmployee(token: String): Call<EmployeeResponse> {
//        return mApiService.myEmployee(token)
//    }
//
//    fun pendingEmployee(token: String): Call<EmployeeResponse> {
//        return mApiService.pendingEmployee(token)
//    }
//
//    fun accountApproveReject(token: String, request :RegisterRequest): Call<LoginResponse> {
//        return mApiService.accountApproveReject(token, request)
//    }
//
//    fun bookingDetail(token: String, bookingId: String): Call<BookingDetailResponse> {
//        return mApiService.bookingDetail(token, bookingId)
//    }
//
//    fun bookingAcceptReject(token: String, request :BookingRequest): Call<LoginResponse> {
//        return mApiService.bookingAcceptReject(token, request)
//    }
//
//    fun startJob(
//        token: String,
//        bookingId: RequestBody,
//        otp: RequestBody,
//        image: MultipartBody.Part
//    ): Call<LoginResponse> {
//        return mApiService.startJob(
//            token,
//            bookingId,
//            otp,
//            image,
//        )
//    }
//
//    fun finishJob(token: String, request :BookingRequest): Call<LoginResponse> {
//        return mApiService.finishJob(token, request)
//    }
//
//    fun earningData(token: String, request :BookingRequest): Call<EarningResponse> {
//        return mApiService.earningData(token, request)
//    }
//
//    fun getNotification(token: String): Call<NotificationResponse> {
//        return mApiService.getNotification(token)
//    }
//
//    fun getWallet(token: String): Call<WalletResponse> {
//        return mApiService.getWallet(token)
//    }
//
//    fun withdrawApi(token: String, request :BookingRequest): Call<LoginResponse> {
//        return mApiService.withdrawApi(token, request)
//    }


//
//    fun importantContact(token: String, limit: RequestBody, pages: RequestBody): Call<ContactResponse> {
//        return mApiService.importantContact(
//            token,
//            limit,
//            pages
//        )
//    }
//
//    fun getConsultation(token: String): Call<ConsultResponse> {
//        return mApiService.getConsultation(
//            token
//        )
//    }
//
//    fun getHandbook(limit: RequestBody, langId: RequestBody, page: RequestBody, token: String): Call<HandbookResponse> {
//        return mApiService.getHandbook(
//            token,
//            langId,
//            limit,
//            page,
//        )
//    }
//
//    fun getVideo(limit: RequestBody, trainingId: RequestBody, page: RequestBody, token: String): Call<VideoResponse> {
//        return mApiService.getVideo(
//            token,
//            trainingId,
//            limit,
//            page,
//        )
//    }
//
//    fun getPublication(limit: RequestBody, page: RequestBody, token: String): Call<PublicationResponse> {
//        return mApiService.getPublication(
//            token,
//            limit,
//            page,
//        )
//    }
//
//    fun getTraining(limit: RequestBody, page: RequestBody, token: String): Call<TrainingResponse> {
//        return mApiService.getTraining(
//            token,
//            limit,
//            page,
//        )
//    }
//
//    fun getContactUs(name: RequestBody, email: RequestBody, subject: RequestBody, message: RequestBody, token: String): Call<CommonResponse> {
//        return mApiService.getContactUs(
//            token,
//            name,
//            email,
//            subject,
//            message
//        )
//    }
//
//    fun getCypc(token: String): Call<CypcResponse> {
//        return mApiService.getCypc(
//            token
//        )
//    }
//
//    fun getLanguage(token: String): Call<LanguageResponse> {
//        return mApiService.getLanguage(
//            token
//        )
//    }
//
//    fun getCaptcha(): Call<CaptchaResponse> {
//        return mApiService.getCaptcha()
//    }
//
//    fun getDetails(token: String): Call<DetailsResponse> {
//        return mApiService.getDetails(
//            token
//        )
//    }
//
//    fun getSubject(token: String): Call<SubjectResponse> {
//        return mApiService.getSubject(
//            token
//        )
//    }
//
//    fun getResource(limit: RequestBody, page: RequestBody,token: String): Call<ResourceResponse> {
//        return mApiService.getResource(
//            token,
//            limit,
//            page,
//        )
//    }
//
//    fun saveAnswer(token: String, request: AnsRequest): Call<CommonResponse> {
//        return mApiService.saveAnswer(
//            token,
//            request
//        )
//    }


//
//    fun loginApi(
//        token: RequestBody,
//        email: RequestBody,
//        password: RequestBody,
//        deviceType: RequestBody,
//        deviceToken: RequestBody
//    ): Call<LoginResponse> {
//        return mApiService.loginApi(token, email, password, deviceType, deviceToken)
//    }
//
//    fun getAllPlans(token: RequestBody): Call<PlanResponse> {
//        return mApiService.getAllPlans(token)
//    }
//
//    fun choosePlanApi(
//        token: RequestBody,
//        userId: RequestBody,
//        versionId: RequestBody,
//        membershipId: RequestBody
//    ): Call<RegisterResponse> {
//        return mApiService.choosePlanApi(token, userId, versionId, membershipId)
//    }
//
//    fun updatePlanApi(
//        token: RequestBody,
//        userId: RequestBody,
//        versionId: RequestBody
//    ): Call<RegisterResponse> {
//        return mApiService.updatePlanApi(token, userId, versionId)
//    }
//
//    fun forgotPassword(
//        token: RequestBody,
//        email: RequestBody
//    ): Call<ForPassResponse> {
//        return mApiService.forgotPassword(token, email)
//    }
//
//
//    fun otpVerify(
//        token: RequestBody,
//        userid: RequestBody,
//        otp: RequestBody
//    ): Call<ForPassResponse> {
//        return mApiService.otpVerify(token, userid, otp)
//    }
//
//    fun resetPassword(
//        token: RequestBody,
//        userid: RequestBody,
//        newPassword: RequestBody
//    ): Call<ForPassResponse> {
//        return mApiService.resetPassword(token, userid, newPassword)
//    }
//
//    fun changePassword(
//        token: RequestBody,
//        userid: RequestBody,
//        oldPassword: RequestBody,
//        newPassword: RequestBody
//    ): Call<ForPassResponse> {
//        return mApiService.changePassword(token, userid, oldPassword, newPassword)
//    }
//
//    fun fullProfile(
//        token: RequestBody,
//        userid: RequestBody
//    ): Call<ProfileResponse> {
//        return mApiService.fullProfile(token, userid)
//    }
//
//    fun editProfile(
//        token: RequestBody,
//        userid: RequestBody,
//        firstName: RequestBody,
//        lastName: RequestBody,
//        gender: RequestBody,
//        dob: RequestBody,
//        newsletter: RequestBody
//    ): Call<ProfileResponse> {
//        return mApiService.editProfile(token, userid, firstName, lastName, gender, dob, newsletter)
//    }
//
//    fun editProfilePic(
//        token: RequestBody,
//        userid: RequestBody,
//        file: MultipartBody.Part
//    ): Call<ProfileResponse> {
//        return mApiService.editProfilePic(token, userid, file)
//    }
//
//    fun addPostOne(
//        token: RequestBody,
//        userid: RequestBody,
//        title: RequestBody,
//        files: MutableList<MultipartBody.Part>,
//        type: RequestBody,
//        privacy: RequestBody,
//        status: RequestBody
//    ): Call<ProfileResponse> {
//        return mApiService.addPostOne(token, userid, title, files, type, privacy, status)
//    }
//
//    fun addPost(
//        token: RequestBody,
//        userid: RequestBody,
//        title: RequestBody,
//        files: MutableList<MultipartBody.Part>,
//        type: RequestBody,
//        privacy: RequestBody,
//        status: RequestBody,
//        thumbnail: MultipartBody.Part
//    ): Call<ProfileResponse> {
//        return mApiService.addPost(token, userid, title, files, type, privacy, status, thumbnail)
//    }
//
//    fun saveMessage(
//        token: RequestBody,
//        userid: RequestBody,
//        exerciseName: RequestBody,
//        files: MutableList<MultipartBody.Part>,
//        totalLoad: RequestBody,
//        reps: RequestBody,
//        notes: RequestBody,
//        status: RequestBody,
//        thumbnail: MultipartBody.Part
//    ): Call<ProfileResponse> {
//        return mApiService.saveMessage(
//            token,
//            userid,
//            exerciseName,
//            files,
//            totalLoad,
//            reps,
//            notes,
//            status,
//            thumbnail
//        )
//    }
//
//    fun myMessages(
//        token: RequestBody,
//        userid: RequestBody,
//        page: RequestBody
//    ): Call<MessageResponse> {
//        return mApiService.myMessages(token, userid, page)
//    }
//
//    fun messageDetail(
//        token: RequestBody,
//        id: RequestBody
//    ): Call<MessageDetailResponse> {
//        return mApiService.messageDetail(token, id)
//    }
//
//    fun unreadMessage(
//        token: RequestBody,
//        id: RequestBody
//    ): Call<MessageDetailResponse> {
//        return mApiService.unreadMessage(token, id)
//    }
//
//    fun messageReply(
//        token: RequestBody,
//        userid: RequestBody,
//        files: MutableList<MultipartBody.Part>,
//        notes: RequestBody,
//        messageId: RequestBody,
//        thumbnail: MultipartBody.Part
//    ): Call<MessageResponse> {
//        return mApiService.messageReply(token, userid, files, notes, messageId, thumbnail)
//    }
//
//    fun textMessageReply(
//        token: RequestBody,
//        userid: RequestBody,
//        notes: RequestBody,
//        messageId: RequestBody
//    ): Call<MessageResponse> {
//        return mApiService.textMessageReply(token, userid, notes, messageId)
//    }
//
//    fun allPost(
//        token: RequestBody,
//        userid: RequestBody,
//        page: RequestBody
//    ): Call<PostResponse> {
//        return mApiService.allPost(token, userid, page)
//    }
//
//    fun myPost(
//        token: RequestBody,
//        userid: RequestBody,
//        page: RequestBody
//    ): Call<PostResponse> {
//        return mApiService.myPost(token, userid, page)
//    }
//
//    fun getPrivacyData(
//        token: RequestBody,
//        type: RequestBody
//    ): Call<PrivacyResponse> {
//        return mApiService.getPrivacyData(token, type)
//    }
//
//    fun contactUs(
//        token: RequestBody,
//        userid: RequestBody,
//        name: RequestBody,
//        message: RequestBody,
//        email: RequestBody
//    ): Call<PrivacyResponse> {
//        return mApiService.contactUs(token, userid, name, message, email)
//    }
//
//    fun postLike(
//        token: RequestBody,
//        userid: RequestBody,
//        postid: RequestBody
//    ): Call<RegisterResponse> {
//        return mApiService.postLike(token, userid, postid)
//    }
//
//    fun postDelete(
//        token: RequestBody,
//        userid: RequestBody
//    ): Call<RegisterResponse> {
//        return mApiService.postDelete(token, userid)
//    }
//
//    fun commentOnPost(
//        token: RequestBody,
//        userid: RequestBody,
//        postid: RequestBody,
//        content: RequestBody
//    ): Call<RegisterResponse> {
//        return mApiService.commentOnPost(token, userid, postid, content)
//    }
//
//    fun allComments(
//        token: RequestBody,
//        postid: RequestBody
//    ): Call<CommentResponse> {
//        return mApiService.allComments(token, postid)
//    }
//
//    fun userLogout(
//        token: RequestBody,
//        userId: RequestBody
//    ): Call<LoginResponse> {
//        return mApiService.userLogout(token, userId)
//    }
//
//    fun faq(
//        token: RequestBody
//    ): Call<FAQResponse> {
//        return mApiService.faq(token)
//    }
//
//    fun getNotifications(
//        token: RequestBody,
//        userId: RequestBody
//    ): Call<NotResponse> {
//        return mApiService.getNotifications(token, userId)
//    }
//
//    fun removeNotifications(
//        token: RequestBody,
//        notId: RequestBody
//    ): Call<NotResponse> {
//        return mApiService.deleteNotifications(token, notId)
//    }
//
//    fun postDetails(
//        token: RequestBody,
//        postId: RequestBody,
//        userId: RequestBody
//    ): Call<SinglePostResponse> {
//        return mApiService.postDetails(token, postId, userId)
//    }
//
//    fun cardioGuidelines(
//        token: RequestBody,
//        month: RequestBody,
//    ): Call<CardioResponse> {
//        return mApiService.cardioGuidelines(token, month)
//    }
//
//    fun getWorkout(
//        token: RequestBody,
//        userId: RequestBody,
//        planId: RequestBody,
//        month: RequestBody
//    ): Call<WorkoutResponse> {
//        return mApiService.getWorkouts(token, userId, planId, month)
//    }
//
//    fun pastWorkout(
//        token: RequestBody,
//        userId: RequestBody,
//        planId: RequestBody,
//        month: RequestBody
//    ): Call<HistoryResponse> {
//        return mApiService.pastWorkouts(token, userId, planId, month)
//    }
//
//
//    fun saveWorkout(
//        token: RequestBody,
//        wId: RequestBody,
//        aId: RequestBody,
//        userId: RequestBody,
//        date: RequestBody,
//        set1: RequestBody,
//        set2: RequestBody,
//        set3: RequestBody,
//        set4: RequestBody,
//        set5: RequestBody,
//        comment: RequestBody,
//    ): Call<SaveWorkoutResponse> {
//        return mApiService.saveWorkouts(
//            token,
//            wId,
//            aId,
//            userId,
//            date,
//            set1,
//            set2,
//            set3,
//            set4,
//            set5,
//            comment
//        )
//    }
//
//    fun editWorkout(
//        token: RequestBody,
//        setId: RequestBody,
//        aId: RequestBody,
//        set1: RequestBody,
//        set2: RequestBody,
//        set3: RequestBody,
//        set4: RequestBody,
//        set5: RequestBody,
//        date: RequestBody,
//        comment: RequestBody,
//    ): Call<SaveWorkoutResponse> {
//        return mApiService.editWorkouts(token, setId, aId, set1, set2, set3, set4,set5, date, comment)
//    }
//
//    fun getWarmUps(
//        token: RequestBody
//    ): Call<WarmupResponse> {
//        return mApiService.getWarmUps(token)
//    }


//    fun loginUser(request: SignInRequest): Call<SignInResponse> {
//        return mApiService.loginUser(request)
//    }
//
//
//    fun editProfile(token: String, name: RequestBody, email: RequestBody, mobile:RequestBody, file: MultipartBody.Part) :Call<EditProfileResponse>{
//         return mApiService.editProfile(token, name, email,mobile, file)
//    }
//
//    fun editProfile(token: String, name: RequestBody, email: RequestBody,mobile:RequestBody) :Call<EditProfileResponse>{
//        return mApiService.editProfile(token, name, email,mobile)
//    }

}
