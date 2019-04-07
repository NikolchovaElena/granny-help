package com.example.granny.domain.entities;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
