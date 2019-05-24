package com.threeNews.springproject.domain;

/**
 * Data Return in response.
 *
 * @author Jinhao Zhong
 * @version 1.0
 */

public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResultVO() {
    }

    /**
     * @param code    The return code for response
     * @param message The message for response
     */
    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param code    The return code for response
     * @param message The message for response
     * @param data    The data to transfer
     */
    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @return The return code for response
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code for response
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The message to send to response
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message to send to response
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The data to send to response
     */
    public T getData() {
        return data;
    }

    /**
     * @param data The data to send to response
     */
    public void setData(T data) {
        this.data = data;
    }

}
