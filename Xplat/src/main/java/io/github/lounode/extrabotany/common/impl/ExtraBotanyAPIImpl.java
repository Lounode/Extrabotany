package io.github.lounode.extrabotany.common.impl;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import io.github.lounode.extrabotany.api.item.equipment.bauble.CoreOfTheVoidVariant;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExtraBotanyAPIImpl implements ExtraBotanyAPI {
    private final Map<String, CoreOfTheVoidVariant> covVariants = new LinkedHashMap<>();

    @Override
    public void registerCOVVariant(CoreOfTheVoidVariant variant) {
        this.covVariants.put(variant.getId(), variant);
    }

    @Override
    public Map<String, CoreOfTheVoidVariant> getCOVVariants() {
        return this.covVariants;
    }
}
