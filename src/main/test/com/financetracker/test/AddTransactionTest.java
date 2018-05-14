import java.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.transactions.TransactionDAO;
import com.financetracker.model.users.User;

import junit.framework.TestCase;

public class AddTransactionTest extends TestCase {

	@Autowired
	TransactionDAO transactionDAO;

	
	@Test
	public void testAddTransaction() {

		try {
			User user = new User("ivan@abv.bg", "123");
			Account account = new Account(user, "myAccount", 2000, "1234", 1, 12, "bg", "Loan");
			LocalDate date = LocalDate.now();
			Transaction transaction = new Transaction("testPayee", 100, date, account, "car", false);
			int result = transactionDAO.addTransaction(transaction);

			assertTrue(result >= 1);
			assertEquals(account.getBalance(), 1900);
		} catch (TransactionException | UserException | AccountException e) {
			e.printStackTrace();
		}

	} 

	@Test
	public void testGetTransactionById() {

		try {
			TransactionDAO dao = new TransactionDAO();
			
			Transaction tr = dao.getTransactionById(13);
			assertEquals(tr.getPayee(), "globul");
		} catch (TransactionException e) {
			e.printStackTrace();
		}

	}

}
