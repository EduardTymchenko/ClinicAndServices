package com.example.clinic.repositories.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Optional<Clinic> create(Clinic clinic) {
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
    public Optional<Clinic> getById(long id) {
        try {
            Clinic clinic = jdbcTemplate.queryForObject("select * from clinics  where id = ? ", clinicRowMapper, id);
            return Optional.ofNullable(clinic);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Clinic> update(Clinic clinic) {
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

    @Override
    public Set<String> getNamesClinicInsurance(boolean hasInsurance) {
        return getAll().stream().filter(clinic -> clinic.isHasInsurance() == hasInsurance )
                .map(Clinic::getName).collect(Collectors.toSet());
    }

    @Override
    public List<Clinic> getAllByText(String searchText, Integer pageNumber, Integer pageSize) {
        return jdbcTemplate.query("select * from clinics c where " +
                        "lower(cast(c.id as varchar)) like lower(concat('%',?0,'%')) or " +
                        "lower(c.name) like lower(concat('%',?0,'%')) or " +
                        "lower(c.location) like lower(concat('%',?0,'%')) or " +
                        "lower(c.phone) like lower(concat('%',?0,'%')) or " +
                        "lower(cast( c.has_insurance as varchar)) like lower(concat('%',?0,'%')) or " +
                        "lower(cast(c.doctors as varchar))  like lower(concat('%',?0,'%')) or " +
                        "lower(case c.type when 0 then 'private' when 1 then 'public' end) like lower(concat('%',?0,'%')) " +
                        "order by c.id limit ?1 offset ?2", clinicRowMapper,
                searchText == null ? "" : searchText, pageSize, pageSize == null || pageNumber == null ||  pageNumber <= 0  ? 0 : pageNumber * pageSize + 1);
    }
}
