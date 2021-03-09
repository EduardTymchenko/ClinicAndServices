package com.example.clinic.repositories.impl;

import com.example.clinic.domain.Service;
import com.example.clinic.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private final RowMapper<Service> serviceRowMapper;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServiceRepositoryImpl(RowMapper<Service> serviceRowMapper, JdbcTemplate jdbcTemplate) {
        this.serviceRowMapper = serviceRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Service create(Service service) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into services (name, fee, coverage, time, clinic_id) values (?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, service.getName());
            ps.setFloat(2, service.getFee());
            ps.setInt(3, service.getCoverage());
            ps.setString(4, service.getTime());
            ps.setLong(5, service.getClinicId());
            return ps;
        }, keyHolder);
        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Service> getAll() {
        return jdbcTemplate.query("select * from services", serviceRowMapper);
    }

    @Override
    public Service getById(long id) {
        return jdbcTemplate.queryForObject("select * from services  where id = ? ", serviceRowMapper, id);
    }

    @Override
    public Service update(Service service) {
        jdbcTemplate.update("update services set name = ?, fee = ?, coverage = ?, time = ?, clinic_id = ? where id = ?",
                service.getName(), service.getFee(), service.getCoverage(), service.getTime(), service.getClinicId(), service.getId());
        return getById(service.getId());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from services where id = ?", id);
    }

    @Override
    public int getNumberOfServices() {
        Integer quantity = jdbcTemplate.queryForObject("select count(*) from services", Integer.class);
        return quantity == null ? 0 : quantity;
    }
}
