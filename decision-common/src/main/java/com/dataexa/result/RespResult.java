package com.dataexa.result;

/**
 *
 * @author hsuyung
 * @date 2017/12/21
 */
public class RespResult {
    public static String success = "操作成功!";
    public static String fail = "操作失败!";

    public static Result success() {
        return getResult_1(success, 0, "");
    }

    public static Result success(String msg) {
        return getResult_1(msg, 0, "");
    }

    public static <T> Result<T> success(T data) {
        return getResult_2(success, 0, data, "");
    }

    public static <T> Result<T> finish (T data) {
        return getResult_2(success, 4, data, "");
    }

    public static <T> Result<T> running (T data) {
        return getResult_2(success, 2, data, "");
    }


    public static <T> Result<T> success(T data, String msg) {
        return getResult_2(msg, 0, data, "");
    }


    public static Result fail() {
        return getResult_1(fail, 1, "");
    }

    /**
     * 使用failWithErrMsg(String errorMsg)替代
     */
    @Deprecated
    public static Result fail(String msg) {
        return getResult_1(msg, 1, "");
    }

    public static <T> Result<T> fail(T data) {
        return getResult_2(fail, 1, data, "");
    }

    public static <T> Result<T> fail(T data, String msg) {
        return getResult_2(msg, 1, data, "");
    }


    public static <T> Result<T> failWithErrMsg(String errorMsg) {
        return getResult_1(fail, 1, errorMsg);
    }

    public static Result failWithErrMsg(String msg, String errorMsg) {
        return getResult_1(msg, 1, errorMsg);
    }

    public static <T> Result<T> failWithErrMsg(T data, String msg, String errorMsg) {
        return getResult_2(msg, 1, data, errorMsg);
    }

    public static <T> Result<T> failWithErrMsg(String msg, Integer code, String errorMsg){
        return getResult_1(msg,code,errorMsg);
    }


    private static Result getResult_1(String msg, Integer code, String errorMsg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setCode(code);
        result.setErrorMsg(errorMsg);
        return result;
    }

    private static <T> Result<T> getResult_2(String msg, Integer code, T data, String errorMsg) {
        Result<T> result = new Result<T>();
        result.setMsg(msg);
        result.setCode(code);
        result.setObject(data);
        return result;
    }



}
