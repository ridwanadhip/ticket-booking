package id.my.ridwanadhip.ticketbooking.event;

import id.my.ridwanadhip.ticketbooking.venue.VenueSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    EventDTO toEventDTO(Event event);
    EventDetail toEventDetail(Event event);

    @Mapping(source = "venueSummary.id", target = "venue.id")
    @Mapping(source = "venueSummary.name", target = "venue.name")
    @Mapping(source = "venueSummary.city", target = "venue.city")
    @Mapping(source = "venueSummary.country", target = "venue.country")
    EventDetail toEventDetail(Event event, VenueSummary venueSummary);
}

