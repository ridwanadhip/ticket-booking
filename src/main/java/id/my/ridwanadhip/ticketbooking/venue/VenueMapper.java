package id.my.ridwanadhip.ticketbooking.venue;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VenueMapper {
    VenueMapper INSTANCE = Mappers.getMapper( VenueMapper.class );

    VenueDTO toVenueDTO(Venue venue);
}
