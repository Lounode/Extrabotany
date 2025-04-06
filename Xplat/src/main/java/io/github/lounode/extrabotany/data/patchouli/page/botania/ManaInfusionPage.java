package io.github.lounode.extrabotany.data.patchouli.page.botania;

import com.demonwav.mcdev.annotations.Translatable;
import io.github.lounode.extrabotany.data.patchouli.page.AbstractPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.EatPage;
import net.minecraft.resources.ResourceLocation;

public class ManaInfusionPage extends AbstractPage {

    public ManaInfusionPage(String recipe) {
        object.addProperty("recipes", recipe);
    }

    public ManaInfusionPage withTitle(@Translatable String title) {
        object.addProperty("heading", title);
        return this;
    }

    public ManaInfusionPage withText(@Translatable String text) {
        object.addProperty("text", text);
        return this;
    }
    @Override
    public ResourceLocation getType() {
        return new ResourceLocation("botania:mana_infusion");
    }
}
