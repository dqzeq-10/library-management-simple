package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Staff;
import com.example.librarymanagement.service.StaffService;
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
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public String listStaff(Model model) {
        List<Staff> staffList = staffService.findAll();
        model.addAttribute("staffList", staffList);
        return "staff/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Staff staff, Model model) {
        if (staffService.existsById(staff.getStaffId())) {
            model.addAttribute("errorMessage", "Mã nhân viên đã tồn tại.");
            return "staff/form";
        }
        staffService.save(staff);
        return "redirect:/staff";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Staff staff = staffService.findById(id);
        model.addAttribute("staff", staff);
        return "staff/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        staffService.deleteById(id);
        return "redirect:/staff";
    }

    @GetMapping("/export-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=danhsach-nhanvien.csv");

        List<Staff> staffList = staffService.findAll();

        ServletOutputStream out = response.getOutputStream();
        out.write(0xEF);
        out.write(0xBB);
        out.write(0xBF);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withHeader("Mã nhân viên", "Họ", "Tên", "Email", "Điện thoại", "Chức vụ"))) {
            for (Staff staff : staffList) {
                csvPrinter.printRecord(
                        staff.getStaffId(),
                        staff.getFirstName(),
                        staff.getLastName(),
                        staff.getEmail(),
                        staff.getPhone(),
                        staff.getPosition()
                );
            }
        }
    }
}