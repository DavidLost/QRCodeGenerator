package de.david.qrcodegen.web;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable {

    String url;
    File file;

    int bufferSize = 1024;

    public Downloader(String url, File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            long fileSize = connection.getContentLengthLong();
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream, bufferSize);
            byte[] buffer = new byte[bufferSize];
            double downloaded = 0;
            int read = 0;
            while ((read = inputStream.read(buffer)) >= 0) {
                outputStream.write(buffer);
                downloaded += read;
                String percent = String.format("%.1f", downloaded*100 / fileSize);
                System.out.println("Downloaded "+percent+"%");
            }
            outputStream.close();
            inputStream.close();
            System.out.println("Download finished!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
