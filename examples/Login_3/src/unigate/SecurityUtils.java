package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

//    public static HttpServletRequest getCurrentRequest() {
//        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (requestAttributes != null) {
//            return requestAttributes.getRequest();
//        }
//        return null;
//    }
//
//    public static Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    public static SecurityUser getAuthenticationPrincipal() {
//        var authentication = getAuthentication();
//        return (authentication != null && authentication.getPrincipal() instanceof SecurityUser)
//                ? (SecurityUser) authentication.getPrincipal()
//                : null;
//    }
//
//    public static AuthUserDetails getAuthenticationDetails() {
//        var authentication = getAuthentication();
//
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            var authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
//            return (AuthUserDetails) authenticationToken.getDetails();
//        }
//
//        return null;
//    }
//
//    /**
//     * Получение текущего пользователя
//     * @return текущий пользователь, либо null, если пользователь не авторизован
//     */
//    public static User getCurrentUser() {
//        var authenticationPrincipal = getAuthenticationPrincipal();
//        return (authenticationPrincipal != null)
//                ? authenticationPrincipal.getUserData()
//                : null;
//    }
//
//    public static String getCurrentUserUsername() {
//        return Optional.ofNullable(getCurrentUser()).map(User::getUsername).orElse(null);
//    }
//
//    public static String getCurrentUserToken() {
//        var authenticationDetails = getAuthenticationDetails();
//
//        return (authenticationDetails != null)
//                ? authenticationDetails.getToken()
//                : null;
//    }
//
//    public static String getCurrentUserIp() {
//        var authenticationDetails = getAuthenticationDetails();
//
//        return (authenticationDetails != null)
//                ? authenticationDetails.getIpAddress()
//                : null;
//    }
//
//    /**
//     * Получение системного пользователя
//     * @return текущий системный пользователь, либо null, если пользователь не авторизован или не является системным
//     */
//    public static SysUser getSysUser() {
//        var user = getCurrentUser();
//        if (isSysUser(user)) {
//            return (SysUser) user;
//        } else {
//            return null;
//        }
//    }

    public static boolean isSysUser(User user) {
        return user instanceof SysUser;
    }

    public static boolean isGeneralUser(User user) {
        return user instanceof IndividualPerson;
    }

//    /**
//     * Проверка, что текущий системный пользователь является администратором<br>
//     * Отличается от {@link SecurityUtils#isSysAdmin()} тем, что при отсутствии системного пользователя с правами администратора выбрасывается {@link AccessDeniedException}
//     * @throws AccessDeniedException при отсутствии системного пользователя с правами администратора
//     */
//    @Deprecated
//    public static void requiredSysAdmin() {
//        if (!SecurityUtils.isSysAdmin()) {
//            throw new AccessDeniedException("Доступ запрещен! Метод доступен только для администратора");
//        }
//    }
//
//    /**
//     * Проверка, что текущий системный пользователь является администратором
//     */
//    public static boolean isSysAdmin() {
//        return isUserAdmin(getSysUser());
//    }
//
//    /**
//     * Проверка, что пользователь является администратором
//     * @param user пользователь
//     */
//    public static boolean isUserAdmin(User user) {
//        return user != null
//                && (
//                        user.getId().equals(UserConstants.SECURITY_ADMIN.getId())
//                                || (!CollectionUtils.isEmpty(user.getRoles()) && user.getRoles().contains(RoleConstants.SECURITY_ADMIN.getId()))
//        );
//    }
}
