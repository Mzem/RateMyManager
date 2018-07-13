package fr.aymax.managerService.controller;

import fr.aymax.managerService.entity.Manager;
import fr.aymax.managerService.dao.ManagerDAO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URI;
import java.util.List;

@RestController
public class ManagerController 
{
	@Autowired
    private ManagerDAO managerDAO;
    
    @CrossOrigin(origins = "http://localhost:8100")
	@RequestMapping(value="/Managers", method=RequestMethod.GET)
    public Iterable<Manager> listManagers() 
    {
        return managerDAO.findAll();
    }
    
    @CrossOrigin(origins = "http://localhost:8100")
    @RequestMapping(value = "/Managers/{username}", method = RequestMethod.GET)
	public Manager showManager(@PathVariable String username) 
	{
		return managerDAO.findByUsername(username);
	}
	
	@CrossOrigin(origins = "http://localhost:8100")
	@PostMapping(value = "/Managers")
    public ResponseEntity<Void> addManager(@RequestBody Manager manager) 
    {
		//RequestBody : user is sending a JSON manager which has to be converted to a java object
		Manager addedManager = managerDAO.save(manager);
		
		//Return standard HTTP response code
        if (addedManager == null)
			//Return 204 no content
			return ResponseEntity.noContent().build();
		//Return code 201 created + URI : URI instanciated from recieved request URL
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(addedManager.getUsername())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    
    @CrossOrigin(origins = "http://localhost:8100")
	@PutMapping (value = "/Managers")
	public void updateManager(@RequestBody Manager manager) {
		managerDAO.save(manager);
	}

	@CrossOrigin(origins = "http://localhost:8100")
	@DeleteMapping(value = "/Managers/{username}")
	public void deleteManager(@PathVariable String username) {
		Manager m = managerDAO.findByUsername(username);
		if (m != null)
			managerDAO.delete(m);
   }
}
