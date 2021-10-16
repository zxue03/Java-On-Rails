package jrails;

import books.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.*;


public class ModelTest {

	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model() {
		};
	}

//    @Test
//    public void id() {
//        assertThat(model.id(), notNullValue());
//    }

	

	@Test
	public void testModelSave() {

		Book b1 = new Book();
		b1.title = "Harry Potter";
		b1.author = "JK Rowling";
		b1.num_copies = 7000;
		b1.save();
//
		Book b2 = new Book();
		b2.title = "The Selfih Gene";
		b2.author = "Richard Dawkins";
		b2.num_copies = 999;
		b2.save();
//		b2.num_copies = 996;
//		b2.save();
////		assert (Model.all(Book.class).size() == 1);
		Book b3 = new Book();
		b3.title = "The Lord of Ring";
		b3.author = "JRR Tolken";
		b3.num_copies = 1000;
		b3.save();
//
//		List<Book> all_books = Model.all(Book.class);
//		
//		for(Book book : all_books) {
//			System.out.println(book.title + " " + book.author + " " + book.num_copies);
//		}
		
		b3.destroy();

	}

	@Test
	public void testReset() {
//		Model.reset();

	}

	@After
	public void tearDown() throws Exception {
	}
}