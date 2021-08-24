package uz.pdp.jwtemailauditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.jwtemailauditapp.enitity.Role;
import uz.pdp.jwtemailauditapp.enitity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(RoleName name);
}
