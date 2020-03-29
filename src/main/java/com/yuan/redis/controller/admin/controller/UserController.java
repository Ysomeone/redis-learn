package com.yuan.redis.controller.admin.controller;


import com.yuan.redis.controller.admin.bean.ActiveUser;
import com.yuan.redis.controller.admin.bean.ResponseBean;
import com.yuan.redis.controller.admin.config.JWTToken;
import com.yuan.redis.controller.admin.converter.RoleConverter;
import com.yuan.redis.controller.admin.pojo.LoginLog;
import com.yuan.redis.controller.admin.pojo.Role;
import com.yuan.redis.controller.admin.pojo.User;
import com.yuan.redis.controller.admin.service.LoginLogService;
import com.yuan.redis.controller.admin.service.RoleService;
import com.yuan.redis.controller.admin.service.UserService;
import com.yuan.redis.controller.admin.util.IPUtil;
import com.yuan.redis.controller.admin.util.JWTUtils;
import com.yuan.redis.controller.admin.util.MD5Utils;
import com.yuan.redis.controller.admin.vo.*;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
@Validated
@Api(value = "系统用户模块",tags = "系统用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 创建登入日志
     * @param
     * @return
     */
    public static LoginLog createLoginLog(HttpServletRequest request) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        LoginLog loginLog=new LoginLog();
        loginLog.setUserName(activeUser.getUser().getUsername());
        loginLog.setIp(IPUtil.getIpAddr(request));
//        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        // 获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setUserSystem(os.getName());
        loginLog.setUserBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }

    /**
     * 用户登入
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登入", notes = "用户名和密码登入系统，登入成功后返回JWTToken")
    @PostMapping("/login")
    public ResponseBean login(@NotBlank(message = "用户名必填") String username,
                              @NotBlank(message = "密码必填") String password,
                              HttpServletRequest request) {
        Object token ;
        User user = userService.findUserByName(username);
        if (user != null) {
            String salt = user.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JWTUtils.sign(username, target);
            JWTToken jwtToken = new JWTToken((String) token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                return ResponseBean.error(e.getMessage());
            }
        }else {
            return ResponseBean.error("用户名不存在");
        }
        //登入日志
        LoginLog loginLog = createLoginLog(request);
        loginLogService.add(loginLog);
        return ResponseBean.success(token);
    }

    /**
     * 用户列表
     * @return
     */
    @ApiOperation(value = "用户列表",notes = "模糊查询用户列表")
    @GetMapping("/findUserList")
    public ResponseBean findUserList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "7") Integer pageSize,
                                     UserVO userVO){
        PageVO<UserVO> userList= userService.findUserList(pageNum,pageSize,userVO);
        return ResponseBean.success(userList);
    }

    /**
     * 用户信息
     * @return
     */
    @ApiOperation(value = "用户信息",notes = "用户登入信息")
    @GetMapping("/info")
    public ResponseBean info() {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar(activeUser.getUser().getAvatar());
        userInfoVO.setUsername(activeUser.getUser().getUsername());
        userInfoVO.setRoles(activeUser.getRoles());
        userInfoVO.setMenus(activeUser.getMenus());
        return ResponseBean.success(userInfoVO);
    }

    /**
     * 加载菜单
     * @return
     */
    @ApiOperation(value = "加载菜单",notes = "用户登入后,根据角色加载菜单树")
    @GetMapping("/findMenu")
    public ResponseBean findMenu() {
        List<MenuNodeVO> menuTreeVOS = userService.findMenu();
        return ResponseBean.success(menuTreeVOS);
    }

    /**
     * 分配角色
     * @param id
     * @param rids
     * @return
     */
    @ApiOperation(value = "分配角色",notes = "用户角色分配")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseBean assignRoles(@PathVariable Long id, @RequestBody Long[] rids){
        try {
            userService.assignRoles(id,rids);
            return ResponseBean.success("分配角色成功");
        } catch (Exception e) {
            return ResponseBean.error("分配角色失败");
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return
     */
    @RequiresPermissions({"user:delete"})
    @ApiOperation(value = "删除用户",notes = "删除用户信息，根据用户ID")
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        try {
            userService.deleteById(id);
            return ResponseBean.success("删除成功");
        } catch (Exception e) {
            return ResponseBean.error("删除失败");
        }
    }

    /**
     * 更新状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "禁用状态",notes = "禁用和启用这两种状态")
    @RequiresPermissions({"user:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseBean updateStatus(@PathVariable Long id, @PathVariable Boolean status){
        try {
            userService.updateStatus(id,status);
            return ResponseBean.success("更新状态成功");
        } catch (Exception e) {
            return ResponseBean.error("更新状态失败");
        }
    }

    /**
     * 更新用户
     * @param id
     * @param userEditVO
     * @return
     */
    @ApiOperation(value = "更新用户",notes = "更新用户信息")
    @RequiresPermissions({"user:update"})
    @PostMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated UserEditVO userEditVO){
        try {
            userService.update(id,userEditVO);
            return ResponseBean.success("更新用户成功");
        } catch (Exception e) {
            return ResponseBean.error("更新用户失败");
        }
    }

    /**
     * 编辑用户
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑用户",notes = "获取用户的详情，编辑用户信息")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        try {
            UserEditVO userVO=userService.edit(id);
            return ResponseBean.success(userVO);
        } catch (Exception e) {
            return ResponseBean.error("编辑失败");
        }
    }

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    @ApiOperation(value = "添加用户",notes = "添加用户信息")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated UserVO userVO){
        try {
            User user = userService.findUserByName(userVO.getUsername());
            if(user==null){
                userService.add(userVO);
                return ResponseBean.success("添加用户成功");
            }else {
                return ResponseBean.error("用户名已被占用");
            }
        } catch (Exception e) {
            return ResponseBean.error("添加用户失败");
        }
    }

    /**
     * 拥有角色ID
     * @param id
     * @return
     */
    @ApiOperation(value = "已有角色",notes = "根据用户id，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseBean roles(@PathVariable Long id){
        List<Long> values=userService.roles(id);
        List<Role> list=roleService.findAll() ;
        //转成前端需要的角色Item
        List<RoleTransferItemVO> items= RoleConverter.converterToRoleTransferItem(list);
        Map<String,Object> map=new HashMap<>();
        map.put("roles",items);
        map.put("values",values);
        return ResponseBean.success(map);
    }

}
