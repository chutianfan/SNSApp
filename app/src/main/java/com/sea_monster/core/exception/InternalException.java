package com.sea_monster.core.exception;

import java.util.HashMap;

public class InternalException extends BaseException
{
    public static final String DEFAULT_EXP_MESSAGE = "操作失败";
    public static final String DEFAULT_LOGIC_EXP_MESSAGE = "操作失败";
    public static final String DEFAULT_NETWORK_EXP_MESSAGE = "网络请求异常";
    public static final String DEFAULT_OTHER_EXP_MESSAGE = "操作失败";
    public static final String DEFAULT_SERVICE_EXP_MESSAGE = "服务器异常";
    public static final int DEF_LOGIC_CODE = 2048;
    public static final int DEF_NETWORK_CODE = 1024;
    public static final int DEF_OTHER_CODE = 3072;
    public static final int DEF_SERVICE_CODE = 4096;
    public static final int DISCARD_TASK = 2001;
    public static final int ENTITY_BUILD_EXP = 2003;
    public static final int IMAGE_GET_FAIL = 2004;
    public static final int MODEL_INCOMPLETE = 2002;
    public static final int NETWORK_DISABLED = 3001;
    public static final int UNLOGIN_EXP = 2005;
    public static final int VALID_EXCEPTION_CODE = 2100;
    private static HashMap<Integer, String> codeMapping = new HashMap();
    private static final long serialVersionUID = 1L;
    private int code;

    static
    {
        codeMapping.put(Integer.valueOf(400), "请求无效");
        codeMapping.put(Integer.valueOf(401), "服务器异常，请注销帐号后重新登录");
        codeMapping.put(Integer.valueOf(403), "服务器异常，请注销帐号后重新登录");
        codeMapping.put(Integer.valueOf(404), "请求失败");
        codeMapping.put(Integer.valueOf(500), "服务器异常，请稍后再试");
        codeMapping.put(Integer.valueOf(501), "服务器异常，请稍后再试");
        codeMapping.put(Integer.valueOf(502), "服务器异常，请稍后再试");
        codeMapping.put(Integer.valueOf(503), "服务器正忙，请稍后再试");
        codeMapping.put(Integer.valueOf(504), "服务器请求超时，请稍后再试");
        codeMapping.put(Integer.valueOf(4001), "请求的参数错误");
        codeMapping.put(Integer.valueOf(4002), "发布//更新内容错误");
        codeMapping.put(Integer.valueOf(4003), "XML解析错误");
        codeMapping.put(Integer.valueOf(4004), "传图片错误");
        codeMapping.put(Integer.valueOf(4005), "上传图片大小错误");
        codeMapping.put(Integer.valueOf(4007), "图片压缩错误");
        codeMapping.put(Integer.valueOf(4008), "用户不存在");
        codeMapping.put(Integer.valueOf(4010), "请求的数据不存在");
        codeMapping.put(Integer.valueOf(4011), "URL错误");
        codeMapping.put(Integer.valueOf(4012), "内容包含非法词");
        codeMapping.put(Integer.valueOf(4013), "不能重复绑定服务");
        codeMapping.put(Integer.valueOf(40002), "请求无效");
        codeMapping.put(Integer.valueOf(3001), "当前网络不可用");
        codeMapping.put(Integer.valueOf(2001), "请求被舍弃");
        codeMapping.put(Integer.valueOf(2002), "保存对象信息不全");
        codeMapping.put(Integer.valueOf(2003), "消息体创建失败");
        codeMapping.put(Integer.valueOf(2004), "图片获取失败");
        codeMapping.put(Integer.valueOf(2005), "用户尚未登陆");
    }

    public InternalException(int paramInt, String paramString)
    {
        super(paramString);
        this.code = paramInt;
    }

    public InternalException(int paramInt, Throwable paramThrowable)
    {
        super(paramThrowable);
        this.code = paramInt;
    }

    public InternalException(String paramString)
    {
        super(paramString);
    }

    public int getCode()
    {
        return this.code;
    }

    public String getCustomErrorMessage(int paramInt, String paramString)
    {
        if (paramInt == this.code)
            return paramString;
        return toString();
    }

    public int getGeneralCode()
    {
        int i = 1024;
        if (codeMapping.containsKey(Integer.valueOf(this.code)))
            i = this.code;
        while ((this.code >= 0) && (this.code < 1024))
            return i;
        if ((this.code >= 1024) && (this.code < 2048))
            return 2048;
        if ((this.code >= 2048) && (this.code < 3072))
            return 3072;
        if ((this.code >= 3072) && (this.code < 4096))
            return 4096;
        return 0;
    }

    public boolean isSpecial()
    {
        return !codeMapping.containsKey(Integer.valueOf(this.code));
    }

    public void setCode(int paramInt)
    {
        this.code = paramInt;
    }

    public String toString()
    {
        if (codeMapping.containsKey(Integer.valueOf(this.code)))
            return (String)codeMapping.get(Integer.valueOf(this.code));
        if ((this.code >= 0) && (this.code < 1024))
            return "网络请求异常";
        if ((this.code >= 1024) && (this.code < 2048))
            return "操作失败";
        if ((this.code >= 2048) && (this.code < 3072))
            return "操作失败";
        if ((this.code >= 3072) && (this.code < 4096))
            return "服务器异常";
        return "操作失败";
    }
}