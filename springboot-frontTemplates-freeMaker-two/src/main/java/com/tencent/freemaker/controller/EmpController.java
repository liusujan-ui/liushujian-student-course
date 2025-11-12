package com.tencent.freemaker.controller;


import com.tencent.freemaker.entity.Emp;
import com.tencent.freemaker.repository.EmpRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 观自在
 * @date 2025-11-12 20:38
 */
@Controller
public class EmpController {

    @Resource
    private EmpRepository empRepository;



    @RequestMapping("/show")
    public String findAll1(HttpSession session) {
        List<Emp> all = empRepository.findAll();
        System.out.println(all.size());
        session.setAttribute("emplist", all);
        return "list";
    }
}
