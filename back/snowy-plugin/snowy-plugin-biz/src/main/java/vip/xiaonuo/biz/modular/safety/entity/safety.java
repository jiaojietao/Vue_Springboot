/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.safety.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.position.entity.BizPosition;
import vip.xiaonuo.biz.modular.user.entity.BizUser;
import vip.xiaonuo.common.handler.CommonSm4CbcTypeHandler;
import vip.xiaonuo.common.pojo.CommonEntity;

import java.util.Date;

/**
 * 人员实体
 *
 * @author xuyuxiang
 * @date 2022/4/21 16:13
 **/
@Getter
@Setter
@TableName(value = "safety", autoResultMap = true)
public class safety  implements TransPojo {

    /** id */
    @TableId
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 排查类型 */
    @ApiModelProperty(value = "排查类型", position = 2)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubleshoot_type;

    /** 排查项目 */
    @ApiModelProperty(value = "排查项目", position = 3)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubleshoot_name;

    /** 排查时间 */
    @ApiModelProperty(value = "排查时间", position = 4)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubleshoot_time;

    /** 隐患明细 */
        @ApiModelProperty(value = "隐患明细", position = 5)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubles_detail;

    /** 隐患图片 */
    @ApiModelProperty(value = "隐患图片", position = 6)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubles_image;

    /** 隐患图片 */
    @ApiModelProperty(value = "整改图片", position = 6)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String update_image;

    /** 昵称 */
    @ApiModelProperty(value = "隐患级别", position = 7)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubles_level;



    /** 整改措施 */
    @ApiModelProperty(value = "整改措施", position = 9)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String deal_method;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 10)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubleshoot_user;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 10)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String org;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 11)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String complete_time;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String others;

    /** 备注 */
    @ApiModelProperty(value = "隐患图片", position = 13)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubles_thumbnail;

    @ApiModelProperty(value = "整改图片", position = 14)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String dea_thumbnail;


}
