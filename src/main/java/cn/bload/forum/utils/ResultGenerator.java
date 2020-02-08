package cn.bload.forum.utils;


import cn.bload.forum.base.ResultBean;
import cn.bload.forum.constenum.Result;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/13 16:12
 * @describe 类描述:
 */
public class ResultGenerator {

    public static ResultBean getSuccessResult(){
        return getSuccessResult(null);
    }

    public static ResultBean getSuccessResult(String message){
        return getSuccessResult(null,message);
    }

    public static <T> ResultBean<T> getSuccessResult(T data){
        return getSuccessResult(data,null);
    }

    public static <T> ResultBean<T> getSuccessResult(T data,String message){
        Result result = Result.SUCCESS;
        if (message == null){
            message = result.getMessage();
        }
        return getResult(result.getCode(),message,data);
    }

    public static ResultBean getErrorResult(){
        return getErrorResult(null);
    }

    public static ResultBean getErrorResult(String message){
        return getErrorResult(null,message);
    }

    public static <T> ResultBean<T> getErrorResult(T data){
        return getErrorResult(data,null);
    }

    public static <T> ResultBean<T> getErrorResult(T data,String message){
        Result result = Result.ERROR;
        if (message == null){
            message = result.getMessage();
        }
        return getResult(result.getCode(),message,data);
    }

    public static ResultBean getUnLoginResult(String message){
        return getResult(Result.NOLOGIN.getCode(),Result.NOLOGIN.getMessage(),null);
    }

    public static <T> ResultBean<T> getResult(Result result,T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(result.getCode());
        resultBean.setMessage(result.getMessage());
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> ResultBean<T> getResult(int code,String message,T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        resultBean.setData(data);
        return resultBean;
    }
}
