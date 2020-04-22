package com.example.freshtext.Utility;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 解析json数据的工具类
 */
public class JsonUtility {

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class type) {

        T t =null;

        try{

            Gson gson =new Gson();

            t = (T) gson.fromJson(json, type);

        }catch(Exception e) {

//TODO: handle exception

        }
        return t;
    }

    public static List getList(String jsonString, Type type){

        List list=null;

        try{

            Gson gson =new Gson();

            list = gson.fromJson(jsonString, type);

        }catch(Exception e) {

        }

        return list;

    }

    /**

     *将对象转换为json

     *@param

     *@paramobj

     *@return

     */

    public static <T> String toJson(T obj) {

        String jsonStr =null;

        try{

            Gson gson =new Gson();

            jsonStr=gson.toJson(obj);

        }catch(Exception e) {

        }

        return jsonStr;

    }
}
