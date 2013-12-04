package com.honeybuy.shop.web.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class SessionAttributeAnnotationResolver implements WebArgumentResolver {

    public Object resolveArgument(MethodParameter parameter,
            NativeWebRequest request) throws Exception {
        System.out.println("I am here");
        Annotation[] parameterAnnotations = parameter.getParameterAnnotations();
        Class<?> parameterType = parameter.getParameterType();

        for (Annotation parameterAnnotation : parameterAnnotations) {
            if (SessionAttribute.class.isInstance(parameterAnnotation)) {
                SessionAttribute sessionAttribute = (SessionAttribute) parameterAnnotation;
                String parameterName = sessionAttribute.value();
                boolean required = sessionAttribute.required();
                HttpServletRequest httprequest = (HttpServletRequest) request
                        .getNativeRequest();
                HttpSession session = httprequest.getSession(false);
                Object result = null;
                if (session != null) {
                    result = session.getAttribute(parameterName);
                }
                if (result == null && required && session == null)
                    raiseSessionRequiredException(parameterName, parameterType);
                if (result == null && required)
                    raiseMissingParameterException(parameterName, parameterType);
                return result;
            }
        }
        return WebArgumentResolver.UNRESOLVED;
    }

    protected void raiseMissingParameterException(String paramName,
            Class<?> paramType) throws Exception {
        throw new IllegalStateException("Missing parameter '" + paramName
                + "' of type [" + paramType.getName() + "]");
    }

    protected void raiseSessionRequiredException(String paramName,
            Class<?> paramType) throws Exception {
        throw new HttpSessionRequiredException(
                "No HttpSession found for resolving parameter '" + paramName
                        + "' of type [" + paramType.getName() + "]");
    }
}