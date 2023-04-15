package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.HotelRoomEntity;
import com.example.holidayplanner.entities.ReservationEntity;
import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.enums.HotelRoomTypeEnum;
import com.example.holidayplanner.models.service.ReservationServiceModel;
import com.example.holidayplanner.models.view.ReservationViewModel;
import com.example.holidayplanner.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserService userService;

    @Mock
    private HotelRoomService hotelRoomService;

    private ModelMapper modelMapper = new ModelMapper();

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, userService, hotelRoomService, modelMapper);
    }

    @Test
    void chooseRooms_shouldChooseAvailableRoomAndReturnTrue() {
        ReservationServiceModel reservationServiceModel = new ReservationServiceModel();
        reservationServiceModel.setType(HotelRoomTypeEnum.SINGLE);
        reservationServiceModel.setStartDate(LocalDate.of(2023, 3, 1));
        reservationServiceModel.setEndDate(LocalDate.of(2023, 3, 3));
        Long hotelId = 1L;

        HotelRoomEntity hotelRoomEntity = new HotelRoomEntity();
        hotelRoomEntity.setId(1L);

        when(hotelRoomService.getValidRooms(reservationServiceModel.getType(), hotelId))
                .thenReturn(Collections.singletonList(hotelRoomEntity));
        when(userService.getCurrentUser()).thenReturn(new UserEntity());
        when(reservationRepository.reservationValidityCheck(reservationServiceModel.getStartDate(),
                reservationServiceModel.getEndDate(), hotelRoomEntity.getId()))
                .thenReturn(Collections.emptyList());

        boolean result = reservationService.chooseRooms(reservationServiceModel, hotelId);

        assertTrue(result);
        verify(hotelRoomService, times(1)).getValidRooms(reservationServiceModel.getType(), hotelId);
        verify(userService, times(1)).getCurrentUser();
        verify(reservationRepository, times(1)).reservationValidityCheck(reservationServiceModel.getStartDate(),
                reservationServiceModel.getEndDate(), hotelRoomEntity.getId());
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
    }

    @Test
    void chooseRooms_shouldReturnFalseWhenNoAvailableRooms() {
        ReservationServiceModel reservationServiceModel = new ReservationServiceModel();
        reservationServiceModel.setType(HotelRoomTypeEnum.SINGLE);
        reservationServiceModel.setStartDate(LocalDate.of(2023, 3, 1));
        reservationServiceModel.setEndDate(LocalDate.of(2023, 3, 3));
        Long hotelId = 1L;

        when(hotelRoomService.getValidRooms(reservationServiceModel.getType(), hotelId))
                .thenReturn(Collections.emptyList());

        boolean result = reservationService.chooseRooms(reservationServiceModel, hotelId);

        assertFalse(result);
        verify(hotelRoomService, times(1)).getValidRooms(reservationServiceModel.getType(), hotelId);
        verify(reservationRepository, never()).reservationValidityCheck(any(), any(), any());
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
    }

    @Test
    void findExpiredByUserId_shouldReturnExpiredReservations() {
        Long userId = 1L;
        ReservationEntity reservationEntity1 = new ReservationEntity();
        reservationEntity1.setId(1L);
        List<ReservationEntity> reservationEntities = Collections.singletonList(reservationEntity1);

        when(reservationRepository.findExpiredByUserId(userId, LocalDate.now()))
                .thenReturn(reservationEntities);

        List<ReservationViewModel> result = reservationService.findExpiredByUserId(userId);

        assertEquals(reservationEntities.size(), result.size());
        for (int i = 0; i < reservationEntities.size(); i++) {
            assertEquals(reservationEntities.get(i).getId(), result.get(i).getId());
        }
    }

    @Test
    void findActiveByUserId_shouldReturnActiveReservations() {
        Long userId = 1L;
        ReservationEntity reservationEntity1 = new ReservationEntity();
        reservationEntity1.setId(1L);
        List<ReservationEntity> reservationEntities = Collections.singletonList(reservationEntity1);

        when(reservationRepository.findActiveByUserId(userId, LocalDate.now()))
                .thenReturn(reservationEntities);

        List<ReservationViewModel> result = reservationService.findActiveByUserId(userId);

        assertEquals(reservationEntities.size(), result.size());
        for (int i = 0; i < reservationEntities.size(); i++) {
            assertEquals(reservationEntities.get(i).getId(), result.get(i).getId());
        }
    }

}
