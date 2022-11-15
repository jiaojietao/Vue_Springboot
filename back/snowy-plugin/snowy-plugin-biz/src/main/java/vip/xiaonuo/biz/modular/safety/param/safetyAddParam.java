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
package vip.xiaonuo.biz.modular.safety.param;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 人员添加参数
 *
 * @author xuyuxiang
 * @date 2022/7/26 15:36
 **/
@Getter
@Setter
public class safetyAddParam {



    /** 排查类型 */
    @ApiModelProperty(value = "排查类型", required = true, position = 1)
    @NotBlank(message = "troubleshoot_type不能为空")
    private String troubleshoot_type;

    /** 排查项目 */
    @ApiModelProperty(value = "排查项目", position = 3)

    private String troubleshoot_name;

    /** 排查时间 */
    @ApiModelProperty(value = "排查时间", position = 4)
    private String troubleshoot_time;

    /** 隐患明细 */
    @ApiModelProperty(value = "隐患明细", position = 5)
    private String troubles_detail;

    /** 隐患图片 */
    @ApiModelProperty(value = "隐患图片", position = 6)
    private String troubles_image;

    /** 昵称 */
    @ApiModelProperty(value = "隐患级别", position = 7)

    private String troubles_level;

    /** 整改图片 */
    @ApiModelProperty(value = "整改图片", position = 8)
    private String update_image;

    /** 整改措施 */
    @ApiModelProperty(value = "整改措施", position = 9)
    private String deal_method;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 10)
    private String troubleshoot_user;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 11)
    private String complete_time;

    /** 隐患图片 */
    @ApiModelProperty(value = "隐患图片", position = 13)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String troubles_thumbnail;

    /** 整改图片 */
    @ApiModelProperty(value = "整改图片", position = 14)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String dea_thumbnail;


    private String others;

}
