package com.Bookstore.store_backend.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class bookImageUtils {



    public static byte[] compressImage(byte[] data) {
        // Create Deflater object for compression
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION); // Set the compression level
        deflater.setInput(data); // Set the input data to be compressed
        deflater.finish(); // Finish the setup

        // Create ByteArrayOutputStream to collect the compressed data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024]; // Temporary buffer for compressed data

        while (!deflater.finished()) {
            // Deflate data into the temporary buffer
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size); // Write the compressed data to the output stream
        }

        // Close the output stream and return the compressed data as a byte array
        try {
            outputStream.close();
        } catch (Exception ignored) {
            // Handle possible exception, if any
        }

        return outputStream.toByteArray(); // Return the compressed byte array
    }


    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);  // Set the input compressed data

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  // Output stream to store decompressed data
        byte[] tmp = new byte[4 * 1024];  // Temporary buffer for decompressed data

        try {
            // Decompress until all data is processed
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);  // Inflate (decompress) the data
                outputStream.write(tmp, 0, count);  // Write the decompressed data to the output stream
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions (e.g., if the data is corrupted)
        } finally {
            try {
                outputStream.close();  // Close the output stream
            } catch (Exception ignored) {
                // Ignore the exception while closing
            }
        }

        return outputStream.toByteArray();  // Return the decompressed byte array
    }

}
