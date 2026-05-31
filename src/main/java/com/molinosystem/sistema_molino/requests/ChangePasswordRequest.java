package com.molinosystem.sistema_molino.requests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class ChangePasswordRequest {
    public Long id;
    public String currentPassword;
    public String newPassword;
}
