    package com.example.AMT.Security.Service;

    import com.example.AMT.Security.Entity.UserEntity;
    import org.jspecify.annotations.Nullable;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.List;

    public class UserPrincipal implements UserDetails {
        private final UserEntity user;
        public UserPrincipal(UserEntity user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            System.out.println("Final Authority from DB: " + user.getRole());
            // ✅ RIGHT: Use the role saved in the database!
            // We add "ROLE_" because .hasRole("ADMIN") looks for "ROLE_ADMIN"
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        }

        @Override
        public @Nullable String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername()  {
            return user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true ;
        }
    }
