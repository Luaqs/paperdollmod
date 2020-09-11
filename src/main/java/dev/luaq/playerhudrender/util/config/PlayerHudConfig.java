package dev.luaq.playerhudrender.util.config;

import dev.luaq.playerhudrender.PHRenderMod;

import java.io.*;
import java.util.logging.Logger;

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
public class PlayerHudConfig {

    private final File configFile;

    private int x = 30;
    private int y = 60;
    private int scale = 25;
    private boolean onlySprinting = false;
    private boolean isHUDVisible = true;

    public PlayerHudConfig() {
        this(PHRenderMod.INSTANCE.getConfigFile());
    }

    public PlayerHudConfig(File configFile) {
        this.configFile = configFile;
        if (!configFile.exists()) {
            if (createConfig()) writeConfigValues();
        }
        readConfig();
    }

    /**
     * Update config class values.
     */
    public void readConfig() {
        try {
            FileReader reader = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            try {
                x = Integer.parseInt(bufferedReader.readLine());
                y = Integer.parseInt(bufferedReader.readLine());
                scale = Integer.parseInt(bufferedReader.readLine());
                onlySprinting = Boolean.parseBoolean(bufferedReader.readLine());
                isHUDVisible = Boolean.parseBoolean(bufferedReader.readLine());
            }
            catch (Exception e) {
            }

            bufferedReader.close();
            reader.close();
        }
        catch (IOException e) {
        }
    }

    /**
     * Updates config file.
     */
    public void writeConfigValues() {
        try {
            FileWriter writer = new FileWriter(configFile, false);
            writer.write(x + "\n");
            writer.write(y + "\n");
            writer.write(scale + "\n");
            writer.write(onlySprinting + "\n");
            writer.write(String.valueOf(isHUDVisible));
            writer.close();
        }
        catch (IOException e) {
            Logger.getGlobal().severe("Unable to write config values.");
        }
    }

    private boolean createConfig() {
        try {
            if (configFile.exists()) {
                return true;
            }
            if (!configFile.createNewFile()) {
                Logger.getGlobal().severe("Unable to create config, default values it is!");
            }
        }
        catch (IOException e) {
        }
        return false;
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

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isOnlySprinting() {
        return onlySprinting;
    }

    public void setOnlySprinting(boolean onlySprinting) {
        this.onlySprinting = onlySprinting;
    }

    public boolean isHUDVisible() {
        return isHUDVisible;
    }

    public void setHUDVisible(boolean HUDVisible) {
        isHUDVisible = HUDVisible;
    }

}
