package com.learning.wiremock.wiremock;

import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;
import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;

public class runserver {

    private static String filesRoot;

    public static void main(final String[] args) {
        final CommandLineOptions options = new CommandLineOptions(args);
        System.out.println(options.filesRoot().getPath());
        runserver.filesRoot = options.filesRoot().getPath();

        final String[] args2 = new String[args.length + 4];
        System.arraycopy(args, 0, args2, 0, args.length);
        args2[args.length] = "--port";
        args2[args.length + 1] = "8081";
        args2[args.length + 2] = "--extensions";
        args2[args.length + 3] = "com.wiremock.ext.wiremock_ext";
        args2[args.length + 4] = "--root-dir";
        args2[args.length + 5] = "D:\\WireMock\\WireMockCsv-master\\WireMockCsv-master\\src\\test\\resources\\factures\\mock";
        WireMockServerRunner.main(args2);
        System.out.println(options.filesRoot().getPath());
    }


    public static String filesRoot()
    {
        return runserver.filesRoot;
    }

}
