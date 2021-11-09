package com.backend.DataAcquisitionObjects;

import java.util.List;

import com.backend.Models.DeviceNotificationMethods;
import com.backend.Repositories.DeviceNotificationMethodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DeviceNotificationMethodDAO {
    
    DeviceNotificationMethodRepository dnmRepo;

    @Autowired
    public DeviceNotificationMethodDAO(DeviceNotificationMethodRepository dnmRepo) {
        this.dnmRepo = dnmRepo;
    }

    public List<DeviceNotificationMethods> getAllDeviceNotificationMethods() {
        return dnmRepo.findAll();
    }

    public DeviceNotificationMethods createDeviceNotificationMethod(DeviceNotificationMethods dnm) {
        return dnmRepo.save(dnm);
    }

    @Transactional
    public void deleteDeviceNotificationMethod(DeviceNotificationMethods dnm) {
        dnmRepo.deleteByDeviceIdAndNotificationId(dnm.getDeviceId(), dnm.getNotificationId());
    }
}
