package com.liu.ylb.front.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.vo.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *  验证token的拦截器
 */
public class TokenInterceptor  implements HandlerInterceptor {

    private StringRedisTemplate redisTemplate;

    public TokenInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //验证token
        //1 判断是否预检请求OPTIONS
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }
        boolean checkToken = false;
        String auth = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(auth) && StringUtils.startsWith(auth,"Bearer")){
            String accessToken = auth.substring(7);
            if(StringUtils.isNotBlank(accessToken)){
                String key = RedisKey.TOKEN_LOGIN + accessToken.toUpperCase(Locale.ROOT);
                if(redisTemplate.hasKey(key)){
                    Object id = redisTemplate.boundHashOps(key).get("id");
                    if(id != null){
                        if(id.equals(request.getHeader("uid"))){
                            checkToken = true;
                        }
                    }
                }
            }
            if(!checkToken){
                CommonResult commonResult = CommonResult.FAIL();
                commonResult.setRCode(RCode.TOKEN_INVALID);
                String json = JSONObject.toJSONString(commonResult);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.println(json);
                printWriter.flush();
                printWriter.close();
            }
        }
        return checkToken;
    }
}
