package com.learning.wiremock.wiremock;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import wiremock.org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class wiremock_ext extends ResponseTransformer {

    private final DbManager manager;
    private final XmlConverter xmlConverter;
    private final ConfigHandler config;
    private String TRANSFORMER_NAME;
    private boolean APPLY_GLOBALLY = false;

    public wiremock_ext(DbManager manager, XmlConverter xmlConverter, ConfigHandler config) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.manager = manager;
        this.xmlConverter = xmlConverter;
        this.config = config;
        this.manager.dbConnect();    }

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

        return null;
    }


}
