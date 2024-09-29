package dev.blend.config;

import com.google.gson.*;
import dev.blend.Client;
import dev.blend.module.Module;
import dev.blend.util.file.FileSystem;
import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConfigManager {

    private final List<File> configs = new ArrayList<>();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public ConfigManager() {
        FileSystem.makeDirs();
        discoverConfigs();
    }

    public void discoverConfigs() {

    }

    public boolean load(final String name) {
        discoverConfigs();
        final File file = new File(FileSystem.configDir + File.separator + name + ".json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                final JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                if (root != null && root.get("client").getAsString().equalsIgnoreCase(Client.name) && root.get("version").getAsDouble() <= Double.parseDouble(Client.version)) {
                    JsonArray modules = root.getAsJsonArray("modules");
                    for (JsonElement thinkingEmoji : modules) {
                        JsonObject module = thinkingEmoji.getAsJsonObject();
                        if (module != null) {
                            final Module theModuleFr = Client.instance.getModuleManager().getModule(module.get("name").getAsString());
                            if (theModuleFr != null) {
                                theModuleFr.loadConfig(module);
                            }
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                Client.logger.warn("Error loading config.", e.getCause());
                return false;
            }
        }
        return false;
    }

    public boolean save(final String name) {
        return save(name, true);
    }

    public boolean save(final String name, final boolean overwrite) {
        discoverConfigs();
        final File file = new File(FileSystem.configDir + File.separator + name + ".json");
        try  {
            if ((overwrite && file.exists()) || !file.exists()) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                JsonObject root = new JsonObject();
                root.addProperty("client", Client.name);
                root.addProperty("version", Client.version);
                JsonArray modules = new JsonArray();
                for (Module m : Client.instance.getModuleManager().getModules()) {
                    modules.add(m.getConfig());
                }
                root.add("modules", modules);
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(root, writer);
                }
                return true;
            }
        } catch (Exception e) {
            Client.logger.warn("Error saving config.", e.getCause());
        }
        return false;
    }

    public File getConfigFile(final String name) {
        return configs.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name + ".json"))
                .findFirst()
                .orElse(null);
    }

    public boolean openFolder() {
        try {
            if (FileSystem.configDir.exists()) {
                Desktop.getDesktop().open(FileSystem.configDir);
                return true;
            }
        } catch (Exception e) {
            Client.logger.warn("Couldn't open config folder.", e.getCause());
        }
        return false;
    }

}
