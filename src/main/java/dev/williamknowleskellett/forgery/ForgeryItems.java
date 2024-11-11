package dev.williamknowleskellett.forgery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ForgeryItems {
    public static Item register(Item item, String path) {
        return Registry.register(Registry.ITEM, new Identifier(Forgery.MOD_ID, path), item);
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
    }

    public static final Item FORGERY = register(
            new Item(new Item.Settings().group(ItemGroup.MISC)),
            "forgery"
    );
}
