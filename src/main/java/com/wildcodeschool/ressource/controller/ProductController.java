package com.wildcodeschool.ressource.controller;

import com.wildcodeschool.ressource.entity.*;
import com.wildcodeschool.ressource.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private CareLabelRepository careLabelRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private ImgProductRepository imgProductRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FabricPatternRepository fabricPatternRepository;
    @Autowired
    private FiberRepository fiberRepository;
    @Autowired
    private OriginRepository originRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PriceRepository priceRepository;

    @GetMapping("/results")
    public String result(Model model) {

        Pageable PageFiber = PageRequest.of(0, 12);
        Page<Fiber> FiberSub = fiberRepository.findAll(PageFiber);
        List<Fiber> mainCompo = FiberSub.get().collect(Collectors.toList());

        Pageable PageOrigin = PageRequest.of(0, 4);
        Page<Origin> originSub = originRepository.findAll(PageOrigin);
        List<Origin> origins = originSub.get().collect(Collectors.toList());

        Pageable PageSupplier = PageRequest.of(0, 4);
        Page<Company> supplierSub = companyRepository.findAll(PageSupplier);
        List<Company> suppliers = supplierSub.get().collect(Collectors.toList());

        Pageable PageCert = PageRequest.of(0, 3);
        Page<Certification> certificationSub = certificationRepository.findAll(PageCert);
        List<Certification> certifications = certificationSub.get().collect(Collectors.toList());

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("certifications", certifications);
        model.addAttribute("prices", priceRepository.findAll());
        model.addAttribute("companies", suppliers);
        model.addAttribute("origins", origins);
        model.addAttribute("compositions", mainCompo);
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("fabricPatterns", fabricPatternRepository.findAll());

        return "results";
    }

    @GetMapping("/results/more/{filter}")
    public String moreFilter(@PathVariable String filter, Model model) {
        if (filter.equals("more-origin")) {
            List<Origin> origins = originRepository.findAll();
            model.addAttribute("lists", origins.subList(4, origins.size()));
            model.addAttribute("name", "origin");
        } else if (filter.equals("more-composition")) {
            List<Fiber> fibers = fiberRepository.findAll();
            model.addAttribute("lists", fibers.subList(12, fibers.size()));
            model.addAttribute("name", "composition");
        } else if (filter.equals("more-supplier")) {
            List<Company> companies = companyRepository.findAll();
            model.addAttribute("lists", companies.subList(4, companies.size()));
            model.addAttribute("name", "supplier");
        } else if (filter.equals("more-certification")) {
            List<Certification> certifications = certificationRepository.findAll();
            model.addAttribute("lists", certifications.subList(4, certifications.size()));
            model.addAttribute("name", "certification");
        }
        return "listsToSeeMore";
    }

    @PostMapping("/results")
    public String postResult() {
        return "redirect:/results";
    }

    @GetMapping("/product")
    public String product() {

        return "product";
    }

}
