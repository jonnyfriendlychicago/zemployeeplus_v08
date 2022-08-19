package com.jonfriend.zemployeeplus_v08.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonfriend.zemployeeplus_v08.models.UserMdl;
//import com.jonfriend.playdatenow_v03.models.UserMdl;
//import com.jonfriend.zemployeeplus_v08.models.UserMdl;
import com.jonfriend.zemployeeplus_v08.models.WorkerMdl;
import com.jonfriend.zemployeeplus_v08.services.UserSrv;
import com.jonfriend.zemployeeplus_v08.services.WorkerSrv;

import java.security.Principal;
import java.util.List;

//import java.security.Principal;
//import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/worker")
public class WorkerCtl {

    @Autowired
    WorkerSrv service;

    @Autowired
    UserSrv userSrv;
    
    @PostMapping("/add")
    public ResponseEntity<WorkerMdl> add(
        @Valid @RequestBody WorkerMdl workerMdl,
        BindingResult result, 
        Principal principal
        ) {

            if ( !result.hasErrors() ) {
            	
            	System.out.println("Path: /worker/add");

            	String authUserEmail = principal.getName(); 
            	System.out.println("authUserEmail: " + authUserEmail); 
            	
            	UserMdl authUserObj = userSrv.findByEmail(authUserEmail);
//            	System.out.println("authUserObj: " + authUserObj); // results of this print stmt look like hell
            	
            	workerMdl.setUserMdl(authUserObj); 
            	

            	return ResponseEntity.status(201).body(this.service.create(workerMdl));
            }
            
            return ResponseEntity.status(422).body(null);     
            
    }

    @PostMapping("/update")
    public ResponseEntity<WorkerMdl> update(
        @Valid @RequestBody WorkerMdl workerMdl,
        BindingResult result) {

            if ( !result.hasErrors() ) {
                return ResponseEntity.status(200).body(this.service.update(workerMdl));
            }
            return ResponseEntity.status(422).body(null);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<WorkerMdl> update(
        @PathVariable Long id
        ) {
            this.service.delete(id);
            return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerMdl> view(
        @PathVariable Long id
        ) {
            return ResponseEntity.status(200).body(this.service.retrieve(id));
    }

//    @GetMapping("/my")
//    public ResponseEntity<List<WorkerMdl>> myTopics(
//        Principal principal
//        ) {
//
//            UserMdl user = userSrv.findByEmail(principal.getName());
//
//            return ResponseEntity.status(200).body(user.getTopics());
//    }
    

  @GetMapping("/all")
  public ResponseEntity<List<WorkerMdl>> getAll(
      ) {
	  return ResponseEntity.status(200).body(this.service.all());
  }
}