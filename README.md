
# SocialMeli (be_java_hisp_w28_g10)

This is a project corresponding to the Sprint 1 of the JAVA bootcamp for Mercado Libre, wave 28.

Mercado Libre continues to grow and for the new year has the objective of beggining with the implementation of a series of tools that allow the buyers and sellers to have an innovative experience, making their relatinship grow.
The Beta version will be known as SocialMeli. Here the buyers will be able to follow their favourite sellers and find out first about all the news related to them.


## Tech Stack

**Backend:** JAVA 21 - Spring Boot 21 - Maven



## API Reference

#### Follow a user

```http
  POST /users/{userId}/follow/{userIdToFollow}
```

| Parameter        | Type  | Description                                                  |
|:-----------------|:------|:-------------------------------------------------------------|
| `userId`         | `int` | **Required**. Number that identifies the current user        |
| `userIdToFollow` | `int` | **Required**. Number that identifies the user to be followed |

#### Get amount of followers for user

```http
  GET /users/{userId}/followers/count
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `int` | **Required**. Number that identifies the each user |

#### Get list of followers for user

```http
  GET /users/{userId}/followers/list
    /users/{UserID}/followers/list?order=name_asc
    /users/{UserID}/followers/list?order=name_desc
```

| Parameter   | Type     | Description                                        |
|:------------|:---------|:---------------------------------------------------|
| `userId`    | `int`    | **Required**. Number that identifies the each user |
| `name_asc`  | `String` | **Optional**. Ascending alphabetical order         |
| `name_desc` | `String` | **Optional**. Descending alphabetical order        |

#### Get list of users followed by an user

```http
  GET /users/{userId}/followed/list
    /users/{UserID}/followed/list?order=name_asc
    /users/{UserID}/followed/list?order=name_desc
```

| Parameter   | Type     | Description                                        |
|:------------|:---------|:---------------------------------------------------|
| `userId`    | `int`    | **Required**. Number that identifies the each user |
| `name_asc`  | `String` | **Optional**. Ascending alphabetical order         |
| `name_desc` | `String` | **Optional**. Descending alphabetical order        |

#### Post new post

```http
  POST /products/post
```

| Parameter      | Type        | Description                                                                         |
|:---------------|:------------|:------------------------------------------------------------------------------------|
| `user_id`      | `int`       | **Required**. Number that identifies the each user                                  |
| `date`         | `LocalDate` | **Required** Date of the post in format dd-MM-yyyy                                  |
| `product_id`   | `int`       | **Required** Identity number of a product associated to a publication               |
| `product_name` | `String`    | **Required** String of characters that represents the name of a product             |
| `type`         | `String`    | **Required** String of characters that represents the type of a product             |
| `brand`        | `String`    | **Required** String of characters that represents the brand of a product            |
| `color`        | `String`    | **Required** String of characters representing the color of a product               |
| `notes`        | `String`    | **Required** String of characters for placing notes or observations about a product |
| `category`     | `int`       | **Required** Identifier used to determine the category a product belongs to         |
| `price`        | `double`    | **Required** Price of the product                                                   |

#### Get all posts from the sellers a user follows from the last two weeks

```http
  GET /products/followed/{userId}/list
    /products/followed/{userId}/list?order=date_asc
    /products/followed/{userId}/list?order=date_desc
```

| Parameter   | Type     | Description                                        |
|:------------|:---------|:---------------------------------------------------|
| `user_id`   | `int`    | **Required**. Number that identifies the each user |
| `date_asc`  | `String` | **Optional**. Ascending date order                 |
| `date_desc` | `String` | **Optional**. Descending date order                |

#### Unfollow a user by another user

```http
  POST /users/{userId}/unfollow/{userIdToUnfollow}
```

| Parameter        | Type   | Description                                                  |
|:-----------------|:-------|:--------------- ----------------------------------------------|
| `userId`         | `int`  | **Required**. Number that identifies the current user        |
| `userIdToFollow` | `int`  | **Required**. Number that identifies the user to be followed |

#### Post new post with promotion

```http
  POST /products/promo-post
