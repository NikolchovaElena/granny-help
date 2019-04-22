package com.example.granny.domain.entities;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.ValidEmail;
import com.example.granny.validation.annotations.ValidFirstName;
import com.example.granny.validation.annotations.ValidLastName;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String imageUrl;
    private String about;
    private boolean isEnabled;
    private Set<Role> authorities;
    private Set<Cause> pins;
    private AddressDetails billingDetails;

    public User() {
        this.authorities = new HashSet<>();
    }

    @PrePersist
    public void prePersist() {
        if (imageUrl == null) {
            imageUrl = GlobalConstants.PROFILE_DEFAULT_IMG;
        }
        if (about == null) {
            about = GlobalConstants.ABOUT_DEFAULT_TEXT;
        }
    }

    @ValidFirstName
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ValidLastName
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ValidEmail
    @Column(nullable = false, unique = true, updatable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @Transient
    public String getUsername() {
        return email;
    }

    @Column
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(columnDefinition = "TEXT")
    @Length(max = 300)
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @ManyToMany(mappedBy = "followers")
    public Set<Cause> getPins() {
        return pins;
    }

    public void setPins(Set<Cause> pins) {
        this.pins = pins;
    }

    @OneToOne
    public AddressDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(AddressDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


}
