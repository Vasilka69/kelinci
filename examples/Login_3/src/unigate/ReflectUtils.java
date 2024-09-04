package src.unigate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ReflectUtils {

    /**
     * Получение публичных геттеров, не аннотированных {@link JsonIgnore}
     *
     * @return Map <наименование атрибута, геттер>
     */
    public static Map<String, Method> getPublicGetters(Class<?> clz) {
        try {
            return Arrays.stream(Introspector.getBeanInfo(clz).getPropertyDescriptors())
                    .filter(pd -> Objects.nonNull(pd.getReadMethod())
                            && Modifier.isPublic(pd.getReadMethod().getModifiers())
                            && !pd.getReadMethod().isAnnotationPresent(JsonIgnore.class)
                            && !"class".equals(pd.getName()))
                    .collect(Collectors.toMap(PropertyDescriptor::getName, PropertyDescriptor::getReadMethod));
        } catch (IntrospectionException e) {
            log.warn("Ошибка при получении геттеров класса {}: {}", clz.getName(), e.getMessage());
            return Collections.emptyMap();
        }
    }
}
