package com.hospital.Patient;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.Patient.Login.User;
import com.hospital.Patient.service.UserService;

//Main Controller Class
@Controller
public class PatientsController {
	
	@Autowired
	UserService userService;
	
	private PatientsRepository patientsRepository;
	
	//Search API
	@GetMapping("/Patients-json-search")
    @ResponseBody
    public List<Patient> SearchAPI(@RequestParam(name="name", required=true, defaultValue="") String name,@RequestParam(name="date", required=true, defaultValue="") String date) 
    {       
		List<Patient> PatientsQueried=patientsRepository.findBynameAndDate(name,date);
		PatientsQueried.addAll(patientsRepository.findBynameAndDateNot(name,date));
		return PatientsQueried;
    }
	
	//Add API
	@GetMapping("/Patients-json-add")
    @ResponseBody
    public List<Patient> AddAPI(@RequestParam(name="name", required=true, defaultValue="") String name,@RequestParam(name="age", required=true, defaultValue="") String age,@RequestParam(name="date", required=true, defaultValue="") String date,@RequestParam(name="temperature", required=true, defaultValue="") String temperature,@RequestParam(name="symptoms", required=true, defaultValue="") String symptoms) 
    {       
		Patient patient=new Patient(name,Integer.parseInt(age),date,Integer.parseInt(temperature),symptoms);
		patientsRepository.save(patient);
		return patientsRepository.findAll();

    }
	
	//Creating object of interface for DB
	@Autowired
	public PatientsController(PatientsRepository patientsRepository) {
		// TODO Auto-generated constructor stub
		this.patientsRepository=patientsRepository;
	}
	
	//Home Page GET mapping
	@GetMapping("/")
	@ResponseBody
	public ModelAndView HomePage(Model model, Principal principal) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loggedin",principal!=null);
		mav.addObject("Patient", new Patient());
		mav.addObject("Query", new Query());
        mav.setViewName("home.html");
        return mav;
	}
	
	//Home Page Add to Patients POST mapping
	@PostMapping("/Patients")
	public ModelAndView patientSubmit(@ModelAttribute("Patients") Patient PatientNew) {
		if(PatientNew.name.compareTo("null")==0||PatientNew.date.compareTo("null")==0||PatientNew.symptoms.compareTo("null")==0) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", "\"null\" is not valid - Hospital Patient Project");
			mav.addObject("error", "\"null\" is not a valid name, date or symptom. Please correct it and try again");
			mav.setViewName("error.html");
			return mav;
		}
		//minimum human body temp (while alive) is 50F
		if(PatientNew.temperature<50) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", "Body Temp too low- Hospital Patient Project");
			mav.addObject("error", "Body temperature below 50F is impossible for living humans. Please check your temperature entry again");
			mav.setViewName("error.html");
			return mav;
		}
		if(PatientNew.age<0) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", "Invalid Age- Hospital Patient Project");
			mav.addObject("error", "Humans, or any object for that matter, can't have a negative age. Please recheck your age entry");
			mav.setViewName("error.html");
			return mav;
		}
		if(PatientNew.date.charAt(2)!='/'||PatientNew.date.charAt(5)!='/'||PatientNew.date.length()!=10) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", "Invalid Date- Hospital Patient Project");
			mav.addObject("error", "Invalid date. Please recheck and reenter date");
			mav.setViewName("error.html");
			return mav;
		}
		else {
			patientsRepository.save(PatientNew);
			List<Patient> Patients= patientsRepository.findAll();
			return new ModelAndView("patients1","Patients",Patients);
		}
	}
	
	@RequestMapping("/PatientsDelete")
    public String PatientDelete(@ModelAttribute(value="Patients1") Patient PatientNew) {
		patientsRepository.delete(PatientNew);
		return "redirect:Patients";   
    }
	
	
	//Home Page search for existing POST mapping
	@PostMapping("/searchExisting")
	public ModelAndView searchExistingPatient(@ModelAttribute("Query") Query QueryNew,Principal principal) {
		if(QueryNew.name=="null"||QueryNew.date=="null") {
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", " \"null\" is not valid - Hospital Patient Project");
			mav.addObject("error", "\"null\" is not a valid name,date or symptom. Please try again");
			mav.addObject("loggedin",principal!=null);
			mav.setViewName("error.html");
			return mav;
		}
		List<Patient> PatientsQueried=patientsRepository.findBynameAndDate(QueryNew.name, QueryNew.date);
		PatientsQueried.addAll(patientsRepository.findBynameAndDateNot(QueryNew.name, QueryNew.date));
		if (PatientsQueried.size()==0){
			ModelAndView mav = new ModelAndView();
			mav.addObject("title", "No results found - Hospital Patient Project");
			mav.addObject("error", "No results found for your Query: Name: "+QueryNew.name+" and Date: "+QueryNew.date);
			mav.addObject("loggedin",principal!=null);
			mav.setViewName("error.html");
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("Patients",PatientsQueried);
			mav.setViewName("searchExisting1.html");
			mav.addObject("loggedin",principal!=null);
			return mav;
		}
	}
	
	//List of All Patients GET mapping
	@GetMapping("/Patients")
	@ResponseBody
	public ModelAndView displayPatients(Model model,Principal principal) {
		List<Patient> Patients= patientsRepository.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("Patients",Patients);
		mav.addObject("loggedin",principal!=null);
		mav.setViewName("patients1");
		return mav;
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loggedin",principal!=null);
		modelAndView.setViewName("login1"); // resources/template/login.html
		return modelAndView;
	}
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loggedin",principal!=null);
		User user = new User();
		modelAndView.addObject("user", user); 
		modelAndView.setViewName("register"); // resources/template/register.html
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@Valid User user,BindingResult bindingResult,ModelMap modelMap,Principal principal) {
		ModelAndView mav = new ModelAndView();
		//Check for validations
		mav.addObject("loggedin",principal!=null);
		if (bindingResult.hasErrors()){
			mav.addObject("message", "Please correct errors in form!!");
			modelMap.addAttribute("bindingResult", bindingResult);
		}
		else if(userService.isUserAlreadyPresent(user)){
			//if user already exists display that
			mav.addObject("message", "User already exists!!");
		}
		else {
			//if not than save user
			userService.saveUser(user);
			mav.addObject("message", "User has been registered successfully!!");
		}
		mav.addObject("user", new User());
		mav.setViewName("register");
		return mav;
	}
}
