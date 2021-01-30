package com.verandah.club.data.rest;

public class ApiEndPoints {
    static final String PLACE_API = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
    static final String NEAR_BY_SEARCH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    static final String LOGIN = "LoginCheck";
    static final String FORGOT_PASSWORD = "ForgetPassword";
    static final String REGISTER = "CustomerRegister";
    static final String SOCIAL_LOGIN = "SocialMediaLogin";
    static final String MOBILE_LANGUAGE = "MobileLanguage";
    static final String SKIP_SCREEN_DATA = "SkipscreenData";
    static final String RESEND_FORGET_PASSWORD = "ResendForgetPassword";
    static final String SMS_VERIFICATION = "Sms_verification";

    static final String TERMS = "TermsofServices";
    static final String PRIVACY = "PrivacyPolicy";
    static final String COUNTRY_STATE = "Country_state_city_list";
    static final String CATEGORY_LIST = "Maincategory_List";
    static final String PROVIDER_REGISTER = "ProviderRegister";
    static final String DASHBOARD = "HomepageData";
    static final String UPDATE_HALAL_OPTION = "switch_to_halal";
    static final String EXPLORE_SEARCH = "Explore_Search";
    static final String CATEGORY_SEARCH_LISTING = "Category_Search_Listing";
    static final String SUBCATEGORY_SEARCH_LISTING = "SubCategory_Search_Listing";
    static final String EXPLORE_SEARCH_LISTING = "Explore_Search_Listing";
    static final String REPORT_LISTING = "report_listing";

    public static final String PROPERTY_DETAIL = "Listing_DetailPage";
    static final String CANCELLATION_POLICY = "Cancellation_policy";
    static final String POPULAR_CITIES_ALL = "PopularCitiesAll";
    static final String CONTACT_HOST = "Contact_host_message";
    static final String TOGGLE_FAV = "Wishlist_AddRemove";
    static final String CHECK_OUT = "Checkout";

    static final String POPULAR_CITIES_LISTING = "Popular_Cities_Listing";

    /*booking*/
    static final String BOOKING_DETAILS = "Request_book_details";

    static final String MY_WISHLIST = "My_Wishlist";
    static final String WISHLIST_ADDREMOVE = "Wishlist_AddRemove";

    public static final String BOOKING_ENQUIRY_VACATION = "Booking_enquiry_vacation";
    public static final String BOOKING_CONFIRMATION_VACATION = "Booking_confirmation_vacation";
    public static final String ADD_TO_CART_VACATION = "Add_to_cart_vacation";

    public static final String BOOKING_ENQUIRY_TOUR = "Booking_enquiry_tour";
    public static final String BOOKING_CONFIRMATION_TOUR = "Booking_confirmation_tour";
    public static final String ADD_TO_CART_TOUR = "Add_to_cart_tour";

    public static final String BOOKING_ENQUIRY_RELAXATION = "Booking_enquiry_relaxation";
    public static final String BOOKING_CONFIRMATION_RELAXATION = "Booking_confirmation_relaxation";
    public static final String ADD_TO_CART_RELAXATION = "Add_to_cart_relaxation";

    public static final String BOOKING_ENQUIRY_RESTAURANT = "Booking_enquiry_restaurant";
    public static final String BOOKING_CONFIRMATION_RESTAURANT = "Booking_confirmation_restaurant";
    public static final String ADD_TO_CART_RESTAURANT = "Add_to_cart_restaurant";

    /*MyCart*/
    static final String MY_CART = "My_cart";
    static final String MY_CART_DELETE = "My_cart_delete";

    /*MyTrip*/
    static final String MY_TRIP = "My_trips";
    static final String BOOKING_DISCUSSION = "Booking_discussion";
    static final String GUEST_CANCEL_BOOKING = "Guest_Cancel_booking";
    static final String GUEST_BEFORE_CANCEL_BOOKING = "Guest_before_cancel_booking";


    static final String PAYMENT_CREDIT_CARD = "User_pay_by_creditcard";
    static final String PAYMENT_STRIPE = "User_pay_by_stripe";
    static final String PAYMENT_PAYPAL = "User_pay_by_paypal";
    static final String ZERO_PAYMENT_PROCESS = "Zero_payment_process";
    static final String CANCEL_PERIOD_LIMIT = "Update_cancel_period_limit";

    /*Profile*/
    static final String ACCOUNT_DETAILS = "User_account_details";
    static final String PUBLIC_PROFILE = "Public_profile";
    static final String UPDATE_ACCOUNT_DETAILS = "Update_account_details";
    static final String CHANGE_PROFILE_PHOTO = "Change_profile_photo";
    static final String RESET_PASSWORD = "Reset_password";


