package soloProject;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

	@RequestMapping("/Logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "Login";
	}
	
	
	
	
}
