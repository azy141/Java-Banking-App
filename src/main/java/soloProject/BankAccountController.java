package soloProject;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class BankAccountController {

	@Autowired
	CustomerAccountRepo customerrep;

	@Autowired
	CurrentAccountRepo currentrep;
	@Autowired
	ISAAccountRepo isarep;
	@Autowired
	SavingsAccountRepo savingsrep;

	@GetMapping("CurrentAccountDepositFunds")
	public String CurrentAccountDepositFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double newBalance = currentAccountBalance + amount;
			currentaccount.setBalance(newBalance);
			currentrep.save(currentaccount);
			model.addAttribute("FundsDeposited", amount + " Has been deposited successfully!");
		}
		return "DepositFundsCurrent";
	}

	@GetMapping("SavingsAccountDepositFunds")
	public String SavingsAccountDepositFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<SavingsAccount> savingsopt = savingsrep.findById(username);
			SavingsAccount savingsaccount = savingsopt.get();

			double savingsAccountBalance = savingsaccount.getBalance();
			double newBalance = savingsAccountBalance + amount;
			savingsaccount.setBalance(newBalance);
			savingsrep.save(savingsaccount);
			model.addAttribute("FundsDeposited", amount + " Has been deposited successfully!");
		}
		return "DepositFundsSavings";
	}

	@GetMapping("ISAAccountDepositFunds")
	public String ISAAccountDepositFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<ISAAccount> isaopt = isarep.findById(username);
			ISAAccount isaaccount = isaopt.get();

			double isaAccountBalance = isaaccount.getBalance();
			double newBalance = isaAccountBalance + amount;
			isaaccount.setBalance(newBalance);
			isarep.save(isaaccount);
			model.addAttribute("FundsDeposited", amount + " Has been deposited successfully!");
		}
		return "DepositFundsISA";
	}

	@GetMapping("CurrentAccountWithdrawFunds")
	public String CurrentAccountWithdrawFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;

			if (amount <= currentAccountAvailableBalance) {
				double newBalance = currentAccountBalance - amount;
				currentaccount.withdraw(amount);
				currentaccount.applyOverDraftChargesInterest();
				currentaccount.setBalance(newBalance);
				currentrep.save(currentaccount);
				model.addAttribute("withdrawingfunds", "Withdrawing Funds...");
			} else if (amount > currentAccountAvailableBalance) {
				model.addAttribute("insufficientfunds", "Insufficient Funds");
			}
		} else {
			model.addAttribute("error", "Error");
		}
		return "WithdrawFundsCurrent";
	}

	@GetMapping("SavingsAccountWithdrawFunds")
	public String SavingsAccountWithdrawFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<SavingsAccount> savingopt = savingsrep.findById(username);
			SavingsAccount savingsaccount = savingopt.get();
			double savingsAccountBalance = savingsaccount.getBalance();

			if (amount <= savingsAccountBalance) {
				double newBalance = savingsAccountBalance - amount;
				savingsaccount.setBalance(newBalance);
				savingsrep.save(savingsaccount);
				model.addAttribute("withdrawingfunds", "Withdrawing Funds...");
			} else if (amount > savingsAccountBalance) {
				model.addAttribute("insufficientfunds", "Insufficient Funds");
			}
		} else {
			model.addAttribute("error", "Error");
		}
		return "WithdrawFundsSavings";
	}

	@GetMapping("ISAAccountWithdrawFunds")
	public String ISAAccountWithdrawFunds(Model model, HttpSession session, @RequestParam("amount") double amount) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<ISAAccount> isaopt = isarep.findById(username);
			ISAAccount isaaccount = isaopt.get();
			double isaAccountBalance = isaaccount.getBalance();

			if (amount <= isaAccountBalance) {
				double newBalance = isaAccountBalance - amount;
				isaaccount.setBalance(newBalance);
				isarep.save(isaaccount);
				model.addAttribute("withdrawingfunds", "Withdrawing Funds...");
			} else if (amount > isaAccountBalance) {
				model.addAttribute("insufficientfunds", "Insufficient Funds");
			}
		} else {
			model.addAttribute("error", "Error");
		}
		return "WithdrawFundsISA";
	}

	@GetMapping("IncreaseOverdraft")
	public String IncreaseOverdraft(Model model, HttpSession session, @RequestParam("amount") double newoverdraft) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentOverdraft = currentaccount.getOverDraftAmount();

			if (currentOverdraft < newoverdraft && newoverdraft <= 2500) {
				currentaccount.setOverDraftAmount(newoverdraft);
				currentrep.save(currentaccount);
				model.addAttribute("newoverdraftset", "Overdraft has been increased to: " + newoverdraft);
			} else if (currentOverdraft < newoverdraft && newoverdraft > 2500) {
				model.addAttribute("cannotincreaseover2500", "Maximum Overdraft Allowed is £2500!");
			} else if (currentOverdraft > newoverdraft) {
				model.addAttribute("cannotreduce", "Please visit the reduce Overdraft page to reduce your overdraft!");
			}
		}

		return "Increaseoverdraftamount";

	}

	@GetMapping("ReduceOverdraft")
	public String ReduceOverdraft(Model model, HttpSession session, @RequestParam("amount") double newoverdraft) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentOverdraft = currentaccount.getOverDraftAmount();
