package fr.aymax.RateMyManager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.aymax.RateMyManager.entity.MyUser;

@Repository
public interface UserDAO extends JpaRepository<MyUser, String>
{
	MyUser findByUsername(String username);
} 
