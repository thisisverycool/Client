package dev.blend.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.blend.util.MC;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.Library;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_BINDING;

@UtilityClass
public class RenderUtil implements MC {

    @Getter
    private DirectContext context;
    private BackendRenderTarget target;
    private Surface surface;
    @Getter
    private Canvas canvas;
    private Framebuffer framebuffer;

    public void makeContext() {
        initFramebuffer();
        context = DirectContext.makeGL();
    }

    public void resize() {
        initSkia();
        framebuffer.delete();
        initFramebuffer();
    }

    public int save() {
        return canvas.save();
    }
    public void restore() {
        canvas.restore();
    }
    public void restore(int saveState) {
        canvas.restoreToCount(saveState);
    }

    public void flush() {
        context.flush();
    }
    public void clearAllGL() {
        context.resetGLAll();
    }

    public void preRender() {
        
    }
    public void postRender() {

    }

    /**
     * DO NOT INTERFERE WITH ANY OF THIS!
     */
    private void initFramebuffer() {
        framebuffer = new SimpleFramebuffer(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, false);
    }

    /**
     * DO NOT INTERFERE WITH ANY OF THIS!
     */
    private void initSkia() {
        if (surface != null)
            surface.close();
        if (target != null)
            target.close();
        target = BackendRenderTarget.makeGL(
                mc.getWindow().getWidth(),
                mc.getWindow().getHeight(),
                0,
                8,
                glGetInteger(GL_FRAMEBUFFER_BINDING),
                FramebufferFormat.GR_GL_RGBA8
        );
        surface = Surface.wrapBackendRenderTarget(
                context,
                target,
                SurfaceOrigin.BOTTOM_LEFT,
                SurfaceColorFormat.RGBA_8888,
                ColorSpace.getSRGB()
        );
        canvas = surface.getCanvas();
    }

}
