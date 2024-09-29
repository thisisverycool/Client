package dev.blend.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.blend.Client;
import dev.blend.module.api.ModuleInfo;
import dev.blend.util.MC;
import dev.blend.util.misc.ValueHolder;
import dev.blend.value.Value;
import lombok.Getter;
import lombok.Setter;;

@Getter
public class Module extends ValueHolder implements MC {

    private final ModuleInfo moduleInfo;
    private boolean enabled;
    @Setter
    private int key;

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            this.moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
        } else {
            throw new IllegalStateException("@ModuleInfo not found on " + this.getClass().getSimpleName());
        }
    }

    public void onEnable() {}
    public void onDisable() {}

    public void toggle() {
        setEnabled(!this.enabled);
    }

    public void setEnabled(final boolean state) {
        if (this.enabled != state && moduleInfo.canBeEnabled()) {
            this.enabled = state;
            if (this.enabled) {
                onEnable();
                Client.instance.getEventBus().register(this);
            } else {
                Client.instance.getEventBus().unregister(this);
                onDisable();
            }
        }
    }

    public String getName() {
        return this.moduleInfo.names()[0];
    }

    public Value<?> getValue(final String name) {
        return values
                .stream()
                .filter(v -> v.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void loadConfig(final JsonObject object) {
        setEnabled(object.get("state").getAsBoolean());
        setKey(object.get("key").getAsInt());
        final JsonArray values = object.get("values").getAsJsonArray();
        if (values != null) {
            for (JsonElement valueElement : values) {
                JsonObject valueObject = valueElement.getAsJsonObject();
                if (valueObject != null) {
                    Value<?> value = getValue(valueObject.get("name").getAsString());
                    if (value != null) {
                        value.loadJsonObject(valueObject);
                    }
                }
            }
        }
    }

    public JsonObject getConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("name", getName());
        object.addProperty("state", enabled);
        object.addProperty("key", key);
        JsonArray values = new JsonArray();
        for (Value<?> v : this.values) {
            values.add(v.getConfig());
        }
        object.add("values", values);
        return object;
    }

}