//			double balance = 0;
//			if (currentaccount.getBalance() > 0) {
//				balance = currentaccount.getBalance();
//			} else if (currentaccount.getBalance() < 0) {
//				balance = Math.abs(currentaccount.getBalance());
//			}
			if (currentOverdraft > newoverdraft && newoverdraft <= 2500 ) {
				if (newoverdraft > currentaccount.getBalance() && currentaccount.getBalance()<0 ){
					model.addAttribute("cannotincreaseoverdraft", "Your overdraft cannot be lower than your balance!");}
				else if (newoverdraft > currentaccount.getBalance()){					
				currentaccount.setOverDraftAmount(newoverdraft);
				currentrep.save(currentaccount);
				model.addAttribute("newoverdraftset", "Overdraft has been reduced to: " + newoverdraft);
			} }
			else if (currentOverdraft < newoverdraft) {
				model.addAttribute("cannotincreaseoverdraft",
						"Please visit the Increase Overdraft page to increase your overdraft!");
			}
		}

		return "Reduceoverdraftamount";
	}

	@GetMapping("goToSendMoneyPageCurrent")
	public String goToSendMoneyPageCurrent(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
		
		}	return "SendMoney";

	}
	
	
	
	
	@GetMapping("CurrentSendMoney")
	public String CurrentSendMoney(Model model, HttpSession session, @RequestParam("amount") double amount,
			@RequestParam("user") String otherusername) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
			
			
			if (amount <= currentAccountAvailableBalance) {
				if (currentrep.existsById(otherusername)) {
					Optional<CurrentAccount> currentotheropt = currentrep.findById(otherusername);
					CurrentAccount currentaccountother = currentotheropt.get();
					currentaccountother = currentotheropt.get();
					double currentAccountotherBalance = currentaccountother.getBalance();
					double newBalance = currentAccountBalance - amount;
					double newBalanceother = currentAccountotherBalance + amount;

					
					currentaccount.setBalance(newBalance);
					currentaccountother.setBalance(newBalanceother);

					currentrep.save(currentaccount);
					currentrep.save(currentaccountother);
					model.addAttribute("fundshavebeensent", "Funds Have Been Sent");
				} else if (currentrep.existsById(otherusername) != true) {
					model.addAttribute("accountNotFound", "Account Not Found");
				}
			}

			else {
				model.addAttribute("insufficientfunds", "Insufficient Funds");
			}
		}
		return "SendMoney";
	}

	@GetMapping("goToIncreaseOverdraft")
	public String goToIncreaseOverdraft(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
			model.addAttribute("overdraftLimit", overdraft);
		}return "Increaseoverdraftamount";

	}

	@GetMapping("goToReduceOverdraft")
	public String goToReduceOverdraft(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
			model.addAttribute("overdraftLimit", overdraft);
				
		}return "Reduceoverdraftamount";

	}


	@GetMapping("goToDepositPageCurrent")
	public String goToDepositPageCurrent(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
		}return "DepositFundsCurrent";
	}

	@GetMapping("goToWithdrawPageCurrent")
	public String goToWithdrawPage(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<CurrentAccount> currentopt = currentrep.findById(username);
			CurrentAccount currentaccount = currentopt.get();
			double currentAccountBalance = currentaccount.getBalance();
			double overdraft = currentaccount.getOverDraftAmount();
			double currentAccountAvailableBalance = overdraft + currentAccountBalance;
			
			model.addAttribute("currentAccountBalance",currentAccountBalance);
			model.addAttribute("currentAccountAvailableBalance",currentAccountAvailableBalance);
		
		}return "WithdrawFundsCurrent";
	}

	@GetMapping("goToDepositPageSavings")
	public String goToDepositPageSavings(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<SavingsAccount> savingsopt = savingsrep.findById(username);
			SavingsAccount savingsaccount = savingsopt.get();
			double savingsAccountBalance = savingsaccount.getBalance();
			
			model.addAttribute("savingsAccountBalance",savingsAccountBalance);
		
		
		}return "DepositFundsSavings";
	}

	@GetMapping("goToWithdrawPageSavings")
	public String goToWithdrawPageSavings(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<SavingsAccount> savingsopt = savingsrep.findById(username);
			SavingsAccount savingsaccount = savingsopt.get();
			double savingsAccountBalance = savingsaccount.getBalance();
			
			model.addAttribute("savingsAccountBalance",savingsAccountBalance);
		
		}return "WithdrawFundsSavings";
	}

	@GetMapping("goToDepositPageISA")
	public String goToDepositPageISA(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<ISAAccount> isaopt = isarep.findById(username);
			ISAAccount isaaccount = isaopt.get();
			double isaAccountBalance = isaaccount.getBalance();
			
			model.addAttribute("isaAccountBalance",isaAccountBalance);
		
		}return "DepositFundsISA";
	}

	@GetMapping("goToWithdrawPageISA")
	public String goToWithdrawPageISA(Model model,HttpSession session) {
		Customer customer = null;

		if (session.getAttribute("CustomerSessions") != null) {
			customer = (Customer) session.getAttribute("CustomerSessions");
			String username = customer.getUsername();
			Optional<ISAAccount> isaopt = isarep.findById(username);
			ISAAccount isaaccount = isaopt.get();
			double isaAccountBalance = isaaccount.getBalance();
			
			model.addAttribute("isaAccountBalance",isaAccountBalance);
			
		}return "WithdrawFundsISA";
	}
	
	
	
	
	
	
	
}
