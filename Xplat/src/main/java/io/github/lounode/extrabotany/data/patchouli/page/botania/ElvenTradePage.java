package io.github.lounode.extrabotany.data.patchouli.page.botania;

import com.demonwav.mcdev.annotations.Translatable;
import io.github.lounode.extrabotany.data.patchouli.page.AbstractPage;
import net.minecraft.resources.ResourceLocation;

public class ElvenTradePage extends AbstractPage<ElvenTradePage> {

    public ElvenTradePage(String recipe) {
        object.addProperty("recipes", recipe);
    }

    public ElvenTradePage withTitle(@Translatable String title) {
        object.addProperty("heading", title);
        return this;
    }

    public ElvenTradePage withText(@Translatable String text) {
        object.addProperty("text", text);
        return this;
    }

    @Override
    public ResourceLocation getType() {
        return ResourceLocation.tryParse("botania:elven_trade");
    }
}
