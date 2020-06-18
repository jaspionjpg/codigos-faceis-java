package gsonutil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;

public class GsonUtil {

    private static final Gson GSON = new Gson();

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

    private GsonUtil() {

    }

    public static Gson gson() {
        return GSON;
    }

    public static GsonBuilder gsonBuilder() {
        return GSON_BUILDER;
    }

    public static JsonObject toJsonObject(String json) {
        if (StringUtils.isBlank(json)) {
            return new JsonObject();
        }

        JsonObject jsonObject = convert(json, JsonObject.class);

        return jsonObject;
    }

    public static <T> T toType(String json, Class<T> type) {
        if (StringUtils.isBlank(json) || type == null) {
            return null;
        }

        T t = convert(json, type);

        return t;
    }

    public static <T> T toType(JsonElement jsonElement, Class<T> type) {
        if (jsonElement == null || type == null) {
            return null;
        }

        T t = convert(jsonElement, type);

        return t;
    }

    public static <T> T toType(String attribute, JsonObject jsonObject, Class<T> type) {
        if (StringUtils.isBlank(attribute) || jsonObject == null || type == null || isNullAttribute(attribute, jsonObject)) {
            return null;
        }

        T t = convert(jsonObject.get(attribute), type);

        return t;
    }

    public static JsonArray toJsonArray(String attributte, JsonObject jsonObject) {
        if (StringUtils.isBlank(attributte) || jsonObject == null || !isArrayAttribute(attributte, jsonObject)) {
            return new JsonArray();
        }

        return jsonObject.getAsJsonArray(attributte);
    }

    public static boolean hasAttributte(String attribute, JsonObject jsonObject) {
        if (StringUtils.isBlank(attribute) || jsonObject == null) {
            return false;
        }

        return has(attribute, jsonObject);
    }

    public static boolean notHasAttributte(String attribute, JsonObject jsonObject) {
        return !hasAttributte(attribute, jsonObject);
    }

    public static boolean isArrayAttribute(String attribute, JsonObject jsonObject) {
        if (StringUtils.isBlank(attribute) || jsonObject == null || notHasAttributte(attribute, jsonObject) || isNullAttribute(attribute, jsonObject)) {
            return false;
        }

        return isArray(jsonObject.get(attribute));
    }

    public static boolean isNullAttribute(String attribute, JsonObject jsonObject) {
        if (StringUtils.isBlank(attribute) || jsonObject == null || notHasAttributte(attribute, jsonObject)) {
            return true;
        }

        return isNull(jsonObject.get(attribute));
    }

    public static boolean isObjectAttribute(JsonElement jsonElement) {
        if (jsonElement == null) {
            return false;
        }

        return isObject(jsonElement);
    }

    private static <T> T convert(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    private static <T> T convert(JsonElement jsonElement, Class<T> type) {
        return GSON.fromJson(jsonElement, type);
    }

    private static boolean has(String attribute, JsonObject jsonObject) {
        return jsonObject.has(attribute);
    }

    private static boolean isNull(JsonElement jsonElement) {
        return jsonElement.isJsonNull();
    }

    private static boolean isArray(JsonElement jsonElement) {
        return jsonElement.isJsonArray();
    }

    private static boolean isObject(JsonElement jsonElement) {
        return jsonElement.isJsonObject();
    }

}
