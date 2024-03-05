package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Subsidy;
import com.example.backend.service.SubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubsidyController {
    @Autowired
    private SubsidyService subsidyService;
    //TOkDO:管理员增删改查，商户看，商户申请（带商品），商户增改?

    //通过id返回活动信息
    @RequestMapping("/api/subsidy_info")
    @CrossOrigin
    public Result searchByID(@RequestBody String id_str) {
        Subsidy subsidy = subsidyService.searchByID(id_str);
        if(subsidy == null) {
            return new Result(400,"活动不存在","");
        }
        return new Result(200,"",subsidy);
    }

    //管理员准备活动
    @RequestMapping("/api/subsidy_add")
    @CrossOrigin
    public Result addSubsidy(@RequestBody Subsidy subsidy) {
        return subsidyService.addSubsidy(subsidy);
    }

    //管理员修改活动
    @RequestMapping("/api/subsidy_update")
    @CrossOrigin
    public Result updateSubsidy(@RequestBody Subsidy subsidy) {
        return subsidyService.updateSubsidy(subsidy);
    }

    //管理员开启活动
    @RequestMapping("/api/subsidy_open")
    @CrossOrigin
    public Result openSubsidy(@RequestBody Subsidy subsidy) {
        return subsidyService.openSubsidy(subsidy);
    }

    //对管理员分页展示所有活动
    @RequestMapping("/api/subsidy_list_all")
    @CrossOrigin
    public Result showSubsidyAll(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",subsidyService.selectSubsidyAll(queryDTO));
    }

    //对商户分页展示所有活动（进行中）
    @RequestMapping("/api/subsidy_list_now")
    @CrossOrigin
    public Result showSubsidyNow(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",subsidyService.selectSubsidyNow(queryDTO));
    }

    //返回参与活动的商店
    @RequestMapping("/api/subsidy_shop_list")
    @CrossOrigin
    public Result showSubsidyShop(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",subsidyService.showSubsidyShops(queryDTO, id));
    }

    //返回参与活动的商品
    @RequestMapping("/api/subsidy_cate_list")
    @CrossOrigin
    public Result showSubsidyCate(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",subsidyService.showSubsidyCategories(queryDTO, id));
    }

}
