# Granny-Help

A simple Spring-MVC based project for programming course at SoftUni.
Granny-Help is an application for volunteers who want to help the elderly

## Key Functionalities

Two main sections:
* Causes section
Anonymous users can only view page.
Registered users can submit, follow/unfollow and comment on causes.
Moderators can CRUD causes, comments.

* Shop section
Anyone can make an order.
Anonymous users need to fill address form. 
Moderators can CRUD products. 

Verification email is send after registration.
Expired tokens, !enabled users are scheduled to be deleted.

Admin can delete, change user role.
Moderators view, delete messages recieved by contact form.
User can CRUD profile.

## Built With

* Java 11.0.2
* [Spring-MVC](https://spring.io/) 
* Spring security
* Javascript
* [Maven](https://maven.apache.org/)
* [Thymeleaf](https://www.thymeleaf.org/)
* [MySQL](https://www.mysql.com/)

Other tools:
* JavaMail
* ModelMapper
* Cloudinary - cloud-based image and video management platform.
* Lombok

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Theme by [Colorlib](https://colorlib.com/)

