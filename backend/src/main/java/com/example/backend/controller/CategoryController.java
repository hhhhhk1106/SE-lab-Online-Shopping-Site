package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.CateRecord;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.service.CateRecordService;
import com.example.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CateRecordService cateRecordService;

    //TOkDO:申请上架；审核上架x2；申请修改；审核修改x2；下架

    //申请上架商品
    @RequestMapping("/api/category_register")
    @CrossOrigin
    public Result addCategory(@RequestBody Category category, HttpServletRequest request) {
        Integer ownerid = (Integer) request.getSession().getAttribute("id");
        if(ownerid == null) {
            return new Result(400,"商户未登录","");
        }
        return categoryService.addCategory(category, ownerid);
    }

    //申请修改
    @RequestMapping("/api/category_update")
    @CrossOrigin
    public Result updateCategory(@RequestBody Category category,@RequestParam(value = "oldCateID")Integer oldCateID,HttpServletRequest request) {
        Integer ownerid = (Integer) request.getSession().getAttribute("id");
        return categoryService.updateCategory(category,oldCateID,ownerid);
    }

    //下架
    @RequestMapping("/api/category_delete")
    @CrossOrigin
    public Result deleteCategory(@RequestBody Category category) {
        return categoryService.deleteCategory(category);
    }

    //审核通过上架/修改/活动（申请记录变为成功，商品状态变为上架/更新
    @RequestMapping("/api/category_pass")
    @CrossOrigin
    public Result passCategory(@RequestBody CateRecord cateRecord) {
        return cateRecordService.passCateRecord(cateRecord);
    }

    //审核不通过上架/修改/活动（申请记录变为失败，商品被删除或不修改
    @RequestMapping("/api/category_not_pass")
    @CrossOrigin
    public Result notPassCategory(@RequestBody CateRecord cateRecord) {
        return cateRecordService.notPassCaterRecord(cateRecord);
    }

    //通过商品id返回信息
    @RequestMapping("/api/category_info")
    @CrossOrigin
    public Result searchByID(@RequestBody String id_str) {
        Category category = categoryService.searchByID(id_str);
        if(category == null) {
            return new Result(400,"商品不存在","");
        }
        return new Result(200,"",category);
    }

    //分页展示商店内商品
    @RequestMapping("/api/category_list_shop")
    @CrossOrigin
    public Result shopList(@RequestBody QueryDTO queryDTO, @RequestParam(value = "shopID")Integer shopID){
        return new Result(200,"",categoryService.selectCatePageByShop(queryDTO,shopID));
    }

    //商户申请商品参加活动
    @RequestMapping("/api/category_subsidy_apply")
    @CrossOrigin
    public Result applySubsidyCate(@RequestBody Category category) {
        return categoryService.applySubsidyCate(category);
    }

}
