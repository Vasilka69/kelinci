package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemInfo {

    private String id;

    private String name;

    private String link;

    private List<MenuItemInfo> childrenItems;

    private ZonedDateTime updateDate;

}
