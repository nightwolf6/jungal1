package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Booking;
import com.example.demo.Application.Model.TourPackage;
import com.example.demo.Application.Model.User;
import com.example.demo.Application.Repository.BookingRepository;
import com.example.demo.Application.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Application.Service.EmailService;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private EmailService emailService;

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    /*
    @Override
    public Booking createBooking(Booking booking) {
        // Calcula el precio total con base en el número de pasajeros y el precio por pasajero del itinerario
        double totalPrice = booking.getNumberOfPassengers() * booking.getItinerary().getPricePerPassenger();
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        booking.setBookingId(id);
        return bookingRepository.save(booking);
    }

     */

    /*
    @Override
    public Booking createBooking(Booking booking) {

        // Asegúrate de que los datos del usuario y el paquete turístico estén presentes
        if (booking.getUser() == null || booking.getTourPackage() == null) {
            throw new IllegalArgumentException("El usuario o el paquete turístico no están correctamente asignados.");
        }

        // Calcula el precio total
        double totalPrice = booking.getNumberOfPassengers() * booking.getItinerary().getPricePerPassenger();
        booking.setTotalPrice(totalPrice);

        // Guarda la reserva en la base de datos
        Booking savedBooking = bookingRepository.save(booking);

        // Notifica al administrador
        try {
            emailService.sendEmail(
                    "jungaltourecuador@gmail.com", // Dirección del administrador
                    "Nueva Reserva Creada",
                    "Una nueva reserva ha sido creada:\n\n" +
                            "Usuario: " + booking.getUser().getUsername() + "\n" +
                            "Paquete: " + booking.getTourPackage().getPackageName() + "\n" +
                            "Total: $" + booking.getTotalPrice()
            );
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }

        return savedBooking;
    }

     */

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Optional<Booking> existingBookingOpt = bookingRepository.findById(id);
        if (!existingBookingOpt.isPresent()) {
            throw new IllegalArgumentException("Booking not found with ID: " + id);
        }

        Booking existingBooking = existingBookingOpt.get();
        boolean statusChanged = !existingBooking.getStatus().equals(booking.getStatus());

        booking.setBookingId(id);
        Booking updatedBooking = bookingRepository.save(booking);

        // Si cambió el estado, notifica al usuario
        if (statusChanged) {
            try {
                emailService.sendEmail(
                        booking.getUser().getEmail(), // Correo del usuario
                        "Actualización de Reserva",
                        "Hola " + booking.getUser().getUsername() + ",\n\n" +
                                "El estado de tu reserva ha cambiado a: " + booking.getStatus() + ".\n" +
                                "Detalles de la reserva:\n" +
                                "Paquete: " + booking.getTourPackage().getPackageName() + "\n" +
                                "Total: $" + booking.getTotalPrice()
                );
            } catch (Exception e) {
                e.printStackTrace(); // Manejo de errores
            }
        }

        return updatedBooking;
    }


    @Override
    public Booking createBooking(Booking booking) {
        // Asegúrate de que los datos del usuario y el paquete turístico estén presentes
        if (booking.getUser() == null || booking.getTourPackage() == null) {
            throw new IllegalArgumentException("El usuario o el paquete turístico no están correctamente asignados.");
        }

        // Calcula el precio total
        double totalPrice = booking.getNumberOfPassengers() * booking.getItinerary().getPricePerPassenger();
        booking.setTotalPrice(totalPrice);

        // Guarda la reserva en la base de datos
        Booking savedBooking = bookingRepository.save(booking);

        // Después de guardar, asegúrate de que los objetos relacionados estén completamente cargados
        User user = savedBooking.getUser();
        TourPackage tourPackage = savedBooking.getTourPackage();

        // Verificación de los datos cargados
        System.out.println("Usuario: " + user.getUsername());  // Verifica que el usuario esté correctamente cargado
        System.out.println("Paquete: " + tourPackage.getPackageName());  // Verifica que el paquete esté correctamente cargado

        // Notifica al administrador
        try {
            emailService.sendEmail(
                    "jungaltourecuador@gmail.com", // Dirección del administrador
                    "Nueva Reserva Creada",
                    "Una nueva reserva ha sido creada:\n\n" +
                            "Usuario: " + user.getUsername() + "\n" +
                            "Paquete: " + tourPackage.getPackageName() + "\n" +
                            "Total: $" + savedBooking.getTotalPrice()
            );
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }

        return savedBooking;
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

}
