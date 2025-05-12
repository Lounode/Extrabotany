package io.github.lounode.extrabotany.common.item.relic.void_archives.variants;

import io.github.lounode.extrabotany.api.item.VoidArchivesVariant;

public class InfiniteWine implements VoidArchivesVariant {

    public static InfiniteWine INSTANCE = new InfiniteWine();

    private static final String ID = "infinite_wine";

    @Override
    public String getId() {
        return ID;
    }
}
