/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.ParkingRight;
import com.qlcc.pojo.Relative;
import com.qlcc.services.ParkingRightService;
import com.qlcc.services.RelativeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/parkings")
public class ApiParkingRightController {

    @Autowired
    private ParkingRightService parkingRightService;

    @Autowired
    private RelativeService relativeService;

    @GetMapping("/")
    public ResponseEntity<?> getParkingRights(@RequestParam int userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    parkingRightService.getParkings(userId));
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @PostMapping(path = "/", consumes = {
        MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<?> createParkingRight(@RequestBody Map<String, String> params) {
        try {
            Relative relative = relativeService.getRelativeById(Integer.parseInt(params.get("relativeId")));

            if (relative == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Relative not found with ID: " + params.get("relativeId"));
            }

            ParkingRight pr = new ParkingRight();
            pr.setRelativeId(relative);
            pr.setLicensePlates(params.get("licensePlates"));
            pr.setTypeOfVehicle(params.get("typeOfVehicle"));

            parkingRightService.addOrUpdate(pr);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Create parking successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @PutMapping(path = "/{pId}", consumes = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> updateParkingRight(@RequestBody Map<String, String> params,
            @PathVariable("pId") int pId) {
        try {
            ParkingRight pr = parkingRightService.getParkingRightById(pId);

            if (pr == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Parking right not found with ID: " + pId);
            }

            pr.setStatus(params.get("status"));

            parkingRightService.addOrUpdate(pr);

            return ResponseEntity.status(HttpStatus.OK).body(
                    "Update parking successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

}
