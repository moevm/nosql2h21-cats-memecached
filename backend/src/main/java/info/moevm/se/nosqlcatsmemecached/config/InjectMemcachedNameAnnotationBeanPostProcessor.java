package info.moevm.se.nosqlcatsmemecached.config;

import com.google.common.base.CaseFormat;
import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InjectMemcachedNameAnnotationBeanPostProcessor implements BeanPostProcessor {
    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        Field injectMemcachedNameField = null;
        Map<String, Method> mapForInject = new HashMap<>();
        for (Field field : fields) {
            InjectMemcachedName injectMemcachedNameAnnotation = field.getAnnotation(InjectMemcachedName.class);
            if (injectMemcachedNameAnnotation != null) {
                if (field.getType() == List.class) {
                    injectMemcachedNameField = field;
                }
            }
        }
        if (injectMemcachedNameField == null) {
            return bean;
        }
        for (Field field : fields) {
            MemcachedName memcachedNameAnnotation = field.getAnnotation(MemcachedName.class);
            if (memcachedNameAnnotation != null) {
                String value = memcachedNameAnnotation.value();

                mapForInject.put(value, bean.getClass().getMethod(getterName(value)));
            }
        }
        injectMemcachedNameField.setAccessible(true);
        ReflectionUtils.setField(injectMemcachedNameField, bean, mapForInject);
        System.out.println(mapForInject);
        System.out.println("SSS");
        return bean;
    }

    private String getterName(String fieldName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "get_" + fieldName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
