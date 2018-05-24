package sampletry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class serverRunner {
    static stubbing stub =new stubbing();
    public static void main (final String[] args){
        final String[] args2 = new String[args.length + 2];
        args2[args.length] = "--port";
        args2[args.length + 1] = "8082";
        CommandLineOptions options = new CommandLineOptions(args2);
        WireMockServer wireMockServer = new WireMockServer(options);
        wireMockServer.start();
        stub.loadStubs(wireMockServer);

    }
}


