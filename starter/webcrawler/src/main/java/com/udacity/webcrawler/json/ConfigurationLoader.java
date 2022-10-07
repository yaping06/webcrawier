package com.udacity.webcrawler.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() throws IOException {
    // TODO: Fill in this method.
    String content = Files.readString(path);
    Reader reader = new StringReader(content);
    CrawlerConfiguration configuration = ConfigurationLoader.read(reader);
    reader.close();
    return configuration;
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) throws IOException {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // TODO: Fill in this method

    ObjectMapper mapper = new ObjectMapper();
    CrawlerConfiguration configuration = mapper.readValue(reader, CrawlerConfiguration.class);


    return new CrawlerConfiguration.Builder()
            .addStartPages(String.valueOf(configuration.getStartPages()))
            .addIgnoredUrls(String.valueOf(configuration.getIgnoredUrls()))
            .addIgnoredWords(String.valueOf(configuration.getIgnoredWords()))
            .setParallelism(configuration.getParallelism())
            .setImplementationOverride(configuration.getImplementationOverride())
            .setMaxDepth(configuration.getMaxDepth())
            .setTimeoutSeconds(configuration.getTimeout().toSecondsPart())
            .setPopularWordCount(configuration.getPopularWordCount())
            .setProfileOutputPath(configuration.getProfileOutputPath())
            .setResultPath(configuration.getResultPath())
            .build();
  }
}
