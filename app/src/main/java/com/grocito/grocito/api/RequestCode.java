package com.grocito.grocito.api;

/**
 * Created by suarebits on 3/12/15.
 */
public class RequestCode {
    public static final int CODE_Register = 1;
    public static final int CODE_Login = 2;
    public static final int CODE_OtpVerifiy = 3;
    public static final int CODE_ResendOtp = 4;
    public static final int CODE_ForgetMobile = 5;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 6;
    public static final int CODE_HomeData = 7;
    public static final int CODE_Category_List = 8;
    public static final int CODE_ProductListing = 9;
    public static final int CODE_ProductDetails = 10;
    public static final int CODE_GetSellerProductItem = 11;
    public static final int CODE_AddToCart=12;
    public static final int CODE_CartCount=13;
    public static final int CODE_GetCartData=14;
    public static final int CODE_UpdateCartData = 15;
    public static final int CODE_DeleteCart = 16;
    public static final int CODE_UserPages = 17;
    public static final int CODE_UserCheckout=18;
    public static final int CODE_DeleteUserAddress=19;
    public static final int CODE_UpdateUserAddress=20;
    public static final int CODE_AddUserAddress=21;
    public static final int CODE_GetDeliveryAmount=22;
    public static final int CODE_UserPlaceOrder=23;
    public static final int CODE_UserCodSuccess=24;
    public static final int CODE_UserOrderList=25;
    public static final int CODE_UserOrderDetails=26;
    public static final int CODE_UserUpdateProfile=27;
    public static final int CODE_MyWallet=28;
    public static final int CODE_GetRaisingComplaintList= 29;
    public static final int CODE_UserCallRequest=30;
    public static final int CODE_UserCatList =31;
    public static final int CODE_UserCheckPinAvailability =32;
    public static final int CODE_AddUserWalletAmount =33;
    public static final int CODE_ProductTypeListing =34;
    public static final int CODE_GetUserNotification=35;
    public static final int CODE_SearchAddressGoogle=36;
    public static final int CODE_GetUserAddress=37;
    public static final int CODE_UserOrderTracking=38;
    public static final int CODE_FilterData=39;
    public static final int CODE_FilterDataProductType=42;
    public static final int CODE_SubmitReviewRating=43;
    public static final int CODE_UserReturnOrder=44;
    public static final int CODE_UserOrderCancel=45;
    public static final int CODE_availableCityList=46;
    public static final int CODE_GetLabourAllJobs=47;
    public static final int CODE_CreateBid=48;
    public static final int CODE_EditBid=49;
    public static final int CODE_getAllBid=50;
    public static final int CODE_getSingleBid=51;
    public static final int CODE_acceptBid=52;
    public static final int CODE_rejectBid=53;
    public static final int CODE_getProfileById=54;


    public static final int CODE_GetAllGroups=100;
    public static final int CODE_GetAllrequest=101;
    public static final int CODE_MakeRequest = 102;
    public static final int CODE_GetAllSentrequest=103;
    public static final int CODE_AcceptRequest=104;
    public static final int CODE_RejectRequest=105;
    public static final int CODE_GetConfirmedRequests=106;
    public static final int CODE_RemoveFromGroupList = 107;
    public static final int CODE_AddMember = 108;
    public static final int CODE_GetGroupData=109;
    public static final int CODE_RemoveMember = 110;
    public static final int CODE_CancelRequest = 111;
    public static final int CODE_GetGroupList = 112;
    public static final int CODE_LeftGroupList = 113;
    public static final int CODE_GetMyLabourList = 114;
    public static final int CODE_addOwnMember = 115;
    public static final int CODE_removeOwnMember = 116;
    public static final int Code_archiveAcceptBids = 117;
    public static final int Code_archiveRejectBids = 118;




    //Constants
    public static String SP_CURRENT_LAT = "lat";
    public static String SP_CURRENT_LONG = "lng";
    public static String SP_NEW_LAT = "newLat";
    public static String SP_NEW_LONG = "newLng";
    public static String SP_DriverStatus = "driverStatus";
    public static final String UserID = "userID";
    public static final String userType = "user_type";
    public static final String KEY_ANIM_TYPE="anim_type";
    public static final String KEY_TITLE="anim_title";
    public static final String LangId = "langid";

    public static int AUTOPLACE_CODE=80;


    public enum TransitionType{
        ExplodeJava,ExplodeXML,SlideJava,SlideXML,FadeJava,FadeXML;
    }

}
