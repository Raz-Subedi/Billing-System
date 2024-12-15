package com.raz.billingsystem.repository;

import com.raz.billingsystem.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,Integer> {
    Package findByPname(String pname);

}
