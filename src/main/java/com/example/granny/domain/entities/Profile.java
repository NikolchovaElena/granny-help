package com.example.granny.domain.entities;

import com.example.granny.constants.GlobalConstants;
import org.hibernate.validator.constraints.Length;
import org.w3c.dom.Text;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

//@Entity
//@Table(name = "profiles")
public class Profile extends BaseEntity {

    private String imageUrl;
    private String about;
    //  private List<Message> messages;

    public Profile() {
        imageUrl = GlobalConstants.PROFILE_DEFAULT_IMG;
        about = GlobalConstants.ABOUT_DEFAULT_TEXT;
    }

    //@Column
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //@Column(columnDefinition = "TEXT")
    //@Length(max = 400)
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }
}
