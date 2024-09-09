package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Утилиты для работы с коллекциями
 */
//@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtils {

    /**
     * Сравнение содержимого 2 коллеций, порядок не важен.
     */
    public static <E> boolean equalContentIgnoreOrd(Collection<E> c1, Collection<E> c2) {
        Assert.notNull(c1, "Коллекция с1 не может быть равна null");
        Assert.notNull(c2, "Коллекция с2 не может быть равна null");
        try {
            return c1.size() == c2.size() && c1.containsAll(c2) && c2.containsAll(c1);
        } catch (ClassCastException e) {
            return false;
        }
    }

//    /**
//     * Проверка на наличие дубликатов в коллекции
//     */
//    public static <E> boolean containDuplicates(Collection<E> c) {
//        Assert.notNull(c, "Коллекция не может быть равна null");
//        Set<E> set = new HashSet<>(c);
//        return set.size() != c.size();
//    }
//
//    /**
//     * Вывод элементов коллекции в строку через ", "
//     */
//    public static String toString(Collection<?> c) {
//        return toString(c, ", ");
//    }
//
//    /**
//     * Вывод элементов коллекции в строку, разделенных с помощью разделителя {@code delimiter}
//     *
//     * @param delimiter разделитель элементов в строке
//     */
//    public static <C> String toString(Collection<C> c, String delimiter) {
//        Assert.notNull(c, "Коллекция не может быть равна null");
//        Assert.notNull(delimiter, "Разделитель строки не может быть равна null");
//        return c.stream()
//                .filter(it -> Objects.nonNull(it) && it != "null")
//                .map(Object::toString)
//                .collect(Collectors.joining(delimiter));
//    }
//
//    /**
//     * Вывод первого not-null элемента
//     *
//     * @param items список элементов
//     */
//    public static <T> T firstNotNull(T... items) {
//        return Arrays.stream(items).filter(Objects::nonNull).findFirst().orElse(null);
//    }
//
//    /**
//     * Вывод первого непустого элемента
//     *
//     * @param items список элементов
//     */
//    public static String firstNotEmpty(String... items) {
//        return Arrays.stream(items).filter(StringUtils::hasText).findFirst().orElse(null);
//    }

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

