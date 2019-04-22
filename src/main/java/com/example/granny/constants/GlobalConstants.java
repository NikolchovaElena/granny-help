package com.example.granny.constants;

import com.example.granny.error.CauseNotFoundException;
import com.example.granny.error.MessageNotFoundException;
import com.example.granny.error.ProductAlreadyExistsException;
import com.example.granny.error.ProductNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;

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
    public static final String URL_USER_EDIT_PASSWORD ="/user/edit/password";
    public static final String URL_USER_EDIT_PROFILE = "/user/edit/profile";
    public static final String URL_USER_VIEW_PROFILE = "/user/profile/{id}";
    public static final String URL_USER_DELETE_PROFILE ="/user/delete/{id}";
    public static final String URL_VIEW_USERS = "/users";
    public static final String URL_USER_DELETE = "/user/delete";
    public static final String URL_SET_ROLE_USER = "/users/set-user/{id}";
    public static final String URL_SET_ROLE_ADMIN = "/users/set-admin/{id}";
    public static final String URL_ROLE_USER_MODERATOR = "/users/set-moderator/{id}";
    public static final String URL_ACCOUNT_VERIFIED = "/account-verified";
    public static final String URL_CONFIRM_ACCOUNT = "/confirm-account";
    public static final String URL_ABOUT = "/about";
    public static final String URL_USER_EDIT_ADDRESS ="/user/address/form";
    public static final String URL_SUBMIT_COMMENT ="/causes/{id}/comments";
    public static final String URL_DELETE_COMMENT ="/delete/comment/{id}";
    public static final String URL_FOLLOW_CAUSE ="/user/follow/causes/{id}";
    public static final String URL_UNFOLLOW_CAUSE ="/user/unfollow/causes/{id}";
    public static final String URL_ADD_TO_CART ="/cart/add";
    public static final String URL_UPDATE_CART ="/cart/update";
    public static final String URL_ADD_PRODUCT ="/products/form";
    public static final String URL_VIEW_PRODUCTS ="/products";
    public static final String URL_VIEW_PRODUCT_DETAILS ="/products/{id}";
    public static final String URL_EDIT_PRODUCT ="/products/form/{id}";
    public static final String URL_DELETE_PRODUCT ="/products/delete/{id}";
    public static final String URL_CONTACT_FORM ="/contact/form";
    public static final String URL_VIEW_MESSAGES ="/messages";
    public static final String URL_VIEW_MESSAGE_DETAILS ="/messages/{id}";
    public static final String URL_DELETE_MESSAGE ="/messages/delete/{id}";
    public static final String URL_SUBMIT_CAUSE="/causes/form";
    public static final String URL_EDIT_CAUSE="/causes/form/{id}";
    public static final String URL_VIEW_CAUSE_DETAILS="/causes/{id}";
    public static final String URL_VIEW_CAUSES="/causes";
    public static final String URL_APPROVE_CAUSE="/causes/approve/{id}";
    public static final String URL_DELETE_CAUSE="/causes/delete/{id}";
    public static final String URL_VIEW_CART="/cart";
    public static final String URL_DELETE_ITEM_FROM_CART="/cart/delete/{id}";
    public static final String URL_CART_CHECKOUT="/cart/checkout";

    public static final String ROLE_ROOT = "ROLE_ROOT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String IS_ANONYMOUS = "isAnonymous()";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";

    public static final String MODEL = "model";
    public static final String CART = "cart";
    public static final String CART_SIZE = "cart-size";
    public static final String PROFILE = "profile";
    public static final String ADDRESS = "address";
    public static final String UNREAD_MESSAGES_SIZE = "messages-size";

    /**
     * Exceptions
     */
    public static final String FIELD_IS_REQUIRED = "Field is required";
    public static final String PASSWORDS_DONT_MATCH = "Passwords don't match";
    public static final String EMAIL_ALREADY_EXISTS = "A user with that email already exists";
    public static final String ACCESS_DENIED = "You are not authorized to access this page";

    public static final UsernameNotFoundException EMAIL_NOT_FOUND_EXCEPTION =
            new UsernameNotFoundException("Email not found");
    public static final IllegalArgumentException NO_USER_WITH_THAT_EXCEPTION =
            new IllegalArgumentException("User could not be found");
    public static final IllegalArgumentException INCORRECT_PASSWORD =
            new IllegalArgumentException("Incorrect password");
    public static final CauseNotFoundException CAUSE_NOT_FOUND =
            new CauseNotFoundException("The cause you requested could not be found");
    public static final IllegalArgumentException NO_CAUSE_WITH_THAT_EXCEPTION =
            new IllegalArgumentException("Cause could not be found");
    public static final String COULD_NOT_DELETE_CAUSE = "You are not authorized to delete this cause";
    public static final MessageNotFoundException MESSAGE_NOT_FOUND =
            new MessageNotFoundException("The message you requested could not be found");
    public static final ProductNotFoundException PRODUCT_NOT_FOUND =
            new ProductNotFoundException("The cause you requested could not be found");
    public static final ProductAlreadyExistsException PRODUCT_ALREADY_EXISTS =
            new ProductAlreadyExistsException("A product with the same name already exists");


}
