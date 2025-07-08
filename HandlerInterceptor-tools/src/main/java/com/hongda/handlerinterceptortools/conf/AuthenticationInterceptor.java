package com.hongda.handlerinterceptortools.conf;

import com.hongda.handlerinterceptortools.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Autowired
    private JWTService jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 跳过非控制器方法的处理
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 检查是否有@PermitAll注解，有则跳过认证
        PermitAll permitAll = handlerMethod.getMethodAnnotation(PermitAll.class);
        if (permitAll != null) {
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{"error": "未授权，请先登录"}");
            return false;
        }

        token = token.substring(7); // 去掉"Bearer "前缀

        try {
            // 验证token
            if (!jwtTokenProvider.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{"error": "Token已失效，请重新登录"}");
                return false;
            }

            // 从token中获取用户信息并设置到请求属性中
            String username = jwtTokenProvider.getUsernameFromToken(token);
            User user = userService.findByUsername(username);

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{"error": "用户不存在"}");
                return false;
            }

            // 检查方法是否有@RequireRole注解
            RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
            if (requireRole != null) {
                // 检查用户是否有所需角色
                String[] roles = requireRole.value();
                boolean hasRole = false;
                for (String role : roles) {
                    if (user.hasRole(role)) {
                        hasRole = true;
                        break;
                    }
                }

                if (!hasRole) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{"error": "权限不足"}");
                    return false;
                }
            }

            // 将用户信息放入请求属性
            request.setAttribute("currentUser", user);

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{"error": "Token验证失败"}");
            return false;
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
