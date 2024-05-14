/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Invoice;
import com.qlcc.services.InvoiceService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class InvoiceController {

    @Autowired
    private Environment env;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String createView(Model model, @RequestParam Map<String, String> paramsRequest) {

        try {
            int totalOrders = invoiceService.getTotalInvoices();
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
            int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

            model.addAttribute("totalPages", totalPages);
            model.addAttribute("invoices", invoiceService.getInvoices(paramsRequest));
        } catch (NumberFormatException ex) {
            model.addAttribute("errMsg", ex.toString());
            System.err.println("Err: " + ex.getMessage());
        }
        return "invoice_list";
    }

    @GetMapping("/invoices/")
    public String addInvoiceView(Model model) {
        model.addAttribute("invoice", new Invoice());

        return "invoice";
    }
    
    @GetMapping("/invoices/{invoiceId}")
    public String updateRoomView(Model model, @PathVariable("invoiceId") int invoiceId) {
        model.addAttribute("invoice", invoiceService.getInvoiceById(invoiceId));

        return "invoice";
    }

    @PostMapping("/invoices/")
    public String addInvoiceProcess(
            Model model,
            @ModelAttribute(value = "invoice") @Valid Invoice invoice,
            BindingResult rs
    ) {
        if (!rs.hasErrors()) {

            try {
                invoiceService.addOrUpdate(invoice);

                return "redirect:/invoices";
            } catch (Exception ex) {
                model.addAttribute("errMsg", ex.toString());
                System.err.println("Err: " + ex.getMessage());
            }
        }

        return "invoice";
    }
}
