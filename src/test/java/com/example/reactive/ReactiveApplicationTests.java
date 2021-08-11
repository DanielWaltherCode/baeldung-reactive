package com.example.reactive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.BaseSubscriber;


@WebFluxTest(controllers = ReactiveController.class)
class ReactiveApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    void contextLoads() {
    }

    @Test
    void testReactiveController() {
        FluxExchangeResult<Foo> response = webClient.get().uri("/foo")
                .header(HttpHeaders.ACCEPT, "text/event-stream")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(Foo.class);

        response.getResponseBody().subscribe(new BaseSubscriber<>() {
            @Override
            public void hookOnNext(Foo value) {
                assertNotNull(value);
                assertEquals(0, (long) value.getId());
                assertEquals(value.getName(), "Foo Flux");
            }
        });

    }
}
