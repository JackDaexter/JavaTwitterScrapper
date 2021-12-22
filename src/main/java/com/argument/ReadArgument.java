package com.argument;
import org.checkerframework.checker.regex.qual.Regex;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Command(name="Twitter filter options", mixinStandardHelpOptions = true, version="v.1.0",
        description = "the research will be calibrated according to your options.")
public class ReadArgument implements Callable<Map<String,String>> {

    @Option(names = {"-c", "--crypto"}, description="Type the name of the crypto that you want informations of ", defaultValue = "Bitcoin")
    private String cryptoName;

    @Option(names = {"-l", "--limit"}, description="How many tweet you want to analyse", defaultValue = "10")
    private String limit;

    @Option(names = {"-b", "--begin"}, description="The year you want the tweet begin : YEAR-MONTH-DAY",defaultValue = "2016-01-01")
    private String begin;

    @Option(names = {"-e", "--end"}, description="The year you want the tweet end : YEAR-MONTH-DAY", defaultValue = "2021-01-01")
    private String end;

    @Option(names = {"-p", "--proxy"}, description="Yes or no you want use proxy", defaultValue = "false")
    private String proxy;

    @Option(names = {"-la", "--lang"}, description="Which language you want to target")
    private String lang;

    @Override
    public Map<String, String> call() {
        Map<String, String> arguments = new HashMap<>();

        if(dateVerification()){
            arguments.put("crypto",cryptoName);
            arguments.put("limit",limit);
            arguments.put("begin",begin);
            arguments.put("end",end);
            arguments.put("proxy",proxy);
        }
        return arguments;
    }

    private boolean dateVerification(){
        Pattern pattern = Pattern.compile("^([0-9]{4}[-][0-9]{2}[-][0-9]{2})$");
        Matcher match = pattern.matcher(begin);
        Matcher matchEnd = pattern.matcher(end);

        if (begin == null ^ end == null) {
            System.err.println("Error: begin or end date is missing");
            return false;
        }

        if(!match.find()){
            System.err.println("Bad date-format");
            return false;
        }
        if(!matchEnd.find()){
            System.err.println("Bad date-format");
            return false;
        }

        assert begin != null;
        LocalDate beginDate =  LocalDate.parse(begin);
        LocalDate endDate =  LocalDate.parse(end);

        if(endDate.compareTo(beginDate) < 0){
            System.err.println("Error : End date have to be bigger than begin date");
            return false;
        }
        return true;
    }
}
