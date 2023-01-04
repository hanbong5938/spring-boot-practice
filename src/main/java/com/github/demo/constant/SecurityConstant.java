package com.github.demo.constant;

public record SecurityConstant() {
    public static final int SIGN_IN_FAIL_LIMIT = 5;
    public static final int EXPIRED_PASSWORD = 90;
    public static final String DEFAULT_TOKEN = "Bearer ";
    public static final String DEFAULT_SECRET = "#nef23bhySAD#783buy2)E#@J#R5httpFNc3vnJF%WEho;lkj;kl3^2@^#23@^#746uy^54%7t5";
    public static final String JWT_CLAIMS = "user_type";
    public static final String UUID = "uuid";
    public static final int ACCESS_JWT_EXPIRED = 14 * 60 * 1000; // 14 minutes
    public static final int REFRESH_JWT_EXPIRED = 14 * 24 * 60 * 60 * 1000; // 2 Weeks
    public static final String EXCEPTION = "exception";
}
