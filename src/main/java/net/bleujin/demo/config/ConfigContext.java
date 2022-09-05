package net.bleujin.demo.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import net.ion.framework.db.DBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.PostSqlDBManager;
import net.ion.framework.util.Debug;


@Component
public class ConfigContext {

	@Value("${user.postgres.jdbc.url}")
	private String jdbcurl ;
	
	@Value("${user.postgres.jdbc.id}")
	private String jdbcid ;
	
	@Value("${user.postgres.jdbc.pwd}")
	private String jdbcpwd ;
	
	
	private DBController dc ;
	public DBController dc(){
		return dc;
	}

	
	@PostConstruct
	public void init() throws SQLException {
		DBManager dbm = new PostSqlDBManager(jdbcurl, jdbcid, jdbcpwd);
		DBController dc = new DBController(dbm);
		dc.initSelf();
		
		Debug.line(jdbcurl);
		this.dc = dc ;
	}

	@PreDestroy
	public void destroy() throws IOException {
		dc.close();
	}
	
	
}
