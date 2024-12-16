package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.enums.ReportTypeEnum;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IBackOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackOfficeServiceImpl implements IBackOfficeService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public String getReport(String reportName, String order, int top) {
        String csvResult = "";
        try{
            ReportTypeEnum reportType = ReportTypeEnum.valueOf(reportName);
            switch (reportType){
                case USERS_BY_FOLLOWERS:

                    break;
                case USERS_BY_FOLLOWS:

                    break;
                case USERS_BY_POSTS:

                    break;
                case POSTS_BY_PRICE:

                    break;
                case POSTS_BY_DISCOUNT:

                    break;
                case POSTS_BY_DATE:

                    break;
            }
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Invalid report name");
        }

        return csvResult;
    }
}
