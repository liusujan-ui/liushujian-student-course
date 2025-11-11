package com.tencent.page.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.page.entity.Emp;
import com.tencent.page.repository.EmpDao;
import com.tencent.page.repository.EmpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 观自在
 * @date 2025-11-11 21:46
 *
 *
 */
@Controller
public class EmpController {

    @Resource
    private EmpRepository empRepository;

    @Resource
    private EmpDao empDao;
    @RequestMapping("/emp/findAll")
    public String findAll(ModelMap modelMap, @RequestParam(defaultValue = "0",value = "pageNum") int pageNum){
        Pageable pageable = PageRequest.of(pageNum, 5, Sort.Direction.DESC, "sal");
        Page<Emp> pages = empRepository.findAll(pageable);
//        System.out.println("当前页数："+pages.getNumber());
//        System.out.println("总页数："+pages.getTotalPages());
//        System.out.println("总数量："+pages.getTotalElements());
//        System.out.println("所有数据："+pages.getContent());
//        System.out.println("是否有上一页："+pages.hasPrevious());
//        System.out.println("是否有下一页："+pages.hasNext());
//        System.out.println("当前页的上一页："+(pages.hasPrevious()?pages.previousPageable().getPageNumber():0));
//        System.out.println("当前页的下一页："+(pages.hasNext()?pages.nextPageable().getPageNumber():0));
        modelMap.addAttribute("pages", pages);
        return "list";
    }
    @RequestMapping("/emp/findAll1")
    public String findAll1(ModelMap modelMap, @RequestParam(defaultValue = "0",value = "pageNum") int pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Emp> emps = empDao.findAll1();
        PageInfo<Emp> pages = new PageInfo<>(emps);
        modelMap.addAttribute("pages", pages);
        return "list1";
    }

}
