package io.github.lounode.extrabotany.data.patchouli.page;

import com.google.gson.JsonObject;

public abstract class AbstractPage implements IPatchouliPage {
    protected JsonObject object = new JsonObject();

    @Override
    public JsonObject build() {
        object.addProperty("type", getType().toString());
        return object;
    }
}
