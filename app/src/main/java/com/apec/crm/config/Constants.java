package com.apec.crm.config;

/**
 * Created by duanlei on 16/9/8.
 */
public class Constants {

    /**
     * 测试服地址
     */
    public static final String TEST_BASE_URL = "http://192.168.8.21:18001/";

    /**
     * result
     */
    public static final int RESULT_CODE_MARK_MAP= 100; //地区标记
    public static final int RESULT_CODE_MAP_SELECT_SUC = 101; //地址搜索成功返回

    public static final int RESULT_CODE_ADD_CONTACT = 102; //添加联系人

    public static final int RESULT_CODE_MORE_DATA = 103; //更多资料
    public static final int RESULT_CODE_SELECT_CUSTOM = 104; //选择客户
    public static final int RESULT_CODE_SELECT_CONTACT = 105; //选择联系人
    public static final int RESULT_CODE_SELECT_CUSTOM_TYPE = 106; //选择客户类型
    public static final int RESULT_CODE_SELECT_CUSTOM_LEVEL = 107; //选择客户分类
    public static final int RESULT_CODE_SELECT_CUSTOM_STATE = 108; //选择客户状态
    public static final int RESULT_CODE_SELECT_CUSTOM_SOURCE = 109; //选择客户来源
    public static final int RESULT_CODE_SELECT_CUSTOM_CLASS = 110; //销售品类
    public static final int RESULT_CODE_SELECT_CUSTOM_OPEN_SEA = 111; //片区
    public static final int RESULT_CODE_SELECT_USER = 112; //选择用户

    public static final int RESULT_CODE_FILTER_CUSTOM = 113; //筛选客户

    public static final int RESULT_CODE_ADD_CUSTOM = 114; //添加联系人

    public static final int RESULT_CODE_ADD_VISIT = 115; //添加拜访

    public static final int RESULT_CODE_CUSTOM_BASE = 116; //编辑基本信息


    /**
     * request
     */
    public static final int REQUEST_CODE_MARK_MAP = 1001; //地图标记
    public static final int REQUEST_CODE_ADD_VISIT = 1002; //添加拜访

    public static final int REQUEST_CODE_SEARCH_MAP = 1003; //搜索地址

    public static final int REQUEST_CODE_ADD_CONTACT = 1004; //添加联系人

    public static final int REQUEST_CODE_MORE_DATA = 1005; //更多资料
    public static final int REQUEST_CODE_SELECT_CUSTOM = 1006; //选择客户
    public static final int REQUEST_CODE_SELECT_ATTR = 1007; //选择联系人
    public static final int REQUEST_CODE_SELECT_USER = 1008; //选择用户

    public static final int REQUEST_CODE_FILTER_CUSTOM = 1009; //筛选客户

    public static final int REQUEST_CODE_ADD_CUSTOM = 1010; //添加联系人

    public static final int REQUEST_CODE_CUSTOM_BASE = 1011; //编辑基本信息

    /**
     * 客户属性类型
     */
    public static final String CUSTOMER_TYPE = "type"; //客户类型
    public static final String CUSTOMER_CLASS = "class"; //销售品类
    public static final String CUSTOMER_LEVEL = "level"; //客户分类
    public static final String CUSTOMER_STATE = "state"; //客户状态
    public static final String CUSTOMER_SOURCE = "source"; //客户来源

}

