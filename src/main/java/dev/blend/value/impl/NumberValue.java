package dev.blend.value.impl;

import com.google.gson.JsonObject;
import dev.blend.util.misc.ValueHolder;
import dev.blend.value.Value;
import lombok.Getter;

import java.util.function.BooleanSupplier;

@Getter
public class NumberValue extends Value<Number> {

    private final Number min, max, increment;

    public NumberValue(String name, ValueHolder parent, Number defaultValue, Number min, Number max, Number increment) {
        super(name, parent, defaultValue);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public NumberValue(String name, ValueHolder parent, BooleanSupplier visibility, Number defaultValue, Number min, Number max, Number increment) {
        super(name, parent, visibility, defaultValue);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    @Override
    public void setValue(Number value) {
        double precision = 1 / increment.doubleValue();
        this.value = Math.max(min.doubleValue(), Math.min(max.doubleValue(), Math.round(value.doubleValue() * precision) / precision));
    }

    @Override
    public void loadJsonObject(JsonObject object) {
        setValue(object.get("value").getAsDouble());
    }

    @Override
    public JsonObject getConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("value", value);
        return object;
    }

    public String getStringValue() {
        if (increment.doubleValue() % 1.0 == 0.0) {
            return String.valueOf(value.intValue());
        } else {
            return String.valueOf(value.doubleValue());
        }
    }

}
