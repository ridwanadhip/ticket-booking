package id.my.ridwanadhip.ticketbooking.venue;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface VenueMapper extends Converter<Venue, VenueDTO> {
    VenueDTO convert(Venue venue);
}
