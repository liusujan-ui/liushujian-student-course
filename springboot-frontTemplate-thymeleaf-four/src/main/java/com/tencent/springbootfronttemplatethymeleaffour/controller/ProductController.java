package com.tencent.springbootfronttemplatethymeleaffour.controller;


import com.tencent.springbootfronttemplatethymeleaffour.entity.Product;
import com.tencent.springbootfronttemplatethymeleaffour.entity.Users;
import com.tencent.springbootfronttemplatethymeleaffour.repository.ProductRepository;
import com.tencent.springbootfronttemplatethymeleaffour.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author 来苏
 * @Description TODO
 */
@Controller
public class ProductController {
    @Resource
    private UsersRepository usersRepository;
    @Resource
    private ProductRepository productRepository;
    @ResponseBody
    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id){
        productRepository.deleteById(id);
        return "success";
    }

    @ResponseBody
    @GetMapping("/product/{id}")
    public Object findProductById(@PathVariable int id){
        return productRepository.findById(id);
    }

    @RequestMapping("/product/update")
    public String updateProduct(Product product){
        productRepository.updateProduct(product);
        return "redirect:/user/list";
    }

    @RequestMapping("/product/add")
    public String addProduct(Product product,HttpSession session){
        product.setUid(((Users)session.getAttribute("userInfo")).getId());
        productRepository.addProduct(product);
        return "redirect:/user/list";
    }
}
