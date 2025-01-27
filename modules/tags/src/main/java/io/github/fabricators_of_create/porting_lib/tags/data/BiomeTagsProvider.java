package io.github.fabricators_of_create.porting_lib.tags.data;

import java.util.concurrent.CompletableFuture;

import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

public final class BiomeTagsProvider extends FabricTagProvider<Biome> {
	public BiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.BIOME, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(Tags.Biomes.NO_DEFAULT_MONSTERS).add(Biomes.MUSHROOM_FIELDS).add(Biomes.DEEP_DARK);
		tag(Tags.Biomes.HIDDEN_FROM_LOCATOR_SELECTION); // Create tag file for visibility

		tag(Tags.Biomes.IS_VOID).add(Biomes.THE_VOID);

		tag(Tags.Biomes.IS_END).forceAddTag(BiomeTags.IS_END);
		tag(Tags.Biomes.IS_NETHER).forceAddTag(BiomeTags.IS_NETHER);
		tag(Tags.Biomes.IS_OVERWORLD).forceAddTag(BiomeTags.IS_OVERWORLD);

		tag(Tags.Biomes.IS_HOT_OVERWORLD)
				.add(Biomes.SWAMP)
				.add(Biomes.MANGROVE_SWAMP)
				.add(Biomes.JUNGLE)
				.add(Biomes.BAMBOO_JUNGLE)
				.add(Biomes.SPARSE_JUNGLE)
				.add(Biomes.DESERT)
				.add(Biomes.ERODED_BADLANDS)
				.add(Biomes.SAVANNA)
				.add(Biomes.SAVANNA_PLATEAU)
				.add(Biomes.WINDSWEPT_SAVANNA)
				.add(Biomes.STONY_PEAKS)
				.add(Biomes.WARM_OCEAN);
		tag(Tags.Biomes.IS_HOT_NETHER)
				.add(Biomes.NETHER_WASTES)
				.add(Biomes.CRIMSON_FOREST)
				.add(Biomes.WARPED_FOREST)
				.add(Biomes.SOUL_SAND_VALLEY)
				.add(Biomes.BASALT_DELTAS);
		tag(Tags.Biomes.IS_HOT_END);
		tag(Tags.Biomes.IS_HOT).forceAddTag(Tags.Biomes.IS_HOT_OVERWORLD).forceAddTag(Tags.Biomes.IS_HOT_NETHER).addOptionalTag(Tags.Biomes.IS_HOT_END.location());

		tag(Tags.Biomes.IS_COLD_OVERWORLD)
				.add(Biomes.TAIGA)
				.add(Biomes.OLD_GROWTH_PINE_TAIGA)
				.add(Biomes.SNOWY_PLAINS)
				.add(Biomes.ICE_SPIKES)
				.add(Biomes.GROVE)
				.add(Biomes.SNOWY_SLOPES)
				.add(Biomes.JAGGED_PEAKS)
				.add(Biomes.FROZEN_PEAKS)
				.add(Biomes.SNOWY_BEACH)
				.add(Biomes.SNOWY_TAIGA)
				.add(Biomes.FROZEN_RIVER)
				.add(Biomes.COLD_OCEAN)
				.add(Biomes.FROZEN_OCEAN)
				.add(Biomes.DEEP_COLD_OCEAN)
				.add(Biomes.DEEP_FROZEN_OCEAN);
		tag(Tags.Biomes.IS_COLD_NETHER);
		tag(Tags.Biomes.IS_COLD_END)
				.add(Biomes.THE_END)
				.add(Biomes.SMALL_END_ISLANDS)
				.add(Biomes.END_MIDLANDS)
				.add(Biomes.END_HIGHLANDS)
				.add(Biomes.END_BARRENS);
		tag(Tags.Biomes.IS_COLD).forceAddTag(Tags.Biomes.IS_COLD_OVERWORLD).addOptionalTag(Tags.Biomes.IS_COLD_NETHER.location()).forceAddTag(Tags.Biomes.IS_COLD_END);

