package net.bleujin.demo.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.ion.framework.db.DBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.PostSqlDBManager;

@Configuration
public class DCConfig {

	private DBController dc;
	
	DCConfig() {
		DBManager dbm = new PostSqlDBManager("jdbc:postgresql://127.0.0.1:5432/ics6", "postgres", "redf");
		DBController dc = new DBController(dbm);
		this.dc = dc ;
		
	}
	
	@Bean
	public DBController dc(){
		return dc;
	}

	@PostConstruct
	public void init() throws SQLException {
		dc.initSelf();
	}

	@PreDestroy
	public void destroy() throws IOException {
		dc.close();
	}
}
