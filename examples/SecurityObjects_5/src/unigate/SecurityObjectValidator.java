package src.unigate;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class SecurityObjectValidator {
    public static final String SECURITY_OBJECT = "Объект безопасности";

    private final SecurityObjectRepository securityObjectRepository;
    private final SecurityObjectTypeRepository securityObjectTypeRepository;

    public void validateBeforeAdd(final SecurityObject securityObject) {
        try {
            if (securityObject == null) {
                throw new RuntimeException(SECURITY_OBJECT);
            }

            validateNotExists(securityObject.getId());
            validateSecurityObjectParent(securityObject);
            validateSecurityObjectType(securityObject);
            validateRequiredAndLengthConditions(securityObject);
            validateUniqueName(securityObject);
        } catch (RuntimeException exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    private void validateNotExists(final String id) {
        if (!hasText(id)) {
            throw new RuntimeException(String.format("%s не может иметь пустой ключ", SECURITY_OBJECT));
        }

        if (securityObjectRepository.existsById(id)) {
            throw new RuntimeException(String.format("%s с кодом %s уже существует", SECURITY_OBJECT, id));
        }
    }

    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty());
    }

    private void validateSecurityObjectParent(final SecurityObject securityObject) {
        if (securityObject.getParentId() != null) {
            if (!securityObjectRepository.existsByParentId(securityObject.getParentId())) {
                throw new RuntimeException("Не найден объект безопасности, в котором содержится редактируемый объект безопасности!");
            }

            List<String> invalidParentIds = securityObjectRepository.findAllDescendantIdsByParentId(securityObject.getId());
            invalidParentIds.add(securityObject.getId());
            if (invalidParentIds.stream()
                    .anyMatch(id -> securityObject.getParentId().equals(id))) {
                throw new RuntimeException("Указан недопустимый объект безопасности в качестве того, в котором содержится редактируемый объект безопасности (цикличность)!");
            }
        }
    }

    private void validateSecurityObjectType(final SecurityObject securityObject) {
        if (securityObject.getSecurityObjectType() == null || securityObject.getSecurityObjectType().getId() == null) {
            throw new RuntimeException("Тип объекта безопасности должен быть указан!");
        }

        if (!securityObjectTypeRepository.existsById(securityObject.getSecurityObjectType().getId())) {
            throw new RuntimeException(String.format("Выбранный тип объекта безопасности с идентификатором %s не найден", securityObject.getId()));
        }
    }

    private void validateRequiredAndLengthConditions(final SecurityObject securityObject) {
        if (!hasText(securityObject.getId())) {
            throw new RuntimeException("ОШИБКА: идентификатор объекта безопасности обязательно должен быть указан");
        }
        if (securityObject.getId().length() > 255) {
            throw new RuntimeException("ОШИБКА: идентификатор объекта безопасности не может превышать 255 символов");
        }


        if (!hasText(securityObject.getName())) {
            throw new RuntimeException("ОШИБКА: объект безопасности должен содержать наименование");
        }

        if (securityObject.getName().length() > 1204) {
            throw new RuntimeException("ОШИБКА: наименование объекта безопасности не может превышать 255 символов");
        }
    }

    private void validateUniqueName(final SecurityObject securityObject) {
        if (securityObject.getName() == null) { return; }

        List<String> ids = securityObjectRepository.findIdByParentIdAndName(
                securityObject.getParentId() != null ? securityObject.getParentId() : "",
                securityObject.getName()
        );
        if (securityObject.getId() != null) {
            ids.remove(securityObject.getId());
        }
        if (!ids.isEmpty()) {
            throw new RuntimeException("ОШИБКА: объект безопасности должен содержать уникальное наименование");
        }
    }
}
