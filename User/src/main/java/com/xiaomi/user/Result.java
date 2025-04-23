package com.xiaomi.user;

// 通用的结果返回类，用于封装后端返回给前端的数据和状态信息
// 泛型参数 T 表示返回数据的类型，可以是任意类型，如 User、String 等
public class Result<T> {
    private String code;

    private String msg;

    private T data;

    // 获取状态码的方法
    public String getCode() {
        return code;
    }

    // 设置状态码的方法
    public void setCode(String code) {
        this.code = code;
    }

    // 获取结果描述信息的方法
    public String getMsg() {
        return msg;
    }

    // 设置结果描述信息的方法
    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 获取具体数据内容的方法
    public T getData() {
        return data;
    }

    // 设置具体数据内容的方法
    public void setData(T data) {
        this.data = data;
    }

    // 无参构造函数，用于创建一个空的 Result 对象
    public Result() {
    }

    // 带数据参数的构造函数，用于创建一个包含具体数据的 Result 对象
    public Result(T data) {
        this.data = data;
    }

    // 创建一个表示成功的 Result 对象，只设置状态码为 "0" 和消息为 "成功"，不包含具体数据
    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    // 创建一个表示成功的 Result 对象，设置状态码为 "0" 和消息为 "成功"，并包含传入的具体数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    // 创建一个表示成功的 Result 对象，设置状态码为 "0"，并包含传入的具体数据和自定义的成功消息
    public static <T> Result<T> success(T data, String msg) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg(msg);
        return result;
    }

    // 创建一个表示错误的 Result 对象，设置传入的状态码和错误消息，不包含具体数据
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}