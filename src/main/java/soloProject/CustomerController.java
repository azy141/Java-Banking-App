package soloProject;


import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.entities.CurrentAccount;
import com.fdmgroup.entities.Customer;
import com.fdmgroup.entities.ISAAccount;
import com.fdmgroup.entities.SavingsAccount;
import com.fdmgroup.repositories.CurrentAccountRepo;
import com.fdmgroup.repositories.CustomerAccountRepo;
import com.fdmgroup.repositories.ISAAccountRepo;
import com.fdmgroup.repositories.SavingsAccountRepo;

@Controller
@SessionAttributes({ "CustomerSessions", "isaaccountsession", "savingsaccountsession", "currentAccountsession" })
public class CustomerController {

	@Autowired
	CustomerAccountRepo customerrep;

	@Autowired
	CurrentAccountRepo currentrep;
	@Autowired
	ISAAccountRepo isarep;
	@Autowired
	SavingsAccountRepo savingsrep;

	@GetMapping("home")
	public String createCustomer(Model model, @ModelAttribute Customer customer) {

		String username = customer.getUsername();
		model.addAttribute("customer", customer);
		customerrep.save(customer);

		int ifCurrentAccount = customer.getIfCurrentAccount();
		int ifISAAccount = customer.getIfISAAccount();
		int ifSavingsAccount = customer.getIfSavingsAccount();

		if (ifCurrentAccount == 1) {
			CurrentAccount currentaccount = new CurrentAccount(username, 0);
			currentrep.save(currentaccount);
			System.out.println(currentaccount);
		}

		if (ifSavingsAccount == 1) {
			SavingsAccount savingsaccount = new SavingsAccount(username, 0);
			savingsrep.save(savingsaccount);
			System.out.println(savingsaccount);
		}
		if (ifISAAccount == 1) {
			ISAAccount ISAAccount = new ISAAccount(username, 0);
			isarep.save(ISAAccount);
			System.out.println(ISAAccount);
		}
		return "Login";

	}

	@GetMapping("loggedIn")
	public String Login(@RequestParam("Username") String username, @RequestParam("Password") String password,
			Model model, HttpSession session) {

		Optional<Customer> copt = customerrep.findById(username);
		Customer customer = copt.get();

		if (customer != null && customer.getPassword().equals(password)) {

			double currentAccountBalance = 0;
			double savingsAccountBalance = 0;
			double isaAccountBalance = 0;

			String customerName = customer.getFirstName() + " " + customer.getLastName();
			model.addAttribute("customerName", customerName);
			model.addAttribute("username", username);

			session.setAttribute("CustomerSessions", customer);

			if (customer.getIfCurrentAccount() == 1) {
				Optional<CurrentAccount> currentopt = currentrep.findById(username);
				CurrentAccount currentaccount = currentopt.get();
				currentAccountBalance = currentaccount.getBalance();
				double overdraft = currentaccount.getOverDraftAmount();
				double currentAccountAvailableBalance = overdraft + currentAccountBalance;
				model.addAttribute("currentaccount", currentAccountBalance);
				model.addAttribute("currentAccountAvailableBalance", currentAccountAvailableBalance);
				model.addAttribute("overdraftLimit", overdraft);
				session.setAttribute("currentAccountsession", currentaccount);
			}

			if (customer.getIfSavingsAccount() == 1) {
				Optional<SavingsAccount> savingsopt = savingsrep.findById(username);
				SavingsAccount savingsaccount = savingsopt.get();
				savingsAccountBalance = savingsaccount.getBalance();
				model.addAttribute("savingsaccount", savingsAccountBalance);

				session.setAttribute("savingsaccountsession", savingsaccount);

			}

			if (customer.getIfISAAccount() == 1) {
				Optional<ISAAccount> isaopt = isarep.findById(username);
				ISAAccount isaaccount = isaopt.get();
				isaAccountBalance = isaaccount.getBalance();
				model.addAttribute("isaaccount", isaAccountBalance);

				session.setAttribute("isaaccountsession", isaaccount);
			}
		}

		else {
			model.addAttribute("accountnotfound", "Account Not Found");
			return "Login";
		}

		return "LoggedInPage";
	}

