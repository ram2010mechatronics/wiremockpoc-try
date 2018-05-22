package com.learning.wiremock.wiremock;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

public class wiremock_ext extends ResponseTransformer {



    @Override
    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public boolean applyGlobally() {
        return false;
    }
}
