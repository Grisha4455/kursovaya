package com.autodealer.controller;

import com.autodealer.entity.CarModel;
import com.autodealer.entity.CustomerRequest;
import com.autodealer.entity.PriceList;
import com.autodealer.service.CarModelService;
import com.autodealer.service.CustomerRequestService;
import com.autodealer.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PublicController {

    private final CarModelService carModelService;
    private final PriceListService priceListService;
    private final CustomerRequestService customerRequestService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<CarModel> featuredCars = carModelService.findAll();
        model.addAttribute("featuredCars", featuredCars);
        model.addAttribute("isAuthenticated", userDetails != null);
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            boolean isCustomer = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));
            model.addAttribute("isCustomer", isCustomer);
        }
        return "public/index";
    }

    @GetMapping("/catalog")
    public String catalog(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) Integer doors,
            @RequestParam(required = false) Integer minPower,
            @RequestParam(required = false) Integer maxPower,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        brand = (brand != null && brand.isEmpty()) ? null : brand;
        color = (color != null && color.isEmpty()) ? null : color;
        transmission = (transmission != null && transmission.isEmpty()) ? null : transmission;

        List<CarModel> cars = carModelService.findByFilters(
                brand, color, transmission, doors, minPower, maxPower);
        model.addAttribute("cars", cars);
        model.addAttribute("isAuthenticated", userDetails != null);
        if (userDetails != null) {
            boolean isCustomer = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));
            model.addAttribute("isCustomer", isCustomer);
        }
        return "public/catalog";
    }

    @GetMapping("/catalog/{id}")
    public String carDetails(@PathVariable Long id, Model model) {
        CarModel car = carModelService.findById(id);
        List<PriceList> prices = priceListService.findByCarModelId(id);
        model.addAttribute("car", car);
        model.addAttribute("prices", prices);
        return "public/car-details";
    }

    @GetMapping("/request")
    public String requestForm(Model model) {
        model.addAttribute("request", new CustomerRequest());
        model.addAttribute("cars", carModelService.findAll());
        return "public/request-form";
    }

    @PostMapping("/request")
    public String submitRequest(@ModelAttribute CustomerRequest request, Model model) {
        request.setStatus("NEW");
        customerRequestService.save(request);
        model.addAttribute("success", true);
        return "public/request-success";
    }

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }
}
