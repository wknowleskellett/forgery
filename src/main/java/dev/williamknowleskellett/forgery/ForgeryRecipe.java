package dev.williamknowleskellett.forgery;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ForgeryRecipe extends SpecialCraftingRecipe {
    public static final SpecialRecipeSerializer<ForgeryRecipe> RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Forgery.MOD_ID, "crafting_special_forgery"), new SpecialRecipeSerializer<>(ForgeryRecipe::new));

    public ForgeryRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack originalItemIngredient = ItemStack.EMPTY;
        ItemStack forgeryIngredientStack = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack currentStack = craftingInventory.getStack(i);
            if (!currentStack.isEmpty()) {
                if (currentStack.isOf(ForgeryItems.FORGERY) && forgeryIngredientStack.isEmpty()) {
                    NbtCompound nbt = currentStack.getNbt();
                    if (nbt == null || !nbt.contains("forged", 10)) {
                        forgeryIngredientStack = currentStack;
                        continue;
                    }
                }

                if (originalItemIngredient.isEmpty()) {
                    originalItemIngredient = currentStack;
                } else {
                    return false;
                }
            }
        }

        return !originalItemIngredient.isEmpty() && !forgeryIngredientStack.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack forgeryResultStack = ItemStack.EMPTY;
        ItemStack originalItemIngredient = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack currentStack = inventory.getStack(i);
            if (!currentStack.isEmpty()) {
                if (currentStack.isOf(ForgeryItems.FORGERY) && forgeryResultStack.isEmpty()) {
                    NbtCompound nbt = currentStack.getNbt();
                    if (nbt == null || !nbt.contains("forged", 10)) {
                        forgeryResultStack = currentStack.copy();
                        forgeryResultStack.setCount(1);
                        continue;
                    }
                }

                if (originalItemIngredient.isEmpty()) {
                    originalItemIngredient = currentStack.copy();
                    originalItemIngredient.setCount(1);
                }
            }
        }

        if (originalItemIngredient.isOf(ForgeryItems.FORGERY)) {
            NbtCompound nbt = originalItemIngredient.getNbt();
            if (nbt != null && nbt.contains("forged", 10)) {
                NbtCompound nbtCompound = nbt.getCompound("forged");
                originalItemIngredient = ItemStack.fromNbt(nbtCompound);
            }
        }

        if (forgeryResultStack.isEmpty() || originalItemIngredient.isEmpty()) {
            return forgeryResultStack;
        }

        NbtCompound forgedNbt = originalItemIngredient.writeNbt(new NbtCompound());
        forgeryResultStack.setSubNbt("forged", forgedNbt);
        return forgeryResultStack;
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return RECIPE_SERIALIZER;
    }

    public DefaultedList<ItemStack> getRemainder(CraftingInventory craftingInventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(craftingInventory.size(), ItemStack.EMPTY);

        ItemStack forgeryResultStack = ItemStack.EMPTY;

        for (int i = 0; i < defaultedList.size(); i++) {
            ItemStack currentStack = craftingInventory.getStack(i);
            if (!currentStack.isEmpty()) {
                if (currentStack.isOf(ForgeryItems.FORGERY) && forgeryResultStack.isEmpty()) {
                    NbtCompound nbt = currentStack.getNbt();
                    if (nbt == null || !nbt.contains("forged", 10)) {
                        forgeryResultStack = currentStack.copy();
                        continue;
                    }
                }

                ItemStack remainderStack = currentStack.copy();
                remainderStack.setCount(1);
                defaultedList.set(i, remainderStack);
            }
        }

        return defaultedList;
    }
}
