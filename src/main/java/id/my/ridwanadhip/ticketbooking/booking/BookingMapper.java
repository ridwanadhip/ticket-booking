package id.my.ridwanadhip.ticketbooking.booking;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface BookingMapper extends Converter<Booking, BookingDTO> {
    BookingDTO convert(Booking booking);
}
