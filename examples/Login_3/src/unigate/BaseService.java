package src.unigate;


import java.io.Serializable;

/**
 * Интерфейс сервиса базовыми методами.
 * @param <T> класс сущности
 * @param <K> класс идентификатора сущности
 */
public interface BaseService<T extends Entity<K>, K extends Serializable> {
//
//    /**
//     * Создание сущности
//     * @param entity сужность
//     * @return Созданная сущность
//     */
//    T add(T entity);
//
//    /**
//     * Обновление сущности
//     * @param entity сущность
//     * @return Обновленная сущность
//     */
//    T update(T entity);
//
//    /**
//     * Удаление сущности по идентификатору
//     * @param key идентификатор сущности
//     */
//    void remove(K key);
//
//    /**
//     * Получение списка сущностей
//     * @return Список сущностей
//     */
//    List<T> findAll();
//
//    /**
//     * Получение сущности по идентификатору
//     * @param key идентификатор сущности
//     * @return Сущность
//     */
//    T findByKey(K key);
//
//    /**
//     * Проверка наличия сущности в хранилище
//     *
//     * @param key идентификатор сущности
//     * @return true - сущность найдена, false - сущность не найдена
//     */
//    boolean exists(K key);
}

