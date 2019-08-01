package com.netease.music.common;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {
    /**
     * 返回码
     */
    private int retCode;

    /**
     * 返回信息
     */
    private String retDesc;

    /**
     * 返回数据
     */
    private T data;


    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T>CommonResponse<T> build(WebCommRetEnum retEnum) {
        CommonResponse<T> ret = new CommonResponse<>();
        ret.setRetCode(retEnum.retCode);
        ret.setRetDesc(retEnum.retMsg);
        return ret;
    }


    public static <T>CommonResponse<T> build(WebCommRetEnum webCommRetEnum, T data) {
        CommonResponse<T> ret = build(webCommRetEnum);
        ret.setData(data);
        return ret;
    }

    public static <T>CommonResponse<T> succ() {
        return CommonResponse.build(WebCommRetEnum.SUCCESS);
    }

    public static <T>CommonResponse<T> succ(T data) {
        CommonResponse<T> ret = CommonResponse.succ();
        ret.setData(data);
        return ret;
    }

    public static <T>CommonResponse<T> fail() {
        return CommonResponse.build(WebCommRetEnum.FAIL);
    }

    public static <T>CommonResponse<T> systemError() {
        return CommonResponse.build(WebCommRetEnum.SYSTEM_ERROR);
    }

    public static <T>CommonResponse<T> parameterError() {
        return CommonResponse.build(WebCommRetEnum.ILLEGAL_PARAM_FAIL);
    }

    public static <T> CommonResponse<T> parameterError(String retDesc) {
        CommonResponse<T> result = CommonResponse.build(WebCommRetEnum.ILLEGAL_PARAM_FAIL);
        result.setRetDesc(retDesc);
        return result;
    }

    /**
     * 失败
     *
     * @param retDesc
     * @return
     */
    public static <T>CommonResponse<T> fail(String retDesc) {
        CommonResponse<T> ret = CommonResponse.build(WebCommRetEnum.FAIL);
        ret.setRetDesc(retDesc);
        return ret;
    }

    /**
     * 失败
     * @param retCode
     * @param retMsg
     * @return
     */
    public static <T>CommonResponse<T> fail(Integer retCode, String retMsg) {
        CommonResponse<T> ret = new CommonResponse<>();
        ret.setRetCode(retCode);
        ret.setRetDesc(retMsg);
        return ret;
    }

    /**
     * 失败
     * @param webCommRetEnum
     * @return
     */
    public static <T>CommonResponse<T> fail(WebCommRetEnum webCommRetEnum) {
        CommonResponse<T> ret = new CommonResponse<>();
        ret.setRetCode(webCommRetEnum.getRetCode());
        ret.setRetDesc(webCommRetEnum.getRetMsg());
        return ret;
    }

    /**
     * 失败
     * @param webCommRetEnum
     * @return
     */
    public static <T>CommonResponse<T> fail(WebCommRetEnum webCommRetEnum, T data) {
        CommonResponse<T> ret = new CommonResponse<>();
        ret.setRetCode(webCommRetEnum.getRetCode());
        ret.setRetDesc(webCommRetEnum.getRetMsg());
        ret.setData(data);
        return ret;
    }

    /**
     * 失败
     *
     * @param retDesc
     * @param data
     * @return
     */
    public static <T>CommonResponse<T> fail(String retDesc, T data) {
        CommonResponse<T> ret = build(WebCommRetEnum.FAIL);
        ret.setRetDesc(retDesc);
        ret.setData(data);
        return ret;
    }

    /**
     * 失败，带回数据
     *
     * @param data
     * @return
     */
    public static <T>CommonResponse<T> failWithData(T data) {
        CommonResponse<T> commonResponse = CommonResponse.build(WebCommRetEnum.FAIL);
        commonResponse.setData(data);
        return commonResponse;
    }

    public boolean isSucc() {
        return retCode == WebCommRetEnum.SUCCESS.retCode;
    }


    public enum WebCommRetEnum {
        SUCCESS(200, "成功"),
        EXPORT_EXCEL_SUCCESS(201, "excel导出成功"),

        FAIL(400, "服务端失败"),
        ILLEGAL_PARAM_FAIL(402, "参数不合法"),
        FORBIDDEN(403, "禁止访问"),
        TOKEN_VALID_FAIL(300, "token校验失败"),

        // 公共返回参数 600-999
        PARAM_ERROR(600, "参数错误"),
        SYSTEM_ERROR(500, "服务端异常"),
        // 1000+ 订单相关

        ;

        private int retCode;

        private String retMsg;

        private WebCommRetEnum(int retCode, String retDesc) {
            this.retCode = retCode;
            this.retMsg = retDesc;
        }

        public int getRetCode() {
            return retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }
    }
}
