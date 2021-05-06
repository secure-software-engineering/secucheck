package de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.utility;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InputStreamUtility {
    public static Map<String, String> getOptionsFromInputStream(InputStream inputStream) {
        Map<String, String> requestOptions = new HashMap<>();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream));
        String body = "";
        try {
            body = URLDecoder.decode(reader.lines().collect(Collectors.joining()), "UTF-8");
            reader.close();

            String[] options = body.split("&");
            for (String option : options) {
                String[] pairs = option.split("=");
                if (pairs.length > 1) {
                    String key = pairs[0];
                    String value = pairs[1];
                    requestOptions.put(key, value);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestOptions;
    }
}