	@GetMapping("Logginpage")
	public String goToLoginPage(Model model, HttpSession session) {

		if (session.getAttribute("CustomerSessions") != null) {

			Customer customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			String customerName = customer.getFirstName() + " " + customer.getLastName();
			model.addAttribute("customerName", customerName);
			model.addAttribute("username", username);

			if (customer.getIfCurrentAccount() == 1) {
				Optional<CurrentAccount> currentopt = currentrep.findById(username);
				CurrentAccount currentaccount = currentopt.get();
				double currentAccountBalance = currentaccount.getBalance();
				double overdraft = currentaccount.getOverDraftAmount();
				double currentAccountAvailableBalance = overdraft + currentAccountBalance;
				model.addAttribute("currentaccount", currentAccountBalance);
				model.addAttribute("currentAccountAvailableBalance", currentAccountAvailableBalance);
				model.addAttribute("overdraftLimit", overdraft);
				
			}

			if (customer.getIfSavingsAccount() == 1) {
				Optional<SavingsAccount> savingsopt = savingsrep.findById(username);
				SavingsAccount savingsaccount = savingsopt.get();
				double savingsAccountBalance = savingsaccount.getBalance();
				model.addAttribute("savingsaccount", savingsAccountBalance);
			}

			if (customer.getIfISAAccount() == 1) {
				Optional<ISAAccount> isaopt = isarep.findById(username);
				ISAAccount isaaccount = isaopt.get();
				double isaAccountBalance = isaaccount.getBalance();
				model.addAttribute("isaaccount", isaAccountBalance);

			}
		}
		return "LoggedInPage";
	}

	@GetMapping("MyProfile")
	public String MyProfile(Model model, HttpSession session) {

		if (session.getAttribute("CustomerSessions") != null) {

			Customer customer = (Customer) session.getAttribute("CustomerSessions");
			Optional<Customer> copt = customerrep.findById(customer.getUsername());
			customer = copt.get();

			model.addAttribute("CustomerModel", customer);
			String username = customer.getUsername();
			String customerName = customer.getFirstName() + " " + customer.getLastName();
			model.addAttribute("customerName", customerName);
			model.addAttribute("username", username);
			String doesCurrentExist = null;
			String doesSavingsExist = null;
			String doesISAExist = null;

			if (customer.getIfCurrentAccount() == 1)
				doesCurrentExist = "Current Account";

			if (customer.getIfSavingsAccount() == 1)
				doesSavingsExist = "Savings Account";
			if (customer.getIfISAAccount() == 1)
				doesISAExist = "ISA Account";

			model.addAttribute("doesCurrentExist", doesCurrentExist);
			model.addAttribute("doesSavingsExist", doesSavingsExist);
			model.addAttribute("doesISAExist", doesISAExist);

		}
		return "myProfile";

	}

	@GetMapping("editProfile")
	public String editProfile(Model model, HttpSession session, @ModelAttribute Customer customer2) {
		if (session.getAttribute("CustomerSessions") != null) {
			Customer customer = (Customer) session.getAttribute("CustomerSessions");

			customer2.setUsername(customer.getUsername());
			customer2.setIfCurrentAccount(customer.getIfCurrentAccount());
			customer2.setIfSavingsAccount(customer.getIfSavingsAccount());
			customer2.setIfISAAccount(customer.getIfISAAccount());

			customerrep.deleteById(customer.getUsername());
			customerrep.save(customer2);
			session.removeAttribute("CustomerSessions");
			session.setAttribute("CustomerSessions", customer2);
			model.addAttribute("Successfuledit", "Profile Successfully Edited");
		}
		return "redirect:MyProfile";

	}

	@GetMapping("RegisterPage")
	public String doLogin() {
		return "Login";
	}

	@GetMapping("Reg")
	public String doRegister() {

		return "Register";
	}
	
	@GetMapping("goToEditMyProfile")
	public String goToEditMyProfile() {
		return "EditYourProfile";

	}

}