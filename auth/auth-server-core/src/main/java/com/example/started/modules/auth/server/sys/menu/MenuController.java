package com.example.started.modules.auth.server.sys.menu;

import com.example.started.common.v0.utils.Result;
import com.example.started.common.v0.utils.TreeNode2;
import com.example.started.common.v0.utils.TreeUtils;
import com.example.started.common.v0.validator.group.AddGroup;
import com.example.started.modules.auth.server.sys.menu.t.*;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
@Validated
public class MenuController {

    private final NemuService menuService;
    private final MenuRepository menuRepository;

    /**
     * 添加菜单
     */
    @PostMapping("add")
    public Result<?> add(@LoginUserId TokenUserId userId, @Validated(AddGroup.class) @RequestBody MenuDtoAdd menuDtoAdd) {
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(menuDtoAdd, entity);
        entity.setCreator(userId.getUserId());
        entity.setCreateDate(new Date());
        menuService.save(entity);
        return Result.ok();
    }

    /**
     * 所有菜单
     */
    @DeleteMapping("del")
    public Result<?> del(@LoginUserId TokenUserId userId, @RequestBody String body) {
        menuService.delById(body);
        return Result.ok();
    }

    /**
     * 添加菜单
     */
    @PostMapping("edit")
    public Result<?> edit(@LoginUserId TokenUserId userId, @Validated(AddGroup.class) @RequestBody MenuDtoEdit menuDtoEdit) {
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(menuDtoEdit, entity);
        entity.setCreator(userId.getUserId());
        entity.setCreateDate(new Date());
        menuRepository.save(entity);
        return Result.ok();
    }

    @GetMapping("info")
    public Result<MenuEntity> info(@LoginUserId TokenUserId userId, @RequestParam("id") String id) {
        MenuEntity byId = menuRepository.findById(id).get();
        return Result.ok(byId);
    }

    /**
     * 所有菜单
     */
    @PostMapping("list")
    @Transactional(readOnly = true)
    public Result<List<TreeNode2<MenuEntity>>> page(@LoginUserId TokenUserId userId) {
//        LambdaQueryWrapper<MenuEntity> where = new LambdaQueryWrapper<>();
//        where.orderByAsc(MenuEntity::getSort);
        List<MenuEntity> list = menuRepository.findAll();
        List<TreeNode2<MenuEntity>> build = TreeUtils.build(list, MenuEntity::getId, MenuEntity::getPid);
        return Result.ok(build);
    }

    /**
     * 所有菜单
     */
    @PostMapping("tree")
    @Transactional(readOnly = true)
    public Result<List<MenuTreeVo>> tree(@LoginUserId TokenUserId userId) {
        List<MenuEntity> list = menuRepository.findAll();
        List<MenuTreeVo> list1 = list.stream().map(MenuTreeVo::new).toList();
        List<MenuTreeVo> build = TreeUtils.build3(list1);
        return Result.ok(build);
    }
}