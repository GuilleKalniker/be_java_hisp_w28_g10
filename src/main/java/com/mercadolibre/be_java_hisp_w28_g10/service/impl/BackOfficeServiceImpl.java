package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ResponseCsvPostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserWithCountDTO;
import com.mercadolibre.be_java_hisp_w28_g10.enums.ReportTypeEnum;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IBackOfficeService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
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
            ReportTypeEnum reportType = ReportTypeEnum.valueOf(reportName);
            if(!validateOrderByReportType(reportType, order) || top < 1){
                throw new BadRequestException("Invalid report order or top");
            }

            switch (reportType){
                case USERS_BY_FOLLOWERS:
                    csvResult = getUsersReports(ReportTypeEnum.USERS_BY_FOLLOWERS ,  order,  top);
                    return csvResult;
                case USERS_BY_FOLLOWS:
                    csvResult = getUsersReports(ReportTypeEnum.USERS_BY_FOLLOWS ,  order,  top);
                    break;
                case USERS_BY_POSTS:

                    break;
                case POSTS_BY_PRICE:
                    csvResult = getPostsByPrice(order,  top);
                    break;
                case POSTS_BY_DISCOUNT:

                    break;
                case POSTS_BY_DATE:
                    csvResult = getPostsByDate(order,  top);
                    break;
            }
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Invalid report name");
        }

        return csvResult;
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

    private String getUsersReports(ReportTypeEnum reportType, String order, int top){
        List<User> users = userRepository.findAllUsers();
        List<FollowRelation> relations = userRepository.findAllFollowRelation();
        // Getting usersByFollowers data
        List<UserWithCountDTO> userWithCountDTOList = users.stream().map(
                        user -> {
                            List<FollowRelation> userRelations;
                            if(reportType == ReportTypeEnum.USERS_BY_FOLLOWERS){
                                userRelations = userRepository.getFollowRelationsByFollowedId(user.getId());
                            }else{
                                userRelations = userRepository.getFollowRelationsByFollowerId(user.getId());
                            }
                            return new UserWithCountDTO(user.getId(), user.getName(), userRelations.size());
                        })
                .toList();
        // Sorting
        if(order.equalsIgnoreCase("count_asc")) {
            userWithCountDTOList = userWithCountDTOList.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .limit(top).toList();
        } else {
            userWithCountDTOList = userWithCountDTOList.stream()
                    .sorted(Comparator.comparing(UserWithCountDTO::getCount))
                    .toList().reversed().stream().limit(top).toList();
        }


        // Generate CSV
        StringBuilder csvContent = new StringBuilder();
        // Headers
        csvContent.append("ID,Name,FollowsAmount\n");
        // Data
        userWithCountDTOList.forEach(f -> {
            csvContent.append(f.getId()).append(',')
                    .append(f.getName()).append(',')
                    .append(f.getCount()).append("\n");
        });

        return csvContent.toString();
    }

    private List<ResponseCsvPostDTO> getPostsByPrice(String order, int top) {
        List<Post> posts = productRepository.findAllPost();
        Comparator<Post> comparator = order.equalsIgnoreCase("price_asc") ?
                Comparator.comparing(Post::getPrice) :
                Comparator.comparing(Post::getPrice).reversed();

        List<Post> sortedPosts = posts.stream()
                .sorted(comparator)
                .limit(top)
                .toList();

        return null;
    }

    private List<ResponseCsvPostDTO> getPostsByDate(String order, int top) {
        List<Post> posts = productRepository.findAllPost();

        Comparator<Post> comparator = order.equalsIgnoreCase("date_asc") ?
                Comparator.comparing(Post::getDate) :
                Comparator.comparing(Post::getDate).reversed();

        List<Post> sortedPosts = posts.stream()
                .sorted(comparator)
                .limit(top)
                .toList();

        return null;
    }


}
