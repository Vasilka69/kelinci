package src.unigate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

//@Slf4j
//@Scope("singleton")
public class UsersLocker {

//    @Autowired
    private EntityLocker<String> entityLocker = new EntityLockerImpl<>();

//    @Autowired
//    private UserRepository userRepository;

    private final Map<String, UserLockCounter> counterCache = new ConcurrentHashMap<>();

    public UserLockCounter getLockCounter(String username) {
        Lock lock = entityLocker.getLock(username);
        lock.lock();
        try {
//            return counterCache.computeIfAbsent(username, owner -> new UserLockCounter(owner, userRepository));
            return counterCache.computeIfAbsent(username, owner -> new UserLockCounter(owner));
        } finally {
            lock.unlock();
        }
    }
}
