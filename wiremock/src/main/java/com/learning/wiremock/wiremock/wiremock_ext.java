package com.learning.wiremock.wiremock;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import java.sql.SQLException;
import java.util.Map;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class wiremock_ext extends ResponseTransformer {

    private final DbManager manager;
    private final XmlConverter xmlConverter;
    private final ConfigHandler config;
    private static final String TRANSFORMER_NAME = "body-transformer";
    private static final boolean APPLY_GLOBALLY = false;

    public wiremock_ext(DbManager manager, XmlConverter xmlConverter, ConfigHandler config) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.manager = manager;
        this.xmlConverter = xmlConverter;
        this.config = config;
        this.manager.dbConnect();
    }

    @Override
    public String getName() {
        return TRANSFORMER_NAME;
    }

    public boolean applyGlobally()
    {
        return APPLY_GLOBALLY;
    }

    @Override
    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {

        final Map<String, Object> queries = this.getQueriesConfig(parameters);
        stubFor(get(urlEqualTo("/local-transform")).willReturn(aResponse()
                .withStatus(200)
                .withBody("Original body")
                .withTransformers("my-transformer", "other-transformer")));
        return response;
    }

    private Map<String,Object> getQueriesConfig(Parameters parameters) {

        return null;
    }


}
