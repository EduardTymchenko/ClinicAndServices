package com.example.clinic.repositories.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ClinicRepositoryImpl implements ClinicRepository {

    private final RowMapper<Clinic> clinicRowMapper;
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public ClinicRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Clinic> clinicRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clinicRowMapper = clinicRowMapper;
    }

    @Override
    public Clinic create(Clinic clinic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into clinics (name, location, phone, type, has_insurance, doctors) values (?,?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, clinic.getName());
            ps.setString(2, clinic.getLocation());
            ps.setString(3, clinic.getPhone());
            ps.setInt(4, clinic.getType().ordinal());
            ps.setBoolean(5, clinic.isHasInsurance());
            ps.setInt(6, clinic.getDoctors());
            return ps;
        }, keyHolder);
        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Clinic> getAll() {
        return jdbcTemplate.query("select * from clinics", clinicRowMapper);
    }

    @Override
    public Clinic getById(long id) {
        return jdbcTemplate.queryForObject("select * from clinics  where id = ? ", clinicRowMapper, id);
    }

    @Override
    public Clinic update(Clinic clinic) {
        jdbcTemplate.update("update clinics set name = ?, location = ?, phone = ?, has_insurance = ?," +
                        "doctors = ?, type = ? where id = ?",
                clinic.getName(), clinic.getLocation(), clinic.getPhone(), clinic.isHasInsurance(),
                clinic.getDoctors(), clinic.getType().ordinal(), clinic.getId());
        return getById(clinic.getId());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from clinics where id = ?", id);
    }

    @Override
    public int getNumberOfClinics() {
        Integer quantity = jdbcTemplate.queryForObject("select count(*) from clinics", Integer.class);
        return quantity == null ? 0 : quantity;
    }

}
