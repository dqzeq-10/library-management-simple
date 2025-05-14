package com.example.librarymanagement.controller;


import com.example.librarymanagement.model.Report;
import com.example.librarymanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String listReports(Model model) {
        model.addAttribute("reports", reportService.getAllAvailableReports());
        return "reports/list";
    }

    @GetMapping("/circulation")
    public String circulationReport(Model model){
        Report report = reportService.generateBookCirculationReport();
        model.addAttribute("report", report);
        return "reports/circulation";
    }

    @GetMapping("/member-activity")
    public String memberActivityReport(Model model){
        Report report = reportService.generateMemberActivityReport();
        model.addAttribute("report", report);
        return "reports/member-activity";
    }

    @GetMapping("overdue")
    public String overdueReport(Model model){
        Report report = reportService.generateOverdueReport();
        model.addAttribute("report", report);
        return "reports/overdue";
    }

    @GetMapping("/fines")
    public String fineReport(Model model){
        Report report = reportService.generateFineReport();
        model.addAttribute("report", report);
        return "reports/fines";
    }
}
