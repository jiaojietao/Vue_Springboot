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
package vip.xiaonuo.biz.modular.safety.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.position.entity.BizPosition;
import vip.xiaonuo.biz.modular.safety.entity.safety;
import vip.xiaonuo.biz.modular.safety.param.*;
import vip.xiaonuo.biz.modular.safety.param.BizUserGrantRoleParam;
import vip.xiaonuo.biz.modular.safety.service.safetyService;
import vip.xiaonuo.biz.modular.user.entity.BizUser;
import vip.xiaonuo.biz.modular.user.param.*;
import vip.xiaonuo.biz.modular.user.param.BizUserExportParam;
import vip.xiaonuo.biz.modular.user.param.BizUserSelectorOrgListParam;
import vip.xiaonuo.biz.modular.user.param.BizUserSelectorPositionParam;
import vip.xiaonuo.biz.modular.user.param.BizUserSelectorRoleParam;
import vip.xiaonuo.biz.modular.user.param.BizUserSelectorUserParam;
import vip.xiaonuo.biz.modular.user.result.BizUserRoleResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * 人员控制器
 *
 * @author xuyuxiang
 * @date 2022/4/22 9:34
 **/
@Api(tags = "隐患排查")
@ApiSupport(author = "SNOWY_TEAM", order = 9)
@RestController
@Validated
public class safetyController {

    @Resource
    private safetyService safetySevice;

    /**
     * 获取人员分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取隐患排查分页")
    @SaCheckPermission("/biz/yinhuan/page")
    @GetMapping("/biz/yinhuan/page")
    public CommonResult<Page<safety>> page(safetyPageParam safetyPageParam) {
        return CommonResult.data(safetySevice.page(safetyPageParam));
    }

    /**
     * 添加人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加隐患")
    @CommonLog("添加隐患")
    @SaCheckPermission("/biz/yinhuan/add")
    @PostMapping("/biz/yinhuan/add")
    public CommonResult<String> add(@RequestBody @Valid safetyAddParam safetyAddParam) {
        safetySevice.add(safetyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑隐患")
    @CommonLog("编辑隐患")
    @SaCheckPermission("/biz/yinhuan/edit")
    @PostMapping("/biz/yinhuan/edit")
    public CommonResult<String> edit(@RequestBody @Valid safetyEditParam safetyEditParam) {
        safetySevice.edit(safetyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除隐患")
    @CommonLog("删除隐患")
    @SaCheckPermission("/biz/yinhuan/delete")
    @PostMapping("/biz/yinhuan/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<safetyIdParam> safetyIdParamList) {
        safetySevice.delete(safetyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取人员详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取隐患详情")
    @SaCheckPermission("/biz/yinhuan/detail")
    @GetMapping("/biz/yinhuan/detail")
    public CommonResult<safety> detail(@Valid safetyIdParam safetyIdParam) {
        return CommonResult.data(safetySevice.detail(safetyIdParam));
    }

    /**
     * 禁用人员
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("禁用人员")
    @CommonLog("禁用人员")
//    @SaCheckPermission("/biz/yinhuan/disableUser")
    @PostMapping("/biz/yinhuan/disableUser")
    public CommonResult<String> disableUser(@RequestBody BizUserIdParam bizUserIdParam) {
        safetySevice.disableUser(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 启用人员
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("启用人员")
    @CommonLog("启用人员")
//    @SaCheckPermission("/biz/yinhuan/enableUser")
    @PostMapping("/biz/yinhuan/enableUser")
    public CommonResult<String> enableUser(@RequestBody @Valid BizUserIdParam bizUserIdParam) {
        safetySevice.enableUser(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 重置人员密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("重置人员密码")
    @CommonLog("重置人员密码")
//    @SaCheckPermission("/biz/yinhuan/resetPassword")
    @PostMapping("/biz/yinhuan/resetPassword")
    public CommonResult<String> resetPassword(@RequestBody @Valid BizUserIdParam bizUserIdParam) {
        safetySevice.resetPassword(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 用户拥有角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取人员拥有角色")
//    @SaCheckPermission("/biz/yinhuan/ownRole")
    @GetMapping("/biz/yinhuan/ownRole")
    public CommonResult<List<String>> ownRole(@Valid BizUserIdParam bizUserIdParam) {
        return CommonResult.data(safetySevice.ownRole(bizUserIdParam));
    }

    /**
     * 给用户授权角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
//    @ApiOperationSupport(order = 10)
//    @ApiOperation("给人员授权角色")
//    @CommonLog("给人员授权角色")
//    @SaCheckPermission("/biz/yinhuan/grantRole")
//    @PostMapping("/biz/yinhuan/grantRole")
//    public CommonResult<String> grantRole(@RequestBody @Valid BizUserGrantRoleParam bizUserGrantRoleParam) {
//        safetySevice.grantRole(bizUserGrantRoleParam);
//        return CommonResult.ok();
//    }

    /**
     * 人员导入
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("人员导入")
    @CommonLog("人员导入")
//    @SaCheckPermission("/biz/yinhuan/import")
    @PostMapping("/biz/yinhuan/import")
    public CommonResult<String> importUser(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        safetySevice.importUser(file);
        return CommonResult.ok();
    }

    /**
     * 人员导出
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("人员导出")
    @CommonLog("人员导出")
//    @SaCheckPermission("/biz/yinhuan/export")
    @GetMapping(value = "/biz/yinhuan/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUser(BizUserExportParam bizUserExportParam, HttpServletResponse response) throws IOException {
        safetySevice.exportUser(bizUserExportParam, response);
    }

    /* ====人员部分所需要用到的选择器==== */

    /**
     * 获取机构树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("获取机构树选择器")
//    @SaCheckPermission("/biz/yinhuan/orgTreeSelector")
    @GetMapping("/biz/yinhuan/orgTreeSelector")
    public CommonResult<List<Tree<String>>> orgTreeSelector() {
        return CommonResult.data(safetySevice.orgTreeSelector());
    }

    /**
     * 获取机构列表选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取机构列表选择器")
//    @SaCheckPermission("/biz/yinhuan/orgListSelector")
    @GetMapping("/biz/yinhuan/orgListSelector")
    public CommonResult<List<BizOrg>> orgListSelector(BizUserSelectorOrgListParam bizUserSelectorOrgListParam) {
        return CommonResult.data(safetySevice.orgListSelector(bizUserSelectorOrgListParam));
    }

    /**
     * 获取岗位选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取岗位选择器")
//    @SaCheckPermission("/biz/yinhuan/positionSelector")
    @GetMapping("/biz/yinhuan/positionSelector")
    public CommonResult<List<BizPosition>> positionSelector(BizUserSelectorPositionParam bizUserSelectorPositionParam) {
        return CommonResult.data(safetySevice.positionSelector(bizUserSelectorPositionParam));
    }

    /**
     * 获取角色选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取角色选择器")
//    @SaCheckPermission("/biz/yinhuan/roleSelector")
    @GetMapping("/biz/yinhuan/roleSelector")
    public CommonResult<List<BizUserRoleResult>> roleSelector(BizUserSelectorRoleParam bizUserSelectorRoleParam) {
        return CommonResult.data(safetySevice.roleSelector(bizUserSelectorRoleParam));
    }

    /**
     * 获取人员选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("获取人员选择器")
//    @SaCheckPermission("/biz/yinhuan/userSelector")
    @GetMapping("/biz/yinhuan/userSelector")
    public CommonResult<List<BizUser>> userSelector(BizUserSelectorUserParam bizUserSelectorUserParam) {
        return CommonResult.data(safetySevice.userSelector(bizUserSelectorUserParam));
    }
}
