package com.example.librarymanagement.service;


import com.example.librarymanagement.model.Report;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.FineRepository;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private FineRepository fineRepository;

    public Report generateBookCirculationReport(){
        Map<String, Object> stats = new HashMap<>();

        Integer totalBooks = jdbcTemplate.queryForObject(
                "SELECT SUM(CopiesAvailable) FROM Books", Integer.class);


        Integer totalLoans = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Loans", Integer.class);

        Integer activeLoans = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Loans WHERE ReturnDate IS NULL", Integer.class);

        Integer dueThisWeek = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Loans WHERE ReturnDate IS NULL AND DueDate BETWEEN GETDATE() AND DATEADD(DAY, 7, GETDATE())", Integer.class);

        List<Map<String, Object>> topBooks = jdbcTemplate.queryForList("SELECT TOP 5 b.Title, COUNT(l.LoanID) AS LoanCount FROM Books b JOIN Loans l ON b.BookID = l.BookID GROUP BY b.Title ORDER BY LoanCount DESC");

        stats.put("totalBooks", totalBooks);
        stats.put("totalLoans", totalLoans);
        stats.put("activeLoans", activeLoans);
        stats.put("dueThisWeek", dueThisWeek);
        stats.put("topBooks", topBooks);
        stats.put("generatedAt", LocalDate.now());

        return new Report("Book Circulation Report", Report.ReportType.BOOK_CIRCULATION, stats);
    }

    public Report generateMemberActivityReport(){
        Map<String, Object> stats = new HashMap<>();

        Integer totalMembers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Members", Integer.class);

        Integer newMembers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Members WHERE MembershipDate >= DATEADD(MONTH, -1, GETDATE())", Integer.class);

        Integer activeMembers = jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT MemberID) FROM Loans WHERE LoanDate >= DATEADD(MONTH, -3, GETDATE())",Integer.class);

        List<Map<String, Object>> topReaders = jdbcTemplate.queryForList( "SELECT TOP 5 m.FirstName + ' ' + m.LastName AS MemberName, COUNT(l.LoanID) AS LoanCount FROM Members m JOIN Loans l ON m.MemberID = l.MemberID GROUP BY m.FirstName, m.LastName ORDER BY LoanCount DESC");

        stats.put("totalMembers", totalMembers);
        stats.put("newMembers", newMembers);
        stats.put("activeMembers", activeMembers);
        stats.put("topReaders", topReaders);
        stats.put("generatedAt", LocalDate.now());

        return new Report("Member Activity Report", Report.ReportType.MEMBER_ACTIVITY, stats);

    }

    public Report generateOverdueReport(){
        Map<String, Object> stats = new HashMap<>();

        List<Map<String, Object>> overdueBooks = jdbcTemplate.queryForList(   "SELECT b.Title, m.FirstName + ' ' + m.LastName AS MemberName, " +
                "l.DueDate, DATEDIFF(DAY, l.DueDate, GETDATE()) AS DaysOverdue " +
                "FROM Loans l " +
                "JOIN Books b ON l.BookID = b.BookID " +
                "JOIN Members m ON l.MemberID = m.MemberID " +
                "WHERE l.ReturnDate IS NULL AND l.DueDate < GETDATE() " +
                "ORDER BY DaysOverdue DESC");


        stats.put("overdueBooks", overdueBooks);
        stats.put("totalOverdue", overdueBooks.size());
        stats.put("generatedAt", LocalDate.now());

        return new Report("Overdue Books Report", Report.ReportType.OVERDUE_BOOKS, stats);
    }

    public Report generateFineReport(){
        Map<String, Object> stats = new HashMap<>();

        Integer totalFines = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Fines", Integer.class);

        Double totalFineAmount = jdbcTemplate.queryForObject("SELECT ISNULL(SUM(Amount), 0.0) FROM Fines", Double.class);

        Double collectedAmount = jdbcTemplate.queryForObject("SELECT ISNULL(SUM(Amount), 0.0) FROM Fines WHERE Paid = 1", Double.class);

        Double pendingAmount = jdbcTemplate.queryForObject("SELECT ISNULL(SUM(Amount), 0.0) FROM Fines WHERE Paid = 0", Double.class);

        stats.put("totalFines", totalFines);
        stats.put("totalFineAmount", totalFineAmount);
        stats.put("collectedAmount", collectedAmount);
        stats.put("pendingAmount", pendingAmount);
        stats.put("generatedAt", LocalDate.now());

        return new Report("Fine Collection Report", Report.ReportType.FINE_COLLECTION, stats);

    }

    public List<Report> getAllAvailableReports() {
        return List.of(
                generateBookCirculationReport(),
                generateMemberActivityReport(),
                generateOverdueReport(),
                generateFineReport()
        );
    }
}
