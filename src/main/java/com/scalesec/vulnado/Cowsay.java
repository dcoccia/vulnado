package com.scalesec.vulnado;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

private Cowsay() {}
public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
// Validate input to prevent unwanted behavior
    String cmd = "/usr/games/cowsay '" + input + "'";
    Logger logger = Logger.getLogger(Cowsay.class.getName());
// Ensure PATH is properly sanitized
    processBuilder.command("bash", "-c", cmd); // Ensure proper validation of input and PATH

    StringBuilder output = new StringBuilder();

try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    try {
      Process process = processBuilder.start();
try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
logger.severe("Exception occurred: " + e.getMessage());
// Remove debug features before production
      logger.warning("Debug feature activated: " + e.getMessage());
    }
    return output.toString();
  }
}
