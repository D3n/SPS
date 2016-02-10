package com.supprojectstarter.bean;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "contributions")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@idContrib")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User contributorUser;
    @ManyToOne
    @JoinColumn(name = "project_fk")
    private Project project;
    private int amountC;

    public int getAmountC() {
        return amountC;
    }

    public void setAmountC(int amount) {
        this.amountC = amount;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getContributorUser() {
        return contributorUser;
    }

    public void setContributorUser(User contributorUser) {
        this.contributorUser = contributorUser;
    }
}
