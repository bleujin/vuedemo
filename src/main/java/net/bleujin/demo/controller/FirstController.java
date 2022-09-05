package net.bleujin.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import net.bleujin.demo.config.ConfigContext;
import net.ion.framework.db.Rows;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.Debug;

@Controller
public class FirstController {
//	
//    @RequestMapping(value="/")
//    public String index() {
//        return "index";
//    }
//    
	
	@Autowired
	private ConfigContext context ;

	@GetMapping(value = "/message") // , produces=MediaType.APPLICATION_JSON_VALUE
	@ResponseBody
	public Map<Integer, Object> testByResponseBody() {
		Map<Integer, Object> members = new HashMap<>();

		for (int i = 1; i <= 20; i++) {
			Map<String, Object> member = new HashMap<>();
			member.put("idx", i);
			member.put("nickname", i + "길동");
			member.put("height", i + 20);
			member.put("weight", i + 30);
			members.put(i, member);
		}
		
		
		Debug.line(context) ;
		
		return members;
	}


	@GetMapping(value = "/api/db", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String rowsByResponseBody() throws IOException, SQLException {

		Rows rows = context.dc().createUserProcedure("test@copyby(?)").addParam(5).execQuery();
		return new JsonStringHandler().handle(rows);
	}

	@PostMapping(value = "/api/query", produces = "application/json")
	@ResponseBody
	public String procQueryBy(@RequestBody HashMap<String, Object> body) throws SQLException {
		IUserProcedure uproc = context.dc().createUserProcedure(body.get("proc").toString());
		for (Object val : ((List) body.get("args"))) {
			Debug.debug(val, val.getClass());
			if (val instanceof List) {
				uproc.addParam(toArray((List) val));
			} else {
				uproc.addParam(val);
			}
		}

		if (body.get("update") != null) {
			int result = uproc.execUpdate();
			return new JsonObject().put("result", result).toString();
		} else {
			Rows rows = uproc.execQuery();
			return new JsonStringHandler().handle(rows);
		}
	}

	private Object[] toArray(List val) {
		List<?> list = (List) val;
		if (list != null && list.size() > 0) {
			Object firstEle = list.get(0);
			if (firstEle instanceof String) {
				return list.toArray(new String[0]);
			} else if (firstEle instanceof Integer || firstEle instanceof Long) {
				Long[] result = new Long[list.size()] ;
				for(int i = 0 ; i<list.size() ; i++) {
					result[i] = Long.parseLong(list.get(i).toString()) ;
				}
				return result;
			} else if (firstEle instanceof Boolean) {
				return list.toArray(new Boolean[0]);
			}
		}
		return list.toArray(new String[0]);
	}

}