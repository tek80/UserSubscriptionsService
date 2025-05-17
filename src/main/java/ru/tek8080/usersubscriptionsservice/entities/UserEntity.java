package ru.tek8080.usersubscriptionsservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "sus")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_subs", schema = "sus",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="sub_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SubscriptionEntity> subscriptions = new HashSet<>();
}
