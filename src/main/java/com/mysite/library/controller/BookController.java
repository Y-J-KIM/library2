package com.mysite.library.controller;

import com.mysite.library.dto.BookDTO;
import com.mysite.library.dto.BookSearchDTO;
import com.mysite.library.entity.Book;
import com.mysite.library.service.BookAPIService;
import com.mysite.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private BookAPIService bookAPIService;

    //도서 등록하기
    @GetMapping("/create")
    public String showForm(@RequestParam(value = "isbn", required = false) String isbn, Model model) {
        BookDTO bookDTO = new BookDTO();
        if (isbn != null) {
            Optional<Book> book = bookService.findById(isbn);
            if (book.isPresent()) {
                bookDTO.setIsbn(book.get().getIsbn());
                bookDTO.setTitle(book.get().getTitle());
                bookDTO.setAuthor(book.get().getAuthor());
                bookDTO.setPublisher(book.get().getPublisher());
                bookDTO.setDescription(book.get().getDescription());
                bookDTO.setImage(book.get().getImage());
            }
        }
        model.addAttribute("book", bookDTO);
        return "books/create";
    }

    //도서 저장 후 list 페이지 이동
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookDTO bookDTO, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setDescription(bookDTO.getDescription());

        if (!imageFile.isEmpty()) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(uploadDir + File.separator + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                book.setImage("/uploads/" + imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bookService.save(book);
        model.addAttribute("message", "등록되었습니다");
        return "redirect:/books/list";
    }

//    @PostMapping("/update")
//    public String updateBook(@ModelAttribute("book") BookDTO bookDTO, @RequestParam("imageFile") MultipartFile imageFile) {
//        Optional<Book> existingBook = bookService.findById(bookDTO.getIsbn());
//        if (existingBook.isPresent()) {
//            Book book = existingBook.get();
//            book.setTitle(bookDTO.getTitle());
//            book.setAuthor(bookDTO.getAuthor());
//            book.setPublisher(bookDTO.getPublisher());
//            book.setDescription(bookDTO.getDescription());
//
//            if (!imageFile.isEmpty()) {
//                try {
//                    // Check if the directory exists, if not, create it
//                    File directory = new File(uploadDir);
//                    if (!directory.exists()) {
//                        directory.mkdirs();
//                    }
//
//                    byte[] bytes = imageFile.getBytes();
//                    Path path = Paths.get(uploadDir + File.separator + imageFile.getOriginalFilename());
//                    Files.write(path, bytes);
//                    book.setImage("/uploads/" + imageFile.getOriginalFilename());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            bookService.save(book);
//        }
//        return "redirect:/books/list";
//    }

    //도서 목록
    @GetMapping("/list")
    public String listBooks(Model model,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookService.findBooks(pageable);
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("paging", bookPage);
        model.addAttribute("size", size);
        return "books/list";
    }

    //도서 삭제
    @PostMapping("/delete/{isbn}")
    public String deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteById(isbn);
        return "redirect:/books/list";
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchDto", new BookSearchDTO());
        return "books/search";
    }

    @PostMapping("/search/results")
    public String searchBooksPost(@ModelAttribute("searchDto") BookSearchDTO searchDto,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) throws IOException {
        return searchBooks(searchDto, page, size, model);
    }

    @GetMapping("/search/results")
    public String searchBooksGet(@ModelAttribute("searchDto") BookSearchDTO searchDto,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) throws IOException {
        return searchBooks(searchDto, page, size, model);
    }

    private String searchBooks(BookSearchDTO searchDto, int page, int size, Model model) throws IOException {
        if (searchDto.getKeyword() == null || searchDto.getKeyword().trim().isEmpty()) {
            model.addAttribute("noResults", true);
            return "books/result";
        }

        Page<Book> books = bookService.searchBooks(searchDto.getSearchBy(), searchDto.getKeyword(), page, size);
        model.addAttribute("books", books.getContent());
        model.addAttribute("paging", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("searchDto", searchDto);
        if (books.isEmpty()) {
            model.addAttribute("noResults", true);
        }
        return "books/result";
    }

//    @GetMapping("/test")
//    public void test() throws IOException {
//        bookAPIService.searchAndSave();
//    }
}
