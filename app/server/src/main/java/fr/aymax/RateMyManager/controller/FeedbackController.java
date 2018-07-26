package fr.aymax.RateMyManager.controller;

import fr.aymax.RateMyManager.entity.ManagedBy;
import fr.aymax.RateMyManager.entity.Feedback;
import fr.aymax.RateMyManager.service.FeedbackService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.util.List;
import java .util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("feedback")
public class FeedbackController 
{
	private final FeedbackService feedbackService;
	
	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
    
    //Retourne des objets feedback qui ne sont pas enregistrés dans la bd, juste pour l'affichage d'un ajout potentiel de feedback à une période précise
    @PreAuthorize("hasAnyAuthority('ROLE_CONSULTANT', 'ROLE_ADMIN')")
	@GetMapping(value="/managers")
    public Iterable<HashMap<String, Object>> listManagers(@RequestParam("consultant") String consultant) 
    {
		return this.feedbackService.findManagersActuels(consultant);
    }
	
	@PreAuthorize("hasAnyAuthority('ROLE_CONSULTANT', 'ROLE_ADMIN')")
	@GetMapping(value = "/getOne")
	public Feedback getOneFeedback(@RequestParam("consultant") String consultant, @RequestParam("manager") String manager, @RequestParam("month") String month)
	{
		return feedbackService.lookup(consultant, manager, month);
	}
	
	//Crée le feedback dans la bd qui a été préalablement vérifié avec le controller /managers
	@PreAuthorize("hasAnyAuthority('ROLE_CONSULTANT', 'ROLE_ADMIN')")
    @PostMapping(value = "/add")
	public String addFeedback(@RequestBody Feedback feedback) 
	{
		//Verif que le feedback n'existe pas puis que les données du feedback sont cohérentes
		if (!feedbackService.feedbackExists(feedback)) {
			if (feedbackService.feedbackPossible(feedback)) {
				feedbackService.saveFeedback(feedback);
				return "OK";
			}
			return "BAD_FEEDBACK";
		} return "EXISTS";
	}
	
    //Modife le feedback de la bd si deja créé
    @PreAuthorize("hasAnyAuthority('ROLE_CONSULTANT', 'ROLE_ADMIN')")
    @PutMapping(value = "/edit")
	public String editFeedback(@RequestBody Feedback feedback) 
	{
		//Verif que le feedback existe
		if (feedbackService.feedbackExists(feedback)) {
			//Grosse manip à cause de l'id qu'on ne fournit pas
			Feedback f = feedbackService.lookup(feedback.getConsultant(), feedback.getManager(), feedback.getMonth());
			f.setNote(feedback.getNote());
			f.setComment(feedback.getComment());
			feedbackService.saveFeedback(f);
			return "OK";
		} return "NOT_EXISTING";
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMIN')")
	@GetMapping(value="/globalRating")
	public Double globalRating(@RequestParam("manager") String manager) {
		return this.feedbackService.globalRating(manager);
	}
	
	//Retourne des objets feedback mais construits juste pour l'affichage
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMIN')")
	@GetMapping(value="/yearRatings")
	public Iterable<HashMap<String, Object>> yearRatings(@RequestParam("manager") String manager, @RequestParam("year") int year) {
		return this.feedbackService.yearRatings(manager, year);
	}
	
	//Retourne un vrai objet feedback trouvé de la bd
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMIN')")
	@GetMapping(value="/monthRatings")
	public Iterable<Feedback> monthRatings(@RequestParam("manager") String manager, @RequestParam("month") String month) {
		return this.feedbackService.monthRatings(manager, month);
	}
}
