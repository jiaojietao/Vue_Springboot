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
package vip.xiaonuo.biz.modular.safety.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import sun.misc.*;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.position.entity.BizPosition;
import vip.xiaonuo.biz.modular.position.service.BizPositionService;
import vip.xiaonuo.biz.modular.safety.entity.safety;
import vip.xiaonuo.biz.modular.safety.mapper.safetyMapper;
import vip.xiaonuo.biz.modular.safety.param.safetyAddParam;
import vip.xiaonuo.biz.modular.safety.param.safetyEditParam;
import vip.xiaonuo.biz.modular.safety.param.safetyIdParam;
import vip.xiaonuo.biz.modular.safety.param.safetyPageParam;
import vip.xiaonuo.biz.modular.safety.service.safetyService;
import vip.xiaonuo.biz.modular.user.entity.BizUser;
import vip.xiaonuo.biz.modular.user.param.*;
import vip.xiaonuo.biz.modular.user.result.BizUserRoleResult;
import vip.xiaonuo.biz.modular.user.service.BizUserService;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
//import vip.xiaonuo.dev.modular.file.entity.DevFile;
//import vip.xiaonuo.dev.modular.file.service.DevFileService;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * 人员Service接口实现类
 *src/main/java/vip/xiaonuo/biz/modular/safety/service/impl/safetyServiceImpl.java
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class safetyServiceImpl extends ServiceImpl<safetyMapper, safety> implements safetyService {

    private static final String SNOWY_SYS_DEFAULT_PASSWORD_KEY = "SNOWY_SYS_DEFAULT_PASSWORD";


    @Resource
    private BizOrgService bizOrgService;
//    @Resource
//    private DevFileService devFileService;
    @Resource
    private BizUserService bizUserService;

    @Resource
    private BizPositionService bizPositionService;

    @Override
   // 返回台账的界面，判断超级用户，orgId(组织机构);
    public Page<safety> page(safetyPageParam safetyPageParam) {
        QueryWrapper<safety> queryWrapper = new QueryWrapper<>();


        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        SaBaseLoginUser user = StpLoginUserUtil.getLoginUser();
//        System.out.println(user.getOrgId());
        if (!user.getAccount().equals("superAdmin")) {
            if (ObjectUtil.isNotEmpty(loginUserDataScope)) {
                queryWrapper.lambda().in(safety::getOrg, loginUserDataScope);
            } else {
                queryWrapper.lambda().eq(safety::getId, StpUtil.getLoginIdAsString());
            }
        }
        if (ObjectUtil.isNotEmpty(safetyPageParam.getOrgId())) {
            BizOrg org = bizOrgService.queryEntity(safetyPageParam.getOrgId());
            if (!org.getParentId().equals("0"))
                queryWrapper.lambda().eq(safety::getOrg, safetyPageParam.getOrgId());
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
//          return null;
    }

    @Override
    public void add(safetyAddParam safetyaddParam) {

        BizUser ORG = bizUserService.queryEntity(StpUtil.getLoginIdAsString());

        safety safety = BeanUtil.toBean(safetyaddParam, safety.class);

        safety.setOrg(ORG.getOrgId());
        safety.setTroubles_image("http://127.0.0.1:82/dev/file/image?id="+safetyaddParam.getTroubles_image());
        safety.setUpdate_image("http://127.0.0.1:82/dev/file/image?id="+safetyaddParam.getUpdate_image());
        this.save(safety);

    }

    @Override
    public void edit(safetyEditParam safetyEditParam) {
        safety safety = this.queryEntity(safetyEditParam.getId());
        BeanUtil.copyProperties(safetyEditParam, safety);
        safety.setTroubles_image("http://127.0.0.1:82/dev/file/image?id="+safetyEditParam.getTroubles_image());//图片预览
        safety.setUpdate_image("http://127.0.0.1:82/dev/file/image?id="+safetyEditParam.getUpdate_image());
        this.updateById(safety);

    }

    @Override
    public void delete(List<safetyIdParam> safetyIdParamList) {
        List<String> safetyIdList = CollStreamUtil.toList(safetyIdParamList, safetyIdParam::getId);
        this.removeBatchByIds(safetyIdList);

    }

    @Override
    public safety detail(safetyIdParam safetyIdParam) {
        return this.queryEntity(safetyIdParam.getId());
    }

    @Override
    public safety queryEntity(String id) {
        safety safety = this.getById(id);
        if (ObjectUtil.isEmpty(safety)) {
            throw new CommonException("隐患详情不存在，id值为：{}", id);
        }
        return safety;
    }

    @Override
    public void disableUser(BizUserIdParam bizUserIdParam) {

    }

    @Override
    public void enableUser(BizUserIdParam bizUserIdParam) {

    }

    @Override
    public void resetPassword(BizUserIdParam bizUserIdParam) {

    }

    @Override
    public List<String> ownRole(BizUserIdParam bizUserIdParam) {
        return null;
    }

    @Override
    public void grantRole(BizUserGrantRoleParam bizUserGrantRoleParam) {

    }

    @Override
    public void importUser(MultipartFile file) {

    }

    @Override
    public void exportUser(BizUserExportParam bizUserExportParam, HttpServletResponse response) throws IOException {

    }

    @Override
    public List<Tree<String>> orgTreeSelector() {
        return null;
    }

    @Override
    public List<BizOrg> orgListSelector(BizUserSelectorOrgListParam bizUserSelectorOrgListParam) {
        return null;
    }

    @Override
    public List<BizPosition> positionSelector(BizUserSelectorPositionParam bizUserSelectorPositionParam) {
        return null;
    }

    @Override
    public List<BizUserRoleResult> roleSelector(BizUserSelectorRoleParam bizUserSelectorRoleParam) {
        return null;
    }

    @Override
    public List<BizUser> userSelector(BizUserSelectorUserParam bizUserSelectorUserParam) {
        return null;
    }




}
