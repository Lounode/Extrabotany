package io.github.lounode.extrabotany.data.patchouli.page.patchouli;

import io.github.lounode.extrabotany.data.patchouli.page.AbstractPage;
import net.minecraft.resources.ResourceLocation;

public class EmptyPage extends AbstractPage<EmptyPage> {

    public EmptyPage drawFiller(boolean drawFiller) {
        object.addProperty("draw_filler", drawFiller);
        return this;
    }

    @Override
    public ResourceLocation getType() {
        return new ResourceLocation("patchouli:crafting");
    }
}
