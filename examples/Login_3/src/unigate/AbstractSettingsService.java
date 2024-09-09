package src.unigate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractSettingsService<T extends Setting> implements SettingsService<T> {

    private final ConcurrentHashMap<String, T> cache = new ConcurrentHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

//    @Value("${ru.kamatech.unigate.security.settings.cache.enabled:true}")
//    private boolean cacheEnabled;
//
//    @PostConstruct
//    private void init() {
//        if (cacheEnabled) {
//            updateCache(getRepository().findAll());
//        }
//    }

//    @Value("${ru.kamatech.unigate.security.settings.cache.enabled:true}")
    private boolean cacheEnabled = true;

//    @PostConstruct
    public AbstractSettingsService() {
        if (cacheEnabled) {
            updateCache(getRepository().findAll());
        }
    }

//    @Override
//    public List<T> getAll() {
//        if (!cacheEnabled) {
//            return getRepository().findAll();
//        } else {
//            return new ArrayList<>(cache.values());
//        }
//    }

    public T get(String id) {
        if (!cacheEnabled) {
            return getRepository().findById(id)
                    .orElse(null);
        } else {
            readLock.lock();
            try {
                return cache.get(id);
            } finally {
                readLock.unlock();
            }
        }
    }

    public void updateCache(List<T> settings) {
        if (!cacheEnabled) {
            return;
        }

        writeLock.lock();
        try {
            if (settings != null) {
                settings.forEach(setting -> cache.put(setting.getId(), setting));
            }
        } finally {
            writeLock.unlock();
        }
    }

//    @Override
//    public boolean existsById(String id) {
//        if (!cacheEnabled) {
//            return getRepository().existsById(id);
//        } else {
//            readLock.lock();
//            try {
//                return cache.containsKey(id);
//            } finally {
//                readLock.unlock();
//            }
//        }
//    }

//    protected List<T> update(List<T> settings) {
//        return getRepository().saveAll(settings);
//    }

//    protected abstract String getObjectType();

//    protected abstract MessageService getMessageService();

    protected abstract JpaRepository<T, String> getRepository();

//    @Override
//    public List<T> save(List<T> settings) {
//        List<T> result = update(settings);
////        sendProtocolEvent(result);
//        if (cacheEnabled) {
//            updateCache(result);
//        }
//        return result;
//    }

//    private void sendProtocolEvent(List<T> settings) {
//        settings.stream()
//            .filter(s -> !s.equals(cache.get(s.getId())))
//            .forEach(s -> getMessageService().sendProtocolEvent(EDIT_EVENT, getObjectType(), s.getId(), s.getId()));
//    }
}
