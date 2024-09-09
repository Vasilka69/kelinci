package src.unigate;

import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class EntityLockerImpl<T> implements EntityLocker<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ConcurrentReferenceHashMap.ReferenceType DEFAULT_REFERENCE_TYPE =
            ConcurrentReferenceHashMap.ReferenceType.WEAK;

    private final ConcurrentMap<T, ReentrantLock> map;

    public EntityLockerImpl() {
        this.map = new ConcurrentReferenceHashMap<>(
                DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL, DEFAULT_REFERENCE_TYPE
        );
    }

    public EntityLockerImpl(int concurrencyLevel, ConcurrentReferenceHashMap.ReferenceType referenceType) {
        this.map = new ConcurrentReferenceHashMap<>(
                DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, concurrencyLevel, referenceType
        );
    }

    public Lock getLock(T entityKey) {
        return this.map.computeIfAbsent(entityKey, (T key) -> new ReentrantLock());
    }

    public long size() {
        return this.map.size();
    }

    public void purgeUnreferenced() {
        ((ConcurrentReferenceHashMap<T, ReentrantLock>) this.map).purgeUnreferencedEntries();
    }
}
