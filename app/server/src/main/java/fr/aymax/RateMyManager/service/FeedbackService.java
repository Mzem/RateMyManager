package fr.aymax.RateMyManager.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import fr.aymax.RateMyManager.dao.ManagedByDAO;
import fr.aymax.RateMyManager.dao.FeedbackDAO;
import fr.aymax.RateMyManager.dao.UserDAO;
import fr.aymax.RateMyManager.entity.ManagedBy;
import fr.aymax.RateMyManager.entity.Feedback;
import fr.aymax.RateMyManager.entity.MyUser;

import java.util.List;
import java.util.ArrayList;
import java .util.HashMap;
import java.util.Calendar;

@Service
public class FeedbackService 
{
	@Autowired
	private ManagedByDAO managedbyDAO;
	@Autowired
	private FeedbackDAO feedbackDAO;
	@Autowired
	private UserDAO userDAO;
	
	public Iterable<HashMap<String, Object>> findManagersActuels(String consultant) 
	{
		List<HashMap<String, Object>> infos = new ArrayList<HashMap<String, Object>>();
		List<ManagedBy> managers = new ArrayList<ManagedBy>();
		Calendar calendar = Calendar.getInstance();
		
		//On regarde si on est à la fin du mois (rq Calendar.MONTH commence à 0) et on crée l'objet feedback correspondant au manager de la periode
		if (calendar.get(Calendar.DAY_OF_MONTH) < 28) 
		{
			managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1);
			for (ManagedBy manager : managers) 
				infos.add(createManagerInfo(consultant, manager, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1));
		} 
		else {
			managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
			for (ManagedBy manager : managers) 
				infos.add(createManagerInfo(consultant, manager, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1));
		}
		//On ajoute toujours les managers du mois précédent
		managers = managedbyDAO.findManagersByMonth(consultant, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
		for (ManagedBy manager : managers) 
			infos.add(createManagerInfo(consultant, manager, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
		
		return infos;
	}
	
	private HashMap<String, Object> createManagerInfo(String consultant, ManagedBy manager, int year, int month) {	
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		
		managerInfo.put("id", manager.getManager());
		
		MyUser managerName = userDAO.findByUsername(manager.getManager());
		managerInfo.put("firstName", managerName.getFirstName());
		managerInfo.put("lastName", managerName.getLastName());
		
		String period = String.format("%02d", month)+"/"+year;
		managerInfo.put("month", period);
		managerInfo.put("description", manager.getDescription());
		
		managerInfo.put("feedback", new Feedback());
		
		return managerInfo;
	}	
	
	public Feedback lookup(String consultant, String manager, String month) {
		Feedback f = feedbackDAO.findByConsultantAndManagerAndMonth(consultant, manager, month);
		if (f == null)
			return new Feedback(consultant, manager, month);
		return f;
	}
	
	public void saveFeedback(Feedback feedback) {
		feedbackDAO.save(feedback);
	}
	
	public boolean feedbackExists(Feedback f) {
		return feedbackDAO.findByConsultantAndManagerAndMonth(f.getConsultant(), f.getManager(), f.getMonth()) != null;
	}
	
	public boolean feedbackPossible(Feedback f) {
		if (f.getNote() > 5) 
			return false;
		String[] month = f.getMonth().split("/");
		return managedbyDAO.findManagerByMonth(f.getConsultant(), f.getManager(), Integer.parseInt(month[1]), Integer.parseInt(month[0])) != null;
	}
	
	public Double globalRating(String manager) {
		return feedbackDAO.globalRating(manager);
	}
	
	public Iterable<HashMap<String, Object>> yearRatings(String manager, int year) 
	{
		List<HashMap<String, Object>> yearFeedbacks = new ArrayList<HashMap<String, Object>>();
		List<Object[]> ratings = new ArrayList<Object[]>();
		ratings = feedbackDAO.yearRatings(manager, "%/"+year);
		
		for (Object[] rating : ratings) {
			try {
				HashMap<String, Object> yearFeedback = new HashMap<String, Object>();
				
				yearFeedback.put("month",rating[1].toString());
				yearFeedback.put("avgRating",Double.parseDouble(rating[0].toString()));
				yearFeedback.put("count",((List<Feedback>) monthRatings(manager, rating[1].toString())).size());
				
				yearFeedbacks.add(yearFeedback);
			} catch (NumberFormatException e) {
				System.err.println("Invalid data");
			}
		}
		return yearFeedbacks;
	}
	
	public Iterable<Feedback> monthRatings(String manager, String month) {
		return feedbackDAO.findByManagerAndMonth(manager, month);
	}
	
	public Integer minYear(String manager) {
		return feedbackDAO.minYear(manager);
	}
}
