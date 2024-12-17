package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.service.IBackOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports/")
public class BackOfficeController {

    @Autowired
    IBackOfficeService backOfficeService;

    @GetMapping("getReport/{reportName}")
    public ResponseEntity<String> getReport(@PathVariable String reportName,
                                                   @RequestParam String order,
                                                   @RequestParam int top) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ reportName +".csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(backOfficeService.getReport(reportName, order, top), headers, HttpStatus.OK);
    }
}
