package com.aiyi.disk.disk.conf;

import com.aiyi.core.exception.AccessOAuthException;
import com.aiyi.core.util.thread.ThreadUtil;
import com.aiyi.disk.disk.entity.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @Author: 郭胜凯
 * @Date: 2019-05-30 15:23
 * @Email 719348277@qq.com
 * @Description: 请求线程统一过滤配置
 */
public class RequestThreadFilterConf implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(RequestThreadFilterConf.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String[] exc = {".js", ".css", ".map", ".png", ".jpg", ".jpeg", ".gif", ".min", ".woff", ".woff2"};
        for (String e: exc){
            if (request.getRequestURI().endsWith(e)){
                return true;
            }
        }

        request.setAttribute("ctx", request.getContextPath());
        request.setAttribute("appName", "蟑螂云盘");
        String reqHeader = request.getHeader("Access-Control-Request-Headers");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Access-Control-Allow-Headers", reqHeader);
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(204);
            return false;
        }

        UserPO loginUser = UserPO.newBuilder().build();
        if (request.getDispatcherType().name().equals("ERROR")){
            return false;
        }
        if (handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            NoAuth methodAnnotation = method.getMethodAnnotation(NoAuth.class);
            if (null == methodAnnotation){
                // 这里是认证入口
//                String token = getRequestToken(request);
//                String requestUri = null;
//                if (method.getMethodAnnotation(NoAccess.class) == null){
//                    requestUri = request.getRequestURI();
//                    ThreadUtil.setCacheData("access", true);
//                }else{
//                    ThreadUtil.setCacheData("access", false);
//                }
//                loginUser = AccessAuthHelper.auth(token, requestUri, request.getMethod().toUpperCase());

                loginUser = (UserPO) request.getSession().getAttribute("LOGIN_USER");

                if (null == loginUser){
                    throw new AccessOAuthException("登录信息已过期, 请重新登录");
                }else{
//                    ThreadUtil.setToken(token);
                    ThreadUtil.setUserEntity(loginUser);
                }
            }
        }

        String requestId = request.getHeader("requestId");
        if (StringUtils.isEmpty(requestId)){
            requestId = UUID.randomUUID().toString().toUpperCase();
        }
        ThreadUtil.setRequestId(requestId);

        // 初始化上下文
        initContext(request, loginUser, response);
        return true;
    }

    /**
     * 初始化本次请求上下文
     * @param request
     *          请求方法
     * @param user
     *          当前用户
     */
    private void initContext(HttpServletRequest request, UserPO user, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("ctx", request.getContextPath());

        ThreadUtil.setUserEntity(user);
        ThreadUtil.setUserName(user.getUsername());
        ThreadUtil.setUserId(user.getId());

        logger.info("requestURI:[{}], requestID:[{}], requestUser:[{}], custAddr:[{}]",
                request.getRequestURI(), ThreadUtil.getRequestId(), user.getUsername(), request.getRemoteAddr());

    }

    /**
     * 获取请求信息中的Token
     * @param request
     *      请求信息
     * @return
     */
    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)){
            token = request.getHeader("access_token");
        }
        if (StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        if (StringUtils.isEmpty(token)){
            token = request.getParameter("access_token");
        }
        if (StringUtils.isEmpty(token)){
            throw new AccessOAuthException("请先登录");
        }
        return token;
    }
}