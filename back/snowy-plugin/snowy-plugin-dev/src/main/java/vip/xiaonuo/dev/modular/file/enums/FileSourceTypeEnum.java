package vip.xiaonuo.dev.modular.file.enums;

import lombok.Getter;

@Getter
public enum FileSourceTypeEnum {


    /** 隐患排查治理计划 */
    PLAN("PLAN"),

    /** 隐患排查标准清单   */
    LIST("LIST"),

    /** 隐患排查工作方案 */
    PROGRAMME("PROGRAMME"),

    /** 隐患治理台账图片 */
    YINHUAN("YINHUAN");
    /** MINIO */


    private final String value;

    FileSourceTypeEnum(String value) {
        this.value = value;
    }
}
