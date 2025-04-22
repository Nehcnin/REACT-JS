import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.service.BookService;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.ServiceFactory;
import edu.codespring.bibliospring.backend.service.UserService;

public class Main {
    public static void main(String[] args) {
        Book book=new Book();
        BookService bookService = ServiceFactory.getBookService();

        book.setTitle("Title1");
        book.setAuthors("egy ketto harom");
        book.setAddedBy("asd");
        try {
            bookService.insert(book);
            System.out.printf("ASDASDASDASD");
        }
        catch (ServiceException e){
            System.out.println("Registration error: "+e.getMessage());
        }


    }
}