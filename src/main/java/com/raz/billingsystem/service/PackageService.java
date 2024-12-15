package com.raz.billingsystem.service;

import com.raz.billingsystem.model.Package;

import java.util.List;

public interface PackageService {

    void addPackage(Package pack);

    List<Package> getAllPackage();

    Package getPackageById(int packId);

    void updatePackage(Package pack);

    void deletePackage(int packId);

    public Package getPackageByName(String name);

}
