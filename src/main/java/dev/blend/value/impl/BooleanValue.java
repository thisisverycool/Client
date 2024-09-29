package dev.blend.value.impl;

import com.google.gson.JsonObject;
import dev.blend.util.misc.ValueHolder;
import dev.blend.value.Value;

import java.util.function.BooleanSupplier;

public class BooleanValue extends Value<Boolean> {

    public BooleanValue(String name, ValueHolder parent, Boolean defaultValue) {
        super(name, parent, defaultValue);
    }

    public BooleanValue(String name, ValueHolder parent, BooleanSupplier visibility, Boolean defaultValue) {
        super(name, parent, visibility, defaultValue);
    }

    @Override
    public void loadJsonObject(JsonObject object) {
        setValue(object.get("value").getAsBoolean());
    }

    @Override
    public JsonObject getConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("value", value);
        return object;
    }

    public void toggle() {
        setValue(!value);
    }

}
