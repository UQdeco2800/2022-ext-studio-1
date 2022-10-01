package com.deco2800.game.components.player.entity;

import com.deco2800.game.entities.configs.ItemConfigs;
import com.deco2800.game.files.FileLoader;

import java.util.*;
import java.util.function.Consumer;

public class Backpack implements Inventory {
    public static final Map<Integer, Item> ID_ITEM_MAP;
    public static final Map<String, Integer> NAME_ID_MAP;
    private Map<Integer, Integer> idNumMap;
    private Set<Integer> inStockItems;

    static {
        ItemConfigs itemConfigs = FileLoader.readClass(ItemConfigs.class, "configs/items.json");
        List<Item> itemList = itemConfigs.itemList;
        ID_ITEM_MAP = new HashMap<>();
        NAME_ID_MAP = new HashMap<>();
        itemList.stream().forEach((item) -> {
            int id = item.getId();
            String name = item.getName();
            ID_ITEM_MAP.put(id, item);
            NAME_ID_MAP.put(name, id);
        });
    }

    public Backpack() {
        idNumMap = new HashMap<>();
        inStockItems = new TreeSet<>();
        ID_ITEM_MAP.keySet().stream().forEach((id) -> {
            idNumMap.put(id, 0);
        });
    }


    @Override
    public boolean contains(int id) {
        return inStockItems.contains(id);
    }

    @Override
    public boolean contains(String itemName) {
        return contains(NAME_ID_MAP.get(itemName));
    }

    @Override
    public int count(int id) {
        Integer num = idNumMap.get(id);
        return num == null ? -1 : num;
    }

    @Override
    public int count(String itemName) {
        return count(NAME_ID_MAP.get(itemName));
    }

    @Override
    public boolean add(int id) {
        Item item = ID_ITEM_MAP.get(id);
        if (item == null) {
            return false;
        }

        if (item.getType() == Item.Type.CLUE_ITEM) {
            if (checkExist(id)) {
                return false;
            } else {
                idNumMap.put(id, idNumMap.get(id) + 1);
                inStockItems.add(id);
            }
        } else if (item.getType() == Item.Type.CONSUMABLE_ITEM) {
            int num = idNumMap.get(id);
            idNumMap.put(id, num + 1);
            if (idNumMap.get(id) == 1) {
                inStockItems.add(id);
            }
        } else if (item.getType() == Item.Type.EQUIPMENT_ITEM) {
            spliceEquipmentIfPossible(id);
        }
        return true;
    }

    @Override
    public boolean add(int id, int num) {
        Item item = ID_ITEM_MAP.get(id);
        if (item == null || num <= 0) {
            return false;
        }
        if (item.getType() != Item.Type.CONSUMABLE_ITEM) {
            throw new IllegalArgumentException("Only can add consumable items!");
        }
        Integer curNum = idNumMap.get(id);
        idNumMap.put(id, curNum + num);
        if (curNum == 0) {
            inStockItems.add(id);
        }
        return true;
    }

    /**
     * Piece together parts to form a complete outfit.
     * For example, after collecting a complete set of diving goggles and wetsuit oxygen cylinders
     * ,a complete diving equipment is formed.
     * @param id item id
     */
    private void spliceEquipmentIfPossible(int id) {
        if (checkNeedSplice(id)) {

        } else {
            idNumMap.put(id, idNumMap.get(id) + 1);
            if (idNumMap.get(id) == 1) {
                inStockItems.add(id);
            }
        }
    }

    /**
     * Test whether it is a componentable equipment part
     * @param id item id
     * @return true if need splice
     */
    private boolean checkNeedSplice(int id) {
        return false;
    }


    /**
     * Check whether the item already exists
     * @param id item id
     * @return true if the item is in stock
     */
    private boolean checkExist(int id) {
        return idNumMap.get(id) > 0;
    }

    /**
     * Remove an item with this id
     * @param id item id
     * @return true if remove an item of this id successfully
     */
    @Override
    public boolean remove(int id) {
        if (!contains(id)) {
            return false;
        }
        Integer num = idNumMap.get(id);
        idNumMap.put(id, num - 1);
        if (num == 1) {
            inStockItems.remove(id);
        }
        return true;
    }

    @Override
    public boolean add(String itemName) {
        Integer id = NAME_ID_MAP.get(itemName);
        if (id == null) {
            return false;
        }
        return add(id);
    }

    @Override
    public boolean add(String itemName, int num) {
        Integer id = NAME_ID_MAP.get(itemName);
        if (id == null) {
            return false;
        }
        return add(id, num);
    }

    @Override
    public boolean remove(String itemName) {
        return NAME_ID_MAP.containsKey(itemName)
                && remove(NAME_ID_MAP.get(itemName));
    }

    @Override
    public boolean remove(int id, int num) {
        Integer beforeNum = idNumMap.get(id);
        if (!contains(id) || beforeNum < num) {
            return false;
        }
        idNumMap.put(id, beforeNum - num);
        if (beforeNum - num == 0) {
            inStockItems.remove(id);
        }
        return true;
    }

    @Override
    public boolean remove(String itemName, int num) {
        return NAME_ID_MAP.containsKey(itemName)
                && remove(NAME_ID_MAP.get(itemName), num);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|||||||||||||||Inventory|||||||||||||||");
        stringBuilder.append(System.lineSeparator());
        inStockItems.stream().forEach((id) -> {
            Integer num = idNumMap.get(id);
            Item item = ID_ITEM_MAP.get(id);
            stringBuilder.append("item: " + item.getName() + " num: " + num + System.lineSeparator());
        });

        return stringBuilder.toString();
    }
}
