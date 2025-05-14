package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.Manager;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.ManagerRepository;
import com.hexa.assetmanagement.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private UserRepository userRepository;

    Manager m1, m2, m3, m4;
    User u1;

    @BeforeEach
    public void init() {
        m1 = new Manager(1, "Sam", "sam@gmail.com", "9876543210", "Chennai", new User(1, "sam", "1234", "MANAGER"));
        m2 = new Manager(2, "Lara", "lara@gmail.com", "9876543211", "Delhi", new User(2, "lara", "1234", "MANAGER"));
        m3 = new Manager(3, "Raj", "raj@gmail.com", "12345", "Mumbai", new User(3, "raj", "1234", "MANAGER")); // invalid contact
        m4 = new Manager(4, "Tom", "tom@gmail.com", "9876543212", "Bangalore");
        u1 = new User(4, "tom", "1234", "MANAGER");
    }

    @Test
    public void addTest() throws UsernameInvalidException {
        // Use case 1: Valid manager
        when(userRepository.findByUsername("sam")).thenReturn(m1.getUser());
        when(managerRepository.save(m1)).thenReturn(m1);
        try {
            assertEquals(m1, managerService.add(m1, "sam"));
        } catch (InvalidContactException e) {
        }

        // Use case 2: Invalid contact number
        try {
            managerService.add(m3, "raj");
        } catch (InvalidContactException e) {
            assertEquals("Invalid contact number...", e.getMessage());
        }

        // Use case 3: Wrong expected result
        when(userRepository.findByUsername("lara")).thenReturn(m2.getUser());
        when(managerRepository.save(m2)).thenReturn(m2);
        try {
            assertNotEquals(m1, managerService.add(m2, "lara"));
        } catch (InvalidContactException e) {
        }

        // Use case 4: Check findByUsername call
        when(userRepository.findByUsername("tom")).thenReturn(u1);
        when(managerRepository.save(m4)).thenReturn(m4); 
        try {
            assertEquals(m4, managerService.add(m4, "tom"));
        } catch (InvalidContactException e) { }
    }

    @Test
    public void getByIdTest() {
        // Use case 1: Valid ID
        when(managerRepository.findById(2)).thenReturn(Optional.of(m2));
        try {
            assertEquals(m2, managerService.getById(2));
        } catch (InvalidIdException e) {
        }

        // Use case 2: Invalid ID
        try {
            managerService.getById(10);
        } catch (InvalidIdException e) {
            assertEquals("Manager id is invalid.....", e.getMessage());
        }

        // Use case 3: Wrong expected value
        try {
            assertNotEquals(m3, managerService.getById(2));
        } catch (InvalidIdException e) {
        }

        verify(managerRepository, times(2)).findById(2);
        verify(managerRepository, times(1)).findById(10);
    }
    @Test
    public void updateTest() {
        Manager m33 = new Manager();
        m33.setContact("9876549999");
        m33.setAddress("Pune");

        // Use case 1: Valid update
        when(managerRepository.findById(3)).thenReturn(Optional.of(m3));
        when(managerRepository.save(m3)).thenReturn(m3);
        try {
            assertEquals(m3, managerService.update(m33, 3));
        } catch (InvalidIdException | InvalidContactException e) {
        }

        // Use case 2: Invalid ID
        try {
            managerService.update(m33, 10);
        } catch (InvalidIdException | InvalidContactException e) {
            assertEquals("Manager id is invalid.....", e.getMessage());
        }

        // Use case 3: Invalid contact
        m33.setContact("123");
        try {
            managerService.update(m33, 3);
        } catch (InvalidIdException | InvalidContactException e) {
            assertEquals("Invalid contact number...", e.getMessage());
        }

        // Use case 4: Update name and email
        m33.setContact(null);
        m33.setAddress(null);
        m33.setEmail("raj123@gmail.com");
        m33.setName("Raj Kumar");
        try {
            assertEquals(m3, managerService.update(m33, 3));
        } catch (InvalidIdException | InvalidContactException e) {
        }
        verify(managerRepository, times(2)).save(m3);
    }
}
