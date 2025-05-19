package io.github.lounode.extrabotany.common.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import io.github.lounode.extrabotany.api.item.CoreOfTheVoidVariant;
import io.github.lounode.extrabotany.api.item.VoidArchivesVariant;

public class ExtraBotanyAPIImpl implements ExtraBotanyAPI {
	private final Map<String, CoreOfTheVoidVariant> covVariants = new LinkedHashMap<>();
	private final Map<String, VoidArchivesVariant> voidArchivesVariants = new LinkedHashMap<>();

	@Override
	public void registerCOVVariant(CoreOfTheVoidVariant variant) {
		this.covVariants.put(variant.getId(), variant);
	}

	@Override
	public void registerVoidArchivesVariant(VoidArchivesVariant variant) {
		this.voidArchivesVariants.put(variant.getId(), variant);
	}

	@Override
	public Map<String, CoreOfTheVoidVariant> getCOVVariants() {
		return this.covVariants;
	}

	@Override
	public Map<String, VoidArchivesVariant> getVoidArchivesVariants() {
		return this.voidArchivesVariants;
	}
}
