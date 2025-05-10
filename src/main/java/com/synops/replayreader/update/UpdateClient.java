package com.synops.replayreader.update;

import com.synops.replayreader.core.event.StartupEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateClient {

  private final WebClient.Builder webClientBuilder;
  private WebClient webClient;

  public UpdateClient(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @EventListener
  public void init(StartupEvent event) {
    webClient = webClientBuilder.baseUrl("https://api.github.com/repos/synopss/replay-reader/")
        .defaultHeaders(HttpHeaders::clear).build();
  }

  public Mono<VersionResponse> getLatestVersion() {
    return webClient.get().uri("releases/latest").retrieve().bodyToMono(VersionResponse.class);
  }
}
