package org.mvss.decl.tasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;

//@Log4j2
public class Main {
    public static final TypeReference<ArrayList<Command>> COMMAND_LIST_TYPE_REFERENCE = new TypeReference<>() {
    };
    public static final String FILE_TO_RUN = "fileToRun";
    public static final String HELP = "help";
    public static final String DECLTRY = "decltry";

    public static void main(String[] args) {
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();
        DefaultParser parser = new DefaultParser();

        options.addOption("f", FILE_TO_RUN, true, "file to run");
        options.addOption(null, HELP, false, "prints this help message");

        ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory());

        yamlObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        yamlObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        yamlObjectMapper.findAndRegisterModules();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(HELP)) {
                formatter.printHelp(DECLTRY, options);
            } else if (cmd.hasOption(FILE_TO_RUN)) {
                String fileName = cmd.getOptionValue(FILE_TO_RUN);
                File file = new File(fileName);
                ArrayList<Command> commandList = yamlObjectMapper.readValue(file, COMMAND_LIST_TYPE_REFERENCE);

                for (Command command : commandList) {
                    System.out.println("Running command " + command);
                    Task task;

                    switch (command.getType()) {
                        case "exec":
                            task = yamlObjectMapper.convertValue(command.getParams(), Exec.class);
                            TaskResult taskResult = task.execute();
                            System.out.println("Task result is " + taskResult);
                            break;
                        case "somethingElse":
                            break;
                        default:
                            System.err.println("Unknown task type " + command.getType());
                    }
                }
            } else {
                formatter.printHelp(DECLTRY, options);
                System.exit(-1);
            }
        } catch (UnrecognizedOptionException | MissingArgumentException uoe) {
            System.err.println(uoe.getMessage());
            formatter.printHelp(DECLTRY, options);
            System.exit(-1);
        } catch (Throwable t) {
            System.err.println("Exception caught while init: " + t.getMessage());
            t.printStackTrace();
            formatter.printHelp(DECLTRY, options);
            System.exit(-2);
        }
    }
}