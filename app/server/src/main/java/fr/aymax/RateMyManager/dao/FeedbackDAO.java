package fr.aymax.RateMyManager.dao;

import java.util.List;

import fr.aymax.RateMyManager.entity.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Integer>
{
	Feedback findByConsultantAndManagerAndMonth(String consultant, String manager, String month);
	
	Iterable<Feedback> findByManagerAndMonth(String manager, String month);
	
	@Query(value = "SELECT AVG(f_note) FROM ratem_feedback WHERE f_manager = :manager ", nativeQuery = true)
	Double globalRating(@Param("manager") String manager);
	
	@Query(value = "SELECT AVG(note), month FROM Feedback WHERE manager = :manager GROUP BY month")
	List<Object[]> yearRatings(@Param("manager") String manager);
} 
