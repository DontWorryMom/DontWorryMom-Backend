package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.User;
import com.backend.Models.Device;
import com.backend.Models.DeviceNotificationMethods;
import com.backend.Models.DeviceNotificationMethods.DeviceNotificationMethodsId;
import com.backend.Models.Location;
import com.backend.Repositories.DeviceNotificationMethodRepository;
import com.backend.Repositories.DeviceRepository;
import com.backend.Repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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

    public void deleteDeviceNotificationMethod(DeviceNotificationMethods dnm) {
        DeviceNotificationMethodsId id = new DeviceNotificationMethodsId(dnm.getDeviceId(), dnm.getNotificationId());
        dnmRepo.deleteById(id);
    }
}
