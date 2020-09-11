package dev.luaq.playerhudrender.gui;

import dev.luaq.playerhudrender.PHRenderMod;
import dev.luaq.playerhudrender.util.config.PlayerHudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

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
public class PHGui extends GuiScreen {

    private int dragX, dragY;
    private boolean isClicked;

    private int newX, newY, newScale;
    private boolean newOnlySprint, newIsHUDVisible;
    private PlayerHudConfig config;

    public PHGui() {
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        buttonList.clear();

        buttonList.add(new GuiButton(2, this.width / 2 - 120 / 2, this.height / 2 - 10, 120, 20, "Toggle Only Sprinting"));
        buttonList.add(new GuiButton(1, this.width / 2 - 120 / 2, this.height / 2 + 10, 120, 20, "Save Config"));

        config = PHRenderMod.INSTANCE.getConfig();
        newX = config.getX();
        newY = config.getY();
        newScale = config.getScale();
        newOnlySprint = config.isOnlySprinting();
        newIsHUDVisible = config.isHUDVisible();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        PHRendererOverlay overlay = PHRenderMod.INSTANCE.getPlayerRenderer();
        FontRenderer renderer = mc.fontRendererObj;
        drawDefaultBackground();

        if (isClicked) {
            newX = mouseX - dragX;
            newY = mouseY - dragY;
        }

        overlay.renderOverlay(newX, newY, newScale);
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawString(renderer, "HUD is " + ((config.isHUDVisible()) ? "VISIBLE" : "INVISIBLE"), 5, this.height - 5 + renderer.FONT_HEIGHT * 2, 0xffffff);
        drawString(renderer, "PlayerHudRender by luaq.", 5, this.height - 5 + renderer.FONT_HEIGHT, 0xffffff);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            saveConfig();
            return;
        }
        if (button.id == 2) {
            newOnlySprint = !newOnlySprint;
        }
    }

    private void saveConfig() {
        config.setX(newX);
        config.setY(newY);
        config.setScale(newScale);
        config.setOnlySprinting(newOnlySprint);

        PHRenderMod.INSTANCE.getConfig().writeConfigValues();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton != 0) {
            return;
        }
        if (mouseX > newX - 15 && mouseX < newX - 15 + 30 && mouseY > newY - 50 && mouseY < newY - 45 + 50) {
            dragX = mouseX - newX;
            dragY = mouseY - newY;
            isClicked = true;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        isClicked = false;

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
//        saveConfig(); may annoy people because there is a temporary freeze every time you close the gui
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        mc.displayGuiScreen(this);

        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
