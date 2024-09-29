package dev.blend.value;

import com.google.gson.JsonObject;
import dev.blend.util.misc.ValueHolder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BooleanSupplier;

@Getter
@Setter
public abstract class Value<T> {

    protected final String name;
    protected final ValueHolder parent;
    protected final BooleanSupplier visibility;
    protected final T defaultValue;
    protected T value;

    public Value(String name, ValueHolder parent, T defaultValue) {
        this(name, parent, () -> true, defaultValue);
    }
    public Value(String name, ValueHolder parent, BooleanSupplier visibility, T defaultValue) {
        this.name = name;
        this.parent = parent;
        this.visibility = visibility;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.parent.values.add(this);
    }

    public void reset() {
        setValue(defaultValue);
    }

    public abstract void loadJsonObject(final JsonObject object);
    public abstract JsonObject getConfig();

}
