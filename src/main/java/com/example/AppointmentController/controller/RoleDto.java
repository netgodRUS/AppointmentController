package com.example.AppointmentController.controller;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.example.AppointmentController.security.model.Role}
 */
public class RoleDto implements Serializable {
    private final Integer id;
    private final String name;

    public RoleDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto entity = (RoleDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}