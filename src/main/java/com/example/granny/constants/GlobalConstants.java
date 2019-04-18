package com.example.granny.constants;

public final class GlobalConstants {
    private GlobalConstants() {
    }

    public static final String PROFILE_DEFAULT_IMG = "https://res.cloudinary.com/logger/image/upload/v1554151912/blank-profile-picture-973460_960_720.png";
    public static final String ABOUT_DEFAULT_TEXT = "I want to make the world a better place";
    public static final String DEFAULT_IMG = "https://res.cloudinary.com/logger/image/upload/v1554803553/nophotoavailable.png";

    /**
     * Web constants
     */
    public static final String URL_INDEX = "/";
    public static final String URL_USER_HOME = "/home";
    public static final String URL_USER_REGISTER = "/user/register";
    public static final String URL_USER_LOGIN = "/user/login";
    public static final String URL_USER_LOGOUT = "/user/logout";
    public static final String URL_USER_PROFILE = "user/profile";
    public static final String URL_USER_PROFILE_EDIT = "user/profile/edit";
    public static final String URL_USER_ALL = "/user/all";
    public static final String URL_ACCOUNT_VERIFIED = "/account-verified";
    public static final String URL_CONFIRM_ACCOUNT = "/confirm-account";
    public static final String URL_ABOUT = "/about";
    public static final String URL_ERROR_UNAUTHORIZED = "/unauthorized";

    public static final String ROLE_ROOT = "ROLE_ROOT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String IS_ANONYMOUS = "isAnonymous()";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";

    public static final String MODEL = "model";
    public static final String CART = "cart";
    public static final String PROFILE = "profile";

    /**
     * Exceptions
     */
    public static final String FIELD_IS_REQUIRED = "Field is required";


}