		tag(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD)
				.add(Biomes.WOODED_BADLANDS)
				.add(Biomes.SAVANNA)
				.add(Biomes.SAVANNA_PLATEAU)
				.add(Biomes.WINDSWEPT_SAVANNA)
				.add(Biomes.WINDSWEPT_FOREST)
				.add(Biomes.WINDSWEPT_HILLS)
				.add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
				.add(Biomes.SNOWY_SLOPES)
				.add(Biomes.JAGGED_PEAKS)
				.add(Biomes.FROZEN_PEAKS);
		tag(Tags.Biomes.IS_SPARSE_VEGETATION_NETHER);
		tag(Tags.Biomes.IS_SPARSE_VEGETATION_END);
		tag(Tags.Biomes.IS_SPARSE_VEGETATION).forceAddTag(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD).addOptionalTag(Tags.Biomes.IS_SPARSE_VEGETATION_NETHER.location()).addOptionalTag(Tags.Biomes.IS_SPARSE_VEGETATION_END.location());

		tag(Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD)
				.add(Biomes.DARK_FOREST)
				.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
				.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
				.add(Biomes.JUNGLE)
				.add(Biomes.BAMBOO_JUNGLE)
				.add(Biomes.MANGROVE_SWAMP);
		tag(Tags.Biomes.IS_DENSE_VEGETATION_NETHER);
		tag(Tags.Biomes.IS_DENSE_VEGETATION_END);
		tag(Tags.Biomes.IS_DENSE_VEGETATION).forceAddTag(Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD).addOptionalTag(Tags.Biomes.IS_DENSE_VEGETATION_NETHER.location()).addOptionalTag(Tags.Biomes.IS_DENSE_VEGETATION_END.location());

		tag(Tags.Biomes.IS_WET_OVERWORLD)
				.add(Biomes.SWAMP)
				.add(Biomes.MANGROVE_SWAMP)
				.add(Biomes.JUNGLE)
				.add(Biomes.BAMBOO_JUNGLE)
				.add(Biomes.SPARSE_JUNGLE)
				.add(Biomes.BEACH)
				.add(Biomes.LUSH_CAVES)
				.add(Biomes.DRIPSTONE_CAVES);
		tag(Tags.Biomes.IS_WET_NETHER);
		tag(Tags.Biomes.IS_WET_END);
		tag(Tags.Biomes.IS_WET).forceAddTag(Tags.Biomes.IS_WET_OVERWORLD).addOptionalTag(Tags.Biomes.IS_WET_NETHER.location()).addOptionalTag(Tags.Biomes.IS_WET_END.location());

		tag(Tags.Biomes.IS_DRY_OVERWORLD)
				.add(Biomes.DESERT)
				.add(Biomes.BADLANDS)
				.add(Biomes.WOODED_BADLANDS)
				.add(Biomes.ERODED_BADLANDS)
				.add(Biomes.SAVANNA)
				.add(Biomes.SAVANNA_PLATEAU)
				.add(Biomes.WINDSWEPT_SAVANNA);
		tag(Tags.Biomes.IS_DRY_NETHER)
				.add(Biomes.NETHER_WASTES)
				.add(Biomes.CRIMSON_FOREST)
				.add(Biomes.WARPED_FOREST)
				.add(Biomes.SOUL_SAND_VALLEY)
				.add(Biomes.BASALT_DELTAS);
		tag(Tags.Biomes.IS_DRY_END)
				.add(Biomes.THE_END)
				.add(Biomes.SMALL_END_ISLANDS)
				.add(Biomes.END_MIDLANDS)
				.add(Biomes.END_HIGHLANDS)
				.add(Biomes.END_BARRENS);
		tag(Tags.Biomes.IS_DRY).forceAddTag(Tags.Biomes.IS_DRY_OVERWORLD).forceAddTag(Tags.Biomes.IS_DRY_NETHER).forceAddTag(Tags.Biomes.IS_DRY_END);

