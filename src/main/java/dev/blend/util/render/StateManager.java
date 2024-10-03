package dev.blend.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.blend.util.interfaces.real.ICapabilityTracker;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.opengl.*;

import java.lang.reflect.Field;

public enum StateManager {

    INSTANCE;

    private boolean blendState, depthState, cullState, scissorState;

    private final ICapabilityTracker BLEND = getTracker("BLEND");
    private final ICapabilityTracker DEPTH = getTracker("DEPTH");
    private final ICapabilityTracker CULL = getTracker("CULL");
    private final ICapabilityTracker SCISSOR = getTracker("SCISSOR");

    private int lastActiveTexture = 0, lastProgram = 0, lastSampler = 0, lastVertexArray = 0, lastArrayBuffer = 0, lastBlendSrcRgb = 0, lastBlendDstRgb = 0, lastBlendSrcAlpha = 0, lastBlendDstAlpha = 0, lastBlendEquationRgb = 0, lastBlendEquationAlpha = 0;

    public void save() {
        depthState = DEPTH.get();
        blendState = BLEND.get();
        cullState = CULL.get();
        scissorState = SCISSOR.get();
        //
        lastActiveTexture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
        lastProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        lastSampler = GL11.glGetInteger(GL33.GL_SAMPLER_BINDING);
        lastArrayBuffer = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
        lastVertexArray = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);

        lastBlendSrcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB);
        lastBlendDstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB);
        lastBlendSrcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA);
        lastBlendDstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA);
        lastBlendEquationRgb = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_RGB);
        lastBlendEquationAlpha = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_ALPHA);
    }

    public void restore() {
        GL20.glUseProgram(lastProgram);
        GL33.glBindSampler(0, lastSampler);
        GL13.glActiveTexture(lastActiveTexture);
        GL30.glBindVertexArray(lastVertexArray);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, lastArrayBuffer);
        GL20.glBlendEquationSeparate(lastBlendEquationRgb, lastBlendEquationAlpha);
        GL14.glBlendFuncSeparate(lastBlendSrcRgb, lastBlendDstRgb, lastBlendSrcAlpha, lastBlendDstAlpha);
        //
        DEPTH.set(depthState);
        BLEND.set(blendState);
        CULL.set(cullState);
        SCISSOR.set(scissorState);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    private ICapabilityTracker getTracker(final String name) {
        try {
            final Class<?> glStateManager = GlStateManager.class;
            final Field field = glStateManager.getDeclaredField(name);
            field.setAccessible(true);
            final Object state = field.get(null);
            final String trackerName = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "com.mojang.blaze3d.platform.GlStateManager$class_1018");
            Field capStateField = null;
            for (Field f : state.getClass().getDeclaredFields()) {
                if (f.getType().getName().equals(trackerName)) {
                    capStateField = f;
                    break;
                }
            }
            assert capStateField != null;
            capStateField.setAccessible(true);
            return (ICapabilityTracker) capStateField.get(state);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Could NOT find the GL state tracker " + name + " in GLStateManager.", e);
        }
    }

}
