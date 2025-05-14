package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Fine;
import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.LoanService;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listLoans(Model model) {
        List<Loan> loans = loanService.findAll();
        model.addAttribute("loans", loans);
        return "loans/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Loan loan = new Loan();
        loan.setLoanDate(new java.sql.Date(System.currentTimeMillis()));
        model.addAttribute("loan", loan);
        model.addAttribute("listBookIDs", bookService.findAll());
        model.addAttribute("listMemberIDs", memberService.findAll());
        return "loans/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Loan loan, Model model) {
        if (loan.getLoanDate() == null) {
            loan.setLoanDate(new java.sql.Date(System.currentTimeMillis()));
        }
        if (loanService.existsById(loan.getLoanId())) {
            model.addAttribute("errorMessagePM", "Mã phiếu mượn đã tồn tại.");
            return "loans/form";
        }
        if (!bookService.existsById(loan.getBookId())) {
            model.addAttribute("errorMessageMS", "Mã sách không tồn tại.");
            return "loans/form";
        }
        if (!memberService.existsById(loan.getMemberId())) {
            model.addAttribute("errorMessageMTV", "Mã thành viên không tồn tại.");
            return "loans/form";
        }
        if (loan.getReturnDate() != null && loan.getReturnDate().before(loan.getLoanDate())) {
            model.addAttribute("errorMessageNTT", "Ngày trả không thể trước ngày mượn.");
            return "loans/form";
        }
        if (loan.getReturnDate() != null && loan.getLoanDate().after(loan.getLoanDate())) {
            model.addAttribute("errorMessageNMS", "Ngày mượn không thể sau ngày mượn.");
            return "loans/form";
        }
        loanService.save(loan);
        return "redirect:/loans";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Loan loan = loanService.findById(id);
        model.addAttribute("loan", loan);
        return "loans/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        loanService.deleteById(id);
        return "redirect:/loans";
    }
    
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable String id) {
        Loan loan = loanService.findById(id);
        if (loan != null && loan.getReturnDate() == null) {
            loan.setReturnDate(new java.sql.Date(System.currentTimeMillis()));
            loanService.save(loan);
        }
        return "redirect:/loans";
    }

    @GetMapping("/export-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=danhsach-muon.csv");

        List<Loan> loanList = loanService.findAll();

        ServletOutputStream out = response.getOutputStream();
        out.write(0xEF);
        out.write(0xBB);
        out.write(0xBF);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withHeader("Mã mượn", "Mã sách", "Mã thành viên", "Ngày mượn", "Hạn trả", "Ngày trả"))) {
            for (Loan loan : loanList) {
                csvPrinter.printRecord(
                        loan.getLoanId(),
                        loan.getBookId(),
                        loan.getMemberId(),
                        loan.getLoanDate(),
                        loan.getDueDate(),
                        loan.getReturnDate()
                        );
            }
        }
    }

}