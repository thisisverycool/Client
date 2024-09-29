package dev.blend.util.misc;

import dev.blend.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class ValueHolder {
    public final List<Value<?>> values = new ArrayList<>();
}
