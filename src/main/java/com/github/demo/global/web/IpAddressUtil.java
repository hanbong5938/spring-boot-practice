package com.github.demo.global.web;

import com.github.demo.constant.IpAddressConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpAddressUtil {

    /**
     * ip 주소 가져오는 메서드
     *
     * @param request
     * @return ip 주소
     */
    public static String getClientIp(final HttpServletRequest request) {
        final String[] ip = {request.getHeader(IpAddressConstant.X_FORWARDED_FOR)};

        // 리스트 확인
        IpAddressConstant.IP_NAME_LIST.stream().filter(name -> isEmpty(ip)).forEach(name
                -> ip[0] = request.getHeader(name));

        // 확인 후 에도 비어 있는 경우
        if (isEmpty(ip)) {
            ip[0] = request.getRemoteAddr();
        }

        return isEmpty(ip) ? IpAddressConstant.LOCAL_ADDRESS : splitIp(ip[0]);
    }

    /**
     * 비어있는지 확인
     *
     * @param ip array 람다식 위해서 배열
     * @return boolean
     */
    private static boolean isEmpty(final String[] ip) {
        return Objects.isNull(ip[0]) || ip[0].isBlank() || ip[0].equalsIgnoreCase(IpAddressConstant.UNKNOWN)
                || Objects.equals(ip[0], "0:0:0:0:0:0:0:1");
    }


    /**
     * 아이피 주소 기준에 맞게 변경
     *
     * @param ip 추출한 ip
     */
    private static String splitIp(final String ip) {
        final String IPADDRESS_PATTERN =
                "(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)";

        final Matcher matcher = Pattern.compile(IPADDRESS_PATTERN).matcher(ip);
        return matcher.find() ? matcher.group() : ip;
    }

}
