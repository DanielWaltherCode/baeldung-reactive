package com.example.reactive;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class ReactiveController {

    @GetMapping(path = "/foo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Foo>> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(sequenceId -> ServerSentEvent.<Foo> builder()
                .id(String.valueOf(sequenceId))
                .event("periodic-foo-event")
                .data(new Foo(sequenceId, "Foo Flux"))
                .build());
    }
}