```

| Parameter      | Type        | Description                                                                         |
|:---------------|:------------|:------------------------------------------------------------------------------------|
| `user_id`      | `int`       | **Required**. Number that identifies the each user                                  |
| `date`         | `LocalDate` | **Required** Date of the post in format dd-MM-yyyy                                  |
| `product_id`   | `int`       | **Required** Identity number of a product associated to a publication               |
| `product_name` | `String`    | **Required** String of characters that represents the name of a product             |
| `type`         | `String`    | **Required** String of characters that represents the type of a product             |
| `brand`        | `String`    | **Required** String of characters that represents the brand of a product            |
| `color`        | `String`    | **Required** String of characters representing the color of a product               |
| `notes`        | `String`    | **Required** String of characters for placing notes or observations about a product |
| `category`     | `int`       | **Required** Identifier used to determine the category a product belongs to         |
| `price`        | `double`    | **Required** Price of the product                                                   |
| `has_promo`    | `boolean`   | **Required** True or false to determine if a product is in promotion or not         |
| `discount`     | `double`    | **Required** Stablishes the discount amount                                         |

#### Get the amount of posts with promotion from an user

```http
  GET /products/promo-post/count?user_id={userId}
```

| Parameter | Type  | Description                                        |
|:----------|:------|:---------------------------------------------------|
| `user_id` | `int` | **Required**. Number that identifies the each user |

#### Get the amount of posts with promotion from an user

```http
  GET /reports/getReport/{reportName}
```

| Parameter            | Type     | Description                                                                                      |
|:---------------------|:---------|:-------------------------------------------------------------------------------------------------|
| `USERS_BY_FOLLOWERS` | `String` | **Required**. Report name: users by how many followers the have                                  |
| `USERS_BY_FOLLOWS`   | `String` | **Required**. Report name: users by how many they follow                                         |
| `USERS_BY_POSTS`     | `String` | **Required**. Report name: users by how many posts they have                                     |
| `POSTS_BY_PRICE`     | `String` | **Required**. Report name: post by their price                                                   |
| `POSTS_BY_DISCOUNT`  | `String` | **Required**. Report name: posts by their discount                                               |
| `POSTS_BY_DATE`      | `String` | **Required**. Report name: posts by their date                                                   |
| `count_asc`          | `String` | **Required**. Ascending amount for reports USERS_BY_FOLLOWERS, USERS_BY_FOLLOWS, USERS_BY_POSTS  |
| `count_desc`         | `String` | **Required**. Descending amount for reports USERS_BY_FOLLOWERS, USERS_BY_FOLLOWS, USERS_BY_POSTS |
| `price_asc"`         | `String` | **Required**. Ascending price for report POSTS_BY_PRICE                                          |
| `price_desc"`        | `String` | **Required**. Descending price for report POSTS_BY_PRICE                                         |
| `discount_asc`       | `String` | **Required**. Ascending price for report POSTS_BY_DISCOUNT                                       |
| `discount_desc`      | `String` | **Required**. Descending price for report POSTS_BY_DISCOUNT                                      |
| `date_asc`           | `String` | **Required**. Ascending price for report POSTS_BY_DATE                                           |
| `date_desc`          | `String` | **Required**. Descending price for report POSTS_BY_DATE                                          |
| `top`                | `int`    | **Required**. Number of results expected                                                         |

## Naming rules
#### Branch name:
- feature/{nro}
  **Example** feature/SCRUM-15
#### Commits
- Format [ tk | type | description ]
- Types : [ US | FIX | FORMAT | DOCS]
  **Example** SCRUM-3 US-0001 make a follow
#### Language
-  English
#### Naming
- General -> camelCase
- DTO Mapping -> snake_case

## Documentation

[Flow chart](https://drive.google.com/file/d/1sEvryaNadPFzJY8JUjBJOYcA089dzhMN/view?usp=drive_link)

[Class diagram](https://drive.google.com/file/d/1yN03FCV8rI6Qs8tF8E_OJ0FYFJ-F5u03/view?usp=drive_link)

## Authors

- [Guillermo Kalniker](https://www.github.com/GuilleKalniker)
- [Astrid Malamud](https://www.github.com/astrid21)
- [Fermin Gonzalez](https://www.github.com/Siomermin)
- [William Nicolas Buitrago Camacho](https://www.github.com/wnicolas)
- [Eliseo David Pets Aliberto](https://www.github.com/david-aliberto)
- [David Santiago Parrado Sanchez](https://www.github.com/santiagoparrado)



