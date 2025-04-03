package com.scalesec.vulnado;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.io.InputStreamReader;

private Cowsay() {}
public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = \"/usr/games/cowsay '\" + input.replaceAll(\"[\\\\\\\\\"'\\\\\\\\]\", \"\") + \"'\";
    Logger logger = Logger.getLogger(Cowsay.class.getName());
logger.info(cmd);
    processBuilder.command(\"bash\", \"-c\", cmd); // Ensure PATH is sanitized and validated

    StringBuilder output = new StringBuilder();

try {
    try {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(processBuilder.start().getInputStream()))) {
        output.append(line).append(\"\\n\");
    } catch (Exception e) {
      // Debugging feature deactivated for production
logger.warning(e.getMessage());
}
return output.toString();
}
}