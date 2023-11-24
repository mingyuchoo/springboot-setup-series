package com.mingyuchoo.reactive;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DBConfig extends AbstractR2dbcConfiguration {

   @Value("${spring.r2dbc.url}")
   private String r2dbcUrl;

   @Value("${sql.schema}")
   private String schema;

   @Value("${sql.data}")
   private String data;

   @Override
   public ConnectionFactory connectionFactory () {
       return ConnectionFactories.get(r2dbcUrl);
   }

   @Bean
    public ConnectionFactoryInitializer initializer (ConnectionFactory connectionFactory) {
       ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
       initializer.setConnectionFactory(connectionFactory);

       CompositeDatabasePopulator databasePopulator = new CompositeDatabasePopulator();
       databasePopulator.addPopulators(new ResourceDatabasePopulator((new ClassPathResource(schema))));
       databasePopulator.addPopulators(new ResourceDatabasePopulator((new ClassPathResource(data))));

       initializer.setDatabasePopulator(databasePopulator);

       return initializer;
   }
}
