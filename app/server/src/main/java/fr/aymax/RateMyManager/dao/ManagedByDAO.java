package fr.aymax.RateMyManager.dao;

import java.util.List;

import fr.aymax.RateMyManager.entity.ManagedBy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ManagedByDAO extends JpaRepository<ManagedBy, Integer>
{
	@Query(value = "SELECT * FROM ratem_managed_by WHERE mb_consultant = :consultant AND ( year(mb_begin_date) <= :annee AND year(mb_end_date) >= :annee ) AND ( month(mb_begin_date) <= :mois AND month(mb_end_date) >= :mois ) AND datediff(mb_end_date, mb_begin_date) > 10", nativeQuery = true)
	List<ManagedBy> findManagersByMonth(@Param("consultant") String consultant, @Param("annee") int annee, @Param("mois") int mois);
	
	@Query(value = "SELECT * FROM ratem_managed_by WHERE mb_consultant = :consultant AND mb_manager = :manager AND ( year(mb_begin_date) <= :annee AND year(mb_end_date) >= :annee ) AND ( month(mb_begin_date) <= :mois AND month(mb_end_date) >= :mois ) AND datediff(mb_end_date, mb_begin_date) > 10", nativeQuery = true)
	ManagedBy findManagerByMonth(@Param("consultant") String consultant, @Param("manager") String manager, @Param("annee") int annee, @Param("mois") int mois);
} 
