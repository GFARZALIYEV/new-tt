package stp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
@NamedEntityGraph(attributeNodes = {
        @NamedAttributeNode("userCv"),
        @NamedAttributeNode("authorities"),
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 3,message = "Ad uzunluğu 3-dən çox olmalıdır")
    private String username;

    @Size(min = 3,message = "Soyad uzunluğu 3 -dən çox olmalıdır")
    private String surname;

    @Column(unique = true)
    @Email(message = "Düzgün email formatı daxil edin")
    @NotBlank(message = "Boşluq istifadə etmək olmaz.")
    private String email;

    @Pattern(regexp = "^(\\+?994|0)(50|51|55|70|77)[0-9]{7}$", message = "Telefon nömrəsi düzgün deyil")
    private String phone;

    @NotBlank(message = "Boşluq istifadə etmək olmaz.")
    @Size(min = 8,message = "Şifrə ən az 8 simvol olmalıdır")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).+$",
            message = "Şifrə bir böyük hərf, bir rəqəm və bir xüsusi simvol daxil etməlidir"
    )
    private String password;

    @Min(value = 18, message = "18 yaşdan az olmaz")
    private Integer age;

    @Pattern(regexp = "^[A-Z]{2}\\d{7}$", message = "Seriya nömrəsi 2 hərf və 7 rəqəmdən ibarət olmalıdır (məsələn: AZ1234567)")
    private String seriaNumber;   //todo Constant olsun ya yox

    private boolean verified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCv userCv;

    @Builder.Default
    private boolean enabled = true;


    @ElementCollection
    private Set<Long> vacancyIds = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
