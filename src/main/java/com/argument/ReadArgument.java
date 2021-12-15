package com.argument;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Date;
import java.util.concurrent.Callable;


@Command(name="Twitter filter options", mixinStandardHelpOptions = true, version="v.1.0",
        description = "the research will be calibrated according to your options.")
public class ReadArgument implements Callable<String[]> {

    @Option(names = {"-w", "--word"}, description="Type the name of the crypto that you want informations of ")
    private String cryptoName;

    @Option(names = {"-n", "--number"}, description="How many tweet you want to analyse")
    private String number;

    @Option(names = {"-b", "--begin"}, description="The date you want the tweet begin")
    private Date begin;

    @Option(names = {"-e", "--end"}, description="The date you want the tweet end")
    private Date end;

    @Override
    public String[] call() {
        return new String[]{cryptoName,number,begin.toString(),end.toString()};
    }
}
