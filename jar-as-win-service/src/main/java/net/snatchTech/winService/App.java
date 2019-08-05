package net.snatchTech.winService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.snatchTech.winService.excp.ExcpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger log = Logger.getLogger(App.class.getName());

    private static Map<String, Map<String, String>> properties = new HashMap<>();

    public static void main( String[] args ) {

        if (args.length == 0){
            throw new IllegalArgumentException("There is no arguments have been passed to the program.");
        } else if (args.length == 1){
            throw new IllegalArgumentException("There is no service task name has been passed to the program.");
        }

        String appName = args[0];
        String taskName = args[1];

        try (InputStream isProps = App.class.getResourceAsStream("/" + taskName + "_logging.properties")){
            LogManager.getLogManager().readConfiguration(isProps);
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        log.log(Level.CONFIG, "The app has been started with name: " + appName + "_" + taskName);

        //read config
        try (InputStream configStream = App.class.getResourceAsStream("/" + taskName + "_config.json")){
            properties = new ObjectMapper().readValue(configStream,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    });
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not read config.json : " + ExcpUtil.getFullDescription(e), e);
            System.exit(1);
        }

        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                log.log(Level.SEVERE, "Uncaught exception in thread: " + t.getName() +
                        ", due to: " + ExcpUtil.getFullDescription(e), e)
        );

        new Thread(() -> {

            while (true) {
                StringBuilder sb = new StringBuilder();

                properties.forEach((prop_group, prop) -> {
                    sb.append("\n").append(prop_group);
                    prop.forEach((name, value) -> {
                        sb.append("\n").append(name).append(" : ").append(value);
                    });
                });

                log.info(sb.toString());

                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "Interrupted exception!!", e);
                }
            }

        }).start();

    }
}
