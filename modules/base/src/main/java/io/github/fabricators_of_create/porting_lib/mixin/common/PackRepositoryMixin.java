package io.github.fabricators_of_create.porting_lib.mixin.common;

import com.google.common.collect.ImmutableSet;
import io.github.fabricators_of_create.porting_lib.extensions.PackRepositoryExtension;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashSet;
import java.util.Set;

@Mixin(PackRepository.class)
public class PackRepositoryMixin implements PackRepositoryExtension {
    @Mutable
    @Shadow @Final private Set<RepositorySource> sources;

    public void pl$addPackFinder(RepositorySource packFinder) {
        if (sources instanceof ImmutableSet<RepositorySource>)
            sources = new HashSet<>(sources);
        sources.add(packFinder);
    }
}
