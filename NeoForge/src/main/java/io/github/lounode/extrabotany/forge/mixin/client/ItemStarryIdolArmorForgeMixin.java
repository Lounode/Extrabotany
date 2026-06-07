package io.github.lounode.extrabotany.forge.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import org.spongepowered.asm.mixin.Mixin;

import io.github.lounode.extrabotany.client.model.ArmorModels;
import io.github.lounode.extrabotany.common.item.equipment.armor.starry_idol.StarryIdolArmorItem;

import java.util.function.Consumer;

@Mixin(StarryIdolArmorItem.class)
public abstract class ItemStarryIdolArmorForgeMixin extends Item {

	private ItemStarryIdolArmorForgeMixin(Item.Properties props) {
		super(props);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
				var model = ArmorModels.get(stack);
				return model != null ? model : defaultModel;
			}
		});
	}
}
