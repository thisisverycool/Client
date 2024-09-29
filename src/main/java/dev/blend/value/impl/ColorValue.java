package dev.blend.value.impl;

import java.awt.*;
import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import dev.blend.util.misc.ValueHolder;
import dev.blend.value.Value;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorValue extends Value<Color> {

    private float hue = 0;
    private float saturation = 1;
    private float brightness = 1;

    public ColorValue(String name, ValueHolder parent, Color defaultValue) {
        super(name, parent, defaultValue);
        setValue(defaultValue);
    }

    public ColorValue(String name, ValueHolder parent, BooleanSupplier visibility, Color defaultValue) {
        super(name, parent, visibility, defaultValue);
        setValue(defaultValue);
    }

    @Override
    public Color getValue() {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    @Override
    public void setValue(Color color) {
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
    }

    public void setValue(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    @Override
    public void loadJsonObject(JsonObject object) {
        setHue(object.get("hue").getAsFloat());
        setSaturation(object.get("saturation").getAsFloat());
        setBrightness(object.get("brightness").getAsFloat());
    }

    @Override
    public JsonObject getConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("hue", hue);
        object.addProperty("brightness", brightness);
        object.addProperty("saturation", saturation);
        return object;
    }

}
