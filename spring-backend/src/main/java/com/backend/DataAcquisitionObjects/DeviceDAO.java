package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.User;
import com.backend.Repositories.EntityDataRepositories.DeviceRepository;
import com.backend.Models.Device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DeviceDAO {
    
    DeviceRepository deviceRepository;

    @Autowired
    public DeviceDAO(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> getAllDevicesFromUser(User user) {
        return this.getAllDevicesFromUser(user.getUserId());
    }

    public List<Device> getAllDevicesFromUser(long userId) {
        return deviceRepository.findByUserId(userId);
    }

    public Device getDeviceById(long deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(device.isPresent()) {
            return device.get();
        } else {
            return null;
        }
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public void deleteDevice(long deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public Device updateDevice(Device pDevice) {
        Optional<Device> deviceOptional = deviceRepository.findById(pDevice.getDeviceId());
        if(deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.assign(pDevice);
            deviceRepository.save(device);
            return device;
        } else {
            return null;
        }
    }
}
