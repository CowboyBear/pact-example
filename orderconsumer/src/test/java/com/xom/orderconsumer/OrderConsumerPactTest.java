package com.xom.orderconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
public class OrderConsumerPactTest {

    @Pact(provider = "OrderAPI", consumer="orderConsumer")
    public RequestResponsePact createPactForOrdersByIdEndPoint(PactDslWithProvider builder) {

        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("id");

        return builder
                .given("It has orders")
                .uponReceiving("a request for the order ap-1234567890")
                    .path("/orders/ap-1234567890")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(body)
                .toPact();
    }

    @Pact(provider = "OrderAPI", consumer="orderConsumer")
    public RequestResponsePact createPactForOrdersByParametersEndPoint(PactDslWithProvider builder) {
        return builder
                .given("It has orders in AP stripes instance")
                .uponReceiving("a request filtering orders by AP stripes instace")
                .path("/orders")
                .query("filters[stripesInstance]=ap")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("{}")
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPactForOrdersByIdEndPoint")
    void testForOrdersById(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/orders/ap-1234567890").execute().returnResponse();
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @PactTestFor(pactMethod = "createPactForOrdersByParametersEndPoint")
    void testForOrdersByParameters(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/orders?filters[stripesInstance]=ap").execute().returnResponse();
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }
}
