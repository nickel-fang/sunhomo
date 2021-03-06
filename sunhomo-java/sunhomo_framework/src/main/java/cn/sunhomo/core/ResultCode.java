package cn.sunhomo.core;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(1, "成功"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30001-39999 */
//    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统内部错误"),

    /* 数据错误：50001-599999 */
    RESULT_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),

    /* 业务错误：80001-89999 */
    BUSINESS_AFTER_APPOINTED_TIME(80001, "已过退报时间"),
    CONTACT_LEADER_FOR_CANCEL(80002, "联系微信群退报"),
    ACTIVITY_HAS_STARTED(80003, "活动已开始"),
    ATTACH_IS_NOT_ALLOWED(80004, "不能带挂报名"),
    DRAW_IS_NOT_ALLOWED_FOR_TIME(80005, "抽签时间未到"),
    DRAW_IS_NOT_NEEDED_FOR_LEADER(80006, "队长无须抽签"),
    DRAW_IS_DONE(80007, "您已抽过签"),
    DRAW_IS_NOT_ALLOWED_FOR_OTHER_PERSON(80008, "您未报名比赛"),
    DRAW_DIVISION_IS_NOT_CONFIGURED(80009, "队长未就位"),
    POINT_NOT_ENOUGH(80010, "积分不够"),
    BATTLE_HAS_CANCELLED(80011, "约战已取消"),
    BATTLE_HAS_ENOUGH_MEMBER(80012, "您来晚了"),
    BATTLE_POSITION_HAS_OCCUPIED(80013, "位置已被占"),
    BATTLE_HAS_NOT_ENROLLED(80014, "您未报名活动"),
    BATTLE_HAS_NOT_COMPLETED(80015, "有约战未成团"),
    BATTLE_BLIND_NOT_ALLOWED(80016, "当天不能盲盒"),
    BATTLE_HAS_BLIND_NOT_COMPLETED(80017, "有盲盒未成团"),
    BLACK_IS_NOT_ALLOWED(80018, "请阅读报名规则"),
    BATTLE_HAS_ENOUGH_BATTLES(80019, "您约战过多"),
    BATTLE_OTHER_HAS_ENOUGH_BATTLES(80020, "被选人约战过多"),
    BATTLE_A1_HAS_ENOUGH_BATTLES(80021, "A1已达约战次数"),
    BATTLE_A2_HAS_ENOUGH_BATTLES(80022, "A2已达约战次数"),
    BATTLE_B1_HAS_ENOUGH_BATTLES(80023, "B1已达约战次数"),
    BATTLE_B2_HAS_ENOUGH_BATTLES(80024, "B2已达约战次数"),
    ACTIVITY_HAS_ENOUGH_BATTLES(80025, "单次活动限10场"),
    BATTLE_BLIND_HAS_ATTENDED(80026, "已报名盲盒"),
    BATTLE_HAS_ATTENDED(80027, "您已约战")
    ;


    private int code;

    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static int getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
