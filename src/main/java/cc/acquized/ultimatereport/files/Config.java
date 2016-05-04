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
package cc.acquized.ultimatereport.files;

import cc.acquized.ultimatereport.Main;
import cc.acquized.ultimatereport.files.framework.CustomFile;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.util.Objects;

public class Config extends CustomFile {

    public Config() {
        super(new File(Main.getInstance().getDataFolder(), "config.yml"));
        load();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void load() {
        super.load();
        if(!Objects.equals(getConfig().getString("UltimateReport.Version"), Main.getInstance().getDescription().getVersion())) {
            file.renameTo(new File(Main.getInstance().getDataFolder(), "config-v" + getConfig().getString("UltimateReport.Version") + ".yml"));
            load();
        }
        Main.pr = ChatColor.translateAlternateColorCodes('&', getConfig().getString("UltimateReport.Prefix"));
    }

}
