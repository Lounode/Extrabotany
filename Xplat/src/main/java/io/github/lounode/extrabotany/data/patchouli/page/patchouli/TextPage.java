package io.github.lounode.extrabotany.data.patchouli.page.patchouli;

import com.demonwav.mcdev.annotations.Translatable;
import io.github.lounode.extrabotany.data.patchouli.page.AbstractPage;
import net.minecraft.resources.ResourceLocation;

public class TextPage extends AbstractPage {

    public TextPage(@Translatable String text) {
        object.addProperty("text", text);
    }

    public TextPage withTitle(String title) {
        object.addProperty("title", title);
        return this;
    }

    @Override
    public ResourceLocation getType() {
        return ResourceLocation.tryParse("patchouli:text");
    }
}
