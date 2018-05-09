package com.example.microservicebase.ws.rest;

import com.example.microservicebase.model.entity.CustomerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public interface CustomerRestContract {

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping("{id}")
    ResponseEntity<?> getOne(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<?> save(@RequestBody CustomerEntity customerEntity);

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Integer id);

}
