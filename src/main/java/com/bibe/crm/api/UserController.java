package com.bibe.crm.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.UserDTO;
import com.bibe.crm.entity.dto.UserPageDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.entity.vo.UserVO;
import com.bibe.crm.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;


    /**
     * 分页列表
     * @param dto
     * @return
     */
    @PostMapping("/pageList")
    public RespVO pageList(@RequestBody UserPageDTO dto){
        Page page = dto.getPage();
        return RespVO.ofSuccess(userService.pageList(dto,page));
    }


    /**
     * 修改
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody UserDTO userDTO){
        return userService.update(userDTO);
    }

    /**
     * 添加
     * @param userDTO
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody UserDTO userDTO){
        return userService.insert(userDTO);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO<UserVO> show(Integer id){
        return userService.show(id);
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    @GetMapping("/checkPassword")
    public RespVO checkPassword(String  password){
        return userService.checkPassword(password);
    }


    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer[] ids){
        return userService.deleteByPrimaryKey(ids);
    }
}
