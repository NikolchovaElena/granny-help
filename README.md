# Granny-Help

A simple Spring-MVC based project for programming course at SoftUni.<br />
Granny-Help is an application for volunteers who want to help the elderly

## Key Functionalities

Two main sections:
* <b>Causes section</b><br />
Anonymous users can only view page.<br />
Registered users can submit, follow/unfollow and comment on causes.<br />
Moderators can CRUD causes, comments.

* <b>Shop section</b><br />
Anyone can make an order.<br />
Anonymous users need to fill address form. <br /><br />
Moderators can CRUD products. 

Verification email is send after registration.<br />
Expired tokens, !enabled users are scheduled to be deleted.

Admin can delete, change user role.<br />
Moderators view, delete messages recieved by contact form.<br />
User can CRUD profile.

## Built With

* Java 11.0.2
* Spring-MVC
* Maven
* Thymeleaf
* Javascript
* MySQL

Other tools:
* JavaMail
* ModelMapper
* Cloudinary - cloud-based image and video management platform.
* Lombok

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Theme by [Colorlib](https://colorlib.com/)

