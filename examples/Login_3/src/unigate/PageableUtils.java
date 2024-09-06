package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableUtils {
    public static final int POSTGRES_MAX_PARAMS = 32767;
//    private static final String ERROR_PAGE = "ОШИБКА: запрашиваемый номер страницы не может быть меньше 0!";
//    private static final String ERROR_LIMIT = "ОШИБКА: размер страницы не может быть меньше 0!";
//
//    // может использоваться вместе с проекциями
//    public static Pageable toPageable(Integer limit, Integer page, @NotNull Sort sort) {
//        if (page != null && page < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_PAGE);
//        }
//
//        if (page == null) {
//            page = 0;
//        }
//
//        if (limit != null && limit < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_LIMIT);
//        }
//
//        if (limit == null || limit == 0) {
//            limit = 25;
//        }
//
//        // передаваемые для (!!) сортировки поля в PageRequest.of(..) используются применительно к сущности,
//        // с проекцией не работает. Для проекций используй нижележащий метод
//        return PageRequest.of(page, limit, sort);
//    }
//
//    public static Pageable toProjectionPageable(@Nullable Integer limit, @Nullable Integer page,
//                                                @Nullable String sortFromRequest,
//                                                @NotNull String idFieldName,
//                                                @Nullable String... additionalSortFields) {
//        if (page != null && page < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_PAGE);
//        }
//
//        if (page == null) {
//            page = 0;
//        }
//
//        if (limit != null && limit < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_LIMIT);
//        }
//
//        if (limit == null || limit == 0) {
//            limit = 25;
//        }
//
//        if (!StringUtils.hasText(sortFromRequest) || "null".equals(sortFromRequest) || "undefined".equals(sortFromRequest)) {
//            sortFromRequest = idFieldName;
//        }
//
//        String direction;
//        if (StringUtils.startsWithIgnoreCase(sortFromRequest, "-")) {
//            sortFromRequest = sortFromRequest.substring(1);
//            direction = "DESC";
//        } else {
//            direction = "ASC";
//        }
//
//        idFieldName = "(" + idFieldName + ")";
//        sortFromRequest = "(" + sortFromRequest + ")";
//
//        String[] arrTemp = {sortFromRequest, idFieldName};
//        String[] finalSortArr;
//        if (additionalSortFields != null) {
//            for (int i = 0; i < additionalSortFields.length; i++) {
//                additionalSortFields[i] = "(" + additionalSortFields[i] + ")";
//            }
//
//            finalSortArr = ArrayUtils.addAll(arrTemp, additionalSortFields);
//        } else {
//            finalSortArr = arrTemp;
//        }
//
//        return PageRequest.of(page, limit, JpaSort.unsafe(Sort.Direction.fromString(direction), finalSortArr));
//    }
//
//    public static Pageable toProjectionPageable(@Nullable Integer limit, @Nullable Integer page) {
//        if (page != null && page < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_PAGE);
//        }
//
//        if (page == null) {
//            page = 0;
//        }
//
//        if (limit != null && limit < 0) {
//            throw new ResourceIllegalArgumentException(ERROR_LIMIT);
//        }
//
//        if (limit == null || limit == 0) {
//            limit = 25;
//        }
//
//        return PageRequest.of(page, limit);
//    }
//
//    public static Sort toSort(String sort, @NotNull String idFieldName) {
//        if (!StringUtils.hasText(sort) || "null".equals(sort) || "undefined".equals(sort)) {
//            sort = idFieldName;
//        }
//        String direction;
//        if (StringUtils.startsWithIgnoreCase(sort, "-")) {
//            sort = sort.substring(1);
//            direction = "DESC";
//        } else {
//            direction = "ASC";
//        }
//        Sort.Direction directionByEnum = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC);
//        return Sort.by(directionByEnum, sort, idFieldName);
//    }

    public static int getPagesCount(int size, int pageSize) {
        return size / pageSize + (size % pageSize != 0 ? 1 : 0);
    }
}
