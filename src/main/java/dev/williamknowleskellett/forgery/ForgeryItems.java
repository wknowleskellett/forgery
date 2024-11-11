package dev.williamknowleskellett.forgery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class ForgeryItems {
    public static Item register(Item item, String path) {
        return Registry.register(Registries.ITEM, new Identifier(Forgery.MOD_ID, path), item);
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                .register((itemGroup) -> itemGroup.add(FORGERY));
    }

    public static final Item FORGERY = register(
            new Item(new Item.Settings()),
            "forgery"
    );
}
