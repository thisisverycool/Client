package dev.blend.ui;

import dev.blend.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class TestGUI extends Screen {

    public TestGUI() {
        super(Text.of("Test"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtil.preRender();
        RenderUtil.rect(20, 20, 100, 100, Color.white);
        RenderUtil.postRender();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
