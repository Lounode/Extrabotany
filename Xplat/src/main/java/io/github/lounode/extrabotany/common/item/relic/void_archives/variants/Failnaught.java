package io.github.lounode.extrabotany.common.item.relic.void_archives.variants;

import io.github.lounode.extrabotany.api.item.VoidArchivesVariant;

public class Failnaught implements VoidArchivesVariant {

    public static Failnaught INSTANCE = new Failnaught();

    private static final String ID = "failnaught";

    @Override
    public String getId() {
        return ID;
    }
}
