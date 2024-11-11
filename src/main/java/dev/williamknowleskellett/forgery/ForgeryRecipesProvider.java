package dev.williamknowleskellett.forgery;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ForgeryRecipesProvider extends FabricRecipeProvider {


    public ForgeryRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        exporter.accept(new RecipeJsonProvider() {
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
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ForgeryItems.FORGERY, 1)
                .input(Items.RED_DYE)
                .input(Items.ORANGE_DYE)
                .input(Items.YELLOW_DYE)
                .input(Items.WHITE_DYE)
                .input(Items.PAPER)
                .input(Items.GREEN_DYE)
                .input(Items.BLACK_DYE)
                .input(Items.PURPLE_DYE)
                .input(Items.BLUE_DYE)
                .criterion("has_paper", conditionsFromItem(Items.PAPER)).offerTo(exporter);
    }
}
