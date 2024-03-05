package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Shop;
import com.example.backend.entity.Subsidy;
import com.example.backend.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;
//    @Autowired
//    private UserMapper userMapper;

    //申请商店
//    @RequestMapping("/api/store_register1")
//    @CrossOrigin
//    public Result addShop1(@RequestBody Shop shop, @RequestParam(value = "id")Integer id) {
//        return shopService.addShop(shop,id);
//    }

    //申请注册商店
    @RequestMapping("/api/store_register")
    @CrossOrigin
    public Result addShop(@RequestBody Shop shop, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"商户未登录","");
        }
        return shopService.addShop(shop,id);
    }

    //申请删除商店
    @RequestMapping("/api/store_delete")
    @CrossOrigin
    public Result deleteShop(@RequestBody Shop shop) {
        return shopService.deleteShop(shop);
    }

    //通过申请
    @RequestMapping("/api/store_pass")
    @CrossOrigin
    public Result passShop(@RequestBody Shop shop) {
        return shopService.passShop(shop);
    }

    //不通过申请
    @RequestMapping("/api/store_not_pass")
    @CrossOrigin
    public Result notPassShop(@RequestBody Shop shop) {
        return shopService.notPassShop(shop);
    }

    //通过删除申请
    @RequestMapping("/api/store_pass_delete")
    @CrossOrigin
    public Result passDeShop(@RequestBody Shop shop) {
        return shopService.passDeShop(shop);
    }

    //不通过删除申请
    @RequestMapping("/api/store_not_pass_delete")
    @CrossOrigin
    public Result notPassDeShop(@RequestBody Shop shop) {
        return shopService.notPassDeShop(shop);
    }

    //分页展示已通过审核的店铺
    @RequestMapping("/api/store_list")
    @CrossOrigin
    public Result shopList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",shopService.selectShopPage(queryDTO));
    }

    //通过商店id返回信息
    @RequestMapping("/api/store_info_id")
    @CrossOrigin
    public Result searchUserByID(@RequestBody String id_str) {
        Shop shop = shopService.searchShopByID(id_str);
        if(shop == null) {
            return new Result(400,"商店不存在","");
        }
        return new Result(200,"",shop);
    }

    //给店主展示自己有的店铺
    @RequestMapping("/api/store_list_owner")
    @CrossOrigin
    public Result shopListOwner(@RequestBody QueryDTO queryDTO, HttpServletRequest request){
        Integer id = (Integer) request.getSession().getAttribute("id");
        String role = (String) request.getSession().getAttribute("role");
        if(id == null) {
            return new Result(400,"商户未登录","");
        } else if (!role.equals("merchant")) {
            return new Result(400,"您不是商户","");
        }
        return new Result(200,"",shopService.selectShopPageOwner(queryDTO, id));
    }

    //分页展示新注册的店铺（给管理员看的）
    @RequestMapping("/api/store_new_list")
    @CrossOrigin
    public Result shopNewList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",shopService.selectShopNewPage(queryDTO));
    }

    //分页展示申请删除的店铺（给管理员看的）
    @RequestMapping("/api/store_delete_list")
    @CrossOrigin
    public Result shopDeleteList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",shopService.selectShopDeletePage(queryDTO));
    }

    //分页展示已通过审核的店铺
    @RequestMapping("/api/store_apply_subsidy")
    @CrossOrigin
    public Result shopApplyList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",shopService.selectShopApplyPage(queryDTO));
    }

    //商户申请活动
    @RequestMapping("/api/shop_subsidy_apply")
    @CrossOrigin
    public Result applySubsidyShop(@RequestBody Subsidy subsidy, HttpServletRequest request) {
        Integer ownerid = (Integer) request.getSession().getAttribute("id");
        if(ownerid == null) {
            return new Result(400,"商户未登录","");
        }
        return shopService.applyShopSubsidy(subsidy,ownerid);
    }

    //管理员通过商户申请活动
    @RequestMapping("/api/shop_subsidy_pass")
    @CrossOrigin
    public Result shopSubsidyPass(@RequestBody Shop shop){
        return shopService.passShopSubsidy(shop);
    }

    //管理员不通过商户申请活动
    @RequestMapping("/api/shop_subsidy_not_pass")
    @CrossOrigin
    public Result shopSubsidyNotPass(@RequestBody Shop shop){
        return shopService.notPassShopSubsidy(shop);
    }
}
