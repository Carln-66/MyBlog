package com.carl.blog.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.entities.SysUser;
import com.carl.blog.feign.IFeignArticleController;
import com.carl.blog.feign.IFeignQuestionController;
import com.carl.blog.feign.req.UserInfoREQ;
import com.carl.blog.system.mapper.SysUserMapper;
import com.carl.blog.system.req.RegisterREQ;
import com.carl.blog.system.req.SysUserCheckPasswordREQ;
import com.carl.blog.system.req.SysUserREQ;
import com.carl.blog.system.req.SysUserUpdatePasswordREQ;
import com.carl.blog.system.service.ISysUserService;
import com.carl.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public Result queryPage(SysUserREQ req) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(req.getUsername())) {
            wrapper.like("username", req.getUsername());
        }
        if (StringUtils.isNotEmpty(req.getMobile())) {
            wrapper.like("mobile", req.getMobile());
        }
        wrapper.orderByDesc("update_date");
        return Result.ok(baseMapper.selectPage(req.getPage(), wrapper));
    }

    @Override
    public Result findRoleIdsById(String id) {
        List<String> roleIds = baseMapper.findRoleIdsById(id);
        return Result.ok(roleIds);
    }

    @Override
    @Transactional
    public Result saveUserRole(String userId, List<String> roleIds) {
        //1. 删除用户角色关系表数据
        baseMapper.deleteUserRoleByUserId(userId);
        //2. 保存新的用户角色关系表数据
        if (CollectionUtils.isNotEmpty(roleIds)) {
            baseMapper.saveUserRole(userId, roleIds);
        }
        return Result.ok();
    }

    @Override
    public Result deleteById(String id) {
        //1. 先查询当前用户是否存在
        SysUser sysUser = baseMapper.selectById(id);
        if (sysUser == null) {
            return Result.error("用户不存在！");
        }
        //2. 若用户存在，则进行删除
        sysUser.setIsEnabled(0);
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return Result.ok();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result checkPassword(SysUserCheckPasswordREQ req) {
        if (StringUtils.isEmpty(req.getUserId())) {
            Result.error("用户ID不能为空，请重试");
        }
        if (StringUtils.isEmpty(req.getOldPassword())) {
            Result.error("原密码能为空，请重试");
        }
        SysUser sysUser = baseMapper.selectById(req.getUserId());
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        //matches方法中参数1：用户输入的密码（明文），参数2：数据库中的密码（密文）
        boolean isOk = passwordEncoder.matches(req.getOldPassword(), sysUser.getPassword());
        if (!isOk) {
            return Result.error("原密码输入错误");
        }
        return Result.ok();
    }

    @Override
    public Result updatePassword(SysUserUpdatePasswordREQ req) {
        if (StringUtils.isEmpty(req.getUserId())) {
            Result.error("用户ID不能为空，请重试");
        }
        if (StringUtils.isEmpty(req.getNewPassword())) {
            Result.error("新密码不能为空，请重试");
        }
        if (StringUtils.isEmpty(req.getRepPassword())) {
            Result.error("确认密码不能为空，请重试");
        }
        if (!req.getNewPassword().equals(req.getRepPassword())) {
            return Result.error("新密码与确认密码不一致，请重试");
        }
        SysUser sysUser = baseMapper.selectById(req.getUserId());
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        //如果有原密码，则校验是否正确
        if (StringUtils.isNotEmpty(req.getOldPassword())) {
            if (!passwordEncoder.matches(req.getOldPassword(), sysUser.getPassword())) {
                return Result.error("原密码输入错误");
            }
        }
        //校验都通过，将新密码进行加密存储
        sysUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
        baseMapper.updateById(sysUser);
        return Result.ok();
    }

    @Autowired
    private IFeignArticleController feignArticleController;

    @Autowired
    private IFeignQuestionController feignQuestionController;

    @Transactional
    @Override
    public Result update(SysUser sysUser) {
        // 1. 查询原用户信息
        SysUser user = baseMapper.selectById(sysUser.getId());
        // 2. 判断更新的用户信息中昵称和头像是否被改变
        if( !StringUtils.equals(sysUser.getNickName(), user.getNickName())
                || !StringUtils.equals(sysUser.getImageUrl(), user.getImageUrl())
        ) {
            //   2.1 只要其中一个被改变，则调用文章和问答微服务更新用户信息
            // 更新文章微服务中的用户信息
            UserInfoREQ userInfoREQ =
                    new UserInfoREQ(sysUser.getId(), sysUser.getNickName(), sysUser.getImageUrl());
            feignArticleController.updateUserInfo(userInfoREQ);
            // 更新问答微服务用户信息
            feignQuestionController.updateUserInfo(userInfoREQ);
        }

        // 3. 更新用户信息表数据 sys_user
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        // 4. 响应更新成功
        return Result.ok();
    }

    @Override
    public Result getUserTotal() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //账户是否可用（1：可用   0：不可用）
        wrapper.eq("is_enabled", 1);
        Integer count = baseMapper.selectCount(wrapper);
        return Result.ok(count);
    }

    @Override
    public Result checkUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        //茶席买大牌则说明存在，存在data = true 已被注册，不存在data = false 未被注册
        return Result.ok(sysUser != null);
    }

    @Override
    public Result register(RegisterREQ req) {
        if (StringUtils.isEmpty(req.getUsername())) {
            return Result.error("用户名不能为空");
        }
        if (StringUtils.isEmpty(req.getPassword())) {
            return Result.error("密码不能为空");
        }
        if (StringUtils.isEmpty(req.getReqPassword())) {
            return Result.error("确认密码不能为空");
        }
        if (!req.getPassword().equals(req.getReqPassword())) {
            return Result.error("两次输入的密码不一致，请重试");
        }

        //校验用户是否存在
        Result result = checkUserName(req.getUsername());
        if ((Boolean) result.getData() ) {
            return Result.error("用户已经被注册！");
        }
        //将新用户信息保存到数据库
        SysUser sysUser = new SysUser();
        sysUser.setUsername(req.getUsername());
        sysUser.setNickName(req.getUsername());
        sysUser.setPassword(passwordEncoder.encode(req.getPassword()));
        //新增操作
        this.save(sysUser);
        return Result.ok();
    }

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        return sysUser;
    }
}
