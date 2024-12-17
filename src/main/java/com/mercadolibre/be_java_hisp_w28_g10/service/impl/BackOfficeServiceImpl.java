package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.UserWithCountDTO;
import com.mercadolibre.be_java_hisp_w28_g10.enums.ReportTypeEnum;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IBackOfficeService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BackOfficeServiceImpl implements IBackOfficeService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private Utilities utilities;

    @Override
    public String getReport(String reportName, String order, int top) {

        List genericList = new ArrayList<>();

        try {
            ReportTypeEnum reportType = ReportTypeEnum.valueOf(reportName);
            if (!validateOrderByReportType(reportType, order) || top < 1) {
                throw new BadRequestException("Invalid report order or top");
            }

            switch (reportType) {
                case USERS_BY_FOLLOWERS:
                    genericList = getUsersReports(ReportTypeEnum.USERS_BY_FOLLOWERS, order, top);
                    break;
                case USERS_BY_FOLLOWS:
                    genericList = getUsersReports(ReportTypeEnum.USERS_BY_FOLLOWS, order, top);
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
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid report name");
        }

        return utilities.generateCsv(genericList);
    }

    private boolean validateOrderByReportType(ReportTypeEnum reportType, String order) {
        if (reportType == ReportTypeEnum.USERS_BY_FOLLOWERS || reportType == ReportTypeEnum.USERS_BY_FOLLOWS
                || reportType == ReportTypeEnum.USERS_BY_POSTS) {
            if (order.equalsIgnoreCase("count_asc") || order.equalsIgnoreCase("count_desc")) {
                return true;
            }
        }
        if (reportType == ReportTypeEnum.POSTS_BY_PRICE && (
                order.equalsIgnoreCase("price_asc") || order.equalsIgnoreCase("price_desc")
        )) {
            return true;
        }
        if (reportType == ReportTypeEnum.POSTS_BY_DISCOUNT && (
                order.equalsIgnoreCase("discount_asc") || order.equalsIgnoreCase("discount_desc")
        )) {
            return true;
        }
        if (reportType == ReportTypeEnum.POSTS_BY_DATE && (
                order.equalsIgnoreCase("date_asc") || order.equalsIgnoreCase("date_desc")
        )) {
            return true;
        }

        return false;
    }

    private List<UserWithCountDTO> getUsersReports(ReportTypeEnum reportType, String order, int top) {
        List<User> users = userRepository.findAllUsers();

        // Getting usersByFollowers data
        List<UserWithCountDTO> userWithCountDTOList = users.stream().map(
                        user -> {
                            List<FollowRelation> userRelations;
                            if (reportType == ReportTypeEnum.USERS_BY_FOLLOWERS) {
                                userRelations = userRepository.getFollowRelationsByFollowedId(user.getId());
                            } else {
                                userRelations = userRepository.getFollowRelationsByFollowerId(user.getId());
                            }
                            return new UserWithCountDTO(user.getId(), user.getName(), userRelations.size());
                        })
                .toList();

        // Sorting
        if (order.equalsIgnoreCase("count_asc")) {
            userWithCountDTOList = userWithCountDTOList.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .limit(top).toList();
        } else {
            userWithCountDTOList = userWithCountDTOList.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .toList().reversed().stream().limit(top).toList();
        }

        return userWithCountDTOList;
    }

}
