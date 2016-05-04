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
package cc.acquized.ultimatereport.sql;

import cc.acquized.ultimatereport.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

@SuppressWarnings("deprecation")
public class Connector {

    private HikariDataSource dataSource;

    private final String adress;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public Connector() {
        adress = Main.getConfig().getConfig().getString("MySQL.Adress");
        port = Main.getConfig().getConfig().getInt("MySQL.Port");
        database = Main.getConfig().getConfig().getString("MySQL.Database");
        username = Main.getConfig().getConfig().getString("MySQL.Username");
        password = Main.getConfig().getConfig().getString("MySQL.Password");
    }

    public void connect() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://" + adress + ":" + port + "/" + database);
        cfg.setDriverClassName("com.mysql.jdbc.Driver");
        cfg.setUsername(username);
        cfg.setPassword(password);
        cfg.setMinimumIdle(5);
        cfg.setMaximumPoolSize(50);
        cfg.setConnectionTimeout(3000);
        dataSource = new HikariDataSource(cfg);
    }

    public void disconnect() {
        if((dataSource != null) && (!dataSource.isClosed())) {
            dataSource.close();
        }
    }

    public void update(String qry) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(qry);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            SQLManager.close(conn, ps, null);
        }
    }

    public ResultSet query(String qry) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            SQLManager.close(conn, ps, rs);
        }
        return null;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
