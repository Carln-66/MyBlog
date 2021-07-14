package com.carl.blog.oauth2.service;

import com.carl.blog.entities.SysMenu;
import com.carl.blog.entities.SysUser;
import com.carl.blog.feign.IFeignSystemController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/05/07/19:00
 * @Description: 用来实现安全配置类中的UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IFeignSystemController feignSystemController;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException("用户名不能为空");
        }
        // 2. 通过用户名查询数据库中的用户信息
        SysUser sysUser = feignSystemController.findUserByUsername(username);
        if (sysUser == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 3. 通过用户id去查询数据库的拥有的权限信息
        List<SysMenu> menuList =
                feignSystemController.findMenuListByUserId(sysUser.getId());

        // 4. 封装权限信息（权限标识符code）
        List<GrantedAuthority> authorities = null;
        if (CollectionUtils.isNotEmpty(menuList)) {
            authorities = new ArrayList<>();
            for (SysMenu menu : menuList) {
                // 权限标识
                String code = menu.getCode();
                authorities.add(new SimpleGrantedAuthority(code));
            }
        }

        // 5. 构建UserDetails接口的实现类JwtUser对象
        JwtUser jwtUser = new JwtUser(sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getNickName(),
                sysUser.getImageUrl(),
                sysUser.getMobile(),
                sysUser.getEmail(),
                sysUser.getIsAccountNonExpired(),
                sysUser.getIsAccountNonLocked(),
                sysUser.getIsCredentialsNonExpired(),
                sysUser.getIsEnabled(),
                authorities);

        return jwtUser;
    }
}
