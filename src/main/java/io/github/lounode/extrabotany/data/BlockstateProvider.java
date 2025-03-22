package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.common.block.PedestalBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;
import static net.minecraft.data.models.model.TextureMapping.getBlockTexture;

public class BlockstateProvider extends vazkii.botania.data.BlockstateProvider {
    public BlockstateProvider(PackOutput packOutput) {
        super(packOutput);
    }
    @NotNull
    @Override
    public String getName() {
        return "ExtraBotany Blockstates and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> remainingBlocks = BuiltInRegistries.BLOCK.stream()
                .filter(b -> ExtraBotany.MODID.equals(BuiltInRegistries.BLOCK.getKey(b).getNamespace()))
                .collect(Collectors.toSet());

        var pedestalTemplate = new ModelTemplate(Optional.of(prefix("block/shapes/pedestal")), Optional.empty(),
                TextureSlot.TOP, TextureSlot.UP, TextureSlot.DOWN, TextureSlot.SIDE, TextureSlot.PARTICLE);
        takeAll(remainingBlocks, b -> b instanceof PedestalBlock).forEach(b -> singleVariantBlockState(b,
                pedestalTemplate.create(b, new TextureMapping()
                        .put(TextureSlot.TOP, getBlockTexture(b, "_top"))
                        .put(TextureSlot.UP, getBlockTexture(b, "_up"))
                        .put(TextureSlot.DOWN, getBlockTexture(b, "_down"))
                        .put(TextureSlot.SIDE, getBlockTexture(b, "_side"))
                        .put(TextureSlot.PARTICLE, getBlockTexture(b, "_down")), this.modelOutput))
        );



        remainingBlocks.forEach(this::cubeAllNoRemove);
    }
}
