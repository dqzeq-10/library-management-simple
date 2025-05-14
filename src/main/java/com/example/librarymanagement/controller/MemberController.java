package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Fine;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.service.MemberService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listMembers(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new Member());
        return "members/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Member member, Model model) {
        if (memberService.existsById(member.getMemberId())) {
            model.addAttribute("errorMessage", "Mã thành viên đã tồn tại.");
            return "members/form";
        }
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        memberService.deleteById(id);
        return "redirect:/members";
    }


    @GetMapping("/export-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=danhsach-thanhvien.csv");

        List<Member> memberList = memberService.findAll();

        ServletOutputStream out = response.getOutputStream();
        out.write(0xEF);
        out.write(0xBB);
        out.write(0xBF);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withHeader("Mã thành viên", "Họ", "Tên", "Ngày sinh", "Địa chỉ", "Điện thoại", "Email", "Ngày tham gia"))) {
            for (Member member : memberList) {
                csvPrinter.printRecord(
                        member.getMemberId(),
                        member.getLastName(),
                        member.getFirstName(),
                        member.getDateOfBirth(),
                        member.getAddress(),
                        member.getPhone(),
                        member.getEmail(),
                        member.getMembershipDate()
                );
            }
        }
    }
}