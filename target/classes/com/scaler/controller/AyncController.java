package com.scaler.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scaler.entity.Base;
import com.scaler.entity.User;
import com.scaler.service.UserService;

@Controller
public class AyncController {

	@Autowired
	private UserService userService;

	@GetMapping("/downdata")
	@ResponseBody
	public Object downdata(Authentication authentication) {
		JSONObject json = new JSONObject();
//			Map map = new Map();
		User theUser = userService.findByName(authentication.getName());
		json.put("data", theUser.getData());

		return json;
	}

	@RequestMapping("/updata")
	@ResponseBody
	public Object updata(Authentication authentication, @RequestBody JSONObject jsonParam) {
		User theUser = userService.findByName(authentication.getName());
		
		theUser.setData(jsonParam.toJSONString());
		
		System.out.println(jsonParam);
		return userService.save(theUser);
	}
}

///*
// * 此util用来去除本项目中object中的id，防止一个用户的数据覆盖另一个用户的数据。
// * 此util并不通用，如果修改了entity，请修改此util。
// */
//class IDUtils {
//	
//	/*
//	 * Get a json or map or list who id is null.
//	 */
//	public static Object getIdIsNull(Object obj) {
//		obj = setIdIsNull(obj);
//		return TraverseByType(obj);
//	}
//	
//	static Object TraverseByType(Object obj) {
//		System.out.println(obj.getClass());
//		
//		if (obj instanceof JSONObject) {
//			return TraverseProcess((JSONObject) obj);
//        } else if (obj instanceof JSONArray) {
//        	return TraverseProcess((JSONArray) obj);
//        } else if (obj instanceof Collection) {
//        	return TraverseProcess((Collection) obj);
//        } else if (obj instanceof List) {
//        	return TraverseProcess((List) obj);
//        } else {
//        	return obj;
//        }
//	}
//	
//	static Object TraverseProcess(Collection c) {
//		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(c);
//		return TraverseProcess(list);
//	}
//	
//	static Object TraverseProcess(List list) {
//		for (Object obj : list) {
//			TraverseByType(obj);
//			obj = setIdIsNull(obj);
//		}
//		return list;
//	}
//	
//    static Object TraverseProcess(JSONObject jsonObject) {
//        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            TraverseByType(jsonObject.get(key));
//            jsonObject.put(key, setIdIsNull(jsonObject.get(key)));
//        }
//        return jsonObject;
//    }
//    
//    static Object TraverseProcess(JSONArray jsonArray) {
//        if (jsonArray != null) {
//            Iterator i = jsonArray.iterator();
//            while (i.hasNext()) {
//                Object key = i.next();
//                TraverseByType(key);
//                jsonArray.remove(key);
//                jsonArray.add(setIdIsNull(key));
//            }
//        }
//        return jsonArray;
//    }
//    
//    static Object setIdIsNull(Object obj) {
//    	if (obj.getClass().getSuperclass().equals(Base.class)) {
//    		Base base = (Base) obj;
//    		System.out.println(base.getId());
//    		base.setId(null);
//    		return base;
//    	} else {
//    		return obj;
//    	}
//    }
//}