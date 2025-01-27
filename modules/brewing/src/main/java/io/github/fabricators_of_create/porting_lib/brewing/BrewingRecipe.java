package io.github.fabricators_of_create.porting_lib.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BrewingRecipe implements IBrewingRecipe {
	private final Ingredient input;
	private final Ingredient ingredient;
	private final ItemStack output;

	public BrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		this.input = input;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean isInput(ItemStack stack) {
		return this.input.test(stack);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return isInput(input) && isIngredient(ingredient) ? getOutput().copy() : ItemStack.EMPTY;
	}

	public Ingredient getInput() {
		return input;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public ItemStack getOutput() {
		return output;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return this.ingredient.test(ingredient);
	}
}
