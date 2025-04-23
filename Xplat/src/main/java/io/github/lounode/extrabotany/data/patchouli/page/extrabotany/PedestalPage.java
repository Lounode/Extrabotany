package io.github.lounode.extrabotany.data.patchouli.page.extrabotany;

import com.demonwav.mcdev.annotations.Translatable;
import io.github.lounode.extrabotany.data.patchouli.page.AbstractPage;
import net.minecraft.resources.ResourceLocation;

public class PedestalPage extends AbstractPage {

    public PedestalPage(String recipe) {
        object.addProperty("recipe", recipe);
    }

    public PedestalPage withTitle(@Translatable String title) {
        object.addProperty("heading", title);
        return this;
    }

    public PedestalPage withText(@Translatable String text) {
        object.addProperty("text", text);
        return this;
    }
    @Override
    public ResourceLocation getType() {
        return new ResourceLocation("extrabotany:pedestal_smash");
    }
}
