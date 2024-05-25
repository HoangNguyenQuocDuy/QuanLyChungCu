/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.configs.ConfigVNPay;
import com.qlcc.dto.PaymentResponse;
import com.qlcc.pojo.Invoice;
import com.qlcc.pojo.Payment;
import com.qlcc.pojo.User;
import com.qlcc.services.InvoiceService;
import com.qlcc.services.PaymentService;
import com.qlcc.services.UserService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/payments")
public class ApiPaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    ResponseEntity<?> createPage(HttpServletRequest req) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Noi dung thanh toan";
        String orderType = "order_type";
        String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
        String vnp_IpAddr = ConfigVNPay.getIpAddress(req);
        String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;

        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(req.getParameter("amount")) * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        String callbackUrl = "http://localhost:8080/QuanLyChungCu/api/payments/callback/"
                + req.getParameter("userId") + "/" + req.getParameter("invoicerentId");
        vnp_Params.put("vnp_ReturnUrl", callbackUrl);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;

        PaymentResponse paymentRes = new PaymentResponse();
        paymentRes.setStatus("OK");
        paymentRes.setMessage("Successfully");
        paymentRes.setURL(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(paymentRes);
    }

    @GetMapping("/callback/{userId}/{invoicerentId}")
    public ResponseEntity<?> processPaymentCallback(HttpServletRequest req,
            @PathVariable("invoicerentId") int invoicerentId,
            @PathVariable("userId") int userId) throws Exception {
        if (req.getParameter("vnp_ResponseCode").equals("00")) {
            Payment payment = new Payment();
            payment.setAmount(Long.parseLong(req.getParameter("vnp_Amount")));
            payment.setBankCode(req.getParameter("vnp_BankCode"));
            payment.setBankTranNo(req.getParameter("vnp_BankTranNo"));
            payment.setTransactionNo(req.getParameter("vnp_TransactionNo"));
            payment.setCreatedAt(new Date());

            Invoice invoice = invoiceService.getInvoiceById(invoicerentId);
            invoice.setStatus("Paid");
            invoiceService.addOrUpdate(invoice);

            User user = userService.getUserById(userId);

            payment.setInvoice(invoice);
            payment.setUserId(user);

            paymentService.addPayment(payment);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPayments(@RequestParam Map<String, String> params) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments(params));
    }
}
