package com.banks.loans.repository;

import com.banks.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoansRepository extends JpaRepository<Loans, Integer> {

    Optional<Loans> findByMobileNumber(String mobileNumber);
}
