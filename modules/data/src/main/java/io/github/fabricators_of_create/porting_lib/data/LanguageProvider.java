package io.github.fabricators_of_create.porting_lib.data;

import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class LanguageProvider implements DataProvider {

	String TRANSLATION_PREFIX = "dimension";

	private final Map<String, String> data = new TreeMap<>();
	private final PackOutput output;
	private final String modid;
	private final String locale;

	public LanguageProvider(PackOutput output, String modid, String locale) {
		this.output = output;
		this.modid = modid;
		this.locale = locale;
	}

	protected abstract void addTranslations();

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		addTranslations();

		if (!data.isEmpty())
			return save(cache, this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.modid).resolve("lang").resolve(this.locale + ".json"));

		return CompletableFuture.allOf();
	}

	@Override
	public String getName() {
		return "Languages: " + locale + " for mod: " + modid;
	}

	private CompletableFuture<?> save(CachedOutput cache, Path target) {
		// TODO: DataProvider.saveStable handles the caching and hashing already, but creating the JSON Object this way seems unreliable. -C
		JsonObject json = new JsonObject();
		this.data.forEach(json::addProperty);

		return DataProvider.saveStable(cache, json, target);
	}

	public void addBlock(Supplier<? extends Block> key, String name) {
		add(key.get(), name);
	}

	public void add(Block key, String name) {
		add(key.getDescriptionId(), name);
	}

	public void addItem(Supplier<? extends Item> key, String name) {
		add(key.get(), name);
	}

	public void add(Item key, String name) {
		add(key.getDescriptionId(), name);
	}

	public void addItemStack(Supplier<ItemStack> key, String name) {
		add(key.get(), name);
	}

	public void add(ItemStack key, String name) {
		add(key.getDescriptionId(), name);
	}

    /*
    public void addBiome(Supplier<? extends Biome> key, String name) {
        add(key.get(), name);
    }

    public void add(Biome key, String name) {
        add(key.getTranslationKey(), name);
    }
    */

	public void addEffect(Supplier<? extends MobEffect> key, String name) {
		add(key.get(), name);
	}

	public void add(MobEffect key, String name) {
		add(key.getDescriptionId(), name);
	}

	public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
		add(key.get(), name);
	}

	public void add(EntityType<?> key, String name) {
		add(key.getDescriptionId(), name);
	}

	public void addTag(Supplier<? extends TagKey<?>> key, String name) {
		add(key.get(), name);
	}

	public void add(TagKey<?> tagKey, String name) {
		add(getTagTranslationKey(tagKey), name);
	}

	public void add(String key, String value) {
		if (data.put(key, value) != null)
			throw new IllegalStateException("Duplicate translation key " + key);
	}

	public void addDimension(ResourceKey<Level> dimension, String value) {
		add(dimension.location().toLanguageKey(TRANSLATION_PREFIX), value);
	}

	public static String getTagTranslationKey(TagKey<?> tagKey) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("tag.");

		ResourceLocation registryIdentifier = tagKey.registry().location();
		ResourceLocation tagIdentifier = tagKey.location();

		stringBuilder.append(registryIdentifier.toShortLanguageKey().replace("/", "."))
				.append(".")
				.append(tagIdentifier.getNamespace())
				.append(".")
				.append(tagIdentifier.getPath().replace("/", "."));

		return stringBuilder.toString();
	}
}
