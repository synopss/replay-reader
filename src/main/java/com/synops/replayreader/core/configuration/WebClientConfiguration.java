package com.synops.replayreader.core.configuration;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {

  @Bean
  public WebClient.Builder webClientBuilder() {
    var httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient));
  }
}