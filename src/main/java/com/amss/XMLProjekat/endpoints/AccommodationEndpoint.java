package com.amss.XMLProjekat.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.beans.Agent;
import com.amss.XMLProjekat.beans.Message;
import com.amss.XMLProjekat.beans.Reservation;
import com.amss.XMLProjekat.beans.Restriction;
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.soap.*;
import com.amss.XMLProjekat.repository.AccomodationRepo;
import com.amss.XMLProjekat.repository.AgentRepo;
import com.amss.XMLProjekat.repository.MessageRepo;
import com.amss.XMLProjekat.repository.ReservationRepo;
import com.amss.XMLProjekat.repository.RestrictionRepo;
import com.amss.XMLProjekat.repository.UserRepo;

@Endpoint
public class AccommodationEndpoint {
	private static final String NAMESPACE = "http://amss.com/XMLProjekat/dto/soap";
	
	@Autowired
	AccomodationRepo accomodationRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AgentRepo agentRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RestrictionRepo restrictionRepo;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	MessageRepo messageRepo;
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "createAccommodationRequest")
	@ResponsePayload
	public CreateAccommodationResponse createAccommodation(@RequestPayload CreateAccommodationRequest create) {
		CreateAccommodationResponse response = new CreateAccommodationResponse();
		response.setSuccess(false);
		Accommodation accommodation = mapper.map(create, Accommodation.class);
		Optional<Agent> agent = agentRepo.findById(create.getAgentId());
		if(agent.isPresent()) {
			accommodation.setAgent(agent.get());
			accommodation = accomodationRepo.save(accommodation);
			response.setAccomodation(mapper.map(accommodation, AccommodationView.class));
			response.setSuccess(true);
		}
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE, localPart = "restrictionRequest")
	@ResponsePayload
	public RestrictionResponse restrict(@RequestPayload RestrictionRequest request) {
		RestrictionResponse response = new RestrictionResponse();
		response.setSuccess(false);
		Optional<Accommodation> accommodation = accomodationRepo.findById(request.getAccommodationId());
		if(accommodation.isPresent()) {
			Restriction newRestriction = new Restriction();
			newRestriction.setRestrictionFrom(request.getRestrictionFrom().toGregorianCalendar().getTime());
			newRestriction.setRestrictionTo((request.getRestrictionTo().toGregorianCalendar().getTime()));
			newRestriction.setAccommodation(accommodation.get());
			newRestriction = restrictionRepo.save(newRestriction);
			accommodation.get().getRestrictions().add(newRestriction);
			response.setAccomodation(mapper.map(accommodation.get(), AccommodationView.class));
			response.setSuccess(true);
		}
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE, localPart = "acceptReservationRequest")
	@ResponsePayload
	public AcceptReservationResponse acceptReservation(@RequestPayload AcceptReservationRequest request) {
		AcceptReservationResponse response = new AcceptReservationResponse();
		response.setSuccess(false);
		Optional<Reservation> reservationOpt = reservationRepo.findById(request.getReservationId());
		if(reservationOpt.isPresent()) {
			reservationOpt.get().setConfirmed(request.isConfirmed());
			Reservation reservation = reservationRepo.save(reservationOpt.get());
			response.setReservations(mapper.map(reservation, ReservationView.class));
			response.setSuccess(true);
		}
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE, localPart = "getReservationsRequest")
	@ResponsePayload
	public GetReservationsResponse getReservations(@RequestPayload GetReservationsRequest request) {
		GetReservationsResponse response = new GetReservationsResponse();
		response.getReservations().addAll(reservationRepo.findByAccommodationId(request.getAccomodationId())
				.stream()
				.map(r -> mapper.map(r, ReservationView.class))
				.collect(Collectors.toList()));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "getInboxRequest")
	@ResponsePayload
	public  GetInboxResponse getInbox(@RequestPayload GetInboxRequest request) {
		GetInboxResponse response = new GetInboxResponse();
		ArrayList<MessageView> messages = new ArrayList<>();
		messageRepo.findByToUserId(request.getAgentId()).forEach(m -> messages.add(mapper.map(m, MessageView.class)));
		response.getMessages().addAll(messages);
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE, localPart = "getSentboxRequest")
	@ResponsePayload
	public GetSentboxResponse getSentbox(@RequestPayload GetSentboxRequest request) {
		GetSentboxResponse response = new GetSentboxResponse();
		ArrayList<MessageView> messages = new ArrayList<>();
		messageRepo.findByFromUserId(request.getAgentId()).forEach(m -> messages.add(mapper.map(m, MessageView.class)));
		response.getMessages().addAll(messages);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "getMessagesRequest")
	@ResponsePayload
	public GetMessagesResponse getMessagesByReservation(@RequestPayload GetMessagesRequest request) {
		GetMessagesResponse response = new GetMessagesResponse();
		ArrayList<MessageView> messages = new ArrayList<>();
		messageRepo.findByReservationId(request.getReservationId()).forEach(m -> messages.add(mapper.map(m, MessageView.class)));
		response.getMessages().addAll(messages);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "sendMessageRequest")
	@ResponsePayload
	public SendMessageResponse getMessagesByReservation(@RequestPayload SendMessageRequest request) {
		SendMessageResponse response = new SendMessageResponse();
		response.setSuccess(false);
		Message message = new Message();
		message.setContent(request.getContent());
		Optional<User> fromUser = userRepo.findById(request.getAgentId());
		Optional<User> toUser = userRepo.findById(request.getReceiverId());
		Optional<Reservation> reservation = reservationRepo.findById(request.getReservationId());
		if(fromUser.isPresent() && toUser.isPresent() && reservation.isPresent()) {
			message.setFromUser(fromUser.get());
			message.setToUser(toUser.get());
			message.setReservation(reservation.get());
			response.setSuccess(true);
		}
		return response;
	}
	
}
