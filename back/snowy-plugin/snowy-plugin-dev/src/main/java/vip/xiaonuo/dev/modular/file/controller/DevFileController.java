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
package vip.xiaonuo.dev.modular.file.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.enums.FileSourceTypeEnum;
import vip.xiaonuo.dev.modular.file.param.DevFileIdParam;
import vip.xiaonuo.dev.modular.file.param.DevFileListParam;
import vip.xiaonuo.dev.modular.file.param.DevFilePageParam;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * 文件控制器
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:26
 **/
@Api(tags = "文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 4)
@RestController
@Validated
public class DevFileController {

    /** 默认文件引擎 */
    private static final String SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY = "SNOWY_SYS_DEFAULT_FILE_ENGINE";

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private DevFileService devFileService;

    /**
     * 动态上传文件返回id
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("动态上传文件返回id")
    @CommonLog("动态上传文件返回id")
    @PostMapping("/dev/file/uploadDynamicReturnId")
    public CommonResult<String> uploadDynamicReturnId(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnId(devConfigApi.getValueByKey(SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY), file,""));
    }

    /**
     * 动态上传文件返回url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("动态上传文件返回url")
    @CommonLog("动态上传文件返回url")
    @PostMapping("/dev/file/uploadDynamicReturnUrl")
    public CommonResult<String> uploadDynamicReturnUrl(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnUrl(devConfigApi.getValueByKey(SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY), file,""));
    }

    /**
     * 本地文件上传，返回文件id
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("上传本地文件返回id")
    @CommonLog("上传本地文件返回id")
    @SaCheckPermission("/dev/file/uploadLocalReturnId")
    @PostMapping("/dev/file/uploadLocalReturnId")
    public CommonResult<String> uploadLocalReturnId(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnId(DevFileEngineTypeEnum.LOCAL.getValue(), file,""));
    }

    /**
     * 本地文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 4)
    @ApiOperation("上传本地文件返回url")
    @CommonLog("上传本地文件返回url")
    @PostMapping("/dev/file/uploadLocalReturnUrl")
    public CommonResult<String> uploadLocalReturnUrl(@RequestPart("file") MultipartFile file) {

        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.LOCAL.getValue(), file,""));
    }
    /**
     *隐患图片件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 17)
    @ApiOperation("隐患图片件上传，返回文件Url")
    @CommonLog("隐患图片件上传，返回文件Url")
    @PostMapping("/dev/file/uploadYinhuanReturnUrl")
    public CommonResult<String> fileUploadYinhuanReturnUrl(@RequestPart("file") MultipartFile file) {

        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.LOCAL.getValue(), file,FileSourceTypeEnum.YINHUAN.getValue()));
    }

    /**
     *隐患排查治理计划文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 18)
    @ApiOperation("隐患排查治理计划文件上传，返回文件Url")
    @CommonLog("隐患排查治理计划文件上传，返回文件Url")
    @PostMapping("/dev/file/uploadPlanReturnUrl")
    public CommonResult<String> fileUploadPlanReturnUrl(@RequestPart("file") MultipartFile file) {

        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.LOCAL.getValue(), file,FileSourceTypeEnum.PLAN.getValue()));
    }

    /**
     *隐患排查标准清单文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 19)
    @ApiOperation("隐患排查标准清单文件上传，返回文件Url")
    @CommonLog("隐患排查标准清单文件上传，返回文件Url")
    @PostMapping("/dev/file/uploadListReturnUrl")
    public CommonResult<String> fileUploadListReturnUrl(@RequestPart("file") MultipartFile file) {

        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.LOCAL.getValue(), file,FileSourceTypeEnum.LIST.getValue()));
    }
    /**
     *隐患排查工作方案文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 19)
    @ApiOperation("隐患排查标准清单文件上传，返回文件Url")
    @CommonLog("隐患排查标准清单文件上传，返回文件Url")
    @PostMapping("/dev/file/uploadProgrammeReturnUrl")
    public CommonResult<String> fileUploadProgrammeReturnUrl(@RequestPart("file") MultipartFile file) {

        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.LOCAL.getValue(),file,FileSourceTypeEnum.PROGRAMME.getValue() ));
    }



    /**
     * 阿里云文件上传，返回文件id
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("上传阿里云文件返回id")
    @CommonLog("上传阿里云文件返回id")
    @PostMapping("/dev/file/uploadAliyunReturnId")
    public CommonResult<String> uploadAliyunReturnId(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnId(DevFileEngineTypeEnum.ALIYUN.getValue(), file,""));
    }

    /**
     * 阿里云文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("上传阿里云文件返回url")
    @CommonLog("上传阿里云文件返回url")
    @PostMapping("/dev/file/uploadAliyunReturnUrl")
    public CommonResult<String> uploadAliyunReturnUrl(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.ALIYUN.getValue(), file,""));
    }

    /**
     * 腾讯云文件上传，返回文件id
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("上传腾讯云文件返回id")
    @CommonLog("上传腾讯云文件返回id")
    @PostMapping("/dev/file/uploadTencentReturnId")
    public CommonResult<String> uploadTencentReturnId(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnId(DevFileEngineTypeEnum.TENCENT.getValue(), file,""));
    }

    /**
     * 腾讯云文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("上传腾讯云文件返回url")
    @CommonLog("上传腾讯云文件返回url")
    @PostMapping("/dev/file/uploadTencentReturnUrl")
    public CommonResult<String> uploadTencentReturnUrl(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.TENCENT.getValue(), file,""));
    }

    /**
     * MINIO文件上传，返回文件id
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 9)
    @ApiOperation("上传MINIO文件返回id")
    @CommonLog("上传MINIO文件返回id")
    @PostMapping("/dev/file/uploadMinioReturnId")
    public CommonResult<String> uploadMinioReturnId(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file,""));
    }

    /**
     * MINIO文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 10)
    @ApiOperation("上传MINIO文件返回url")
    @CommonLog("上传MINIO文件返回url")
    @PostMapping("/dev/file/uploadMinioReturnUrl")
    public CommonResult<String> uploadMinioReturnUrl(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(devFileService.uploadReturnUrl(DevFileEngineTypeEnum.MINIO.getValue(), file,""));
    }

    /**
     * 获取文件分页列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取文件分页列表")
    @SaCheckPermission("/dev/file/page")
    @GetMapping("/dev/file/page")
    public CommonResult<Page<DevFile>> page(DevFilePageParam devFilePageParam) {

        return CommonResult.data(devFileService.page(devFilePageParam));
    }



    /**
     * 获取文件分页列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取文件分页列表")
    @SaCheckPermission("/dev/file/filePlan")
    @GetMapping("/dev/file/filePlan")
    public CommonResult<Page<DevFile>> filePlan(DevFilePageParam devFilePageParam) {

        return CommonResult.data(devFileService.file(devFilePageParam,FileSourceTypeEnum.PLAN.getValue()));
    }

    @ApiOperationSupport(order = 17)
    @ApiOperation("获取文件分页列表")
    @SaCheckPermission("/dev/file/fileList")
    @GetMapping("/dev/file/fileList")
    public CommonResult<Page<DevFile>> fileList(DevFilePageParam devFilePageParam) {

        return CommonResult.data(devFileService.file(devFilePageParam,FileSourceTypeEnum.LIST.getValue()));
    }
    @ApiOperationSupport(order = 18)
    @ApiOperation("获取文件分页列表")
    @SaCheckPermission("/dev/file/fileProgramme")
    @GetMapping("/dev/file/fileProgramme")
    public CommonResult<Page<DevFile>> fileProgramme(DevFilePageParam devFilePageParam) {

        return CommonResult.data(devFileService.file(devFilePageParam,FileSourceTypeEnum.PROGRAMME.getValue()));
    }

    /**
     * 获取文件列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("获取文件列表")
    @GetMapping("/dev/file/list")
    public CommonResult<List<DevFile>> list(DevFileListParam devFileListParam) {
        return CommonResult.data(devFileService.list(devFileListParam));
    }

    /**
     * 下载文件
     *
     * @author xuyuxiang
     * @date 2022/6/21 15:44
     **/
    @ApiOperationSupport(order = 13)
    @ApiOperation("下载文件")
    @CommonLog("下载文件")
    @GetMapping(value = "/dev/file/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(@Valid DevFileIdParam devFileIdParam, HttpServletResponse response) throws IOException {
            devFileService.download(devFileIdParam, response);
    }

    /**
     * 删除文件
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("删除文件")
    @CommonLog("删除文件")
    @PostMapping(value = "/dev/file/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevFileIdParam> devFileIdParamList) {
        devFileService.delete(devFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取文件详情
     *
     * @author xuyuxiang
     * @date 2022/6/21 15:44
     **/
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取文件详情")
    @GetMapping("/dev/file/detail")
    public CommonResult<DevFile> detail(@Valid DevFileIdParam devFileIdParam) {
        return CommonResult.data(devFileService.detail(devFileIdParam));
    }
//    @ApiOperationSupport(order = 16)
//    @ApiOperation("获取文件base64")
//    @GetMapping("/dev/file/getImgFileToBase64")
//    public String getImgFileToBase64(@Valid DevFileIdParam devFileIdParam) {
//        return devFileService.getImgFileToBase64(devFileIdParam);
//    }
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取图片")
    @GetMapping(value = "/dev/file/image",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@Valid DevFileIdParam devFileIdParam) throws Exception {
        return devFileService.getImage(devFileIdParam);


    }

}
