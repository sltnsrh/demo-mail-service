package com.salatin.demomailservice.model;

import com.salatin.demomailservice.model.enums.EmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private EmailType type;
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @Override
    public String toString() {
        return "Log{"
            + "id=" + id
            + ", userId=" + user.getId()
            + ", type=" + type
            + ", createdOn=" + createdOn
            + '}';
    }
}
