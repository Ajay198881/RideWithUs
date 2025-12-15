package com.robo.RideWithUs.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robo.RideWithUs.Entity.Bookings;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Integer>{

	// For completed rides (multiple)
    List<Bookings> findByCustomerMobileNumberAndBookingStatus(long mobileNo, String bookingStatus);

    // For active ride (only one)
    Bookings findFirstByCustomerMobileNumberAndBookingStatus(long mobileNo, String bookingStatus);

    // Driver booking history
    List<Bookings> findByVehicle_Driver_MobileNumberAndBookingStatus(long mobileNo, String bookingStatus);
    
}


