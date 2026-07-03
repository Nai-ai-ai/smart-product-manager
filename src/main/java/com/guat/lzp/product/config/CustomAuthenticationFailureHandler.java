package com.guat.lzp.product.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理 - 根据不同错误类型跳转不同参数
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorType;
        
        if (exception instanceof BadCredentialsException) {
            // 密码错误
            errorType = "bad_credentials";
        } else if (exception instanceof DisabledException) {
            // 账号被禁用
            errorType = "disabled";
        } else if (exception instanceof LockedException) {
            // 账号被锁定
            errorType = "locked";
        } else {
            // 其他错误（包括用户不存在）
            errorType = "error";
        }
        
        response.sendRedirect(request.getContextPath() + "/login?error=" + errorType);
    }
}
