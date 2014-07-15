package com.camel.highavailable.hajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import net.sf.hajdbc.SimpleDatabaseClusterConfigurationFactory;
import net.sf.hajdbc.TransactionMode;
import net.sf.hajdbc.cache.simple.SimpleDatabaseMetaDataCacheFactory;
import net.sf.hajdbc.dialect.mysql.MySQLDialectFactory;
import net.sf.hajdbc.distributed.jgroups.JGroupsCommandDispatcherFactory;
import net.sf.hajdbc.sql.DataSource;
import net.sf.hajdbc.sql.DriverDatabase;
import net.sf.hajdbc.sql.DriverDatabaseClusterConfiguration;
import net.sf.hajdbc.sql.TransactionModeEnum;
import net.sf.hajdbc.state.simple.SimpleStateManagerFactory;

public class HaJDBCMain {

    public static void main(String[] args) {
        try {
         // Define each database in the cluster
            DriverDatabase db1 = new DriverDatabase();
            db1.setId("db1");
            db1.setLocation("jdbc:mysql://192.168.137.100:3306/repl_test?useUnicode=true&amp;characterEncoding=utf-8");
            db1.setUser("root");
            db1.setPassword("123456");
            db1.setWeight(0);

            DriverDatabase db2 = new DriverDatabase();
            db2.setId("db2");
            db2.setLocation("jdbc:mysql://192.168.137.101:3306/repl_test?useUnicode=true&amp;characterEncoding=utf-8");
            db2.setUser("root");
            db2.setPassword("123456");
            db1.setWeight(1);
//            DataSource ds = new DataSource();
//            ds.setConfig("");
            // Define the cluster configuration itself
            DriverDatabaseClusterConfiguration config = new DriverDatabaseClusterConfiguration();
            // Specify the database composing this cluster
            config.setDatabases(Arrays.asList(db1, db2));
            // Define the dialect
            config.setDialectFactory(new MySQLDialectFactory());
            // Don't cache any meta data
            config.setDatabaseMetaDataCacheFactory(new SimpleDatabaseMetaDataCacheFactory());
            // Use an in-memory state manager
            config.setStateManagerFactory(new SimpleStateManagerFactory());
            // Make the cluster distributable
            config.setDispatcherFactory(new JGroupsCommandDispatcherFactory());
            config.setTransactionMode(TransactionModeEnum.SERIAL);

            // Register the configuration with the HA-JDBC driver
            net.sf.hajdbc.sql.Driver.setConfigurationFactory("mycluster", new SimpleDatabaseClusterConfigurationFactory<java.sql.Driver, DriverDatabase>(config));
            
            // Database cluster is now ready to be used!
            Connection conn = DriverManager.getConnection("jdbc:ha-jdbc:mycluster", "root", "123456");

            //Connection conn = DriverManager.getConnection("jdbc:ha-jdbc:mycluster", "root", "123456");
            PreparedStatement st = conn.prepareStatement("select * from repl_user");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                StringBuffer sb = new StringBuffer();
                sb.append(rs.getInt("id")).append("::").append(rs.getString("name")).append("::").append(
                        rs.getInt("age")).append("::").append(rs.getInt("sex"));
                System.out.println(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void defineDB (){
     
    }

}
