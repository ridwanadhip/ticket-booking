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

    @Mapping(source = "event.id", target = "id")
    @Mapping(source = "event.name", target = "name")
    EventDetail toEventDetail(Event event, VenueSummary venue);
}

