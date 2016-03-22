package com.fujitsu.assetmanager.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json 工具�?
 * 
 * @author maofw
 * 
 */
public class JsonUtils
{
    public static final JSONObject getJSONObject(JSONObject obj, String key)
    {
        
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.getJSONObject(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static final JSONArray getJSONArray(JSONObject obj, String key)
    {
        
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.getJSONArray(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static final String getString(JSONObject obj, String key)
    {
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.getString(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static final Object getObject(JSONObject obj, String key)
    {
        
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.get(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static final boolean getBoolean(JSONObject obj, String key)
    {
        
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.getBoolean(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static final int getInteger(JSONObject obj, String key)
    {
        if (isExistAndNotNull(obj, key))
        {
            try
            {
                return obj.getInt(key);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return -1;
    }
    
    private static final boolean isExistAndNotNull(JSONObject obj, String key)
    {
        if (obj != null && obj.has(key) && !obj.isNull(key))
        {
            return true;
        }
        return false;
    }
    
    public static JSONObject setJosn(Map<String, Object> map)
        throws Exception
    {
        JSONObject json = null;
        StringBuffer temp = new StringBuffer();
        if (!map.isEmpty())
        {
            temp.append("{");
            // 遍历map
            Set set = map.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext())
            {
                Map.Entry entry = (Map.Entry)i.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                temp.append("\"" + key + "\":");
                if (value instanceof Map<?, ?>)
                {
                    temp.append(setJosn((Map<String, Object>)value) + ",");
                }
                else if (value instanceof List<?>)
                {
                    temp.append(setList((List<Map<String, Object>>)value) + ",");
                }
                else if (value instanceof String)
                {
                    temp.append("\"");
                    temp.append(value);
                    temp.append("\"");
                    temp.append(",");
                }
                else
                {
                    temp.append(value + ",");
                }
            }
            if (temp.length() > 1)
            {
                temp = new StringBuffer(temp.substring(0, temp.length() - 1));
            }
            temp.append("}");
            json = new JSONObject(temp.toString());
        }
        return json;
    }
    
    private static String setOList(List list)
    {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++)
        {
            Object obj = list.get(i);
            temp.append("\"");
            temp.append(obj.toString());
            temp.append("\"");
            temp.append(",");
            
        }
        if (temp.length() > 1)
        {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }
    
    public static String setList(List<Map<String, Object>> list)
        throws Exception
    {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++)
        {
            Map<String, Object> m = list.get(i);
            if (i == list.size() - 1)
            {
                temp.append(setJosn(m));
            }
            else
            {
                temp.append(setJosn(m) + ",");
            }
        }
        if (temp.length() > 1)
        {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }
    
    public static String mapToSJson(Map map)
        throws Exception
    {
        boolean bool = false;
        Set set = map.keySet();
        Iterator it = set.iterator();
        StringBuffer jsonStr = new StringBuffer("{");
        Object key = null;
        Object value = null;
        while (it.hasNext())
        {
            key = it.next().toString();
            value = map.get(key);
            assembleJsonConsle(jsonStr, key, value);
            bool = true;
        }
        if (bool)
            jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("}");
        return jsonStr.toString();
    }
    
    /***
     * bean转json字符�?
     * @param obj
     * @return
     * @throws Exception 
     */
    public static String beanToSJson(Object obj)
    {
        boolean bool = false;
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String key = "";
        Object value = null;
        StringBuffer jsonStr = new StringBuffer("{");
        for (int i = 0; i < fields.length; i++)
        {
            key = fields[i].getName();
            try
            {
                value = invoke(obj, fields[i].getClass(), getGFunction(key), null);
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                assembleJsonConsle(jsonStr, key, value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            bool = true;
        }
        if (bool)
            jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("}");
        return jsonStr.toString();
    }
    
    /**
     * lis转json字符�?
     * @param list
     * @return
     * @throws Exception 
     */
    public static String listToSJson(List list)
    {
        StringBuffer jsonStr = new StringBuffer("[");
        boolean bool = false;
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                jsonStr.append("");
                jsonStr.append(beanToSJson(list.get(i)));
                jsonStr.append(",");
                bool = true;
            }
        }
        if (bool)
            jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }
    
    /**
     * 返回类型
     * @param clazz
     * @return
     */
    private static int getType(Object obj)
    {
        String type = obj.getClass().getSimpleName();
        if ("String[][]".equals(type))
            return 0;
        if ("String[]".equals(type))
            return 1;
        if ("String".equals(type))
            return 2;
        if ("Integer".equals(type))
            return 2;
        if ("Long".equals(type))
            return 2;
        if ("Short".equals(type))
            return 2;
        if (obj instanceof Map)
            return 3;
        //         if(type.indexOf("Map")>-1)return 3;
        if (obj instanceof List)
            return 4;
        //         if(type.indexOf("List")>-1)return 4;
        else
            return 5;
    }
    
    /**
     * 组装控制方法
     * @param jsonStr
     * @param key
     * @param value
     * @param type 1是对�?0是String
     * @return
     * @throws Exception 
     */
    private static StringBuffer assembleJsonConsle(StringBuffer jsonStr, Object key, Object value)
        throws Exception
    {
        if (value == null)
            return jsonStr;
        int type = getType(value);
        switch (type)
        {
            case 0:
            case 1:
                assembleJsonStr(jsonStr, key, getSString(value, type), 1);
                break;
            case 2:
                assembleJsonStr(jsonStr, key, value, 0);
                break;
            case 3:
                assembleJsonStr(jsonStr, key, mapToSJson((Map)value), 1);
                break;
            case 4:
                assembleJsonStr(jsonStr, key, listToSJson((List)value), 1);
                break;
            case 5:
                assembleJsonStr(jsonStr, key, beanToSJson(value), 1);
                break;
            default:
                break;
        }
        return jsonStr;
    }
    
    /**
     * 组装
     * @param jsonStr
     * @param key
     * @param value
     * @param type 1是对�?0是String
     * @return
     */
    private static StringBuffer assembleJsonStr(StringBuffer jsonStr, Object key, Object value, int type)
    {
        if (type == 1)
            return jsonStr.append("\"").append(key).append("\":").append(value).append(",");
        else
            return jsonStr.append("\"").append(key).append("\":\"").append(value).append("\",");
    }
    
    /**
     * 组装数组
     * @param obj
     * @param type
     * @return
     */
    private static StringBuffer getSString(Object obj, int type)
    {
        StringBuffer str = new StringBuffer("[");
        boolean bool = false;
        if (type == 0 && obj != null)
        {
            String[][] strs = (String[][])obj;
            for (int i = 0; i < strs.length; i++)
            {
                str.append("[");
                if (strs[i] != null)
                    for (int j = 0; j < strs[i].length; j++)
                    {
                        str.append("\"").append(strs[i][j]).append("\",");
                        bool = true;
                    }
                if (bool)
                    str.deleteCharAt(str.length() - 1);
                str.append("],");
                bool = true;
            }
        }
        else if (obj != null)
        {
            String[] strs = (String[])obj;
            for (int j = 0; j < strs.length; j++)
            {
                str.append("\"").append(strs[j]).append("\",");
                bool = true;
            }
        }
        if (bool)
            str.deleteCharAt(str.length() - 1);
        str.append("]");
        return str;
    }
    
    /**
     * 获取get方法名称
     * @param attr
     * @return
     */
    private static String getGFunction(String attr)
    {
        return "get" + attr.substring(0, 1).toUpperCase() + attr.substring(1);
    }
    
    /**
     * 获取set方法名称
     * @param attr
     * @return
     */
    private static String getSFunction(String attr)
    {
        return "set" + attr.substring(0, 1).toUpperCase() + attr.substring(1);
    }
    
    /**
     * 调用方法
     * @param clazz
     * @param function
     * @param objs
     * @return
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    private static Object invoke(Object obj, Class attrType, String function, Object attr)
        throws Exception
    {
        Method method = obj.getClass().getMethod(function);
        return method.invoke(obj);
    }
}
