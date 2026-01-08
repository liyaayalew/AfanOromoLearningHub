package com.example.afan_oromo_learning.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {
    public interface DownloadListener {
        void onDownloadComplete(String filePath);
        void onDownloadFailed(String error);
        void onProgressUpdate(int progress);
    }

    public static void downloadFile(Context context, String fileUrl, String fileName, DownloadListener listener) {
        new DownloadTask(context, listener).execute(fileUrl, fileName);
    }

    private static class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private DownloadListener listener;

        public DownloadTask(Context context, DownloadListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            String fileUrl = params[0];
            String fileName = params[1];

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode();
                }

                int fileLength = connection.getContentLength();

                File directory = new File(Environment.getExternalStorageDirectory(), "AfanOromoLearning");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File outputFile = new File(directory, fileName);

                InputStream input = connection.getInputStream();
                FileOutputStream output = new FileOutputStream(outputFile);

                byte[] data = new byte[4096];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        output.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) {
                        publishProgress((int) (total * 100 / fileLength));
                    }
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                return outputFile.getAbsolutePath();

            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (listener != null) {
                listener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (listener != null) {
                if (result != null && !result.startsWith("Server returned") && !result.contains("Exception")) {
                    listener.onDownloadComplete(result);
                    Toast.makeText(context, "File downloaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onDownloadFailed(result != null ? result : "Download failed");
                    Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static boolean isFileDownloaded(String fileName) {
        File directory = new File(Environment.getExternalStorageDirectory(), "AfanOromoLearning");
        File file = new File(directory, fileName);
        return file.exists();
    }
}