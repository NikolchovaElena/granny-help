package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import javax.validation.constraints.NotNull;

public class CommentBindingModel  {

    private String comment;

    public CommentBindingModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