		tag(Tags.Biomes.IS_CONIFEROUS_TREE).forceAddTag(Tags.Biomes.IS_TAIGA).add(Biomes.GROVE);
		tag(Tags.Biomes.IS_SAVANNA_TREE).forceAddTag(Tags.Biomes.IS_SAVANNA);
		tag(Tags.Biomes.IS_JUNGLE_TREE).forceAddTag(Tags.Biomes.IS_JUNGLE);
		tag(Tags.Biomes.IS_DECIDUOUS_TREE).add(Biomes.FOREST).add(Biomes.FLOWER_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.DARK_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.WINDSWEPT_FOREST);

		tag(Tags.Biomes.IS_MOUNTAIN_SLOPE).add(Biomes.SNOWY_SLOPES).add(Biomes.MEADOW).add(Biomes.GROVE).add(Biomes.CHERRY_GROVE);
		tag(Tags.Biomes.IS_MOUNTAIN_PEAK).add(Biomes.JAGGED_PEAKS).add(Biomes.FROZEN_PEAKS).add(Biomes.STONY_PEAKS);
		tag(Tags.Biomes.IS_MOUNTAIN).forceAddTag(BiomeTags.IS_MOUNTAIN).forceAddTag(Tags.Biomes.IS_MOUNTAIN_PEAK).forceAddTag(Tags.Biomes.IS_MOUNTAIN_SLOPE);

