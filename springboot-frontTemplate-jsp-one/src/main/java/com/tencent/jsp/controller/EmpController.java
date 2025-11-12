package com.tencent.jsp.controller;

import com.tencent.jsp.entity.Emp;
import com.tencent.jsp.repository.EmpRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 观自在
 * @date 2025-11-12 20:38
 */
@Controller
public class EmpController {

    @Resource
    private EmpRepository empRepository;
    @RequestMapping("/findAll")
    public String findAll(HttpSession session) {
        List<Emp> all = empRepository.findAll();
        session.setAttribute("emps", all);
        return "list";
    }
}
