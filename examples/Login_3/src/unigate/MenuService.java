package src.unigate;

import java.util.List;

/**
 * Сервис меню
 */
public interface MenuService {
//    List<MenuItemInfo> getAll();
//    List<MenuItemInfo> getMenu();
    List<MenuItemInfo> getMenu(User user);
}
