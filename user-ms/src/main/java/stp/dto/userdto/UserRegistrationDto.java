package stp.dto.userdto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto {

    @Size(min = 3,message = "Ad uzunluğu 3-dən çox olmalıdır")
    private String name;

    @Size(min = 3,message = "Soyad uzunluğu 3 -dən çox olmalıdır")
    private String surname;

    @Column(unique = true)
    @Email(message = "Düzgün email formatı daxil edin")
    @NotBlank(message = "Boşluq istifadə etmək olmaz.")
    private String email;

    @NotBlank(message = "Boşluq istifadə etmək olmaz.")
    @Size(min = 8,message = "Şifrə ən az 8 simvol olmalıdır")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).+$",
            message = "Şifrə bir böyük hərf, bir rəqəm və bir xüsusi simvol daxil etməlidir"
    )
    private String password;

    @NotBlank(message = "Təkrar şifrə boş ola bilməz.")
    private String confirmPassword;

    @Pattern(regexp = "^(\\+?994|0)(50|51|55|70|77)[0-9]{7}$", message = "Telefon nömrəsi düzgün deyil")
    private String phone;

    @Pattern(regexp = "^[A-Z]{2}\\d{7}$", message = "Seriya nömrəsi 2 hərf və 7 rəqəmdən ibarət olmalıdır (məsələn: AZ1234567)")
    private String seriaNumber;   //todo Constant olsun ya yox
}
