package com.github.demo.constant;

import java.util.List;

public record IpAddressConstant() {
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final List<String> IP_NAME_LIST = List.of("x-real-ip", "x-original-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR");
    public static final String LOCAL_ADDRESS = "127.0.0.1";
    public static final String UNKNOWN = "unknown";
}
