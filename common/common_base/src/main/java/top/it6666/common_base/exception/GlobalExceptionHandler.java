package top.it6666.common_base.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.it6666.common_utils.utils.ResponseResult;

/**
 * @author BNTang
 * 全局异常处理器, 只要发生了异常,如果在自己控制当中.没有去捕获,就会到此控制器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e) {
        e.printStackTrace();
        return ResponseResult.error();
    }

    /**
     * <b>
     * 自定义异常
     * </b>
     */
    @ExceptionHandler(BnTangException.class)
    @ResponseBody
    public ResponseResult error(BnTangException e) {
        e.printStackTrace();
        return ResponseResult.error().code(e.getCode()).message(e.getMsg());
    }
}