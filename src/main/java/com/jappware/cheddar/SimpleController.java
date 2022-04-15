package com.jappware.cheddar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class SimpleController {

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/comments")
    public ResponseEntity<String> getComments() {
        System.out.println("commented");
        return new ResponseEntity<>("comments", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/admin")
    public ResponseEntity<String> deleteComments() {
        System.out.println("deleted");
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}