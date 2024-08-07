package pro.sky.exever.hogwarts.school;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

public class TestUrlGenerator {
    public static String generateUrl(Class<?> aClass, String methodName, Class<?>... parameters) {
        String methodUrl;
        String controllerUrl = aClass.getAnnotation(RequestMapping.class).value()[0];
        methodUrl = getMethodMapping(aClass, methodName, parameters);
        return controllerUrl + methodUrl;
    }

    public static String generateUrl(Class<?> aClass) {
        return aClass.getAnnotation(RequestMapping.class).value()[0];
    }

    private static String getMethodMapping(Class<?> aClass, String methodName, Class<?>... parameters) {
        try {
            Annotation[] annotations = aClass.getMethod(methodName, parameters).getAnnotations();
            AnnotatedType[] classAnnotatedInterfaces = aClass.getAnnotatedInterfaces();
            Annotation[] classAnnotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == GetMapping.class) {
                    return aClass.getMethod(methodName, parameters).getAnnotation(GetMapping.class).value()[0];
                }
                if (annotation.annotationType() == PostMapping.class) {
                    return aClass.getMethod(methodName, parameters).getAnnotation(PostMapping.class).value()[0];
                }
                if (annotation.annotationType() == DeleteMapping.class) {
                    return aClass.getMethod(methodName, parameters).getAnnotation(DeleteMapping.class).value()[0];
                }
                if (annotation.annotationType() == PutMapping.class) {
                    return aClass.getMethod(methodName, parameters).getAnnotation(PutMapping.class).value()[0];
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

}
