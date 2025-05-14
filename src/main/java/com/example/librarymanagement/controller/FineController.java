package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Fine;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.FineService;
import com.example.librarymanagement.service.LoanService;
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
@RequestMapping("/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @Autowired
    private LoanService loanService;

    @GetMapping
    public String listFines(Model model) {
        List<Fine> fines = fineService.findAll();
        model.addAttribute("fines", fines);
        return "fines/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("fine", new Fine());
        model.addAttribute("listLoanIDs", loanService.findAll());
        return "fines/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Fine fine, Model model) {
        if (fineService.existsById(fine.getFineId())) {
            model.addAttribute("errorMessageTP", "Mã tiền phạt đã tồn tại.");
            return "fines/form";
        }
        if (!loanService.existsById(fine.getLoanId())){
            model.addAttribute("errorMessagePM", "Mã phiếu mượn không tồn tại.");
            return "fines/form";
        }
        fineService.save(fine);
        return "redirect:/fines";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Fine fine = fineService.findById(id);
        model.addAttribute("fine", fine);
        return "fines/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        fineService.deleteById(id);
        return "redirect:/fines";
    }

    @GetMapping("/export-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=danhsach-phat.csv");

        List<Fine> fineList = fineService.findAll();

        ServletOutputStream out = response.getOutputStream();
        out.write(0xEF);
        out.write(0xBB);
        out.write(0xBF);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withHeader("Mã phạt", "Mã mượn", "Số tiền", "Thành toán"))) {
            for (Fine fine : fineList) {
                csvPrinter.printRecord(
                        fine.getFineId(),
                        fine.getLoanId(),
                        fine.getAmount(),
                        fine.getPaid()
                );
            }
        }
    }
}