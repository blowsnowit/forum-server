package cn.bload.forum.constenum;

import lombok.Getter;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/13 16:13
 * @describe 类描述:
 */
public enum Result {
    /**
     * 操作成功
     */
    SUCCESS(200,"操作成功"),
    /**
     * 操作错误
     */
    ERROR(201,"操作错误"),
    /**
     * 操作失败
     */
    FAIL(202,"操作失败"),
    /**
     * 未登錄
     */
    NOLOGIN(100,"未登录")
    ;

    @Getter
    private int code;
    @Getter
    private String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
