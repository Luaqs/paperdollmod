package dev.luaq.playerhudrender.gui.component;

import net.minecraft.client.gui.GuiScreen;

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
public class PHSlider extends GuiScreen {

    private int x, y, w, h, min, max, value;
    private int dragY;
    private boolean isClicked = false;

    public PHSlider(int x, int y, int w, int h, int min, int max, int value) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        y = mouseY - dragY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0) {
            return;
        }

        if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + height) {
            dragY = mouseY - y;
            this.isClicked = true;
        }

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.isClicked = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
