package com.rbose.onlinebanking.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.rbose.onlinebanking.entity.Appointment;
import com.rbose.onlinebanking.repository.AppointmentDao;
import com.rbose.onlinebanking.service.AppointmentService;

import java.util.List;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao appointmentDao;

    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    public Appointment findAppointment(Long id) {
        return appointmentDao.findById(id).get();
    }

    public void confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentDao.save(appointment);
    }
}
