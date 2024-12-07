package org.bananalaba.springcdtemplate.config;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.cache.CacheBuilder;
import org.bananalaba.springcdtemplate.data.BlobItem;
import org.bananalaba.springcdtemplate.repository.BlobGenerator;
import org.bananalaba.springcdtemplate.repository.BlobItemGenerator;
import org.bananalaba.springcdtemplate.repository.BlobItemRepository;
import org.bananalaba.springcdtemplate.repository.CacheGeneratingBlobItemRepository;
import org.bananalaba.springcdtemplate.repository.MetadataGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobRepositoryModule {

    @Bean
    public BlobItemRepository imageRepository() {
        var metadataGenerator = new MetadataGenerator(
            new Random(),
            Map.of(
                "format", List.of("jpeg", "png", "bmp", "svg"),
                "compression", List.of("gzip", "none")
            )
        );
        return newRepository(imageRepositoryConfig(), metadataGenerator);
    }

    @Bean
    @ConfigurationProperties(prefix = "image-repository")
    public BlobRepositoryConfig imageRepositoryConfig() {
        return new BlobRepositoryConfig();
    }

    @Bean
    public BlobItemRepository textRepository() {
        var metadataGenerator = new MetadataGenerator(
            new Random(),
            Map.of(
                "characterEncoding", List.of("utf-8", "utf-16", "ascii"),
                "compression", List.of("gzip", "none")
            )
        );
        return newRepository(textRepositoryConfig(), metadataGenerator);
    }

    @Bean
    @ConfigurationProperties(prefix = "text-repository")
    public BlobRepositoryConfig textRepositoryConfig() {
        return new BlobRepositoryConfig();
    }

    @Bean
    public BlobItemRepository videoRepository() {
        var metadataGenerator = new MetadataGenerator(
            new Random(),
            Map.of(
                "format", List.of("mp4", "mov"),
                "compression", List.of("gzip", "none")
            )
        );
        return newRepository(videoRepositoryConfig(), metadataGenerator);
    }

    @Bean
    @ConfigurationProperties(prefix = "video-repository")
    public BlobRepositoryConfig videoRepositoryConfig() {
        return new BlobRepositoryConfig();
    }

    private BlobItemRepository newRepository(final BlobRepositoryConfig config, final MetadataGenerator metadataGenerator) {
        var cache = CacheBuilder.newBuilder()
            .maximumSize(config.getCacheSize())
            .<Long, BlobItem>build();

        var blobGenerator = new BlobGenerator(
            config.getBlobGenerationGrowthFactor(),
            config.getBlobGenerationMinIterations(),
            config.getBlobGenerationMaxIterations()
        );
        var itemGenerator = new BlobItemGenerator(metadataGenerator, blobGenerator);

        return new CacheGeneratingBlobItemRepository(cache, itemGenerator);
    }

}
