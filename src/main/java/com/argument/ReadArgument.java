package com.argument;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


@Command(name="Twitter filter options", mixinStandardHelpOptions = true, version="v.1.0",
        description = "the research will be calibrated according to your options.")
public class ReadArgument implements Callable<Map<String,String>> {

    @Option(names = {"-c", "--crypto"}, description="Type the name of the crypto that you want informations of ", defaultValue = "Bitcoin")
    private String cryptoName;

    @Option(names = {"-l", "--limit"}, description="How many tweet you want to analyse", defaultValue = "10")
    private String limit;

    @Option(names = {"-b", "--begin"}, description="The year you want the tweet begin")
    private String begin;

    @Option(names = {"-e", "--end"}, description="The year you want the tweet end")
    private String end;

    @Option(names = {"-p", "--proxy"}, description="Yes or no you want use proxy", defaultValue = "false")
    private String proxy;

    @Option(names = {"-la", "--lang"}, description="Which language you want to target")
    private String lang;

    @Override
    public Map<String, String> call() {
        Map<String, String> arguments = new HashMap<>();

        arguments.put("crypto",cryptoName);
        arguments.put("limit",limit);
        arguments.put("begin",begin);
        arguments.put("end",end);
        arguments.put("proxy",proxy);

        return arguments;
    }
}
