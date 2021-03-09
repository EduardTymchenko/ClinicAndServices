package com.example.clinic.repositories.impl;

import com.example.clinic.domain.Service;
import com.example.clinic.repositories.ServiceRepository;
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
public class ServiceRepositoryImpl implements ServiceRepository {

    private final RowMapper<Service> serviceRowMapper;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServiceRepositoryImpl(RowMapper<Service> serviceRowMapper, JdbcTemplate jdbcTemplate) {
        this.serviceRowMapper = serviceRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Service> create(Service service) {
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
    public Optional<Service> getById(long id) {
        try{
            Service service = jdbcTemplate.queryForObject("select * from services  where id = ? ", serviceRowMapper, id);
            return Optional.ofNullable(service);
        }
        catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Service> update(Service service) {
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

    @Override
    public Set<String> getNamesByFee(float minFee, float maxFee) {
        return getAll().stream().filter((service -> service.getFee() >= minFee && service.getFee() <= maxFee))
                .map(service -> service.getName()).collect(Collectors.toSet());
    }
}
