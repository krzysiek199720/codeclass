package com.github.krzysiek199720.codeclass.core.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class FlywayConfig {

	@Value("${codeclass.db.migration.enable:false}")
	private boolean migrating;

	@Value("${codeclass.db.migration.throw:true}")
	private boolean throwing;

	@Bean
	public FlywayMigrationStrategy defaultMigrationStrategy(){
		FlywayMigrationStrategy strategy = new FlywayMigrationStrategy(){
			@Override
			public void migrate(Flyway flyway){
				if(migrating)
					flyway.migrate();
				else{
					try{
						flyway.validate();
					} catch (FlywayException exc){
						if(throwing)
							throw exc;
						log.error("Database did not pass migration verification!!!", exc);
					}
				}
			}
		};

		return strategy;
	}

}
