package org.sda.services;

import org.sda.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService {
    public Location getLocationById(UUID id);
    public List<Location> getAllLocation();
    public void addLocation(Location location);
    public void updateLocation(Location location);
    public void deleteLocation(Location location);
}
