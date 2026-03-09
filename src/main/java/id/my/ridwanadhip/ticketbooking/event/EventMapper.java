package id.my.ridwanadhip.ticketbooking.event;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface EventMapper extends Converter<Event, EventDTO> {
    EventDTO convert(Event event);
}
