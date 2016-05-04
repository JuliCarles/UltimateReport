/* Copyright 2016 Acquized
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.acquized.ultimatereport.files.framework;

import cc.acquized.ultimatereport.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings({"ResultOfMethodCallIgnored", "deprecation"})
public class CustomFile {

    public File file;
    public Configuration config;

    public CustomFile(File file) {
        this.file = file;
    }

    /**
     * Loads the Config and if it doesn't exists, writes it and loads it then.
     */
    public void load() {
        try {
            if(!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()) {
                Files.copy(Main.getInstance().getResourceAsStream(file.getName()), file.toPath());
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ex) {
            ProxyServer.getInstance().getConsole().sendMessage("[UltimateReport] An error occured.");
            ex.printStackTrace();
        }
    }

    /**
     * Saves the current Config
     */
    public void save() {
        try {
            if((config != null) && (file != null)) {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
            }
        } catch (IOException ex) {
            ProxyServer.getInstance().getConsole().sendMessage("[UltimateReport] An error occured.");
            ex.printStackTrace();
        }
    }

    /**
     * Reloads the current config
     */
    public void reload() {
        save();
        load();
    }

    @NotNull
    public Configuration getConfig() {
        return config;
    }

    @NotNull
    public File getFile() {
        return file;
    }

}
