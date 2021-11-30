package info.moevm.se.nosqlcatsmemecached.config;

import com.google.common.base.CaseFormat;
import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class InjectMemcachedNameAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        Field injectMemcachedNameField;
        for (Field field : fields) {
            InjectMemcachedName injectMemcachedNameAnnotation = field.getAnnotation(InjectMemcachedName.class);
            if (injectMemcachedNameAnnotation != null && field.getType() == Map.class) {
                injectMemcachedNameField = field;
                String targetClassName = injectMemcachedNameAnnotation.value();
                injectMemcachedNameField.setAccessible(true);
                ReflectionUtils.setField(injectMemcachedNameField, bean,
                    getMapForInject(injectMemcachedNameAnnotation.value(), injectMemcachedNameAnnotation.type()));
            }
        }
        return bean;
    }

    @SneakyThrows
    private Map<String, Method> getMapForInject(String className, String type) {
        Map<String, Method> map = new HashMap<>();
        Class<?> targetClass = Class.forName(className);
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field targetField : targetFields) {
            MemcachedName memcachedNameAnnotation = targetField.getAnnotation(MemcachedName.class);
            if (memcachedNameAnnotation != null) {
                String value = memcachedNameAnnotation.value();
                String name = type.equals("getter") ? methodName(value, "get_") : methodName(value, "set_");
                map.put(value,
                    Arrays.stream(targetClass.getDeclaredMethods())
                          .filter(method -> method.getName().contains(name)).findFirst()
                          .get());
            }
        }
        return map;
    }

    private String methodName(String name, String prefix) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, prefix + name);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
