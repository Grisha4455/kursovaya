package com.autodealer.repository;

import com.autodealer.entity.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    List<CreditOffer> findByActiveTrue();
}

