package com.learning.wiremock.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import wiremock.org.apache.commons.lang3.StringUtils;
import java.net.URLDecoder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class wiremock_ext extends ResponseTransformer {

    private static final String TRANSFORMER_NAME = "body-transformer";
    private static final boolean APPLY_GLOBALLY = false;
    private static ObjectMapper jsonMapper = initJsonMapper();
    private static ObjectMapper xmlMapper = initXmlMapper();
    private static ObjectMapper initJsonMapper() {
        return new ObjectMapper();
    }

    private static ObjectMapper initXmlMapper() {
        JacksonXmlModule configuration = new JacksonXmlModule();
        configuration.setXMLTextElementName("value");
        return new XmlMapper(configuration);
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
        Map object = null;
        String requestBody = request.getBodyAsString();
        try {
            object = jsonMapper.readValue(requestBody, Map.class);
        } catch (IOException e) {
            try {
                object = xmlMapper.readValue(requestBody, Map.class);
            } catch (IOException ex) {
                // Validate is a body has the 'name=value' parameters
                if (StringUtils.isNotEmpty(requestBody) && (requestBody.contains("&") || requestBody.contains("="))) {
                    object = new HashMap();
                    String[] pairedValues = requestBody.split("&");
                    for (String pair : pairedValues) {
                        String[] values = pair.split("=");
                        object.put(values[0], values.length > 1 ? decodeUTF8Value(values[1]) : "");
                    }
                } else if (request.getAbsoluteUrl().split("\\?").length == 2) { // Validate query string parameters
                    object = new HashMap();
                    String absoluteUrl = request.getAbsoluteUrl();
                    String[] pairedValues = absoluteUrl.split("\\?")[1].split("&");
                    for (String pair : pairedValues) {
                        String[] values = pair.split("=");
                        object.put(values[0], values.length > 1 ? decodeUTF8Value(values[1]) : "");
                    }
                } else {
                    System.err.println("[Body parse error] The body doesn't match any of 3 possible formats (JSON, XML, key=value).");
                }
            }
        }
        return null;
    }


}
