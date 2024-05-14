/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.dto;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author DELL
 */
@Data
public class PaymentResponse implements Serializable{
    private String status;
    private String message;
    private String URL;
}
