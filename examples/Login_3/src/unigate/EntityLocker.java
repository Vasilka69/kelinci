package src.unigate;

import java.util.concurrent.locks.Lock;

public interface EntityLocker<T> {

    Lock getLock(T entityKey);

    long size();

    void purgeUnreferenced();
}
