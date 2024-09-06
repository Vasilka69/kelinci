package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

//    private final MenuItemRepository menuItemRepository;
    private final UserService userService;

//    @Override
//    public List<MenuItemInfo> getAll() {
//        List<MenuItem> items = menuItemRepository.findAll();
//
//        return mapMenuLevel(mapMenuItem(items, item -> true), null);
//    }
//
//    @Override
//    public List<MenuItemInfo> getMenu() {
//        return getMenu(SecurityUtils.getCurrentUser());
//    }

    @Override
    public List<MenuItemInfo> getMenu(User user) {
//        List<MenuItem> items = menuItemRepository.findAll();
        List<MenuItem> items = findAll();
        Predicate<MenuItem> filter = item -> user == null || item.getSecurityObjectId() == null || item.getAction() == null
                || userService.hasPermission(user, item.getSecurityObjectId(), item.getAction());

        return mapMenuLevel(mapMenuItem(items, filter), null);
    }

    private List<MenuItem> findAll() {
        return Arrays.asList(
                new MenuItem(
                    "id 1",
                    "parentId 1",
                    "name 1",
                    "link 1",
                    "securityObjectId 1",
                    "action 1",
                    1,
                    ZonedDateTime.now()
                ),
                new MenuItem(
                    "id 2",
                    "parentId 2",
                    "name 2",
                    "link 2",
                    "securityObjectId 2",
                    "action 2",
                    2,
                    ZonedDateTime.now()
                )
        );
    }

    private Map<String, List<MenuItem>> mapMenuItem(List<MenuItem> items, Predicate<MenuItem> filter) {
        return items.stream()
                .filter(filter)
                .collect(Collectors.toMap(
                        MenuItem::getParentId,
                        item -> {
                            ArrayList<MenuItem> result = new ArrayList<>();
                            result.add(item);
                            return result;
                        },
                        (item1, item2) -> {
                            item1.addAll(item2);
                            return item1;
                        }
                ));
    }

    private List<MenuItemInfo> mapMenuLevel(Map<String, List<MenuItem>> items, String parentId) {
        if (items.get(parentId) == null) {
            return Collections.emptyList();
        }

        return items.get(parentId).stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.nullsLast(Comparator.comparing(MenuItem::getOrd)))
                .map(item -> new MenuItemInfo(item.getId(), item.getName(), item.getLink(), mapMenuLevel(items, item.getId()), item.getUpdateDate()))
                .filter(item -> StringUtils.hasText(item.getLink()) || !CollectionUtils.isEmpty(item.getChildrenItems()))
                .collect(Collectors.toList());
    }
}
