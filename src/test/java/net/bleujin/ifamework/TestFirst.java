package net.bleujin.ifamework;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;
import net.ion.framework.db.DBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.PostSqlDBManager;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;

public class TestFirst extends TestCase {

	
	private DBController dc;

	@BeforeEach
	protected void setUp() throws SQLException {
		DBManager dbm = new PostSqlDBManager("jdbc:postgresql://127.0.0.1:5432/ics6", "postgres", "redf") ;
		dc = new DBController(dbm) ;
		dc.initSelf() ;
	}
	
	@AfterEach
	protected void tearDown() throws Exception {
		dc.close();
	}
	
	@Test
	public void testQuery() throws Exception {
		// dc.getRows("select * from copy_tbl").debugPrint();
		// test$firstfn('bleujin', 3, true, string_to_array2('abc,def',','), '{"a":3, "b": "def"}'::jsonb ) ;
		Rows rows = dc.createUserProcedure("test@firstfn('bleujin', 3, true, array['abc','def'], '{\"a\":3, \"b\": \"def\"}'::jsonb)").execQuery() ; 
		rows.debugPrint();
	}
	

	@Test
	public void testDB() throws Exception {
		Rows rows = dc.createUserProcedure("test@firstfn('bleujin', 3, true, array['abc','def'], '{\"a\":3, \"b\": \"def\"}'::jsonb)").execQuery() ;
		rows.debugPrint();
		
	}
	
	@Test
	public void stringFormat() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("value", "bleujin");
		values.put("column", 3);
		StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
		String result = sub.replace("There's an incorrect value '%(value)' in column # %(column)");
		Debug.line(result) ;
	}
	
	@Test
	public void elementType() throws Exception {
		List list = ListUtil.toList(1, 3) ;
		Type type = list.getClass().getGenericSuperclass();
		Debug.line(type);
		
		Debug.line(list.get(0) instanceof String, list.get(0) instanceof Integer, list.get(0) instanceof Boolean) ; 
        
		// Debug.line( ((ParameterizedType)list) ) ; 

	}
	
	
	
	
	
	
	
}
