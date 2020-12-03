package com.dataexa.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author yusf
 * Date 2018/12/30
 */
public class Result<T> {

    /**
     * 状态码
     */
    /*0:成功 1:失败 2:运行中 3:新建 4:停止*/
    private Integer code;
    /**
     * 自定义提示信息
     */
    private String msg;
    /**
     * 系统提示信息
     **/
    private String errorMsg;
    /**
     * 结果内容
     */
    private T object;

    private Map<String,String> columnsHeader = new LinkedHashMap<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        if(null == object){
            return;
        }
        Annotation[] annotations = object.getClass().getAnnotations();
        for (Annotation annotation:annotations){
            if(annotation instanceof JSONField){
                System.out.println(true);
            }
        }
        this.object = object;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, String> getColumnsHeader() {
        return columnsHeader;
    }

    public void setColumnsHeader(Map<String, String> columnsHeader) {
        this.columnsHeader = columnsHeader;
    }

    public <C> void  reflectColumnsToHeader(Class<C> entityClass){
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field:fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            Boolean flag = false;
            for (Annotation annotation:annotations){
                if(annotation instanceof JSONField){
                    flag = true;
                    Boolean serialize = ((JSONField) annotation).serialize();
                    if(serialize){
                        String name = ((JSONField) annotation).name();
                        String[] nameColumn = name.split(":");
                        if(nameColumn.length == 2 ){
                            columnsHeader.put(nameColumn[0],nameColumn[1]);
                        }else if(nameColumn.length == 1 && !nameColumn.equals("")){
                            columnsHeader.put(name, name);
                        }
                    }
                }
            }
            if(!flag){
                columnsHeader.put(field.getName(),field.getName());
            }
        }

    }

}
