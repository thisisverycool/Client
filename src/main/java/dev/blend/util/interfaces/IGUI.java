package dev.blend.util.interfaces;

public interface IGUI {
    void init();
    void render(double mouseX, double mouseY);
    boolean click(double mouseX, double mouseY, int button);
    void release(double mouseX, double mouseY, int button);
    boolean key(double mouseX, double mouseY, int keyCode);
    void close();
}
