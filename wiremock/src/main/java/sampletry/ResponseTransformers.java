package sampletry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import io.restassured.RestAssured;
import org.junit.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.http.HttpHeader.httpHeader;
import static io.restassured.RestAssured.given;


public class ResponseTransformers {

    WireMockServer wm;
    public static io.restassured.response.Response response;


    @Test
    public void transformsStubResponse() {
        startWithExtensions((Class<? extends Extension>) StubResponseTransformer.class);

        wm.stubFor(get(urlEqualTo("/response-transform")).willReturn(aResponse().withBody("Original body")));


    }

    @Test
    public void acceptsTransformerParameters() {
        startWithExtensions((Class<? extends Extension>) StubResponseTransformerWithParams.class);

        wm.stubFor(get(urlEqualTo("/response-transform-with-params")).willReturn(
                aResponse()
                        .withTransformerParameter("name", "John")
                        .withTransformerParameter("number", 66)
                        .withTransformerParameter("flag", true)
                        .withBody("Original body")));

        response =  given()
                .contentType("application/json")
                .body("<var>101</var>")
                .post("/response-transform-with-params");
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
        System.out.println(response.getContentType());
        System.out.println(RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath );

    }

    @Test
    public void globalTransformAppliedWithLocalParameters() {
        startWithExtensions((Class<? extends Extension>) GlobalResponseTransformer.class);

        wm.stubFor(get(urlEqualTo("/global-response-transform")).willReturn(aResponse()));


    }

    @SuppressWarnings("unchecked")
    private void startWithExtensions(Class<? extends Extension> extensionClasses) {
        wm = new WireMockServer(wireMockConfig().dynamicPort().extensions(extensionClasses));
        wm.start();

    }

    public static class StubResponseTransformer extends ResponseTransformer {

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
            return Response.Builder.like(response)
                    .but().body("Modified body")
                    .build();
        }

        @Override
        public boolean applyGlobally() {
            return true;
        }

        @Override
        public String getName() {
            return "stub-transformer";
        }
    }

    public static class StubResponseTransformerWithParams extends ResponseTransformer {

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
            return Response.Builder.like(response)
                    .but().body(parameters.getString("name") + ", "
                            + parameters.getInt("number") + ", "
                            + parameters.getBoolean("flag"))
                    .build();
        }

        @Override
        public boolean applyGlobally() {
            return true;
        }

        @Override
        public String getName() {
            return "stub-transformer-with-params";
        }
    }

    public static class GlobalResponseTransformer extends ResponseTransformer {

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
            return Response.Builder.like(response).but()
                    .headers(response.getHeaders().plus(httpHeader("X-Extra", "extra val")))
                    .build();
        }

        @Override
        public String getName() {
            return "global-response-transformer";
        }

        @Override
        public boolean applyGlobally() {
            return true;
        }
    }

    public void testMethod(){
        WireMockServer wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/api/get-magic"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"currently\":{\"windSpeed\":12.34}}")));

        wireMockServer.start();

    }
}