		tag(Tags.Biomes.IS_FOREST).forceAddTag(BiomeTags.IS_FOREST);
		tag(Tags.Biomes.IS_BIRCH_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST);
		tag(Tags.Biomes.IS_FLOWER_FOREST).add(Biomes.FLOWER_FOREST);
		tag(Tags.Biomes.IS_FLORAL).forceAddTag(Tags.Biomes.IS_FLOWER_FOREST).add(Biomes.SUNFLOWER_PLAINS).add(Biomes.CHERRY_GROVE).add(Biomes.MEADOW);
		tag(Tags.Biomes.IS_BEACH).forceAddTag(BiomeTags.IS_BEACH);
		tag(Tags.Biomes.IS_STONY_SHORES).add(Biomes.STONY_SHORE);
		tag(Tags.Biomes.IS_DESERT).add(Biomes.DESERT);
		tag(Tags.Biomes.IS_BADLANDS).forceAddTag(BiomeTags.IS_BADLANDS);
		tag(Tags.Biomes.IS_PLAINS).add(Biomes.PLAINS).add(Biomes.SUNFLOWER_PLAINS);
		tag(Tags.Biomes.IS_SNOWY_PLAINS).add(Biomes.SNOWY_PLAINS);
		tag(Tags.Biomes.IS_TAIGA).forceAddTag(BiomeTags.IS_TAIGA);
		tag(Tags.Biomes.IS_HILL).forceAddTag(BiomeTags.IS_HILL);
		tag(Tags.Biomes.IS_WINDSWEPT).add(Biomes.WINDSWEPT_HILLS).add(Biomes.WINDSWEPT_GRAVELLY_HILLS).add(Biomes.WINDSWEPT_FOREST).add(Biomes.WINDSWEPT_SAVANNA);
		tag(Tags.Biomes.IS_SAVANNA).forceAddTag(BiomeTags.IS_SAVANNA);
		tag(Tags.Biomes.IS_JUNGLE).forceAddTag(BiomeTags.IS_JUNGLE);
		tag(Tags.Biomes.IS_SNOWY).add(Biomes.SNOWY_BEACH).add(Biomes.SNOWY_PLAINS).add(Biomes.ICE_SPIKES).add(Biomes.SNOWY_TAIGA).add(Biomes.GROVE).add(Biomes.SNOWY_SLOPES).add(Biomes.JAGGED_PEAKS).add(Biomes.FROZEN_PEAKS);
		tag(Tags.Biomes.IS_ICY).add(Biomes.ICE_SPIKES).add(Biomes.FROZEN_PEAKS);
		tag(Tags.Biomes.IS_SWAMP).add(Biomes.SWAMP).add(Biomes.MANGROVE_SWAMP);
		tag(Tags.Biomes.IS_OLD_GROWTH).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.OLD_GROWTH_PINE_TAIGA).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
		tag(Tags.Biomes.IS_LUSH).add(Biomes.LUSH_CAVES);
		tag(Tags.Biomes.IS_SANDY).add(Biomes.DESERT).add(Biomes.BADLANDS).add(Biomes.WOODED_BADLANDS).add(Biomes.ERODED_BADLANDS).add(Biomes.BEACH);
		tag(Tags.Biomes.IS_MUSHROOM).add(Biomes.MUSHROOM_FIELDS);
		tag(Tags.Biomes.IS_PLATEAU).add(Biomes.WOODED_BADLANDS).add(Biomes.SAVANNA_PLATEAU).add(Biomes.CHERRY_GROVE).add(Biomes.MEADOW);
		tag(Tags.Biomes.IS_SPOOKY).add(Biomes.DARK_FOREST).add(Biomes.DEEP_DARK);
		tag(Tags.Biomes.IS_WASTELAND);
		tag(Tags.Biomes.IS_RARE).add(Biomes.SUNFLOWER_PLAINS).add(Biomes.FLOWER_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(Biomes.BAMBOO_JUNGLE).add(Biomes.SPARSE_JUNGLE).add(Biomes.ERODED_BADLANDS).add(Biomes.SAVANNA_PLATEAU).add(Biomes.WINDSWEPT_SAVANNA).add(Biomes.ICE_SPIKES).add(Biomes.WINDSWEPT_GRAVELLY_HILLS).add(Biomes.MUSHROOM_FIELDS).add(Biomes.DEEP_DARK);

		tag(Tags.Biomes.IS_RIVER).forceAddTag(BiomeTags.IS_RIVER);
		tag(Tags.Biomes.IS_SHALLOW_OCEAN).add(Biomes.OCEAN).add(Biomes.LUKEWARM_OCEAN).add(Biomes.WARM_OCEAN).add(Biomes.COLD_OCEAN).add(Biomes.FROZEN_OCEAN);
		tag(Tags.Biomes.IS_DEEP_OCEAN).forceAddTag(BiomeTags.IS_DEEP_OCEAN);
		tag(Tags.Biomes.IS_OCEAN).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(Tags.Biomes.IS_SHALLOW_OCEAN).forceAddTag(Tags.Biomes.IS_DEEP_OCEAN);
		tag(Tags.Biomes.IS_AQUATIC_ICY).add(Biomes.FROZEN_RIVER).add(Biomes.DEEP_FROZEN_OCEAN).add(Biomes.FROZEN_OCEAN);
		tag(Tags.Biomes.IS_AQUATIC).forceAddTag(Tags.Biomes.IS_OCEAN).forceAddTag(Tags.Biomes.IS_RIVER);

		tag(Tags.Biomes.IS_CAVE).add(Biomes.LUSH_CAVES).add(Biomes.DRIPSTONE_CAVES).add(Biomes.DEEP_DARK);
		tag(Tags.Biomes.IS_UNDERGROUND).forceAddTag(Tags.Biomes.IS_CAVE);

		tag(Tags.Biomes.IS_NETHER_FOREST).add(Biomes.CRIMSON_FOREST).add(Biomes.WARPED_FOREST);
		tag(Tags.Biomes.IS_OUTER_END_ISLAND).add(Biomes.END_HIGHLANDS).add(Biomes.END_MIDLANDS).add(Biomes.END_BARRENS);

		// Backwards compat with pre-1.21 tags. Done after so optional tag is last for better readability.
		// TODO: Remove backwards compat tag entries in 1.22
		tag(Tags.Biomes.IS_MOUNTAIN_SLOPE).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_slope"));
		tag(Tags.Biomes.IS_MOUNTAIN_PEAK).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_peak"));
		tagWithOptionalLegacy(Tags.Biomes.IS_MOUNTAIN);
		tagWithOptionalLegacy(Tags.Biomes.IS_HOT_OVERWORLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_HOT_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_HOT_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_HOT);
		tagWithOptionalLegacy(Tags.Biomes.IS_COLD_OVERWORLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_COLD_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_COLD_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_COLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_SPARSE_VEGETATION_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_SPARSE_VEGETATION_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_SPARSE_VEGETATION);
		tag(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_sparse/overworld"));
		tag(Tags.Biomes.IS_SPARSE_VEGETATION_NETHER).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_sparse/nether"));
		tag(Tags.Biomes.IS_SPARSE_VEGETATION_END).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_sparse/end"));
		tag(Tags.Biomes.IS_SPARSE_VEGETATION).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_sparse"));
		tag(Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_dense/overworld"));
		tag(Tags.Biomes.IS_DENSE_VEGETATION_NETHER).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_dense/nether"));
		tag(Tags.Biomes.IS_DENSE_VEGETATION_END).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_dense/end"));
		tag(Tags.Biomes.IS_DENSE_VEGETATION).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_dense"));
		tagWithOptionalLegacy(Tags.Biomes.IS_WET_OVERWORLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_WET_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_WET_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_WET);
		tagWithOptionalLegacy(Tags.Biomes.IS_DRY_OVERWORLD);
		tagWithOptionalLegacy(Tags.Biomes.IS_DRY_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_DRY_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_DRY);
		tagWithOptionalLegacy(Tags.Biomes.IS_CONIFEROUS_TREE);
		tagWithOptionalLegacy(Tags.Biomes.IS_SPOOKY);
		tagWithOptionalLegacy(Tags.Biomes.IS_DEAD);
		tagWithOptionalLegacy(Tags.Biomes.IS_LUSH);
		tagWithOptionalLegacy(Tags.Biomes.IS_MUSHROOM);
		tagWithOptionalLegacy(Tags.Biomes.IS_MAGICAL);
		tagWithOptionalLegacy(Tags.Biomes.IS_RARE);
		tagWithOptionalLegacy(Tags.Biomes.IS_PLATEAU);
		tagWithOptionalLegacy(Tags.Biomes.IS_MODIFIED);
		tagWithOptionalLegacy(Tags.Biomes.IS_FLORAL);
		tag(Tags.Biomes.IS_AQUATIC).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", "is_water"));
		tagWithOptionalLegacy(Tags.Biomes.IS_DESERT);
		tagWithOptionalLegacy(Tags.Biomes.IS_PLAINS);
		tagWithOptionalLegacy(Tags.Biomes.IS_SWAMP);
		tagWithOptionalLegacy(Tags.Biomes.IS_SANDY);
		tagWithOptionalLegacy(Tags.Biomes.IS_SNOWY);
		tagWithOptionalLegacy(Tags.Biomes.IS_WASTELAND);
		tagWithOptionalLegacy(Tags.Biomes.IS_VOID);
		tagWithOptionalLegacy(Tags.Biomes.IS_CAVE);
		tagWithOptionalLegacy(Tags.Biomes.IS_END);
		tagWithOptionalLegacy(Tags.Biomes.IS_NETHER);
		tagWithOptionalLegacy(Tags.Biomes.IS_OVERWORLD);
	}
	@SafeVarargs
	private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags) {
		for (TagKey<Biome> key : tags) {
			tag(key).add(biome);
		}
	}

	private TagAppender<Biome> tagWithOptionalLegacy(TagKey<Biome> tag) {
		return tag(tag).addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", tag.location().getPath()));
	}

    public FabricTagBuilder tag(TagKey<Biome> tag) {
        return getOrCreateTagBuilder(tag);
    }

    @Override
	public String getName() {
		return "PortingLib Biome Tags";
	}
}
