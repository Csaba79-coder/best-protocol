package com.csaba79coder.bestprotocol.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**

 A utility class for compressing and decompressing images using the Deflater and Inflater classes.
 */
public class ImageUtil {

    /**
     * Compresses the given input data using the Deflater class and returns the compressed data as a new byte array.
     *
     * @param data the input byte array to compress
     * @return a new byte array containing the compressed data
     */
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    /**
     * Decompresses the given input data using the Inflater class and returns the decompressed data as a new byte array.
     *
     * @param data the input byte array to decompress
     * @return a new byte array containing the decompressed data
     */
     public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    // as all the methods are static, I don't need to instantiate this class! to achieve this, I need to make the constructor private
    private ImageUtil() {

    }
}
