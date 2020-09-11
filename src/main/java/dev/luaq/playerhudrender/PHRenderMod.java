package dev.luaq.playerhudrender;

import dev.luaq.playerhudrender.command.PlayerHudCmd;
import dev.luaq.playerhudrender.gui.PHRendererOverlay;
import dev.luaq.playerhudrender.util.config.PlayerHudConfig;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

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
@Mod(modid = "PHRenderMod", version = "1.0")
public class PHRenderMod {

    @Mod.Instance
    public static PHRenderMod INSTANCE;

    private File configFile;
    private PHRendererOverlay playerRenderer;
    private PlayerHudConfig config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE = this;

        configFile = event.getSuggestedConfigurationFile();
        config = new PlayerHudConfig(configFile);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        playerRenderer = new PHRendererOverlay();

        MinecraftForge.EVENT_BUS.register(playerRenderer);
        ClientCommandHandler.instance.registerCommand(new PlayerHudCmd());
    }

    public File getConfigFile() {
        return configFile;
    }

    public PlayerHudConfig getConfig() {
        return config;
    }

    public PHRendererOverlay getPlayerRenderer() {
        return playerRenderer;
    }

}
