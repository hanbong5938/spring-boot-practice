package com.github.demo.constant;

public record CustomLogConstant() {
    public static final String PRE_FIX = "[";
    public static final String SUB_FIX = "]";
    public static final String HTTP = PRE_FIX + "HTTP" + SUB_FIX;
    public static final String URI = PRE_FIX + "URI" + SUB_FIX;
    public static final String API = PRE_FIX + "API" + SUB_FIX;
    public static final String METHOD = PRE_FIX + "METHOD" + SUB_FIX;
    public static final String IP = PRE_FIX + "IP" + SUB_FIX;
    public static final String USER = PRE_FIX + "USER" + SUB_FIX;
    public static final String PARAMS = PRE_FIX + "PARAMS" + SUB_FIX;
    public static final String BODY = PRE_FIX + "BODY" + SUB_FIX;
    public static final String FILE = PRE_FIX + "FILE" + SUB_FIX;
    public static final String MULTIPART = "multipart/form-data; boundary=----WebKitFormBoundary";
    public static final String NO_DATA = "";
    public static final String LOG_PARAM = " : {}, ";
    public static final String SIGN_IN = "SIGN-IN";
    public static final String SIGN_IN_FORMAT = PRE_FIX + SIGN_IN + SUB_FIX +
            " USER-ID " + LOG_PARAM + " IP" + LOG_PARAM + "USER-AGENT" + LOG_PARAM + " ACCOUNT-TYPE " + LOG_PARAM;
    public static final CharSequence ACCOUNT_TYPE = PRE_FIX + "ACCOUNT_TYPE" + SUB_FIX;
}
