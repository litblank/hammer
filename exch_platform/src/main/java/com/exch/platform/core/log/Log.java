package com.exch.platform.core.log;

import com.alibaba.fastjson.JSONObject;
import com.exch.platform.core.shiro.ShiroKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 此项目中统一使用这种方式输出日志
 *
 * chenyadong
 */
public class Log {

    private static Logger log = LoggerFactory.getLogger(Log.class);

        private static void info(JSONObject json){
            getBase(json);
            log.info("POINT>"+json.toJSONString());
        }
        /**
         * info 日志格式例如
         *
         * Log.info("OrderNo::1000","username::1111")
         *
         * @param logs
         */
        public static void info(String ...logs){
            JSONObject json=getParm(logs);
            Log.info(json);
        }

        public static void error(String ...logs){
            JSONObject json=getParm(logs);
            Log.error(json);
        }

        private static void error(JSONObject json){
            getBase(json);
            log.error("POINT>"+json.toJSONString());
        }

        private static JSONObject getParm(String ...logs){
            JSONObject json=new JSONObject(true);
            int i=1;
            for(String log: logs){
                String[] str=log.split("::");
                if(str.length==2){
                    json.put(str[0],str[1]);
                }else if(str.length==1){
                    json.put("parm"+(i++),str[0]);
                }
            }
            return json;
        }

        private static void getBase(JSONObject json) {
            String strackMethod="";
            StackTraceElement[] temp=Thread.currentThread().getStackTrace();
            if(temp != null && temp.length>2){
                StackTraceElement stack=(StackTraceElement)temp[4];
                strackMethod = stack.getClassName()+stack.getMethodName()+"@"+stack.getLineNumber();
            }
            String username=ShiroKit.getUser()!=null?ShiroKit.getUser().getName():"";
            json.put("strackMethod",strackMethod);
            json.put("userName",username);
        }
    }
