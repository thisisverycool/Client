package dev.blend.value.impl;

import com.google.gson.JsonObject;
import dev.blend.util.misc.ValueHolder;
import dev.blend.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ModeValue extends Value<String> {

    private final List<String> modes = new ArrayList<>();

    public ModeValue(String name, ValueHolder parent, String defaultValue) {
        super(name, parent, defaultValue);
        modes.add(defaultValue);
        setValue(defaultValue);
    }

    public ModeValue(String name, ValueHolder parent, BooleanSupplier visibility, String defaultValue) {
        super(name, parent, visibility, defaultValue);
        modes.add(defaultValue);
        setValue(defaultValue);
    }

    public ModeValue add(String mode) {
        if (!modes.contains(mode))
            modes.add(mode);
        return this;
    }

    @Override
    public String getValue() {
        if (!modes.contains(value))
            setValue(defaultValue);
        return value;
    }

    @Override
    public void setValue(String value) {
        if (modes.contains(value)) {
            this.value = value;
        } else {
            this.value = defaultValue;
        }
    }

    @Override
    public void loadJsonObject(JsonObject object) {
        setValue(object.get("value").getAsString());
    }

    @Override
    public JsonObject getConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("value", value);
        return object;
    }

    public void next() {
        if (!value.equalsIgnoreCase(modes.getLast())) {
            value = modes.get(modes.indexOf(value) + 1);
        } else {
            value = modes.getFirst();
        }
    }

    public void previous() {
        if (!value.equalsIgnoreCase(modes.getFirst())) {
            value = modes.get(modes.indexOf(value) - 1);
        } else {
            value = modes.getLast();
        }
    }

    public boolean is(String mode) {
        return getValue().equalsIgnoreCase(mode);
    }
    public boolean isNot(String mode) {
        return !getValue().equalsIgnoreCase(mode);
    }

}
