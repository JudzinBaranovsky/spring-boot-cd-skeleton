package org.bananalaba.springcdtemplate.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlobRepositoryConfig {

    private int cacheSize;

    private int blobGenerationGrowthFactor;
    private int blobGenerationMinIterations;
    private int blobGenerationMaxIterations;

}
