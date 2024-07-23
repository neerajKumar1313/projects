package com.example.fabricshoppingcart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;


import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<Item> items;
}

