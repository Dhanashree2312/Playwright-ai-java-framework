package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    private static final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    private JsonUtils() {
    }

    public static String toJson(Object object) {

        return gson.toJson(object);
    }

    public static <T> T fromJson(
            String json,
            Class<T> classType) {

        return gson.fromJson(
                json,
                classType
        );
    }
}