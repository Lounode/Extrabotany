package io.github.lounode.extrabotany.common.item.material;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import java.util.Locale;

public class GildedPotatoItem extends Item {

	private static final String ADVANCEMENT_NAME = "ubisoft";

	public GildedPotatoItem(Properties properties) {
		super(properties);
	}

	//TODO onAnvilRepair
	public static void onAnvilRepair() {
		if (/*player.getLevel().isClientSide()*/ false) {
			return;
		}
		ItemStack stack = ItemStack.EMPTY;

		if (!stack.is(ExtraBotanyItems.gildedPotato)) {
			return;
		}

		if (ADVANCEMENT_NAME.equals(stack.getDisplayName().getString().toLowerCase(Locale.ROOT))) {
			//PlayerHelper.grantCriterion(serverPlayer, LibAdvancementNames.POTATO_SERVER, "code_triggered");
		}
	}
}