//    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
//        return map == null || map.isEmpty();
//    }
//
//    /**
//     * Сортировка коллекции и изменение размера согласно параметрам
//     *
//     * @param c      коллекция
//     * @param sort   порядок сравнения атрибутов, "-" перед наименованием атрибута обозначает обратный порядок
//     * @param offset количество "отбрасываемых" элементов с начала
//     * @param limit  максимальное количество элементов в результирующей выборке
//     */
//    public static <T> List<T> sortAndResize(Collection<T> c, String sort, Integer offset, Integer limit) {
//        Stream<T> stream = c.stream();
//        if (hasText(sort)) {
//            stream = stream.sorted(createComparator(sort));
//        }
//        if (offset != null) {
//            stream = stream.skip(offset);
//        }
//        if (limit != null) {
//            stream = stream.limit(limit);
//        }
//        return stream.collect(Collectors.toList());
//    }
//
//    /**
//     * Изменение размера согласно параметрам
//     *
//     * @param c      коллекция
//     * @param offset количество "отбрасываемых" элементов с начала
//     * @param limit  максимальное количество элементов в результирующей выборке
//     */
//    public static <T> List<T> resize(Collection<T> c, Integer offset, Integer limit) {
//        Stream<T> stream = c.stream();
//        if (offset != null) {
//            stream = stream.skip(offset);
//        }
//        if (limit != null) {
//            stream = stream.limit(limit);
//        }
//        return stream.collect(Collectors.toList());
//    }
//
//    /**
//     * Компаратор для сравнения 2 объектов в определенном порядке атрибутов
//     *
//     * @param sort порядок сравнения атрибутов, "-" перед наименованием атрибута обозначает обратный порядок
//     */
//    public static <T> Comparator<T> createComparator(String sort) {
//        if (!hasText(sort)) {
//            return (o1, o2) -> 0;
//        }
//        String[] sortArgs = sort.split(",");
//        return (l, r) -> {
//            Map<String, Method> lGetters = getPublicGetters(l.getClass());
//            Map<String, Method> rGetters = getPublicGetters(r.getClass());
//            for (String sortArg : sortArgs) {
//                //если аргумент сортировки начинается с "-", то обратный порядок
//                var order = 1;
//                if (sortArg.startsWith("-")) {
//                    sortArg = sortArg.substring(1);
//                    order = -1;
//                }
//                var result = compare(l, r, lGetters.get(sortArg), rGetters.get(sortArg), sortArg);
//                if (result != 0) {
//                    return result * order;
//                }
//            }
//            return 0;
//        };
//    }
//
//    private static int compare(Object lObj, Object rObj, Method lGetter, Method rGetter, String sortArg) {
//        if (lGetter != null && rGetter != null) {
//            if (lGetter.getReturnType() == rGetter.getReturnType()) {
//                try {
//                    return compare(lGetter.invoke(lObj), rGetter.invoke(rObj));
//                } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
//                    log.warn("Ошибка при сравнении атрибута {} классов {} и {}: {}", sortArg, lObj.getClass().getName(), rObj.getClass().getName(), e.getMessage());
//                }
//            } else {
//                return  0;
//            }
//        } else {
//            return compare(lGetter, rGetter);
//        }
//
//        return 0;
//    }
//
//    private static int compare(Object left, Object right) {
//        if (left != null && right != null) {
//            if (left instanceof Comparable) {
//                return ((Comparable) left).compareTo(right);
//            } else {
//                return left.toString().compareTo(right.toString());
//            }
//        } else if (left != null) {
//            return -1;
//        } else if (right != null) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
//
//    public static <T> Set<T> appendAllToSet(Set<T> c1, Set<T> c2) {
//        Set<T> result = new HashSet<>();
//        if (!CollectionUtils.isEmpty(c1) && CollectionUtils.isEmpty(c2)) {
//            result.addAll(c1);
//        }
//        if (CollectionUtils.isEmpty(c1) && !CollectionUtils.isEmpty(c2)) {
//            result.addAll(c2);
//        }
//        if (!CollectionUtils.isEmpty(c1) && !CollectionUtils.isEmpty(c2)) {
//            result.addAll(c1);
//            result.addAll(c2);
//        }
//        return result;
//    }
//
//    public static <T> boolean containsAny(Collection<T> expected, Collection<T> actual) {
//        Assert.notNull(expected, "Коллекция expected не может быть равна null");
//        Assert.notNull(actual, "Коллекция actual не может быть равна null");
//        return expected.stream()
//                .anyMatch(actual::contains);
//    }
//
//    public static <T> List<T> getNonContains(Collection<T> expected, Collection<T> actual) {
//        Assert.notNull(expected, "Коллекция expected не может быть равна null");
//        Assert.notNull(actual, "Коллекция actual не может быть равна null");
//        return expected.stream()
//                .filter(key -> !actual.contains(key))
//                .collect(Collectors.toList());
//    }
//
//    public static boolean equalsMapIgnoreOrd(Map<String, List<String>> expected, Map<String, List<String>> actual) {
//        Assert.notNull(expected, "Expected Map не может быть равна null");
//        Assert.notNull(actual, "Actual Map не может быть равна null");
//        if (expected.isEmpty() && actual.isEmpty()) {
//            return true;
//        }
//        if (expected.keySet().size() != actual.keySet().size() || !expected.keySet().containsAll(actual.keySet())) {
//            return false;
//        }
//        return expected.keySet().stream()
//                .allMatch(key -> equalContentIgnoreOrd(expected.get(key), actual.get(key)));
//    }
}
