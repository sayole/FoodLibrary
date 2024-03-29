package com.foodlibrary.foodlibrary.controller;

import com.foodlibrary.foodlibrary.entity.Product;
import com.foodlibrary.foodlibrary.entity.Zzim;
import com.foodlibrary.foodlibrary.service.ProductService;
import com.foodlibrary.foodlibrary.service.UserService;
import com.foodlibrary.foodlibrary.service.ZzimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZzimController {
    @Autowired
    ZzimService zzimService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @RequestMapping(value="/addzzim",method= RequestMethod.POST)
    public ResponseEntity<Void> addzzim(@RequestBody Zzim zzim) {
        if(zzimService.findLike(zzim) == null){
            zzimService.addZzim(zzim);
        }else{
            zzimService.deleteZzim(zzim);
        }
        Product product = productService.getOneProduct(zzim.getPrdlstreportno());
        product.setZzimcount(zzimService.countZzim(zzim));
        productService.updateProduct(product);
        System.out.println("check");

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value="/zzimUsers/{productNo}",method= RequestMethod.POST)
    public ResponseEntity<List<String>> getZzimUsers(@PathVariable String productNo) {
        return new ResponseEntity<List<String>>(zzimService.getProductZzimCount(productNo),HttpStatus.OK);
    }

    @RequestMapping(value="/userzzimproduct/{nickName}",method= RequestMethod.POST)
    public ResponseEntity<List<String>> getUserZzimProduct(@PathVariable String nickName) {
        List<Zzim> userProducts = zzimService.getZzimAsNickname(nickName);
        List<String> products = new ArrayList<String>();
        for(Zzim product : userProducts) {
            products.add(product.getPrdlstreportno());
        }
        return new ResponseEntity<List<String>>(products,HttpStatus.OK);
    }
}
