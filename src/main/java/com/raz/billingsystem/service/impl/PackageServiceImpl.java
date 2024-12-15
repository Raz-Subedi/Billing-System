package com.raz.billingsystem.service.impl;

import com.raz.billingsystem.model.Package;
import com.raz.billingsystem.repository.PackageRepository;
import com.raz.billingsystem.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packRepo;
    @Override
    public void addPackage(Package pack) {
        packRepo.save(pack);
    }

    @Override
    public List<Package> getAllPackage() {

        return packRepo.findAll();
    }

    @Override
    public Package getPackageById(int packId) {
        return packRepo.findById(packId).get();
    }

    @Override
    public void updatePackage(Package pack) {
        packRepo.save(pack);
    }

    @Override
    public void deletePackage(int packId) {
        packRepo.deleteById(packId);
    }

    @Override
    public Package getPackageByName(String name) {
        return packRepo.findByPname(name);
    }
}
