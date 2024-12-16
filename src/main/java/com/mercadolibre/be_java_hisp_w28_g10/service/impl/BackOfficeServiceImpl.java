package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.UserWithCountDTO;
import com.mercadolibre.be_java_hisp_w28_g10.enums.ReportTypeEnum;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IBackOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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
            // General Validations
            ReportTypeEnum reportType = ReportTypeEnum.valueOf(reportName);
            if(!validateOrderByReportType(reportType, order) || top < 1){
                throw new BadRequestException("Invalid report order or top");
            }

            switch (reportType){
                case USERS_BY_FOLLOWERS:
                    csvResult = getUsersByFollowers(order, top);
                    return csvResult;
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

    private String getUsersByFollowers(String order, int top) {
        List<User> users = userRepository.findAllUsers();

        // Getting usersByFollowers data
        List<UserWithCountDTO> usersByFollowers = users.stream().map(
                user -> {
                    List<FollowRelation> userRelations = userRepository.getFollowRelationsByFollowedId(user.getId());
                    return new UserWithCountDTO(user.getId(), user.getName(), userRelations.size());
                })
                .toList();

        // Sorting
        if(order.equalsIgnoreCase("count_asc")) {
            usersByFollowers = usersByFollowers.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .toList();
        } else {
            usersByFollowers = usersByFollowers.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .toList().reversed();
        }

        // Filter top 'X' items
        usersByFollowers = usersByFollowers.stream().limit(top).toList();

        // Generate CSV
        StringBuilder csvContent = new StringBuilder();
        // Headers
        csvContent.append("ID,Name,FollowersAmount\n");
        // Data
        usersByFollowers.forEach(f -> {
            csvContent.append(f.getId()).append(',')
                      .append(f.getName()).append(',')
                      .append(f.getCount()).append("\n");
        });

        return csvContent.toString();
    }

    private boolean validateOrderByReportType(ReportTypeEnum reportType, String order){
        if(reportType == ReportTypeEnum.USERS_BY_FOLLOWERS || reportType == ReportTypeEnum.USERS_BY_FOLLOWS
                || reportType == ReportTypeEnum.USERS_BY_POSTS){
            if(order.equalsIgnoreCase("count_asc") || order.equalsIgnoreCase("count_desc")){
                return true;
            }
        }
        if(reportType == ReportTypeEnum.POSTS_BY_PRICE && (
                order.equalsIgnoreCase("price_asc") || order.equalsIgnoreCase("price_desc")
        )){
            return true;
        }
        if(reportType == ReportTypeEnum.POSTS_BY_DISCOUNT && (
                order.equalsIgnoreCase("discount_asc") || order.equalsIgnoreCase("discount_desc")
        )){
            return true;
        }
        if(reportType == ReportTypeEnum.POSTS_BY_DATE && (
                order.equalsIgnoreCase("date_asc") || order.equalsIgnoreCase("date_desc")
        )){
            return true;
        }

        return false;
    }
}
