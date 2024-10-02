package com.donbosco.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "user_flight",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    @JsonManagedReference
    private Set<Flight> flights = new HashSet<>();

    // Constructor privado para el Builder
    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.role = builder.role;
        this.reservations = builder.reservations;
        this.flights = builder.flights;
    }

    public static User fromJson(String username, String password, String email, ERole role) {//para el Crud
        return new Builder()
            .username(username)
            .password(password)
            .email(email)
            .role(role)
            .build();
    }
    

    // Constructor vacío
    public User() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ERole getRole() {
        return role;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    // Builder estático
    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private ERole role;
        private Set<Reservation> reservations = new HashSet<>();
        private Set<Flight> flights = new HashSet<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public Builder reservations(Set<Reservation> reservations) {
            this.reservations = reservations;
            return this;
        }

        public Builder flights(Set<Flight> flights) {
            this.flights = flights;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
    
}
