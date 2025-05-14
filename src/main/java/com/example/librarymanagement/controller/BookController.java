package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.Staff;
import com.example.librarymanagement.service.BookService;
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
@RequestMapping("/books")
public class BookController {
    
    @Autowired //Bạn không cần new UserService() – Spring sẽ tự động làm điều đó!
    private BookService bookService;


    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Book book, Model model) {
        if(bookService.existsById(book.getBookId())) {
            model.addAttribute("errorMessage", "Mã sách đã tồn tại.");
            return "books/form";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/export-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=danhsach-sach.csv");

        List<Book> bookList = bookService.findAll();

        ServletOutputStream out = response.getOutputStream();
        out.write(0xEF);
        out.write(0xBB);
        out.write(0xBF);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withHeader("Mã sách", "Tiêu đề", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "ISBN", "Số lượng có sẵn"))) {
            for (Book book  : bookList) {
                csvPrinter.printRecord(
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getPublicationYear(),
                        book.getIsbn(),
                        book.getCopiesAvailable()
                );
            }
        }
    }
}