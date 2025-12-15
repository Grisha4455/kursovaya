package com.autodealer.controller;

import com.autodealer.entity.Customer;
import com.autodealer.entity.TestDrive;
import com.autodealer.entity.ServiceAppointment;
import com.autodealer.entity.CreditOffer;
import com.autodealer.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CarModelService carModelService;
    private final TestDriveService testDriveService;
    private final ServiceAppointmentService serviceAppointmentService;
    private final CreditOfferService creditOfferService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        if (customerService.existsByUsername(customer.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "customer/register";
        }
        customerService.save(customer);
        return "redirect:/login?registered";
    }

    @GetMapping("/customer/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        model.addAttribute("customer", customer);
        model.addAttribute("testDrives", testDriveService.findByCustomerId(customer.getId()));
        model.addAttribute("appointments", serviceAppointmentService.findByCustomerId(customer.getId()));
        return "customer/dashboard";
    }

    @GetMapping("/customer/test-drive")
    public String testDriveForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        model.addAttribute("testDrive", new TestDrive());
        model.addAttribute("cars", carModelService.findAll());
        model.addAttribute("customer", customer);
        return "customer/test-drive-form";
    }

    @PostMapping("/customer/test-drive")
    public String bookTestDrive(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute TestDrive testDrive) {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        testDrive.setCustomer(customer);
        testDrive.setStatus("В ожидании");
        testDriveService.save(testDrive);
        return "redirect:/customer/dashboard?success=testdrive";
    }

    @GetMapping("/customer/service")
    public String serviceForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        model.addAttribute("appointment", new ServiceAppointment());
        model.addAttribute("customer", customer);
        return "customer/service-form";
    }

    @PostMapping("/customer/service")
    public String bookService(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute ServiceAppointment appointment) {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        appointment.setCustomer(customer);
        appointment.setStatus("В ожидании");
        serviceAppointmentService.save(appointment);
        return "redirect:/customer/dashboard?success=service";
    }

    @GetMapping("/customer/credit")
    public String creditCalculator(Model model) {
        model.addAttribute("offers", creditOfferService.findActiveOffers());
        return "customer/credit-calculator";
    }

    @PostMapping("/customer/credit/calculate")
    @ResponseBody
    public BigDecimal calculateCredit(@RequestParam BigDecimal price,
            @RequestParam BigDecimal downPayment,
            @RequestParam BigDecimal rate,
            @RequestParam Integer months) {
        return creditOfferService.calculateMonthlyPayment(price, downPayment, rate, months);
    }
}
