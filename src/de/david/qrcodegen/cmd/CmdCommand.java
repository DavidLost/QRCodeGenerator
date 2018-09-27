package de.david.qrcodegen.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CmdCommand {

    private String command;
    private Process process;

    public CmdCommand(String command) {
        this.command = command;
    }

    /*public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }*/

    public void execute() {
        try {
            process = Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getOut() {
        return getStd(new BufferedReader(new InputStreamReader(process.getInputStream())));
    }

    /*public String[] getError() {
        return getStd(new BufferedReader(new InputStreamReader(process.getErrorStream())));
    }*/

    private String[] getStd(BufferedReader stdReader) {

        ArrayList<String> std = new ArrayList<>();
        try {
            String line;
            while ((line = stdReader.readLine()) != null) {
                std.add(line);
            }
        } catch (IOException e) {}
        String stdString = std.toString();
        return stdString.substring(1,stdString.length()-1).split(", ");
    }

}
