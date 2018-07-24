package fr.aymax.RateMyManager.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import fr.aymax.RateMyManager.dao.ManagedByDAO;
import fr.aymax.RateMyManager.dao.FeedbackDAO;
import fr.aymax.RateMyManager.entity.ManagedBy;
import fr.aymax.RateMyManager.entity.Feedback;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class FeedbackService 
{
	@Autowired
	private ManagedByDAO managedbyDAO;
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	public Iterable<Feedback> findManagersActuels(String consultant) 
	{
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		List<ManagedBy> managers = new ArrayList<ManagedBy>();
		Calendar calendar = Calendar.getInstance();
		
		//On regarde si on est à la fin du mois (rq Calendar.MONTH commence à 0) et on crée l'objet feedback correspondant au manager de la periode
		if (calendar.get(Calendar.DAY_OF_MONTH) < 28) 
		{
			managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1);
			
			for (ManagedBy manager : managers)
				feedbacks.add(new Feedback(consultant, manager.getManager(), (calendar.get(Calendar.MONTH)-1)+"/"+calendar.get(Calendar.YEAR)));
		} else {
			managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
			for (ManagedBy manager : managers)
				feedbacks.add(new Feedback(consultant, manager.getManager(), (calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)));
		}
			
		//On ajoute toujours les managers du mois précédent
		managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
		for (ManagedBy manager : managers)
			feedbacks.add(new Feedback(consultant, manager.getManager(), calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)));
		
		return feedbacks;
	}
	
	public Feedback lookup(String consultant, String manager, String month) {
		return feedbackDAO.findByConsultantAndManagerAndMonth(consultant, manager, month);
	}
	
	public void saveFeedback(Feedback feedback) {
		feedbackDAO.save(feedback);
	}
	
	public boolean feedbackExists(Feedback f) {
		return feedbackDAO.findByConsultantAndManagerAndMonth(f.getConsultant(), f.getManager(), f.getMonth()) != null;
	}
	
	public boolean feedbackPossible(Feedback f) {
		String[] month = f.getMonth().split("/");
		return managedbyDAO.findManagerByMonth(f.getConsultant(), f.getManager(), Integer.parseInt(month[1]), Integer.parseInt(month[0])) != null;
	}
	
	public Double globalRating(String manager) {
		return feedbackDAO.globalRating(manager);
	}
	
	public Iterable<Feedback> yearRatings(String manager) {
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		List<Object[]> ratings = new ArrayList<Object[]>();
		ratings = feedbackDAO.yearRatings(manager);
		for (Object[] rating : ratings) {
			try {
				feedbacks.add(new Feedback(null, manager, rating[1].toString(), Double.parseDouble(rating[0].toString())));
			} catch (NumberFormatException e) {
				System.err.println("Invalid data");
			}
		}
		return feedbacks;
	}
	
	public Iterable<Feedback> monthRatings(String manager, String month) {
		return feedbackDAO.findByManagerAndMonth(manager, month);
	}
}