    static final String CLEANING_REQUEST = "Cleaning_request";

    static final String REPORT_ISSUE = "Report_issue";
    static final String LATE_CHECKOUT_REQUEST = "Late_checkout_request";

    /*Invoice*/
    public static final String INVOICE = "Invoice";
    public static final String REVIEW_BOOKING = "Review_booking";

    /*Inbox*/
    static final String INBOX = "Inbox";
    static final String INBOX_DELETE_CONVERSATION = "Inbox_Delete_Conversation";
    static final String INBOX_CONVERSATION = "Inbox_Conversation";
    static final String INBOX_SEND_MESSAGE = "Inbox_Send_Message";
    static final String INBOX_HOST_APPROVAL = "Inbox_Host_Approval";
    static final String INBOX_HOST_DECLINE = "Inbox_Host_Decline";

    /*Blogs*/
    static final String BLOGS = "Blog_Category_List";
    static final String BLOG = "Blog_Detail_view";
    static final String BLOG_ADD_COMMENT = "Blog_Detail_view";
    static final String MY_BLOGS = "User_blogs_list";
    static final String BLOG_CATEGORY = "Blog_category";
    static final String ADD_EDIT_BLOG = "Add_edit_blog";


    /*Review*/
    static final String REVIEW_LISTING = "Review_listing";

    /*Payment method*/
    static final String USER_PAYMENT_DETAILS = "User_payment_details";

    /*Post Review*/
    static final String REPLY_REVIEW_COMMENTS = "reply_review_comments";

    static final String POST_REVIEW = "Post_review";

    /*Become Host*/
    static final String BECOME_HOST = "Become_Host";

    /*Listing cat*/
    static final String LISTING_CAT = "Host_listing_services";
    /*Bonus*/
    static final String MY_BONUS_POINTS = "My_bonus_points";


    /*Listing*/
    public static final String LISTING_STAY = "Tour_Add_Listing";
    public static final String LISTING_EXPERIENCES = "Vacation_Add_Listing";
    public static final String LISTING_RELAXATION = "Relaxation_Add_Listing";
    public static final String LISTING_RESTAURANT = "Restaurant_Add_Listing";
    public static final String LISTING_CAR = "Charter_Add_Listing";
    static final String LISTING_PHOTO = "Add_Photo_Listing";

    /*Finance*/
    static final String FINANCE_REPORT_LIST = "Finance_report_list";

    /*Halal Certification*/
    static final String HALAL_INTRODUCTION_PAGE = "Halal_introduction_page";

    static final String HALAL_TEST_QUESTION = "Halal_test_question";
    static final String HALAL_CERTIFICATE_RESULT = "Halal_certificate_result";
    static final String HALAL_CERTIFICATE_SUBMIT = "Halal_certificate_submit";
    static final String BONUS_REQUEST_TO_ADMIN = "BonusrequestToAdmin";

    /*Reservation*/
    static final String MY_RESERVATION = "My_reservation";
    static final String HOST_CANCEL_BOOKING = "Host_Cancel_booking";


    /*Announcement*/
    static final String ANNOUNCEMENT = "Announcement";


    /*Booking Details*/
    static final String SCAN_QR_CODE = "scan_qr_code";
    static final String MY_RESERVATION_DETAILS = "My_reservation_details";
    static final String VACATION_CHECK_IN_OUT = "Vacation_Check_In_Out";


    /*Currency List*/
    static final String CURRENCY_LIST = "Currency_values";

    /*MyListing*/
    static final String MY_LISTING = "My_listing";

    public static final String MY_LISTING_PREVIEW = "My_listing_preview";

    /*Category verification*/
    static final String CATEGORY_VERIFICATION = "Category_verification";

    /*Add Extra Services*/
    static final String ADD_EXTRA_SERVICES = "Add_ertra_services ";

    /*my calendar*/
    static final String MY_CALENDAR = "My_calendar";

    /* submit Proof*/
    static final String SUBMIT_VERIFICATION_PHOTO = "submit_verification_photo_andriod";
    static final String MY_TRIPS_DETAILS = "My_trips_details";

    static final String PROPERTY_REVIEWS = "property_reviews/{id}";

    static final String EXPERIENCE_SCHEDULE_IMAGE = "Experience_Schedule_Image";
    static final String RESTAURANT_MENU_IMAGE = "Restaurant_Menu_Image";
}
