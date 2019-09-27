package com.aiyi.disk.disk.conf;

import com.aiyi.core.annotation.po.EnumName;
import com.aiyi.core.beans.ResultBean;
import com.aiyi.core.exception.AccessOAuthException;
import com.aiyi.core.exception.AccessResourceOAuthException;
import com.aiyi.core.exception.RequestParamException;
import com.aiyi.core.exception.ServiceInvokeException;
import com.aiyi.core.util.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 郭胜凯
 * @Date: 2019-05-30 15:23
 * @Email 719348277@qq.com
 * @Description: 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    protected Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    private Map<String, Logger> loggerMap = new HashMap<>();

    private Logger getLogger(String clazz){
        Logger logger = loggerMap.get(clazz);
        if (null == logger){
            logger = LoggerFactory.getLogger(clazz);
            loggerMap.put(clazz, logger);
        }
        return logger;
    }

    /**
     * 参数校验异常400
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultBean validationException(Exception e) {
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        return ResultBean.error(e.getMessage()).setCode(400);
    }

    /**
     * 请求参数异常400
     * @param e
     * @return
     */
    @ExceptionHandler(value = RequestParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultBean requestParamException(Exception e) {
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        return ResultBean.error(e.getMessage()).setCode(400);
    }

    /**
     * 参数转换异常400
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultBean illegalArgumentException(Exception e) {
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        List<StackTraceElement> stackList = getStackList(e);

        // 针对枚举异常消息友好化
        String msg = e.getMessage();
        if (msg.startsWith("No enum constant")){
            String enumClassStr = msg.replace("No enum constant ", "");
            enumClassStr = enumClassStr.substring(0, enumClassStr.lastIndexOf("."));
            try {
                Class<?> clazz = Class.forName(enumClassStr);
                String value = msg.substring(msg.lastIndexOf(".")).replace(".", "");
                EnumName annotation = clazz.getAnnotation(EnumName.class);
                if (null == annotation || "".equals(annotation.value())){
                    msg = enumClassStr + "枚举中, 不包含[" + value + "]这个类型";
                }else{
                    msg = annotation.value() + "中, 不包含[" + value + "]这个类型";
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return ResultBean.error(msg).setCode(400)
                .putResponseBody("exception", e.getClass()).putResponseBody("stackList", stackList);
    }


    /**
     * 登录权限认证异常401
     * @param e
     * @return
     */
    @ExceptionHandler(value = AccessOAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResultBean accessOAuthException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        if (request.getHeader("Content-Type").toLowerCase().contains("application/json")){
            return ResultBean.error(e.getMessage()).setCode(401);
        }else{
            try {
                response.sendRedirect(request.getContextPath() + "/login");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    /**
     * 资源权限认证异常403
     * @param e
     * @return
     */
    @ExceptionHandler(value = AccessResourceOAuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResultBean accessResourceOAuthException(Exception e){
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        return ResultBean.error(e.getMessage()).setCode(403);
    }

    /**
     * 服务器处理自定义捕获异常500
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceInvokeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultBean invokeException(Exception e) {
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), e.getMessage());
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        return ResultBean.error(e.getMessage()).setCode(500);
    }

    /**
     * 其他未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean exception(Exception e, HttpServletResponse httpResponse) {
        httpResponse.setStatus(500);
        String msg = "服务器忙，请稍后再试";
        if (null != e.getMessage()){
            msg = e.getMessage();
        }
        String format = MessageFormat
                .format("requestId:[{0}], msg:[{1}]", ThreadUtil.getRequestId(), msg);
        getLogger(e.getStackTrace()[0].getClassName()).error(format, e);
        List<StackTraceElement> stackList = getStackList(e);
        return ResultBean.error(msg).setCode(500)
                .putResponseBody("exception", e.getClass()).putResponseBody("stackList", stackList);
    }

    /**
     * 获取可能有帮助的异常堆栈信息
     * @param e
     * @return
     */
    private List<StackTraceElement> getStackList(Exception e){
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<StackTraceElement> stackList = new ArrayList<>();
        for (StackTraceElement stackTraceElement: stackTrace){
            if (stackTraceElement.getClassName().contains("com.front")){
                stackList.add(stackTraceElement);
            }
        }
        return stackList;
    }
}
