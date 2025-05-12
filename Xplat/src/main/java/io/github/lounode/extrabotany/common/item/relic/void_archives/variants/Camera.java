package io.github.lounode.extrabotany.common.item.relic.void_archives.variants;

import io.github.lounode.extrabotany.api.item.VoidArchivesVariant;

public class Camera implements VoidArchivesVariant {

    public static Camera INSTANCE = new Camera();

    private static final String ID = "camera";

    @Override
    public String getId() {
        return ID;
    }
}
