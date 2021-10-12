package info.moevm.se.nosqlcatsmemecached.config;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
                ReflectionUtils.setField(injectMemcachedNameField, bean, getMapForInject(targetClassName));
            }
        }
        return bean;
    }

    @SneakyThrows
    private Map<String, Function<?, ?>> getMapForInject(String className) {
        Map<String, Function<?, ?>> map = new HashMap<>();
        Class<?> targetClass = Class.forName(className);
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field targetField : targetFields) {
            MemcachedName memcachedNameAnnotation = targetField.getAnnotation(MemcachedName.class);
            if (memcachedNameAnnotation != null) {
                String value = memcachedNameAnnotation.value();
                map.put(value, new Function<>() {
                    @SneakyThrows
                    @Override
                    public Object apply(Object cat) {
                        return targetClass.getMethod(getterName(value)).invoke(cat);
                    }
                });
            }
        }
        return map;
    }

    private String getterName(String fieldName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "get_" + fieldName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
