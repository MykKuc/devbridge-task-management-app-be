package com.BESourceryAdmissionTool.user.model;

import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Length(min = 1, max = 50, message = "Incorrect Length of a name")
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")
    @Length(min = 1, max = 50, message = "Incorrect Length of an email.")
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9]).{6,}")
    @Length(min = 1, max = 70, message = "Incorrect Length of a password.")
    @JsonIgnore
    private String password;

    private String token;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<TaskVote> votes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
