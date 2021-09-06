package com.latoken.api.client.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

final class Util {
    static String getUserAgent() {
        return Lazy.load("user-agent", () -> {
            Logger logger = LoggerFactory.getLogger(Util.class);
            try {
                logger.debug("fetching user agent info");
                Properties properties = new Properties();
                properties.load(Util.class.getClassLoader().getResourceAsStream("project.properties"));

                String osName = System.getProperty("os.name");
                String osVersion = System.getProperty("os.version");
                String osArchitecture = System.getProperty("os.arch");
                String artifactId = properties.getProperty("artifactId");
                String version = properties.getProperty("version");

                return osName + " " + osVersion + " (" + osArchitecture + ") / " + artifactId + ":" + version;
            } catch (Exception e) {
                logger.warn("failed to retrieve user agent information", e);
                return "unknown";
            }
        });
    }
}

final class Lazy {
    static HashMap<String, Object> cache = new HashMap<>();

    @SuppressWarnings("unchecked")
    static <T> T load(String key, Supplier<T> supplier) {
        return (T) cache.computeIfAbsent(key, s -> supplier.get());
    }
}

final class Validation {
    static <T> T checkNotNull(T obj) {
        if (obj == null) throw new NullPointerException();
        return obj;
    }

    static boolean notNullOrEmpty(String key) {
        return key != null && !key.isEmpty();
    }

    static <T extends Collection<?>> T checkNotEmpty(T collection) {
        if (isNullOrEmpty(collection)) {
            throw new IllegalStateException("collection is empty");
        }
        return collection;
    }

    static <T extends Map<?, ?>> T checkNotEmpty(T map) {
        if (isNullOrEmpty(map)) {
            throw new IllegalStateException("map is empty");
        }
        return map;
    }

    static <T extends Collection> boolean isNullOrEmpty(T collection) {
        return collection == null || collection.isEmpty();
    }

    static <T extends Map<?, ?>> boolean isNullOrEmpty(T map) {
        return map == null || map.isEmpty();
    }
}

final class Hex {
    static private final int BASELENGTH = 128;
    static private final int LOOKUPLENGTH = 16;
    static final private byte[] hexNumberTable = new byte[BASELENGTH];
    static final private char[] lookUpHexAlphabet = new char[LOOKUPLENGTH];


    static {
        for (int i = 0; i < BASELENGTH; i++) {
            hexNumberTable[i] = -1;
        }
        for (int i = '9'; i >= '0'; i--) {
            hexNumberTable[i] = (byte) (i - '0');
        }
        for (int i = 'F'; i >= 'A'; i--) {
            hexNumberTable[i] = (byte) (i - 'A' + 10);
        }
        for (int i = 'f'; i >= 'a'; i--) {
            hexNumberTable[i] = (byte) (i - 'a' + 10);
        }

        for (int i = 0; i < 10; i++) {
            lookUpHexAlphabet[i] = (char) ('0' + i);
        }
        for (int i = 10; i <= 15; i++) {
            lookUpHexAlphabet[i] = (char) ('A' + i - 10);
        }
    }

    /**
     * Encode a byte array to hex string
     *
     * @param binaryData array of byte to encode
     * @return return encoded string
     */
    static public String encode(byte[] binaryData) {
        if (binaryData == null)
            return null;
        int lengthData = binaryData.length;
        int lengthEncode = lengthData * 2;
        char[] encodedData = new char[lengthEncode];
        int temp;
        for (int i = 0; i < lengthData; i++) {
            temp = binaryData[i];
            if (temp < 0)
                temp += 256;
            encodedData[i * 2] = lookUpHexAlphabet[temp >> 4];
            encodedData[i * 2 + 1] = lookUpHexAlphabet[temp & 0xf];
        }
        return new String(encodedData);
    }

    /**
     * Decode hex string to a byte array
     *
     * @param encoded encoded string
     * @return return array of byte to encode
     */
    static public byte[] decode(String encoded) {
        if (encoded == null)
            return null;
        int lengthData = encoded.length();
        if (lengthData % 2 != 0)
            return null;

        char[] binaryData = encoded.toCharArray();
        int lengthDecode = lengthData / 2;
        byte[] decodedData = new byte[lengthDecode];
        byte temp1, temp2;
        char tempChar;
        for (int i = 0; i < lengthDecode; i++) {
            tempChar = binaryData[i * 2];
            temp1 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
            if (temp1 == -1)
                return null;
            tempChar = binaryData[i * 2 + 1];
            temp2 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
            if (temp2 == -1)
                return null;
            decodedData[i] = (byte) ((temp1 << 4) | temp2);
        }
        return decodedData;
    }
}

