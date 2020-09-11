package dev.luaq.playerhudrender.gui;

import dev.luaq.playerhudrender.PHRenderMod;
import dev.luaq.playerhudrender.util.config.PlayerHudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Copyright (c) 2019 luaq.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PHRendererOverlay {

    private final Minecraft minecraft;

    public PHRendererOverlay() {
        minecraft = Minecraft.getMinecraft();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE || this.minecraft.gameSettings.showDebugInfo || this.minecraft.currentScreen instanceof PHGui) {
            return;
        }
        PlayerHudConfig config = PHRenderMod.INSTANCE.getConfig();
        if (config.isOnlySprinting() && !minecraft.thePlayer.isSprinting()) {
            return;
        }
        int x = config.getX();
        int y = config.getY();
        int scale = config.getScale();
        renderOverlay(x, y, scale);
    }

    /*
     * The comments in this method are my best estimate as to what the GuiInventory#drawEntityOnScreen method was doing
     * I also tried to clean up the method a bit.
     */
    public void renderOverlay(float x, float y, float scale) {
        EntityLivingBase ent = minecraft.thePlayer;
        ScaledResolution resolution = new ScaledResolution(minecraft);

        // enable rendering (transform)
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 50.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        // store og values
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (15 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);

        // assign temp values for rendering
        ent.renderYawOffset = (float) Math.atan(30 / 40.0F) * 20.0F;
        ent.rotationYaw = (float) Math.atan(30 / 40.0F) * 40.0F;
        if (x < resolution.getScaledWidth() / 2) {
            ent.renderYawOffset *= -1;
            ent.rotationYaw *= -1;
        }
        ent.rotationPitch = 0;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;

        // render the player
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);

        // reassign values
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;

        // return back to normal rendering
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }


}
