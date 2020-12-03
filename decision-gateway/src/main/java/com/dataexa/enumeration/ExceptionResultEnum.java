package com.dataexa.enumeration;

/**
 * @Author 胡志成
 * @Date 2020/8/25
 */
public enum ExceptionResultEnum {
    BUSINESS_EXCEPTION(-1000, "业务异常"),
    BAD_REQUEST(400, "请求参数错误,请检查"),
    UNAUTHORIZED(401, "权限不足无法访问,请检查"),
    FORBIDDEN(403, "服务器拒绝访问,请检查"),
    NOT_FOUND(404, "无法找到文件,请检查"),
    METHOD_NOT_ALLOWED(405, "请求类型错误,请检查"),
    INTERNAL_SERVER_ERROR(500, "服务内部错误,请检查"),
    NOT_IMPLEMENTED(501, "服务无法识别请求,请检查"),
    BAD_GATEWAY(502, "网关错误,请检查"),
    SERVICE_UNAVAILABLE(503, "服务器目前无法使用,请检查"),
    GATEWAY_TIMEOUT(504, "网关超时,请检查");

    private final Integer code;
    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ExceptionResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getExceptionMsg(Integer code) {
        for (ExceptionResultEnum exceptionResultEnum : values()) {
            if (exceptionResultEnum.getCode().equals(code)) {
                return exceptionResultEnum.getMsg();
            }
        }
        return "";
    }
}
