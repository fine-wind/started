package com.example.started.modules.table.controller;

import com.example.started.modules.table.bo.add.ShowDataBo;
import com.example.started.modules.table.service.TableDbService;
import com.example.started.modules.table.vo.list.ShowDataVo;
import com.example.started.modules.table.bo.ShowQueryBo;
import com.example.common.v0.utils.Result;
import com.example.started.modules.table.vo.edit.ShowEditVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
@Api(tags = "有关显示的接口")
public class ShowController {

    private final TableDbService tableDbService;

    @Autowired
    public ShowController(TableDbService tableDbService) {
        this.tableDbService = tableDbService;
    }

    /**
     * 添加
     *
     * @param vp 虚拟路径
     * @return 结果
     */
    @GetMapping("t/{vp}")
    public Result<ShowEditVo> tableInfo(@PathVariable("vp") String vp) {
        return Result.ok(tableDbService.tableInfo(vp));
    }

    /**
     * 添加
     *
     * @param vp      虚拟路径
     * @param addBody 要添加的数据
     * @return 结果
     */
    @PreAuthorize("@se.hasRole('show.table.put')")
    @PutMapping("/{vp}")
    public Result<Object> add(@PathVariable("vp") String vp, @RequestBody List<ShowDataBo> addBody) {
        // todo 数据校验
        tableDbService.saveData(vp, addBody);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param vp 虚拟路径
     * @param id .
     * @return .
     */
    @DeleteMapping("/{vp}/{id}")
    public Result<Object> delete(@PathVariable("vp") String vp, @PathVariable("id") String id) {
        // todo 删除权限校验
        return Result.ok();
    }

    /**
     * 更新
     *
     * @param vp      虚拟路径
     * @param queryBo .
     * @return .
     */
    @PostMapping("/{vp}")
    public Result<ShowDataVo> update(@PathVariable("vp") String vp, @RequestBody ShowQueryBo queryBo) {
        // todo  权限校验
        return Result.ok();
    }

    /**
     * 获取列表
     *
     * @param vp      虚拟路径
     * @param queryBo .
     * @return .
     */
    @PreAuthorize("@se.hasRole('show.table.list')")
    @PostMapping("/list/{vp}")
    public Result<ShowDataVo> list(@PathVariable("vp") String vp, @RequestBody ShowQueryBo queryBo) {
        // todo  权限校验 和 查询字段校验
        ShowDataVo list = tableDbService.list(vp, queryBo);
        return Result.ok(list);
    }

    /**
     * 详情
     *
     * @param vp 虚拟路径
     * @param id id
     * @return 数据
     */
    @GetMapping("/info/{vp}/{id}")
    public Result<Object> get(@PathVariable("vp") String vp, @PathVariable("id") String id) {
        // todo 详情权限校验
        return Result.ok();
    }

}
