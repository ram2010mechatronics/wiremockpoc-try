package com.learning.wiremock.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Wiremock_try1 {
	
	public static Response response;
	
	BodyTransformers bt = new BodyTransformers();
	
	@Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).extensions(bt));
    
    @Test
    public void willReturnFieldWithNameValueWhenOnlyRootElementForXml() {
        wireMockRule.stubFor(post(urlMatching("/test/rootXml"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("content-type", "application/xml")
                .withBody("{\"var\":\"$(value)\"}")
                .withTransformers("body-transformer")));
        
       response =  given()
            .contentType("application/xml")
            .body("<var>101</var>")
            .post("/test/rootXml");
       System.out.println(response.getStatusCode());
       System.out.println(response.getBody().asString());
       System.out.println(response.getContentType());
       System.out.println(RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath );

    }
}
