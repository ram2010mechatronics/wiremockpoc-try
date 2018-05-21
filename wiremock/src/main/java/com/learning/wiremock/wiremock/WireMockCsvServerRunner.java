package com.learning.wiremock.wiremock;

import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;


public class WireMockCsvServerRunner {

	private static String filesRoot;

	public static void main(final String[] args) {
		final CommandLineOptions options = new CommandLineOptions(args);
		WireMockCsvServerRunner.filesRoot = options.filesRoot().getPath();

		final String[] args2 = new String[args.length + 2];
		System.arraycopy(args, 0, args2, 0, args.length);
		args2[args.length] = "--extensions";
		args2[args.length + 1] = "com.wiremock.extension.csv.WireMockCsv";

		WireMockServerRunner.main(args2);
	}

	
	public static String filesRoot() {
		return WireMockCsvServerRunner.filesRoot;
	}
//try
}

