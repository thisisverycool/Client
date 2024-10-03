package dev.blend.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.blend.util.MC;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.Rect;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

@UtilityClass
public class RenderUtil implements MC {

    @Getter
    private DirectContext context;
    private BackendRenderTarget target;
    private Surface surface;
    @Getter
    private Canvas canvas;
    private Framebuffer framebuffer;
    private int fbo;

    public void makeContext() {
        initFrameBuffer();
        context = DirectContext.makeGL();
    }

    public void resize() {
        initSkia();
        framebuffer.delete();
        initFrameBuffer();
    }

    public void rect(Number x, Number y, Number width, Number height, Color color) {
        try (
                Paint paint = new Paint()
                        .setAntiAlias(true)
                        .setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue())
        ) {
            getCanvas().drawRect(Rect.makeXYWH(x.floatValue(), y.floatValue(), width.floatValue(), height.floatValue()), paint);
        }
    }
    public void rect(Number x, Number y, Number width, Number height, Shader shader) {
        try (
                Paint paint = new Paint()
                        .setAntiAlias(true)
                        .setShader(shader)
        ) {
            getCanvas().drawRect(Rect.makeXYWH(x.floatValue(), y.floatValue(), width.floatValue(), height.floatValue()), paint);
        }
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
        fbo = glGetInteger(GL_DRAW_FRAMEBUFFER_BINDING);
        StateManager.INSTANCE.save();
        clearAllGL();
    }
    public void postRender() {
        flush();
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
        pre();
        framebuffer.draw(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
        StateManager.INSTANCE.restore();
        post();
    }

    private void pre() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL_LESS);
        RenderSystem.clear(GL_DEPTH_BUFFER_BIT, false);
    }
    private void post() {
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
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

    private void initFrameBuffer() {
        framebuffer = new SimpleFramebuffer(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight(), true, false);
    }

}
