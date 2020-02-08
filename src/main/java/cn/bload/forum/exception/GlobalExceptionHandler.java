package cn.bload.forum.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bload.forum.base.ResultBean;
import cn.bload.forum.utils.ResultGenerator;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/19 17:24
 * @describe 类描述: 全局异常拦截，异常请抛出 MyRuntimeException
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyRuntimeException.class)
    @ResponseBody
    public ResultBean exceptionHandler(MyRuntimeException e){
        return ResultGenerator.getErrorResult(e.getMessage());
    }

    @ExceptionHandler(value = UnLoginException.class)
    @ResponseBody
    public ResultBean exceptionHandler(UnLoginException e){
        return ResultGenerator.getUnLoginResult(e.getMessage());
    }

    /**
     * 捕获vaild验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean exceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        return ResultGenerator.getErrorResult(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean exceptionHandler(Exception e){
        e.printStackTrace();
        return ResultGenerator.getErrorResult();
    }
}
