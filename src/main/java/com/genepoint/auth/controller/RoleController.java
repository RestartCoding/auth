package com.genepoint.auth.controller;

import com.genepoint.auth.controller.dto.ApiResponse;
import com.genepoint.auth.controller.dto.role.CreateRoleDTO;
import com.genepoint.auth.entity.Role;
import com.genepoint.auth.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("")
    public ApiResponse<Object> create(@RequestBody @Valid CreateRoleDTO createRoleDTO) {
        Role role = roleService.create(createRoleDTO);
        return ApiResponse.ok(Map.of("id", role.getId()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") int id){
        roleService.removeById(id);
        return ApiResponse.ok(null);
    }
}