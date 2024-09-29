package dev.blend.util.file;

import dev.blend.Client;
import dev.blend.util.MC;
import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FileSystem implements MC {

    public final File clientDir = new File(mc.runDirectory + File.separator + Client.name.toLowerCase());
    public final File configDir = new File(clientDir, "configs");

    public void makeDirs() {
        if (!clientDir.exists())
            clientDir.mkdir();
        if (!configDir.exists())
            configDir.mkdir();
    }

}
