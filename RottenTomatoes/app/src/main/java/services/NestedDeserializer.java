package services;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by logan on 2/15/16.
 */

public class NestedDeserializer<T> implements JsonDeserializer<T> {

    private Class<T> mClass;
    private String mKey;

    public NestedDeserializer(Class<T> targetClass, String key) {
        mClass = targetClass;
        mKey = key;
    }

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        JsonElement content = je.getAsJsonObject().get(mKey);
        return new Gson().fromJson(content, mClass);

    }
}