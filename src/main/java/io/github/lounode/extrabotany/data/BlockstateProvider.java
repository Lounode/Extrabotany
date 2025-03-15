package io.github.lounode.extrabotany.data;

import com.google.gson.JsonElement;
import io.github.lounode.extrabotany.ExtraBotany;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import vazkii.botania.api.BotaniaAPI;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;

import static net.minecraft.data.models.model.ModelLocationUtils.getModelLocation;
import static net.minecraft.data.models.model.TextureMapping.getBlockTexture;

public class BlockstateProvider implements DataProvider {
    protected final PackOutput packOutput;

    protected final List<BlockStateGenerator> blockstates = new ArrayList<>();

    protected final Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();
    protected final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput = models::put;

    public BlockstateProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected Logger getLogger() {
        return BotaniaAPI.LOGGER;
    }

    @NotNull
    @Override
    public String getName() {
        return "ExtraBotany Blockstates and Models";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        try {
            registerStatesAndModels();
        } catch (Exception e) {
            getLogger().error("Error registering states and models", e);
        }

        PackOutput.PathProvider blockstatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        PackOutput.PathProvider modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        List<CompletableFuture<?>> output = new ArrayList<>();

        for (BlockStateGenerator state : blockstates) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            Path path = blockstatePathProvider.json(id);
            output.add(DataProvider.saveStable(cache, state.get(), path));
        }

        for (Map.Entry<ResourceLocation, Supplier<JsonElement>> e : models.entrySet()) {
            ResourceLocation modelId = e.getKey();
            Path path = modelPathProvider.json(modelId);
            output.add(DataProvider.saveStable(cache, e.getValue().get(), path));
        }
        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }

    protected void registerStatesAndModels() {
        Set<Block> remainingBlocks = BuiltInRegistries.BLOCK.stream()
                .filter(b -> ExtraBotany.MODID.equals(BuiltInRegistries.BLOCK.getKey(b).getNamespace()))
                .collect(Collectors.toSet());
        // Manually written blockstate + models
        remainingBlocks.forEach(this::cubeAllNoRemove);
    }

    protected void cubeAllNoRemove(Block block) {
        cubeAll(new HashSet<>(), block);
    }

    protected void cubeAll(Set<Block> blocks, Block block) {
        ResourceLocation texture = getBlockTexture(block);
        cubeAllWithVariants(blocks, block, new ResourceLocation[] { texture });
    }

    protected void cubeAllWithVariants(Set<Block> blocks, Block block, ResourceLocation[] textures) {
        var weights = new Integer[textures.length];
        Arrays.fill(weights, 1);
        cubeAllWithVariants(blocks, block, textures, weights);
    }

    protected void cubeAllWithVariants(Set<Block> blocks, Block block, ResourceLocation[] textures, Integer[] weights) {
        int length = textures.length;
        if (length != weights.length) {
            throw new IllegalArgumentException("Arrays must have equal length");
        }
        ResourceLocation[] models = new ResourceLocation[length];
        for (int i = 0; i < length; i++) {
            String suffix = i == 0 ? "" : "_" + i;
            ResourceLocation modelId = getModelLocation(block, suffix);
            models[i] = ModelTemplates.CUBE_ALL.create(modelId, TextureMapping.cube(textures[i]), this.modelOutput);
        }
        cubeAllWithModels(blocks, block, models, weights);
    }

    protected void cubeAllWithModels(Set<Block> blocks, Block block, ResourceLocation[] models, Integer[] weights) {
        int length = models.length;
        if (length != weights.length) {
            throw new IllegalArgumentException("Arrays must have equal length");
        }
        var indices = IntStream.range(0, length).boxed();
        this.blockstates.add(MultiVariantGenerator.multiVariant(block, indices.map(i -> maybeWeight(weights[i], Variant.variant().with(VariantProperties.MODEL, models[i]))
        ).toArray(Variant[]::new)));
        blocks.remove(block);
    }

    protected Variant maybeWeight(int weight, Variant variant) {
        return withMaybe(VariantProperties.WEIGHT, weight, weight != 1, variant);
    }

    protected <T> Variant withMaybe(VariantProperty<T> property, T value, boolean shouldAdd, Variant variant) {
        if (shouldAdd) {
            variant.with(property, value);
        }
        return variant;
    }
}
