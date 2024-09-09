package src.unigate;

import java.time.ZonedDateTime;

public class UserLockCounter {

    private final String owner;

    private Integer count;

    private Long startDate;

//    private final UserRepository userRepository;

    private final Object lock = new Object();

//    public UserLockCounter(String owner, UserRepository userRepository) {
//        this.owner = owner;
//        this.count = 0;
//        this.startDate = System.currentTimeMillis();
//        this.userRepository = userRepository;
//    }

    public UserLockCounter(String owner/*, UserRepository userRepository*/) {
        this.owner = owner;
        this.count = 0;
        this.startDate = System.currentTimeMillis();
//        this.userRepository = userRepository;
    }

    public void resetCount() {
        synchronized (lock) {
            count = 0;
        }
    }

    public boolean checkLock(SettingPassword attemptsBeforeLock) {
        if (attemptsBeforeLock == null || !attemptsBeforeLock.isEnable()) {
            return false;
        }

        boolean locked = false;

        synchronized (lock) {
            long duration = (System.currentTimeMillis() - startDate) / 1000 / 60;
            if (attemptsBeforeLock.getDuration() != null && duration >= attemptsBeforeLock.getDuration()) {
                count = 0;
                startDate = System.currentTimeMillis();
            }

            count++;

            if (attemptsBeforeLock.getValue() != null && count > attemptsBeforeLock.getValue()) {
                if (owner != null) {
//                    User user = userRepository.findFirstByUsernameIgnoreCase(owner.toUpperCase());
                    User user = MessageServiceImpl.findFirstByUsernameIgnoreCase(owner.toUpperCase());
                    if (Boolean.TRUE.equals(user.getAccountNonLocked())) {
                        user.setAccountNonLocked(false);
                        user.setBlockReason(BlockReason.LOGIN);
                        user.setBlockComment(null);
                        user.setLastModified(ZonedDateTime.now());
//                        userRepository.save(user);
                        save(user);
                    }
                }

                locked = true;
            }
        }

        return locked;
    }

    private void save(User user) {
        System.out.printf("Пользователь %s сохранен в БД", user);
    }

}
