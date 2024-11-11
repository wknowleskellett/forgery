package dev.williamknowleskellett.forgery;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ForgeryRecipesProvider extends FabricRecipesProvider {

    public ForgeryRecipesProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> consumer) {
        consumer.accept(new RecipeJsonProvider() {
            public void serialize(JsonObject json) {
            }

            public RecipeSerializer<?> getSerializer() {
                return ForgeryRecipe.RECIPE_SERIALIZER;
            }

            public Identifier getRecipeId() {
                return new Identifier(Forgery.MOD_ID, "forge");
            }

            @Nullable
            public JsonObject toAdvancementJson() {
                return null;
            }

            public Identifier getAdvancementId() {
                return new Identifier("");
            }
        });
        ShapelessRecipeJsonFactory.create(ForgeryItems.FORGERY, 1)
                .input(Items.RED_DYE)
                .input(Items.ORANGE_DYE)
                .input(Items.YELLOW_DYE)
                .input(Items.WHITE_DYE)
                .input(Items.PAPER)
                .input(Items.GREEN_DYE)
                .input(Items.BLACK_DYE)
                .input(Items.PURPLE_DYE)
                .input(Items.BLUE_DYE)
                .criterion("has_paper", conditionsFromItem(Items.PAPER)).offerTo(consumer);
    }
}
