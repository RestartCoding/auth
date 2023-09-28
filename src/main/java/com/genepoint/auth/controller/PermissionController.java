package com.genepoint.auth.controller;

import com.genepoint.auth.controller.dto.ApiResponse;
import com.genepoint.auth.controller.dto.permission.CreatePermissionDTO;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ApiResponse<Object> create(@RequestBody @Valid CreatePermissionDTO dto) {
        Permission permission = permissionService.create(dto);
        return ApiResponse.ok(Map.of("id", permission.getId()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") int id){
        permissionService.removeById(id);
        return ApiResponse.ok(null);
    }
}
